package hackerstolz.de.instact.p2p;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.uepaa.p2pkit.ConnectionCallbacks;
import ch.uepaa.p2pkit.ConnectionResult;
import ch.uepaa.p2pkit.ConnectionResultHandling;
import ch.uepaa.p2pkit.KitClient;
import ch.uepaa.p2pkit.discovery.InfoTooLongException;
import ch.uepaa.p2pkit.discovery.P2pListener;
import ch.uepaa.p2pkit.discovery.Peer;
import ch.uepaa.p2pkit.discovery.PeersContract;
import ch.uepaa.p2pkit.messaging.MessageListener;
import hackerstolz.de.instact.ImageUtils;
import hackerstolz.de.instact.data.Contact;
import hackerstolz.de.instact.data.Label;

/**
 * Created by muszy on 26-Sep-15.
 */
public class P2pKitDataProvider {

    private static final String TAG = P2pKitDataProvider.class.getName();
    private static final String P2P_KIT_APP_KEY = "eyJzaWduYXR1cmUiOiI0cDRjZzFqUENPQm1BRTFQTis3dm1PZjBiQ0hHU2lueVNWZEdaVlNSUTVPVzJMRENQMjA5S01YSzdueTJKdGRPRXVmc213MmVGZ3NrVEJXakFlM2F1eTdIQklNTXkrMC81RitwbTlBL0p5QjJhVkxmZEZNaEd3UWo2c3EyRDZ4dWRhUkdxODJzNkRaeDFWVnFHN3pwWlpKNHZMU2xrcUVTLytWUUtXWGE3ODA9IiwiYXBwSWQiOjEyNjAsInZhbGlkVW50aWwiOjE2Nzk0LCJhcHBVVVVJRCI6IkMxNzcxQThFLTk0ODYtNDNFRS05NTgxLThBMDUwMzZFMUY4RCJ9";
    private static final String TYPE_LABELS="LABELS";
    private static final String TYPE_IMAGE="IMAGE";
    private Context mContext;
    private static Gson gson = new Gson();
    private ConnectionListener mConnectionListener;
    private final MessageListener mMessageListener;

    {
        mMessageListener = new MessageListener() {
            @Override
            public void onStateChanged(int state) {
                Log.d(TAG, "MessageListener | State changed: " + state);
            }

            @Override
            public void onMessageReceived(long timestamp, UUID origin, String type, byte[] message) {
                if (type.equals( TYPE_LABELS)) {

                    try {
                        Contact contact = Contact.get(origin.toString());
                        Type t = String[].class;
                        String labels[]=gson.fromJson(new String(message), t);
                        List<String> oldLabels=contact.labelList();
                        for (String label : labels) {
                            Label l = new Label(label, contact);
                            if(!oldLabels.contains(label))
                            l.save();
                        }
                    }catch (Exception e){
                        Log.e(TAG,""+e.getMessage());
                    }

                }
                if (type.equals( TYPE_IMAGE)) {
                   ImageUtils.saveImageAsBase64(new String(message),origin.toString());

                }
                Log.d(TAG, "MessageListener | Message received: From=" + origin + " type=" + type + " message=" + new String(message).substring(0,new String(message).length()<30?new String(message).length():30));
            }
        };
    }

    public P2pKitDataProvider(Context context, ConnectionListener connectionListener) {
        mContext = context;
        mConnectionListener = connectionListener;
    }

    public void init() {
        final int statusCode = KitClient.isP2PServicesAvailable(mContext);
        Log.d(TAG, "kit client status code: " + statusCode);
        if (statusCode == ConnectionResult.SUCCESS) {
            KitClient client = KitClient.getInstance(mContext);
            client.registerConnectionCallbacks(new ConnectionCallbacks() {
                @Override
                public void onConnected() {
                    Log.d(TAG, "p2p kit connected");
                    addP2pListener();
                    setDiscoveryInfo();
                    mConnectionListener.onConnected();
                }

                @Override
                public void onConnectionSuspended() {
                    Log.d(TAG, "p2p kit suspended");
                }

                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    Log.d(TAG, "Connection failed: " + connectionResult.getStatusCode());
                }
            });

            if (client.isConnected()) {
                Log.d(TAG, "Client already initialized");
            } else {
                Log.d(TAG, "Connecting P2PKit client");
                client.connect(P2P_KIT_APP_KEY);
            }

        } else {
            ConnectionResultHandling.showAlertDialogForConnectionError(mContext, statusCode);
        }
    }

    public List<UUID> getCurrentPeerIds() {
        List<UUID> currentPeers = new ArrayList<>();
        if(KitClient.getInstance(mContext).isConnected()) {
            Uri peersContentUri = KitClient.getInstance(mContext).getPeerContentUri();

            ContentResolver contentResolver = mContext.getContentResolver();
            Cursor cursor = contentResolver.query(peersContentUri, null, null, null, null);
            int nodeIdColumnIndex = cursor.getColumnIndex(PeersContract.NODE_ID);

            while (cursor.moveToNext()) {
                UUID nodeId = UUID.fromString(cursor.getString(nodeIdColumnIndex));
                currentPeers.add(nodeId);
            }
            cursor.close();
        }

        return currentPeers;
    }

    private void addP2pListener() {
        Log.d(TAG, "add p2p listener");
        KitClient.getInstance(mContext).getDiscoveryServices().addListener(new P2pListener() {
            @Override
            public void onStateChanged(final int state) {
                Log.d(TAG, "P2pListener | State changed: " + state);
            }

            @Override
            public void onPeerDiscovered(final Peer peer) {
                updateData(peer);
            }

            private void updateData(final Peer peer){
                String info = "NO_INFO";
                if (peer.getDiscoveryInfo() != null && peer.getDiscoveryInfo().length > 0) {
                    info = new String(peer.getDiscoveryInfo());
                    Contact contact = new Contact(info, "xing", peer.getNodeId().toString());
                    if(Contact.get(peer.getNodeId().toString())==null) {
                        contact.save();
                        mConnectionListener.onNewContact(contact);
                    }
                }

                String json = gson.toJson(Contact.get("ME").labelList());
                boolean forwarded = KitClient.getInstance(mContext).getMessageServices().sendMessage(peer.getNodeId(),
                        TYPE_LABELS, json.getBytes());
                Log.d(TAG, "P2pListener | labels send: " + json + " to: " + peer.getNodeId() + " success: " + forwarded);
                forwarded = KitClient.getInstance(mContext).getMessageServices().sendMessage(peer.getNodeId(),
                        TYPE_IMAGE, ImageUtils.loadImageAsBase64("ME").getBytes());
                Log.d(TAG, "P2pListener | image send: " + ImageUtils.loadImageAsBase64("ME").substring(0,new String(ImageUtils.loadImageAsBase64("ME")).length()<30?new String(ImageUtils.loadImageAsBase64("ME")).length():30) + " to: " + peer.getNodeId() + " success: " + forwarded);
                Log.d(TAG, "P2pListener | Peer discovered: " + peer.getNodeId() + " with info: " + info);

            }

            @Override
            public void onPeerLost(final Peer peer) {
                Log.d(TAG, "P2pListener | Peer lost: " + peer.getNodeId());
            }

            @Override
            public void onPeerUpdatedDiscoveryInfo(Peer peer) {
                String info = "NO_INFO";
                if (peer.getDiscoveryInfo() != null && peer.getDiscoveryInfo().length > 0) {
                    info = new String(peer.getDiscoveryInfo());
                }
                Log.d(TAG, "P2pListener | Peer updated: " + peer.getNodeId() + " with new info: " + info);
                updateData(peer);
            }
        });
        KitClient.getInstance(mContext).getMessageServices().addListener(mMessageListener);
    }

    private void setDiscoveryInfo() {
        try {
            KitClient.getInstance(mContext).getDiscoveryServices().setP2pDiscoveryInfo((""+Contact.get("ME").name).getBytes());
        } catch (InfoTooLongException e) {
            Log.d(TAG, "P2pListener | The discovery info is too long");
        }
    }
}

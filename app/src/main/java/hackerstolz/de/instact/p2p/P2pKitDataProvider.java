package hackerstolz.de.instact.p2p;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

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
import hackerstolz.de.instact.data.Contact;

/**
 * Created by muszy on 26-Sep-15.
 */
public class P2pKitDataProvider {

    private static final String TAG = P2pKitDataProvider.class.getName();
    private static final String P2P_KIT_APP_KEY = "eyJzaWduYXR1cmUiOiI0cDRjZzFqUENPQm1BRTFQTis3dm1PZjBiQ0hHU2lueVNWZEdaVlNSUTVPVzJMRENQMjA5S01YSzdueTJKdGRPRXVmc213MmVGZ3NrVEJXakFlM2F1eTdIQklNTXkrMC81RitwbTlBL0p5QjJhVkxmZEZNaEd3UWo2c3EyRDZ4dWRhUkdxODJzNkRaeDFWVnFHN3pwWlpKNHZMU2xrcUVTLytWUUtXWGE3ODA9IiwiYXBwSWQiOjEyNjAsInZhbGlkVW50aWwiOjE2Nzk0LCJhcHBVVVVJRCI6IkMxNzcxQThFLTk0ODYtNDNFRS05NTgxLThBMDUwMzZFMUY4RCJ9";

    private Context mContext;
    private ConnectionListener mConnectionListener;

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
                String info = "NO_INFO";
                if(peer.getDiscoveryInfo() != null && peer.getDiscoveryInfo().length > 0) {
                    info = new String(peer.getDiscoveryInfo());
                }
                Contact contact= new Contact(info,"","");
                contact.save();
                Log.d(TAG, "P2pListener | Peer discovered: " + peer.getNodeId() + " with info: " + info);
            }

            @Override
            public void onPeerLost(final Peer peer) {
                Log.d(TAG, "P2pListener | Peer lost: " + peer.getNodeId());
            }

            @Override
            public void onPeerUpdatedDiscoveryInfo(Peer peer) {
                String info = "NO_INFO";
                if(peer.getDiscoveryInfo() != null && peer.getDiscoveryInfo().length > 0) {
                    info = new String(peer.getDiscoveryInfo());
                }
                Log.d(TAG, "P2pListener | Peer updated: " + peer.getNodeId() + " with new info: " + info);
            }
        });
    }

    private void setDiscoveryInfo() {
        try {
            KitClient.getInstance(mContext).getDiscoveryServices().setP2pDiscoveryInfo("Hello p2pkit from HackerStolz".getBytes());
        } catch (InfoTooLongException e) {
            Log.d(TAG, "P2pListener | The discovery info is too long");
        }
    }
}

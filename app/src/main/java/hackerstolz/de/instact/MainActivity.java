package hackerstolz.de.instact;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ch.uepaa.p2pkit.ConnectionCallbacks;
import ch.uepaa.p2pkit.ConnectionResult;
import ch.uepaa.p2pkit.ConnectionResultHandling;
import ch.uepaa.p2pkit.KitClient;
import ch.uepaa.p2pkit.discovery.P2pListener;
import ch.uepaa.p2pkit.discovery.Peer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String P2P_KIT_APP_KEY = "eyJzaWduYXR1cmUiOiI0cDRjZzFqUENPQm1BRTFQTis3dm1PZjBiQ0hHU2lueVNWZEdaVlNSUTVPVzJMRENQMjA5S01YSzdueTJKdGRPRXVmc213MmVGZ3NrVEJXakFlM2F1eTdIQklNTXkrMC81RitwbTlBL0p5QjJhVkxmZEZNaEd3UWo2c3EyRDZ4dWRhUkdxODJzNkRaeDFWVnFHN3pwWlpKNHZMU2xrcUVTLytWUUtXWGE3ODA9IiwiYXBwSWQiOjEyNjAsInZhbGlkVW50aWwiOjE2Nzk0LCJhcHBVVVVJRCI6IkMxNzcxQThFLTk0ODYtNDNFRS05NTgxLThBMDUwMzZFMUY4RCJ9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initP2pKit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initP2pKit() {
        final int statusCode = KitClient.isP2PServicesAvailable(this);
        Log.d(TAG, "kit client status code: " + statusCode);
        if (statusCode == ConnectionResult.SUCCESS) {
            KitClient client = KitClient.getInstance(this);
            client.registerConnectionCallbacks(new ConnectionCallbacks() {
                @Override
                public void onConnected() {
                    Log.d(TAG, "p2p kit connected");
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
            ConnectionResultHandling.showAlertDialogForConnectionError(this, statusCode);
        }
    }

    public void addP2pListener() {
        KitClient.getInstance(MainActivity.this).getDiscoveryServices().addListener(new P2pListener() {
            @Override
            public void onStateChanged(int i) {
                Log.d(TAG, "state changed to: " + i);
            }

            @Override
            public void onPeerDiscovered(Peer peer) {
                Log.d(TAG, "Discovered a peer: " + peer.getNodeId());
            }

            @Override
            public void onPeerLost(Peer peer) {
                Log.d(TAG, "Lost peer: " + peer.getNodeId());
            }

            @Override
            public void onPeerUpdatedDiscoveryInfo(Peer peer) {
                Log.d(TAG, "updated peer infor: " + peer.getNodeId());
            }
        });
    }
}

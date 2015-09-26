package hackerstolz.de.instact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.UUID;

import hackerstolz.de.instact.p2p.ConnectionListener;
import hackerstolz.de.instact.p2p.P2pKitDataProvider;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private P2pKitDataProvider p2pDataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p2pDataProvider = new P2pKitDataProvider(this, new P2pConnectionListener());
        p2pDataProvider.init();
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

    public class P2pConnectionListener implements ConnectionListener {
        @Override
        public void onConnected() {
            logCurrentPeers();
        }

        private void logCurrentPeers() {
            List<UUID> peerIds = p2pDataProvider.getCurrentPeerIds();
            Log.d(TAG, "===== CURRENT PEERS: ");
            for(UUID peerId : peerIds) {
                Log.d(TAG, "Peer: " + peerId);
            }
        }
    }
}

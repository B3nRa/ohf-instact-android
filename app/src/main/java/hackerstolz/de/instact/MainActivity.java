package hackerstolz.de.instact;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import java.util.Locale;

import java.util.List;
import java.util.UUID;

import hackerstolz.de.instact.p2p.ConnectionListener;
import hackerstolz.de.instact.p2p.P2pKitDataProvider;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private P2pKitDataProvider p2pDataProvider;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p2pDataProvider = new P2pKitDataProvider(this, new P2pConnectionListener());
        p2pDataProvider.init();
   


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);

//        final ActionBar actionBar = getSupportActionBar();
//
//        // Specify that tabs should be displayed in the action bar.
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//        // Create a tab listener that is called when the user changes tabs.
//        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
//            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//                // show the given tab
//            }
//
//            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//                // hide the given tab
//            }
//
//            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//                // probably ignore this event
//            }
//        };
//
//        // Add 3 tabs, specifying the tab's text and TabListener
//        for (int i = 0; i < 3; i++) {
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText("Tab " + (i + 1))
//                            .setTabListener(tabListener));
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            return rootView;
        }
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
                    addP2pListener();
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
        Log.d(TAG, "add p2p listener");
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

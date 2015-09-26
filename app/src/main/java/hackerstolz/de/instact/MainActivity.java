package hackerstolz.de.instact;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.astuetz.PagerSlidingTabStrip;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import hackerstolz.de.instact.data.Contact;
import hackerstolz.de.instact.p2p.ConnectionListener;
import hackerstolz.de.instact.p2p.P2pKitDataProvider;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String P2P_KIT_APP_KEY = "eyJzaWduYXR1cmUiOiI0cDRjZzFqUENPQm1BRTFQTis3dm1PZjBiQ0hHU2lueVNWZEdaVlNSUTVPVzJMRENQMjA5S01YSzdueTJKdGRPRXVmc213MmVGZ3NrVEJXakFlM2F1eTdIQklNTXkrMC81RitwbTlBL0p5QjJhVkxmZEZNaEd3UWo2c3EyRDZ4dWRhUkdxODJzNkRaeDFWVnFHN3pwWlpKNHZMU2xrcUVTLytWUUtXWGE3ODA9IiwiYXBwSWQiOjEyNjAsInZhbGlkVW50aWwiOjE2Nzk0LCJhcHBVVVVJRCI6IkMxNzcxQThFLTk0ODYtNDNFRS05NTgxLThBMDUwMzZFMUY4RCJ9";

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

    private P2pKitDataProvider p2pDataProvider;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);

        p2pDataProvider.init();

        setup();
    }

    private void setup(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primaryDark));
        }
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
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

        private RecyclerView mRecyclerView;
        private ContactListView mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;

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

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this.getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);


            // specify an adapter (see also next example)
            mAdapter = new ContactListView();
            mRecyclerView.setAdapter(mAdapter);

            new P2pKitDataProvider(new P2pConnectionListener(this));

            return rootView;
        }
    }

    public class P2pConnectionListener implements ConnectionListener {
        private PlaceholderFragment mPlaceholderFragment;

        P2pConnectionListener(PlaceholderFragment fragment){
            mPlaceholderFragment = fragment;
        }

        @Override
        public void onConnected() {
            logCurrentPeers();
            p2pDataProvider = new P2pKitDataProvider(this, new P2pConnectionListener());
            List<Contact> peerIds = p2pDataProvider.getCurrentPeerIds();
            mPlaceholderFragment.mAdapter.addContacts(peerIds);
            mPlaceholderFragment.mAdapter.notifyDataSetChanged();
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

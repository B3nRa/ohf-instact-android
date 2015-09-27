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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import hackerstolz.de.instact.data.Contact;
import hackerstolz.de.instact.p2p.ConnectionListener;
import hackerstolz.de.instact.p2p.P2pKitDataProvider;

/**
 * Created by Benjamin on 27.09.2015.
 */
public class EventActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
//        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>netly</font>"));

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>netly</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp_m);

        setup();

        mRecyclerView = (RecyclerView) this.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EventListView();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setup() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.primaryDark));
        }
    }
}

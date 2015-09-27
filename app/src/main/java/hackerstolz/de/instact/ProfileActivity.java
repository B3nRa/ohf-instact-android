package hackerstolz.de.instact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.SupportActionModeWrapper;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import hackerstolz.de.instact.data.Contact;

/**
 * Created by muszy on 26-Sep-15.
 */
public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getName();

    private static List<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) {
//            getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>netly</font>"));
//
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeButtonEnabled(true);
//        }
//        DrawerLayout dl = (DrawerLayout) findViewById(R.id.profile_navdrawer);
//        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, dl, R.drawable.ic_menu_white_48dp, R.string.open_drawer, R.string.close_drawer) {
//
//        };
//        dl.setDrawerListener(abdt);

        Intent intent = getIntent();
        if (intent != null) {
            String p2pId = intent.getStringExtra(ContactListView.P2P_ID);
            if (p2pId == null || p2pId.isEmpty()) {
                addFakeTags();
            } else {
                Contact contact = Contact.get(p2pId);
                fillViewWithContactData(contact);
            }
        }
    }

    private void fillViewWithContactData(Contact contact) {
        // set avatar
        String imageData = ImageUtils.loadImageAsBase64(contact.p2pId);
        byte[] imageAsBytes = Base64.decode(imageData.getBytes(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        CircularImageView userAvatar = (CircularImageView) findViewById(R.id.user_avatar);
        userAvatar.setImageBitmap(bitmap);

        // set name
        TextView tvName = (TextView) findViewById(R.id.tv_username);
        tvName.setText(contact.getName());

        // set labels
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.tags_flowlayout);
        LayoutInflater inflater = LayoutInflater.from(this);
        int i = 0;
        for (String label : contact.labelList()) {
            TextView tv = (TextView) inflater.inflate(R.layout.tag_list_item, flowLayout, false);
            tv.setText(label);
            if ((i % 2) == 0) {
                GradientDrawable drawable = (GradientDrawable) tv.getBackground();
                drawable.setStroke(1, Color.TRANSPARENT);
                drawable.setColor(getResources().getColor(R.color.secondary_color_transparent));
            } else {
                GradientDrawable drawable = (GradientDrawable) tv.getBackground();
                drawable.setStroke(1, Color.DKGRAY);
                drawable.setColor(Color.TRANSPARENT);
            }
            i++;

            flowLayout.addView(tv, new FlowLayout.LayoutParams(10, 10));
        }

    }

    private void addFakeTags() {
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.tags_flowlayout);
        tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Web Development");
        tags.add("Android");
        tags.add("Design Rockstar");
        tags.add("Python");
        tags.add("Hackathons");
        tags.add("Rocket Engineer");
        tags.add("JavaScript");
        tags.add("C / C++");
        tags.add("HTML5 & CSS3");
        tags.add("Travelling");

        LayoutInflater inflater = LayoutInflater.from(this);
        int i = 0;
        for (String tag : tags) {
            TextView tv = (TextView) inflater.inflate(R.layout.tag_list_item, flowLayout, false);
            tv.setText(tag);
            if ((i % 2) == 0) {
                GradientDrawable drawable = (GradientDrawable) tv.getBackground();
//                drawable.setStroke(1, getResources().getColor(R.color.secondary_color));
                drawable.setStroke(1, Color.TRANSPARENT);
                drawable.setColor(getResources().getColor(R.color.secondary_color_transparent));
            } else {
                GradientDrawable drawable = (GradientDrawable) tv.getBackground();
                drawable.setStroke(1, Color.DKGRAY);
                drawable.setColor(Color.TRANSPARENT);
            }
            i++;

            flowLayout.addView(tv, new FlowLayout.LayoutParams(10, 10));
        }
    }
}

package hackerstolz.de.instact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
    private static final String ADD = "Add Label +";

    private static List<String> tags;

    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>netly</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp_m);

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

        CircularImageView connectBtn = (CircularImageView) findViewById(R.id.connect_profile_btn);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/henningmu"));
                startActivity(browserIntent);
            }
        });
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

        // add edit tag
        TextView tv = (TextView) inflater.inflate(R.layout.tag_list_item, flowLayout, false);
        tv.setText(ADD);
        GradientDrawable drawable = (GradientDrawable) tv.getBackground();
        drawable.setStroke(1, Color.TRANSPARENT);
        drawable.setColor(getResources().getColor(R.color.primary_color_transparent));
        flowLayout.addView(tv, new FlowLayout.LayoutParams(10, 10));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEditTag();
            }
        });
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

        // add edit tag
        TextView tv = (TextView) inflater.inflate(R.layout.tag_list_item, flowLayout, false);
        tv.setText(ADD);
        GradientDrawable drawable = (GradientDrawable) tv.getBackground();
        drawable.setStroke(1, Color.TRANSPARENT);
        drawable.setColor(getResources().getColor(R.color.primary_color_transparent));
        flowLayout.addView(tv, new FlowLayout.LayoutParams(10, 10));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEditTag();
            }
        });
    }

    private void handleEditTag() {
        Log.d(TAG, "Edit Tag Pressed!!");
    }
}

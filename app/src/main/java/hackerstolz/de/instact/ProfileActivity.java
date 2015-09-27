package hackerstolz.de.instact;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>netly</font>"));

        addTags();
    }

    private void addTags() {
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

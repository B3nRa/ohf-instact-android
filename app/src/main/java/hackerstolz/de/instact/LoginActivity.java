package hackerstolz.de.instact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.pkmmte.circularimageview.CircularImageView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import hackerstolz.de.instact.data.Contact;
import hackerstolz.de.instact.data.Label;

/**
 * Created by muszy on 26-Sep-15.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();
    public static final int RC_USER_IMAGE = 1337;

    private CircularImageView mCircularAvatar;
    private Bitmap userAvatar;
    private Uri userAvatarUri;

    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
//        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>netly</font>"));
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setElevation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>netly</font>"));

        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClass(Contact.class);
        configurationBuilder.addModelClass(Label.class);
        ActiveAndroid.initialize(configurationBuilder.create());
        initViews();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_USER_IMAGE && resultCode == RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                userAvatarUri = data.getData();
                userAvatar = ImageUtils.getCorrectlyOrientedImage(this, userAvatarUri);
                mCircularAvatar.setImageBitmap(userAvatar);
                mCircularAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (IOException io) {
                Log.e(TAG, io.getMessage());
            }
        }
    }

    public void initViews() {
        Button continueButton = (Button) findViewById(R.id.continue_button);
        ImageButton linkedInButton = (ImageButton) findViewById(R.id.linkedin_button);
        mCircularAvatar = (CircularImageView) findViewById(R.id.add_user_avatar);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "continue clicked");
                extractAndSaveContactFromViews();
                Intent intent = new Intent(LoginActivity.this, EventActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        mCircularAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, RC_USER_IMAGE);
            }
        });

        linkedInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etUserName = (EditText) findViewById(R.id.edittext_username);
                EditText etTags = (EditText) findViewById(R.id.edittext_tags);
                mCircularAvatar.setImageResource(R.drawable.random_avatar_04);
                etUserName.setText("Natalia Karbasova");
                etTags.setText("Data journalism, Multimedia-Journalismis, Intrapreneurship, Rapid Prototyping");
            }
        });
    }

    private void extractAndSaveContactFromViews() {
        EditText etUserName = (EditText) findViewById(R.id.edittext_username);
        EditText etTags = (EditText) findViewById(R.id.edittext_tags);

        String userName = etUserName.getText().toString();
        String rawTags = etTags.getText().toString();
        List<String> tags = Arrays.asList(rawTags.split(","));
        String p2pId = "ME";
        String xing = "barfoo"; // TODO!
        try {
            Contact contact = new Contact(userName, xing, p2pId);
            contact.save();
            ImageUtils.saveUserImage(ImageUtils.shrinkBitmap(userAvatar), p2pId);
            for (String label : tags) {
                Label l = new Label(label, contact);
                l.save();
            }
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }


}

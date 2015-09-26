package hackerstolz.de.instact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import java.util.Arrays;
import java.util.List;

import hackerstolz.de.instact.data.Contact;
import hackerstolz.de.instact.data.Label;

/**
 * Created by muszy on 26-Sep-15.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>netly</font>"));

        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClass(Contact.class);
        configurationBuilder.addModelClass(Label.class);
        ActiveAndroid.initialize(configurationBuilder.create());
        initViews();
    }

    public void initViews() {
        Button continueButton = (Button) findViewById(R.id.continue_button);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "continue clicked");
                extractAndSaveContactFromViews();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    private void extractAndSaveContactFromViews() {
        EditText etUserName = (EditText) findViewById(R.id.edittext_username);
        EditText etTags = (EditText) findViewById(R.id.edittext_tags);

        String userName = etUserName.getText().toString();
        String rawTags = etTags.getText().toString();
        List<String> tags = Arrays.asList(rawTags.split(","));
        String p2pId = "ME"; // TODO!
        String xing = "barfoo"; // TODO!
        try {
            Contact contact = new Contact(userName, xing, p2pId);
            contact.save();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

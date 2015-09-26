package hackerstolz.de.instact;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.pkmmte.circularimageview.CircularImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import hackerstolz.de.instact.data.Contact;
import hackerstolz.de.instact.helper.ContactDbHelper;

/**
 * Created by muszy on 26-Sep-15.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getName();

    public static final int RC_USER_IMAGE = 1337;

    private CircularImageView mCircularAvatar;
    private Bitmap userAvatar;
    private Uri userAvatarUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Contact.mDbHelper = new ContactDbHelper(this);
        setContentView(R.layout.activity_login);

        initViews();
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
                userAvatar = getCorrectlyOrientedImage(userAvatarUri);
                mCircularAvatar.setImageBitmap(userAvatar);
                mCircularAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (IOException io) {
                Log.e(TAG, io.getMessage());
            }
        }
    }

    public Bitmap getCorrectlyOrientedImage(Uri photoUri) throws IOException {
        InputStream is = this.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = this.getContentResolver().openInputStream(photoUri);
        srcBitmap = BitmapFactory.decodeStream(is);
        is.close();

    /*
     * if the orientation is not 0 (or -1, which means we don't know), we
     * have to do a rotation.
     */
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        return srcBitmap;
    }

    public int getOrientation(Uri photoUri) {
    /* it's on the external media. */
        Cursor cursor = this.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public void initViews() {
        Button continueButton = (Button) findViewById(R.id.continue_button);
        mCircularAvatar = (CircularImageView) findViewById(R.id.add_user_avatar);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "continue clicked");
                extractAndSaveContactFromViews();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
    }

    private void extractAndSaveContactFromViews() {
        EditText etUserName = (EditText) findViewById(R.id.edittext_username);
        EditText etTags = (EditText) findViewById(R.id.edittext_tags);

        String userName = etUserName.getText().toString();
        String rawTags = etTags.getText().toString();
        List<String> tags = Arrays.asList(rawTags.split(","));
        String p2pId = "foobar"; // TODO!
        String xing = "barfoo"; // TODO!
        try {
            Contact contact = new Contact(userName, xing, p2pId, tags);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
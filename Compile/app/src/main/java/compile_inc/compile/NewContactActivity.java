package compile_inc.compile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.andreabaccega.widget.FormEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewContactActivity extends Activity {

    private Contact newContact;
    private Bitmap newImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
    }
    public void onClickProfilePicture(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }
    private final int SELECT_PHOTO = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        CircleImageView img = (CircleImageView) findViewById (R.id.profile_image);
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        img.setImageBitmap(selectedImage);
                        newImage = selectedImage;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_add_contact, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add_contact) {
            FormEditText firstName = (FormEditText) this.findViewById(R.id.new_contactFirstName);
            FormEditText lastName = (FormEditText) this.findViewById(R.id.new_contactLastName);
            FormEditText email = (FormEditText) this.findViewById(R.id.new_contactEmail);
            FormEditText phone = (FormEditText) this.findViewById(R.id.new_contactPhone);

            int lastNameLen = lastName.length();
            int emailLen = email.length();
            int phoneLen = phone.length();
            boolean firstNameTest = true;
            boolean lastNameTest = true;
            boolean emailTest = true;
            boolean phoneTest = true;

            if (!firstName.testValidity()) {
                firstNameTest = false;
            }
            if (emailLen > 0) {
                emailTest = email.testValidity();
            }
            if (lastNameLen > 0) {
                lastNameTest = lastName.testValidity();
            }
            if (phoneLen > 0) {
                phoneTest = phone.testValidity();
            }
            if (firstNameTest && emailTest && lastNameTest && phoneTest) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                Log.d("Contact_add:  ", "Adding a Contact");
                EditText text = (EditText) findViewById(R.id.new_contactFirstName);
                String _firstName = text.getText().toString();
                text = (EditText) findViewById(R.id.new_contactLastName);
                String _lastName = text.getText().toString();
                text = (EditText) findViewById(R.id.new_contactEmail);
                String _email = text.getText().toString();
                text = (EditText) findViewById(R.id.new_contactAddress);
                String _address = text.getText().toString();
                text = (EditText) findViewById(R.id.new_contactPhone);
                String _phone = text.getText().toString();
                newContact = new Contact(_firstName, _lastName, _email, _address, _phone);
                MainActivity.db.dbAddContact(newContact);
                //saving picture to internal storage
                int count = MainActivity.db.dbGetContactsCount();
                Contact testContact = MainActivity.db.dbGetContact(count);
                int dbID = testContact.getId();
                Context context = getBaseContext();
                try {
                    FileOutputStream fos = openFileOutput(Integer.toString(dbID), Context.MODE_PRIVATE);
                    newImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                } catch (Exception FileNotFoundException) {

                }
            }
        }
        return super.onOptionsItemSelected(item);

    }
}

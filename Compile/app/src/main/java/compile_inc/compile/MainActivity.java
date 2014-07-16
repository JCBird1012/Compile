package compile_inc.compile;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.database.Cursor;


import java.io.FileOutputStream;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Log.d("Creating: ", "Creating ..");
        // initializes the local database of contacts
        ContactDatabaseHandler db = new ContactDatabaseHandler(this);



        Contact testContact = new Contact("John", "Gallagher", "johnjon8@gmail.com",
                "2934 Belmont Ave Ardmore PA.", "2672401429", "yo this guy is awesome bro");

        // the db.dbAddContact will a contact to the database initialized above! (hopefully...)
        //db.dbAddContact(testContact);

        //Contact retrievedContact = db.dbGetContact(0);

        //testPrintContact(retrievedContact);
        //idk what max is doing here, but he should put in comments so that his team can understand
        getAndroidContacts();

    }

    //just a simple test, not permanent
    public void testPrintContact(Contact contact) {
        Gson gson = new Gson();
        String json = gson.toJson(contact);
        TextView t = (TextView) findViewById(R.id.testText);
        t.append(json);
        String FILENAME = "contactSave";
    }



    public void getAndroidContacts () {
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_contact) {
            Intent intent = new Intent(this, NewContactActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);


    }
}



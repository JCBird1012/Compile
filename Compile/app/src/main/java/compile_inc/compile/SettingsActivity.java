package compile_inc.compile;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.List;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    }

    //Deletes all of the contacts in the database.
    public void onClickDeleteAll(View v) {
        List<Contact> contacts = MainActivity.db.dbGetAllContacts();
        while(contacts.size()>0) { //starts at the bottom of the database and works up,
            // deleting everything while not changing any of their id's in the process. (efficient)
            MainActivity.db.dbDeleteContact(contacts.get(contacts.size()-1));
            contacts = MainActivity.db.dbGetAllContacts();
        }
        onResume();
    }

    //Adds existing contacts in your people application
    public void onClickPullContacts(View v) {
        getAndroidContacts();

        onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAndroidContacts () {


        String phoneNumber = null;
        String email = null;

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

        StringBuffer output = new StringBuffer();


        ContentResolver contentResolver = getContentResolver();


        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        {

            // Loop for every contact in the phone

            if (cursor.getCount() > 0) {


                while (cursor.moveToNext()) {
                    Contact newContact = new Contact();
                    String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                    String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

                    //debugging
                    Log.d("Pulling from contacts: ", "Working ..");
                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
                    if (hasPhoneNumber > 0) {

                        // Query and loop for every phone number of the contact
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));

                        }
                        phoneCursor.close();
                        // Query and loop for every email of the contact
                        Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,    null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
                        while (emailCursor.moveToNext()) {
                            email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

                        }

                        emailCursor.close();
                    }
                    //sends phone number to the Contact object
                    newContact.setPhone_num(phoneNumber);
                    String spliced[] = name.split("\\s");
                    //checks for an "@" symbol in the first name of the contact pulled from the
                    // local phone contacts to see whether it is actually an email address.
                    if(spliced[0].indexOf("\u0040") !=-1){
                        newContact.setFirstName("unknown contact information");
                        email = spliced[0];
                    }   else {
                        newContact.setFirstName(spliced[0]);
                    }

                    //checks to see whether there is or is not a last name placed in the name
                    // category
                    if(spliced.length > 1 ) {
                        newContact.setLastName(spliced[1]);
                    }

                    //sets the email in the local contact app to the contact object
                    newContact.setEmail(email);
                    Log.d("new Contact email:  ", email);
                    if (email != "") {
                        // sends the newly created object to the local contact database only if
                        // there is a valid email entered for that contact ---TODO implement
                        // facebook syncing with these email addresses to pull first and last
                        // names into the application.
                        MainActivity.db.dbAddContact(newContact);
                    }
                }
            }
        }
    }
}

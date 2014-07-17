package compile_inc.compile;


import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    //declares the database
    protected static ContactDatabaseHandler db;
    private CardAdapter adapter;
    private ListView card_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Log.d("Creating: ", "Creating ..");
        // initializes the local database of contacts
        db = new ContactDatabaseHandler(this);

//        Contact testContact = new Contact("John", "Gallagher", "johnjon8@gmail.com",
//                "2934 Belmont Ave Ardmore PA.", "2672401429", "yo this guy is awesome bro");
//        db.dbAddContact(testContact);

        List<Contact> fullContacts =  db.dbGetAllContacts();
        this.card_list = (ListView) findViewById(R.id.listView);
        this.adapter = new CardAdapter(this, (ArrayList) fullContacts);
        this.card_list.setAdapter(this.adapter);
        Log.d("Adapter:  ","it should have run");

        //idk what max is doing here, but he should put in comments so that his team can understand
        getAndroidContacts();

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Contact> fullContacts =  db.dbGetAllContacts();
        this.card_list = (ListView) findViewById(R.id.listView);
        this.adapter = new CardAdapter(this, (ArrayList) fullContacts);
        this.card_list.setAdapter(this.adapter);

        //testFunction();
    }

    //A function meant to test the database and other functions --- TODO remove this eventually ---
    public void testFunction(){
//        Contact testContact = new Contact("John", "Gallagher", "johnjon8@gmail.com",
//                "2934 Belmont Ave Ardmore PA.", "2672401429", "yo this guy is awesome bro");
//
//
//        Contact testContact2 = new Contact("Max", "Plank", "yo@google.com", "2939 Element St",
//                "5554443322", "dis guy is cool too!");
//        // the db.dbAddContact will a contact to the database initialized above if there are
//        // fewer 2 already in existence!
//        // (hopefully...)
//        List<Contact> allContacts = db.dbGetAllContacts();
//
//        if(2 > allContacts.size()) {
//            db.dbAddContact(testContact);
//            db.dbAddContact(testContact2);
//        }
//
//
//        Log.d("Retrieving:  ", "Retrieving ..");
//        Contact retrievedContact = db.dbGetContact(1);
//        Contact retrievedContact2 = db.dbGetContact(2);
//
//        //reads all of the contacts currently in the database
//        Log.d("Reading: ","Reading all contacts ..");
//        allContacts = db.dbGetAllContacts();
//
//
//        //prints some contacts for testing purposes
//        Log.d("Printing:  ", "Printing ..");
//        Log.d("Number of contacts", String.valueOf(allContacts.size()));
//        //clears current text
//        TextView t = (TextView) findViewById(R.id.testText);
//        t.setText("");
//        for (int i = 0; i < allContacts.size(); i++) {
//            testPrintContact(allContacts.get(i));
//            Log.d("Loop:  ", "Running loop ..");
//        }
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


        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);
        {

            // Loop for every contact in the phone

            if (cursor.getCount() > 0) {


                while (cursor.moveToNext()) {
                    String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                    String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
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
                }
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete, menu);
        getMenuInflater().inflate(R.menu.settings, menu);
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
        if (id == R.id.action_delete_all) { //deletes all contact info from the database
            List<Contact> contacts = db.dbGetAllContacts();
            while(contacts.size()>0) { //starts at the bottom of the database and works up,
            // deleting everything while not changing any of their id's in the process. (efficient)
                db.dbDeleteContact(contacts.get(contacts.size()-1));
                contacts = db.dbGetAllContacts();

            }
            onResume();
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);


    }
}



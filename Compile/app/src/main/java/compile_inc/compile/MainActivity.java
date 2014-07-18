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

// --- TODO create a List of valid contacts i.e. contacts with email addresses as well as first
// and last names
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

    //A function meant to test the database and other functions ---note,
    // probably doesn't work properly anymore --- TODO remove this
    // eventually ---
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








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);


    }
}



package compile_inc.compile;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// --- TODO create a List of valid contacts i.e. contacts with email addresses as well as first
// and last names
public class MainActivity extends Activity {

    //declares the database

    protected static ContactDatabaseHandler db;
    private CardAdapter adapter;
    private ListView card_list;
    private ArrayList<Contact> contacts_full;

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
        contacts_full = (ArrayList<Contact>) db.dbGetAllContacts();
        List<Contact> fullContacts =  contacts_full;
        ArrayList<Contact> revisedContacts = listToValid(fullContacts);
        this.card_list = (ListView) findViewById(R.id.listView);
        this.adapter = new CardAdapter(this, (ArrayList) revisedContacts);
        this.card_list.setAdapter(this.adapter);

        card_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                expandCard(view, i);

            }
        });

        //idk what max is doing here, but he should put in comments so that his team can understand

    }

    @Override
    protected void onResume() {
        super.onResume();
//        List<Contact> fullContacts =  db.dbGetAllContacts();
//        ArrayList<Contact> revisedContacts = listToValid(fullContacts);
//        this.card_list = (ListView) findViewById(R.id.listView);
//        this.adapter = new CardAdapter(this, (ArrayList) revisedContacts);
//        this.card_list.setAdapter(this.adapter);
//        this.card_list = (ListView) findViewById(R.id.listView);
//
//        this.card_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                expandCard(view, i);
//
//            }
//        });
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

    public boolean contactHasEmail(Contact contact) {
        if (contact.getEmail()!=null) {
            return true;
        }
        return false;
    }
    //checks to see whether all of the Contacts in a given List are valid/have email addresses
    // attached.
    public ArrayList<Contact> listToValid(List<Contact> contacts) {
        ArrayList<Contact> newContacts = new ArrayList<Contact>();
        for(int i = 0; i < contacts.size(); i++) {
            if(contactHasEmail(contacts.get(i)))//finish
            newContacts.add(contacts.get(i));
        }

        return newContacts;
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

//expands/makes smaller a card on click
    public void expandCard(View v, int position) {
        Contact contact = contacts_full.get(position);
        TextView emailAddr = (TextView) v.findViewById(R.id.card_email_addr);
        emailAddr.setText(String.valueOf(contact.getEmail()));
        emailAddr.setPadding(10, 10, 10, 10);
        CircleImageView cardImg = (CircleImageView) v.findViewById(R.id.card_img_face);
        TextView firstName = (TextView) v.findViewById(R.id.card_first_name);
        TextView lastName = (TextView) v.findViewById(R.id.card_last_name);
        RelativeLayout relLayout = (RelativeLayout) v.findViewById(R.id.card_row);
        RelativeLayout cardNames = (RelativeLayout) v.findViewById(R.id.card_names);
        //define layout parameters for the face-image.
        RelativeLayout.LayoutParams img_p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //define layout params for the relative layout containing both the first and last names
        RelativeLayout.LayoutParams cardNames_p = new RelativeLayout.LayoutParams(ViewGroup
                .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //define layout params for first name
        RelativeLayout.LayoutParams firstName_p = new RelativeLayout.LayoutParams(ViewGroup
                .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //define layout params for last name
        RelativeLayout.LayoutParams lastName_p = new RelativeLayout.LayoutParams(ViewGroup
                .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = findViewById(R.id.main_layout);
        if (contact.isSelected() == 1) {
            contact.setSelected(0);
            emailAddr.setVisibility(View.GONE);
            img_p.addRule(RelativeLayout.ALIGN_LEFT);
            img_p.height = 110;
            img_p.width = 110;
            cardImg.setLayoutParams(img_p);
            cardNames_p.addRule(RelativeLayout.ALIGN_LEFT);
            cardNames_p.addRule(RelativeLayout.RIGHT_OF, cardImg.getId());
            cardNames.setLayoutParams(cardNames_p);
            firstName.setTextSize(18);
            lastName.setTextSize(18);
        } else {

            contact.setSelected(1);
            emailAddr.setVisibility(View.VISIBLE);
            img_p.addRule(RelativeLayout.CENTER_HORIZONTAL);
            img_p.height = 200;
            img_p.width = 200;
            cardImg.setLayoutParams(img_p);
            cardNames_p.addRule(RelativeLayout.CENTER_HORIZONTAL);
            cardNames_p.addRule(RelativeLayout.BELOW, cardImg.getId());
            cardNames.setLayoutParams(cardNames_p);
            firstName.setTextSize(25);
            lastName.setTextSize(25);

        }


    }

}



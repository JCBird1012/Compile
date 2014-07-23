package compile_inc.compile;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// --- TODO create a List of valid contacts i.e. contacts with email addresses as well as first
// and last names
public class MainActivity extends Activity {

    protected static ContactDatabaseHandler db;
    View.OnClickListener deleteContactFunc = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            // set title
            alertDialogBuilder.setTitle("Delete Contact?");
            alertDialogBuilder.setIcon(R.drawable.ic_red_trash);

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure you want to delete this contact?")
                    .setCancelable(false)
                    .setPositiveButton("Yes, delete it.", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            {
                                deleteSingleContact(contact_to_delete);
                                adapter = new CardAdapter(activity, (ArrayList) contacts_full);
                                card_list.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                Toast toast = Toast.makeText(getApplicationContext(), "Deleted contact.", Toast.LENGTH_SHORT);
                                toast.show();
                                onResume();
                            }
                        }
                    })
                    .setNegativeButton("No, don't delete.", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            onResume();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    };
    //declares the database
    private Activity activity = this;
    private CardAdapter adapter;
    private ListView card_list;
    private ArrayList<Contact> contacts_full;
    private Contact contact_to_delete = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Log.d("Creating: ", "Creating ..");
        // initializes the local database of contacts
        db = new ContactDatabaseHandler(this);
        contacts_full = (ArrayList<Contact>) db.dbGetAllContacts();
        List<Contact> fullContacts = contacts_full;
        ArrayList<Contact> revisedContacts = listToValid(fullContacts);
        this.card_list = (ListView) findViewById(R.id.listView);
        this.adapter = new CardAdapter(this, (ArrayList) contacts_full);
        this.card_list.setAdapter(this.adapter);


        card_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                expandCard(view, i);

            }
        });
    }

    public void welcomeScreen() {

        if (db.dbGetContactsCount() == 0) {
            View b = findViewById(R.id.welcome);
            View c = findViewById(R.id.welcome2);
            b.setVisibility(View.VISIBLE);
            c.setVisibility(View.VISIBLE);
        } else {
            View b = findViewById(R.id.welcome);
            View c = findViewById(R.id.welcome2);
            b.setVisibility(View.INVISIBLE);
            c.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        welcomeScreen();

    }

    public boolean contactHasEmail(Contact contact) {
        if (contact.getEmail() != null) {
            return true;
        }
        return false;
    }

    //checks to see whether all of the Contacts in a given List are valid/have email addresses
    // attached.
    public ArrayList<Contact> listToValid(List<Contact> contacts) {
        ArrayList<Contact> newContacts = new ArrayList<Contact>();
        for (int i = 0; i < contacts.size(); i++) {
            if (contactHasEmail(contacts.get(i)))//finish
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
        TextView phoneNum = (TextView) v.findViewById(R.id.card_phone_num);
        emailAddr.setText(String.valueOf(contact.getEmail()));
        phoneNum.setText(contact.getPhone_num());
        emailAddr.setPadding(10, 10, 10, 10);
        CircleImageView cardImg = (CircleImageView) v.findViewById(R.id.card_img_face);
        TextView firstName = (TextView) v.findViewById(R.id.card_first_name);
        TextView lastName = (TextView) v.findViewById(R.id.card_last_name);
        RelativeLayout relLayout = (RelativeLayout) v.findViewById(R.id.card_row);
        RelativeLayout cardNames = (RelativeLayout) v.findViewById(R.id.card_names);
        ImageView deleteIcon = (ImageView) v.findViewById(R.id.card_delete_icon);
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
        //define layout params for delete icon
        RelativeLayout.LayoutParams deleteIcon_p = new RelativeLayout.LayoutParams(ViewGroup
                .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = findViewById(R.id.main_layout);
        if (contact.isSelected() == 1) {
            contact.setSelected(0);
            emailAddr.setVisibility(View.GONE);
            phoneNum.setVisibility(View.GONE);
            img_p.addRule(RelativeLayout.ALIGN_LEFT);
            img_p.height = 110;
            img_p.width = 110;
            cardImg.setLayoutParams(img_p);
            cardNames_p.addRule(RelativeLayout.ALIGN_LEFT);
            cardNames_p.addRule(RelativeLayout.RIGHT_OF, cardImg.getId());
            cardNames.setLayoutParams(cardNames_p);
            deleteIcon.setVisibility(View.GONE);
            firstName.setTextSize(18);
            lastName.setTextSize(18);
        } else {

            contact.setSelected(1);
            emailAddr.setVisibility(View.VISIBLE);
            phoneNum.setVisibility(View.VISIBLE);
            img_p.addRule(RelativeLayout.CENTER_HORIZONTAL);
            img_p.height = 200;
            img_p.width = 200;
            cardImg.setLayoutParams(img_p);
            cardNames_p.addRule(RelativeLayout.CENTER_HORIZONTAL);
            cardNames_p.addRule(RelativeLayout.BELOW, cardImg.getId());
            cardNames.setLayoutParams(cardNames_p);
            contact_to_delete = contact;
            deleteIcon_p.addRule(RelativeLayout.ALIGN_TOP);
            deleteIcon_p.addRule(RelativeLayout.ALIGN_RIGHT);
            deleteIcon.setVisibility(View.VISIBLE);
            deleteIcon.setLayoutParams(deleteIcon_p);
            deleteIcon.setOnClickListener(deleteContactFunc);
            firstName.setTextSize(25);
            lastName.setTextSize(25);
        }
    }

    //deletes a single contact
    public void deleteSingleContact(Contact contact) {
        db.dbDeleteContact(contact);
        contacts_full = (ArrayList<Contact>) db.dbGetAllContacts();
    }


}





package compile_inc.compile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import compile_inc.compile.R;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    }

    public void onClick(View v) {
        List<Contact> contacts = MainActivity.db.dbGetAllContacts();
        while(contacts.size()>0) { //starts at the bottom of the database and works up,
            // deleting everything while not changing any of their id's in the process. (efficient)
            MainActivity.db.dbDeleteContact(contacts.get(contacts.size()-1));
            contacts = MainActivity.db.dbGetAllContacts();
        }
        onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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
}

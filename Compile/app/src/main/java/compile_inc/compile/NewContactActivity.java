package compile_inc.compile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import compile_inc.compile.R;

public class NewContactActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
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

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Log.d("Contact_add:  ", "Adding a Contact");
            EditText text = (EditText)findViewById(R.id.new_contactFirstName);
            String _firstName = text.getText().toString();
            text = (EditText)findViewById(R.id.new_contactLastName);
            String _lastName = text.getText().toString();
            text = (EditText)findViewById(R.id.new_contactEmail);
            String _email = text.getText().toString();
            Contact newContact = new Contact(_firstName, _lastName, _email);
            MainActivity.db.dbAddContact(newContact);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}

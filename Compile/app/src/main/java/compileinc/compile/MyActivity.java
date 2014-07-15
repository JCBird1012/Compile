package compileinc.compile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import android.widget.TextView;



public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Contact contact = new Contact("John", "Gallagher", "johnjon8@gmail.com", "2934 Belmont Ave Ardmore PA.");
        saveContact(contact);

    }


    public void saveContact(Contact contact) {
        Gson gson = new Gson();
        String json = gson.toJson(contact);
        TextView t = (TextView)findViewById(R.id.testText);
        t.append(json);
        System.out.println(json);
}

    public class Contact{
        public String firstName;
        public String lastName;
        public String email;
        public String address;

        public Contact(String startFirstName, String startLastName, String startEmail, String startAddress){
            firstName = startFirstName;
            lastName = startLastName;
            email = startEmail;
            address = startAddress;
        }
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

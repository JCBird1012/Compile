package compile_inc.compile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import android.widget.TextView;

import java.io.FileOutputStream;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        Contact contact = new Contact("John", "Gallagher", "johnjon8@gmail.com", "2934 Belmont Ave Ardmore PA.", "2672401429");


        saveContact(contact);
    }

    //creates an initial file in which the contact json info will be stored, only needs to be called once ever.
    public void createFile(){
        String file = "contactSave";
      //  file.createNewFile();

    }


    public void saveContact(Contact contact) {
        Gson gson = new Gson();
        String json = gson.toJson(contact);
        TextView t = (TextView)findViewById(R.id.testText);
        t.append(json);
        String FILENAME = "contactSave";
//        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//
//        fos.write(json.getBytes());
//        fos.close();



    }

    public class Contact{
        String firstName;
        String lastName;
        String email;
        String address;
        String phone_num;
        int id;

        public Contact(){
            firstName = "";
            lastName = "";
            email = "";
            address = "";
            phone_num = "";
        }
        //constructor
        public Contact(String startFirstName, String startLastName, String startEmail, String startAddress, String startPhone_num){
            firstName = startFirstName;
            lastName = startLastName;
            email = startEmail;
            address = startAddress;
            phone_num = startPhone_num;
        }
        //constructor with id value
        public Contact(int _id, String startFirstName, String startLastName, String startEmail, String startAddress, String startPhone_num){
            firstName = startFirstName;
            lastName = startLastName;
            email = startEmail;
            address = startAddress;
            phone_num = startPhone_num;
            id = _id;
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



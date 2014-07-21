package compile_inc.compile;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

//this is class will add stuff to the cards in our card layout --- TODO make it work... ---
public class CardAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Contact> contacts;
    private static LayoutInflater inflater=null;

    public CardAdapter(Activity activity, ArrayList<Contact> contacts) {
        this.activity = activity;
        this.contacts = contacts;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return this.contacts.size();
    }
    @Override
    public Object getItem(int i) {
        return this.contacts.get(i);
    }
    @Override
    public long getItemId(int i) {
        //return this.contacts.get(i).getId();
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("getView:  ","Running ...");
        View vi = convertView;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.card_row, null);
        }

        TextView firstName = (TextView)vi.findViewById(R.id.card_first_name);
        TextView lastName = (TextView)vi.findViewById(R.id.card_last_name);

        Contact contact = contacts.get(position);

        firstName.setText(contact.getFirstName());
        lastName.setText(contact.getLastName());
        Bitmap image = null;
        CircleImageView cardImg = (CircleImageView) vi.findViewById(R.id.card_img_face);
        int dbID = contact.getId();
        FileInputStream fis;
        try {
            fis = activity.openFileInput(Integer.toString(dbID));
            image = BitmapFactory.decodeStream(fis);
            fis.close();
            cardImg.setImageBitmap(image);
            cardImg.setVisibility(View.VISIBLE);
        }catch(Exception FileNotFoundException){

        }



        return vi;
    }



    public void expand(View v) {
    Log.d("onClick","");
}
}


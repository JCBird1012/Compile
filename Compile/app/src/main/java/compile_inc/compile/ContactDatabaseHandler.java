package compile_inc.compile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DUCA on 7/15/2014.
 */
public class ContactDatabaseHandler extends SQLiteOpenHelper {
    //static variables
    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "contactsDatabase";

    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE_NUM = "phone_num";
    private static final String KEY_DESCRIPTION = "description";


    public ContactDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE_TABLE" + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_FIRST_NAME + " TEXT," + KEY_LAST_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_ADDRESS +
                " TEXT," + KEY_PHONE_NUM + " TEXT," + KEY_DESCRIPTION + " TEXT" + ")";

    }

    //prolly not gonna use this ever except for reset of app maybe
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        //create tables again after reset
        onCreate(db);
    }

    //operations for create, read, update, delete. --- TODO, make this stuff work ---
    //adds a new contact to the database
    public void dbAddContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME,contact.getFirstName());
        values.put(KEY_LAST_NAME, contact.getLastName());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_PHONE_NUM, contact.getPhone_num());
        values.put(KEY_DESCRIPTION, contact.getDescription());

        //insert a row
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }
    //gets a single contact from the database and puts it in a contact object
    public Contact dbGetContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_FIRST_NAME,
                KEY_LAST_NAME, KEY_EMAIL, KEY_ADDRESS, KEY_PHONE_NUM, KEY_DESCRIPTION },
                KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return contact;
    }
    //gets a list of all contacts in the database
    public List<Contact> dbGetAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        //select all query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loops through all the rows to add to the list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contact.setAddress(cursor.getString(4));
                contact.setPhone_num(cursor.getString(5));
                contact.setDescription(cursor.getString(6));
            }   while (cursor.moveToNext());
        }
        //returns arraylist of contact objects with their id's
        return contactList;
    }
    //gets number of contacts
    public int dbGetContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        //returns the count of contacts
        return cursor.getCount();
    }
    //updating single contact
    public int dbUpdateContact(Contact contact) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, contact.getFirstName());
        values.put(KEY_LAST_NAME, contact.getLastName());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_ADDRESS, contact.getAddress());
        values.put(KEY_PHONE_NUM, contact.getPhone_num());
        values.put(KEY_DESCRIPTION, contact.getDescription());

        //updates the whole row for the contact data
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] { String.valueOf
                (contact.getId())});
    }
    //delete single contact
    public void dbDeleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(contact.getId())});
        db.close();
    }

}

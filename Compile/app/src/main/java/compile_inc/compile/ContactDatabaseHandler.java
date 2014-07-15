package compile_inc.compile;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    }
    //gets a single contact from the database
    public Contact dbGetContact(int id) {
        Contact contact = new Contact();
        return contact;
    }
    //gets a list of all contacts in the database
//    public List<Contact> dbGetAllContacts() {
//
//    }
    //gets number of contacts
    public int dbGetContactsCount() {
        return 0;
    }
    //updating single contact
    public int dbUpdateContact(Contact contact) {
        return 0;
    }
    //delete single contact
    public void dbDeleteContact(Contact contact) {

    }

}

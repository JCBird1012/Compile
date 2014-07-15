package compile_inc.compile;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_CONTACTS);

        //create tables again after reset
        onCreate(db);
    }
}

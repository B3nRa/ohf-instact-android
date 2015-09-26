package hackerstolz.de.instact.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hackerstolz.de.instact.data.ContactContract.ContactEntry;
import hackerstolz.de.instact.data.LabelContract;
import hackerstolz.de.instact.data.MeetingContract;


/**
 * Created by floatec on 26/09/15.
 */
public class ContactDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATETIME_TYPE = " DATETIME";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
                    ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactEntry.COLUMN_NAME_CONTACT_ID + TEXT_TYPE + COMMA_SEP +
                    ContactEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactEntry.COLUMN_NAME_XING + TEXT_TYPE + COMMA_SEP +
                    ContactEntry.COLUMN_NAME_P2P_ID + TEXT_TYPE +
    " );" +
                    "CREATE TABLE " + LabelContract.LabelEntry.TABLE_NAME + " (" +
                    LabelContract.LabelEntry._ID + " INTEGER PRIMARY KEY," +
                    LabelContract.LabelEntry.COLUMN_NAME_CONTACT_ID + TEXT_TYPE + COMMA_SEP +
                    LabelContract.LabelEntry.COLUMN_NAME_NAME + TEXT_TYPE +
                    " );"+
                    "CREATE TABLE " + MeetingContract.MeetingEntry.TABLE_NAME + " (" +
                    MeetingContract.MeetingEntry._ID + " INTEGER PRIMARY KEY," +
                    MeetingContract.MeetingEntry.COLUMN_NAME_CONTACT_ID + TEXT_TYPE + COMMA_SEP +
                    MeetingContract.MeetingEntry.COLUMN_NAME_TIME + DATETIME_TYPE +
                    " )";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME+"; DROP TABLE IF EXISTS " + LabelContract.LabelEntry.TABLE_NAME+"; DROP TABLE IF EXISTS " + MeetingContract.MeetingEntry.TABLE_NAME;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contact.db";

    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
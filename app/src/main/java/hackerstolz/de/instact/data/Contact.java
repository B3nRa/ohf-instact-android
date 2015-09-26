package hackerstolz.de.instact.data;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hackerstolz.de.instact.helper.ContactDbHelper;

/**
 * Created by floatec on 26/09/15.
 */
public class Contact {
    private String name;
    private long id;
    private String xing;
    private String p2pId;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getXing() {
        return xing;
    }

    public String getP2pId() {
        return p2pId;
    }

    public List<String> getLabels() {
        return labels;
    }

    public static ContactDbHelper mDbHelper;
    private List<String> labels=new ArrayList<String>();
    public Contact(String name,String xing,String p2pId,List <String> labels) throws Exception {
        this(0,name,xing,p2pId,labels,true);
    }


    public Contact(long id,String name,String xing,String p2pId,List <String> labels,boolean save2db) throws Exception {
        this.id=id;
        this.name=name;
        this.xing=xing;
        this.p2pId=p2pId;
        this.labels=labels;
        if (save2db) {
            this.save();
        }
    }
    public static List<Contact> find() throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected mdHelper");
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_NAME_NAME,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,

        };

// How you want the results sorted in the resulting Cursor
        String sortOrder ="";


        Cursor cursor = db.query(
                ContactContract.ContactEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        cursor.moveToFirst();
        List<Contact> all=new ArrayList<Contact>();
        int i=0;
        while (!cursor.isAfterLast())  {
            Long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_ID)
            );
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_NAME)
            );
            String p2pId = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_P2P_ID)
            );
            String xing = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_XING)
            );

            List labels=new ArrayList();
            all.add(new Contact(id,name,xing,p2pId,labels,false));
            i++;
            cursor.moveToNext();
        }
        return all;
    }
    public static List<Contact> findLabels(long id) throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected mdHelper");
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_NAME_NAME,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,

        };

// How you want the results sorted in the resulting Cursor
        String sortOrder ="";


        Cursor cursor = db.query(
                ContactContract.ContactEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        cursor.moveToFirst();
        List<Contact> all=new ArrayList<Contact>();
        int i=0;
        while (!cursor.isAfterLast())  {
            Long cid = cursor.getLong(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_ID)
            );
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_NAME)
            );
            String p2pId = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_P2P_ID)
            );
            String xing = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_XING)
            );

            List labels=new ArrayList();
            all.add(new Contact(cid,name,xing,p2pId,labels,false));
            i++;
            cursor.moveToNext();
        }
        return all;
    }


    public Contact(long id) throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected mdHelper");
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_NAME_NAME,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,

        };

// How you want the results sorted in the resulting Cursor
        String sortOrder ="";


        Cursor cursor = db.query(
                ContactContract.ContactEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        cursor.moveToFirst();
        this.name = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_NAME)
        );
        this.p2pId = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_P2P_ID)
        );
        this.xing = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_XING)
        );
        this.id=id;
    }

    public Contact(String p2pId) throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected mdHelper");
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_NAME_NAME,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,

        };

// How you want the results sorted in the resulting Cursor
        String sortOrder ="";


        Cursor cursor = db.query(
                ContactContract.ContactEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID+"="+p2pId,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        cursor.moveToFirst();
        this.name = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_NAME)
        );
        this.id = cursor.getLong(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_ID)
        );
        this.xing = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_XING)
        );

        this.p2pId=p2pId;

    }

    private void save() throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected Mdhelper");
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_ID, id);
        values.put(ContactContract.ContactEntry.COLUMN_NAME_NAME, name);
        values.put(ContactContract.ContactEntry.COLUMN_NAME_P2P_ID, p2pId);

        values.put(ContactContract.ContactEntry.COLUMN_NAME_XING, xing);


// Insert the new row, returning the primary key value of the new row
        id = db.insert(
                ContactContract.ContactEntry.TABLE_NAME,
                null,
                values);
//        deleteAllLabel();
        for (int i = 0; i <labels.size(); i++) {
//            saveLabel(labels.get(i));
        }
    }
    private void saveLabel(String name) throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected Mdhelper");
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(LabelContract.LabelEntry.COLUMN_NAME_CONTACT_ID, id);
        values.put(LabelContract.LabelEntry.COLUMN_NAME_NAME, name);


// Insert the new row, returning the primary key value of the new row
        id = db.insert(
                LabelContract.LabelEntry.TABLE_NAME,
                null,
                values);
    }
    public void addMeeting() throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected Mdhelper");
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MeetingContract.MeetingEntry.COLUMN_NAME_CONTACT_ID, id);
        values.put(MeetingContract.MeetingEntry.COLUMN_NAME_TIME, "date('now')");


// Insert the new row, returning the primary key value of the new row
        id = db.insert(
                MeetingContract.MeetingEntry.TABLE_NAME,
                null,
                values);
    }

    private void deleteAllLabel() throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected Mdhelper");
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = LabelContract.LabelEntry.COLUMN_NAME_CONTACT_ID + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };
// Issue SQL statement.
        db.delete(LabelContract.LabelEntry.TABLE_NAME, selection, selectionArgs);
    }
}

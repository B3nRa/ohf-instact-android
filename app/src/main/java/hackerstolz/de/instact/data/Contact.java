package hackerstolz.de.instact.data;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hackerstolz.de.instact.helper.ContactDbHelper;

/**
 * Created by floatec on 26/09/15.
 */
public class Contact {
    private String name;
    private int id;
    private String xing;
    private String p2pId;
    static Context context;
    ContactDbHelper mDbHelper = new ContactDbHelper(context);
    private List<Label> labels=new ArrayList<Label>();
    Contact(String name,String xing,String p2pId,List <Label> labels){
        this.name=name;
        this.xing=xing;
        this.p2pId=p2pId;
        this.labels=labels;
    }

    private void save(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_ID, id);
        values.put(ContactContract.ContactEntry.COLUMN_NAME_NAME, name);
        values.put(ContactContract.ContactEntry.COLUMN_NAME_P2P_ID, p2pId);

        values.put(ContactContract.ContactEntry.COLUMN_NAME_XING, xing);


// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                ContactContract.ContactEntry.TABLE_NAME,
                "",
                values);
    }
}

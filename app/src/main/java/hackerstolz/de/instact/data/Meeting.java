package hackerstolz.de.instact.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;


/**
 * Created by floatec on 26/09/15.
 */
@Table(name = "Meeting")
public class Meeting extends Model {
    private static final String[] COLS = new String[]
            { CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART};


    @Column(name = "Contact")
    public Contact contact;
    @Column(name = "Timestamp")
    public Date timestamp;
    public Meeting(){
        super();
    }
    public Meeting(Contact contact){
        super();
        this.contact=contact;
        this.timestamp=new Date();
    }



}


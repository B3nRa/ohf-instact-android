package hackerstolz.de.instact.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by floatec on 26/09/15.
 */
@Table(name = "Meeting")
public class Meeting extends Model {
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


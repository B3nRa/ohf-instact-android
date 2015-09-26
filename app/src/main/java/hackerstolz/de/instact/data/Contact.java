package hackerstolz.de.instact.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;


import java.util.List;

/**
 * Created by floatec on 26/09/15.
 */

@Table(name = "Contacts")
public class Contact extends Model {
    @Column(name = "Name")
    public String name;
    @Column(name = "Xing")
    public String xing;
    @Column(name = "p2pid")
    public String p2pId;
    public List<Label> labels() {
        return getMany(Label.class, "Label");
    }

    public String getName() {
        return name;
    }

    public static List<Contact> getAll() {
        return new Select()
                .from(Contact.class).execute();
    }



    public Contact(String name,String xing,String p2pId){
        super();
        this.p2pId=p2pId;
        this.xing=xing;
        this.name=name;
    }

    public Contact(){
        super();
    }


}

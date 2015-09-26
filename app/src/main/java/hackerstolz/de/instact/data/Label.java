package hackerstolz.de.instact.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by floatec on 26/09/15.
 */
@Table(name = "Labels")
public class Label extends Model {
    @Column(name = "Contact")
    public Contact contact;
    @Column(name = "Title")
    public String Title;


}

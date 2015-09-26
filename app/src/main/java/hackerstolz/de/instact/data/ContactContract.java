package hackerstolz.de.instact.data;

import android.provider.BaseColumns;

/**
 * Created by floatec on 26/09/15.
 */
public class ContactContract {
    public ContactContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String COLUMN_NAME_CONTACT_ID = "contactid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_XING = "xing";
        public static final String COLUMN_NAME_P2P_ID = "p2pid";
    }
}

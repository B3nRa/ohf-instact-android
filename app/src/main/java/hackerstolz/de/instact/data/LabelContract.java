package hackerstolz.de.instact.data;

import android.provider.BaseColumns;

/**
 * Created by floatec on 26/09/15.
 */
public class LabelContract {
    public LabelContract() {}

    /* Inner class that defines the table contents */
    public static abstract class LabelEntry implements BaseColumns {
        public static final String TABLE_NAME = "label";
        public static final String COLUMN_NAME_CONTACT_ID = "contactid";
        public static final String COLUMN_NAME_NAME = "name";
    }
}

package hackerstolz.de.instact.data;

import android.provider.BaseColumns;

/**
 * Created by floatec on 26/09/15.
 */
public class MeetingContract {
    public MeetingContract() {}

    /* Inner class that defines the table contents */
    public static abstract class MeetingEntry implements BaseColumns {
        public static final String TABLE_NAME = "meeting";
        public static final String COLUMN_NAME_CONTACT_ID = "contactid";
        public static final String COLUMN_NAME_TIME = "meeting_time";
    }
}

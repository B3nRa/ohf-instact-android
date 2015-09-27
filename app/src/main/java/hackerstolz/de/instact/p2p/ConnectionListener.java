package hackerstolz.de.instact.p2p;

import hackerstolz.de.instact.data.Contact;

/**
 * Created by muszy on 26-Sep-15.
 */
public interface ConnectionListener {
    void onConnected();
    void onNewContact(Contact contact);
}

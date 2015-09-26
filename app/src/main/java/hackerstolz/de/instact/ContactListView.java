package hackerstolz.de.instact;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import hackerstolz.de.instact.data.Contact;
import hackerstolz.de.instact.data.Label;
import hackerstolz.de.instact.tags.TagListView;

public class ContactListView extends RecyclerView.Adapter<ContactListView.ContactViewHolder> {
    private List<Contact> mContacts;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactListView(List<Contact> contacts) {
        mContacts = contacts;
    }

    public void addContact(Contact contact){
        mContacts.add(contact);
    }

    public void addContacts(List<Contact> contacts){
        mContacts.addAll(contacts);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.cardView.setText(mDataset[position]);
        Contact contact = mContacts.get(position);
        holder.contactName.setText(contact.getName());
        //TODO: add image here

        List<String> labelStrings = contact.getLabels();
        holder.mAdapter.addLabels(labelStrings);
        holder.mAdapter.notifyDataSetChanged();
        //holder.contactLabels.setText(contact.getLabels().toString());
//        holder.contactImage.setImage(null);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView contactName;
        public CircularImageView contactImage;
        public RecyclerView tagRecyclerView;

        public List<String> mLabels;

        public TagListView mAdapter;

        public ContactViewHolder(View v) {
            super(v);
            contactName = (TextView) v.findViewById(R.id.contact_name);
            contactImage = (CircularImageView) v.findViewById(R.id.contact_img);
            tagRecyclerView = (RecyclerView) v.findViewById(R.id.my_tag_recycler_view);

            mAdapter = new TagListView();
            tagRecyclerView.setAdapter(mAdapter);
//            mAdapter = new TagListView();
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ContactViewHolder(v);
    }
}
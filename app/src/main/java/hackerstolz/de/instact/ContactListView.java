package hackerstolz.de.instact;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;

import java.util.List;

import hackerstolz.de.instact.data.Contact;

public class ContactListView extends RecyclerView.Adapter<ContactListView.ContactViewHolder> {
    public static final String P2P_ID = "p2p_id";
    private List<Contact> mContacts;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactListView(List<Contact> contacts) {
        mContacts = contacts;
    }

    public void clear() {
        mContacts.clear();
    }

    public void addContact(Contact contact) {
        mContacts.add(contact);
    }

    public void addContacts(List<Contact> contacts) {
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
        Contact contact = mContacts.get(position);
        holder.contactName.setText(contact.getName());
        holder.contact = contact;
        //TODO: add image here

        List<String> labels = contact.labelList();
        LayoutInflater inflater = LayoutInflater.from(holder.mContext);
        for (String label : labels) {
            TextView tv = (TextView) inflater.inflate(R.layout.tag_list_item, holder.tagRecyclerView, false);
            tv.setText(label);
            holder.tagRecyclerView.addView(tv, new FlowLayout.LayoutParams(10, 10));
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView contactName;
        public CircularImageView contactImage;
        //        public LinearLayout tagRecyclerView;
        public FlowLayout tagRecyclerView;
        public Context mContext;
        //        private LinearLayout.LayoutManager mLayoutManager;
        public Contact contact;
//        private LinearLayout.LayoutManager mLayoutManager;

        public List<String> mLabels;

        public ContactViewHolder(View v) {
            super(v);
            contactName = (TextView) v.findViewById(R.id.contact_name);
            contactImage = (CircularImageView) v.findViewById(R.id.contact_img);
            tagRecyclerView = (FlowLayout) v.findViewById(R.id.my_tag_recycler_view);
            mContext = v.getContext();

            CircularImageView imageView = (CircularImageView) v.findViewById(R.id.contact_img);
            String p2pid = "";
            if (contact != null) {
                p2pid = contact.p2pId;
                String imageData = ImageUtils.loadImageAsBase64(p2pid);
                byte[] imageAsBytes = Base64.decode(imageData.getBytes(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                imageView.setImageBitmap(bitmap);
            }

            final String payLoadId = p2pid;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ProfileActivity.class);
                    intent.putExtra(P2P_ID, payLoadId);
                    mContext.startActivity(intent);
                }
            });

//            tagRecyclerView.setHasFixedSize(true);
//            mAdapter = new TagListView();
//            mLayoutManager = new LinearLayoutManager(v.getContext());
//            tagRecyclerView.setLayoutManager(mLayoutManager);
//            tagRecyclerView.setAdapter(mAdapter);
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

        return new ContactViewHolder(v);
    }
}

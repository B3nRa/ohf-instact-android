package hackerstolz.de.instact.tags;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;

import java.util.List;

import hackerstolz.de.instact.R;

/**
 * Created by Benjamin on 26.09.2015.
 */
public class TagListView extends RecyclerView.Adapter<TagListView.TagViewHolder>{


    private List<String> mTags;

    public TagListView(){}

    public TagListView(List<String> tags){
        mTags = tags;
    }

    public void addLabels(List<String> labels){
        mTags = labels;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class TagViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView contactLabel;

        public TagViewHolder(View v) {
            super(v);
            contactLabel = (TextView) v.findViewById(R.id.contact_labels);
        }
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TagViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.cardView.setText(mDataset[position]);
        String tag = mTags.get(position);
        holder.contactLabel.setText(tag);
        //holder.contactLabels.setText(contact.getLabels().toString());
//        holder.contactImage.setImage(null);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new TagViewHolder(v);
    }

}

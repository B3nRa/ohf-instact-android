package hackerstolz.de.instact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;

import java.util.Arrays;
import java.util.List;

import hackerstolz.de.instact.data.Meeting;

/**
 * Created by Benjamin on 27.09.2015.
 */
public class EventListView extends RecyclerView.Adapter<EventListView.EventViewHolder>{
    private List<Meeting> mEvents;

    private List<String> eventNames = Arrays.asList(new String[]{
            "IHK - Oktoberhackfest", "Refugee Hacks", "Burda Bootcamp"
    });

    private List<String> eventDates = Arrays.asList(
      new String[]{
              "24.12.2014", "28.04.2011", "22.03.2009"
      }
    );

    private List<String> images = Arrays.asList(new String[]{

    });

    public EventListView(){

    }

    public EventListView(List<Meeting> events){
        mEvents = events;
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mEvents.size();
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        Meeting event = mEvents.get(position);
        holder.mEventName.setText(eventNames.get(position));
        holder.mDate.setText(eventDates.get(position));

        int i = (int) (Math.random() * images.size());
//        String img = images.get(i);
        LayoutInflater inflater = LayoutInflater.from(holder.mContext);
        for(String image : images) {
            CircularImageView bv = (CircularImageView) inflater.inflate(R.layout.circular_image, holder.mContactImgWrapper, false);
            int resID = holder.mContext.getResources().getIdentifier(image, "drawable", "hackerstolz.de");
            bv.setImageResource(resID);
            holder.mContactImgWrapper.addView(bv, new FlowLayout.LayoutParams(10, 10));
        }
        //TODO: add image here
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);

        return new EventViewHolder(v);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mContactImgWrapper;
        TextView mEventName;
        TextView mDate;
        Context mContext;
        
        public EventViewHolder(View v){
            super(v);
            mContactImgWrapper = (LinearLayout) v.findViewById(R.id.event_contact_images);
            mEventName = (TextView) v.findViewById(R.id.event_name);
            mDate = (TextView) v.findViewById(R.id.event_date);
            mContext = v.getContext();
        }
    }
}

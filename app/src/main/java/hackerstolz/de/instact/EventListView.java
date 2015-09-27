package hackerstolz.de.instact;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;

import java.util.ArrayList;
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

    private List<String> eventAddresses = Arrays.asList(new String[]{
       "IHK, Orleansstraße 10, Munich", "To be announced, Mannheim", "Bootcamp, Karlstraße 3, Munich"
    });

    private List<String> eventDates = Arrays.asList(
      new String[]{
            "", "17", "22"
      }
    );

    private List<String> eventDatesBelow = Arrays.asList(
            new String[]{
                   "", "SEP", "OKT"
            }
    );

    private List<String> images = Arrays.asList(new String[]{
        "random_avatar_01", "random_avatar_02","random_avatar_03","random_avatar_04","random_avatar_05",
            "random_avatar_06","random_avatar_07","random_avatar_08","random_avatar_09","random_avatar_10",
            "random_avatar_11","random_avatar_12","random_avatar_13","random_avatar_14","random_avatar_15",
    });

    private List<String> numberMembers = Arrays.asList(new String[]{
            "ic_person_outline_black_01", "ic_person_outline_black_02", "ic_person_outline_black_03"
    });

    private List<String> numberMembersPlain = Arrays.asList(new String[]{
            "+17", "+42", "+74"
    });


    public EventListView(){

    }

    public EventListView(List<Meeting> events){
        mEvents = events;
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return eventNames.size();
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        Meeting event = mEvents.get(position);
        holder.mEventName.setText(eventNames.get(position));
        if(position == 0){
            Typeface font = Typeface.createFromAsset(holder.mContext.getApplicationContext().getAssets(), "fontawesome-webfont.ttf");
            holder.mDate.setTypeface(font);
            holder.mDate.setText(R.string.star_icon);
            holder.mDateBelow.setText("NOW");
        }
        else {
            holder.mDate.setText(eventDates.get(position));
            holder.mDateBelow.setText(eventDatesBelow.get(position));
        }
//        Typeface font = Typeface.createFromAsset(holder.mContext.getApplicationContext().getAssets(), "fontawesome-webfont.ttf");
//        holder.mDate.setTypeface(font);
//        holder.mDate.setText(R.string.star_icon);
        holder.mEventAddress.setText(eventAddresses.get(position));

//        String img = images.get(i);
        LayoutInflater inflater = LayoutInflater.from(holder.mContext);
        int k = 0;
        List<Integer> randoms = new ArrayList<>();
        while(k < 5) {
            int i = -1;
            do {
                 i = (int) (Math.random() * images.size());
            }while (randoms.contains(i));
            randoms.add(i);

            String image = images.get(i);
            CircularImageView bv = (CircularImageView) inflater.inflate(R.layout.circular_image, holder.mContactImgWrapper, false);
            int resID = holder.mContext.getApplicationContext().getResources().getIdentifier(image, "drawable", "hackerstolz.de.instact");
//            bv.setBackgroundResource(resID);
            bv.setImageResource(resID);
            holder.mContactImgWrapper.addView(bv);
            k++;
        }

        TextView numberMember = (TextView) inflater.inflate(R.layout.number_member_view_plain, holder.mContactImgWrapper, false);
        numberMember.setText(numberMembersPlain.get(position));
        holder.mContactImgWrapper.addView(numberMember);
//        ImageView imageView = (ImageView) inflater.inflate(R.layout.number_member_view, holder.mContactImgWrapper, false);
//        int resID = holder.mContext.getApplicationContext().getResources().getIdentifier(numberMembers.get(position), "drawable", "hackerstolz.de.instact");
//        imageView.setImageResource(resID);
//        holder.mContactImgWrapper.addView(imageView);
        //TODO: add image here
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return new EventViewHolder(v);
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mContactImgWrapper;
        TextView mEventName;
        TextView mEventAddress;
        TextView mDate;
        TextView mDateBelow;
        Context mContext;
        
        public EventViewHolder(View v){
            super(v);
            mContactImgWrapper = (LinearLayout) v.findViewById(R.id.event_contact_images);
            mEventName = (TextView) v.findViewById(R.id.event_name);
            mEventAddress = (TextView) v.findViewById(R.id.event_address);
            mDate = (TextView) v.findViewById(R.id.event_date);
            mDateBelow = (TextView) v.findViewById(R.id.event_date_below);
            mContext = v.getContext();
        }
    }
}

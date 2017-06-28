package amrith.com.volunteers.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import amrith.com.volunteers.R;
import amrith.com.volunteers.models.Event;
import amrith.com.volunteers.models.Feed;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amrith on 6/28/17.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ItemViewHolder> {
    private List<Event> eventList;
    private Context context;

    public EventListAdapter(Context con, List<Event> events) {
        context = con;
        eventList = events;
    }

    @Override
    public EventListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.feed, parent, false);
        return new EventListAdapter.ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(EventListAdapter.ItemViewHolder holder, int position) {
        Event event=eventList.get(position);
//        Picasso.with(context).load(feed.ownerImage).fit().error(R.drawable.icon).into(holder.profilePic);
        holder.userName.setText(feed.ownerName);
        holder.desc.setText(feed.desc);
        holder.time.setText(feed.updatedAt.substring(0,10));
        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Viewed This Feed", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(eventList!=null)
            return eventList.size();
        else return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.profileImage)
        ImageView profilePic;

        @BindView(R.id.userName)
        TextView userName;

        @BindView(R.id.timeStamp)
        TextView time;

        @BindView(R.id.description)
        TextView desc;

        @BindView(R.id.done)
        Button done;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}

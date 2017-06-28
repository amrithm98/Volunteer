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
    private ItemClickListener clickListener;

    public EventListAdapter(Context con, List<Event> events) {
        context = con;
        eventList = events;
    }

    @Override
    public EventListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.event, parent, false);
        return new EventListAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventListAdapter.ItemViewHolder holder, int position) {
        Event event=eventList.get(position);
        holder.eventName.setText(event.name);
    }

    @Override
    public int getItemCount() {
        if(eventList!=null)
            return eventList.size();
        else return 0;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.description)
        TextView eventName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(v, getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener {
        public void onClick(View view, int position);
    }
}

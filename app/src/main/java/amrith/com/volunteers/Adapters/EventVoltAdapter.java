package amrith.com.volunteers.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import amrith.com.volunteers.R;
import amrith.com.volunteers.models.Event;
import amrith.com.volunteers.models.EventVolt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amrith on 7/13/17.
 */

public class EventVoltAdapter extends RecyclerView.Adapter<EventVoltAdapter.ItemViewHolder> {

    List<EventVolt> voltList;
    Context context;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.team_volt, parent, false);
        return new EventVoltAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
//        Event event=eventList.get(position);
//        holder.eventName.setText(event.name);
    }

    @Override
    public int getItemCount() {
        if(voltList!=null)
            return voltList.size();
        else return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.event_name)
        TextView eventName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}

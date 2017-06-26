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
import amrith.com.volunteers.Utils.Global;
import amrith.com.volunteers.models.Feed;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amrith on 6/26/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ItemViewHolder> {

    private List<Feed> feedList;
    private Context context;

    public FeedAdapter(Context con, List<Feed> feeds) {
        context = con;
        feedList = feeds;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.feed, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Feed feed=feedList.get(position);
        Picasso.with(context).load(feed.ownerImage).fit().error(R.drawable.icon).into(holder.profilePic);
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
        return feedList.size();
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

package amrith.com.volunteers.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public interface ItemClickListener {
        public void onClick(View view, int position);
    }

    private ItemClickListener clickListener;
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
        Feed item=feedList.get(position);
        //Uri uri = Uri.fromFile(new File(images.get(position).getDataPath()));
//        Picasso.with(context).load(url.substring(0,url.length()-1)+item.image).fit().error(R.drawable.sample).into(holder.ivItem);
//        holder.tvItem.setText(item.name);
//        String price= "₹"+String.valueOf(item.price);
//        holder.tvPrice.setText(price);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.profileImage)
        ImageView profilePic;

        @BindView(R.id.userName)
        TextView userName;

        @BindView(R.id.timeStamp)
        TextView time;

        @BindView(R.id.description)
        TextView desc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, getAdapterPosition());
            }
        }
    }

}

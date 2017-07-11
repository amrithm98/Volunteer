package amrith.com.volunteers.Adapters;

/**
 * Created by amrith on 7/11/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import amrith.com.volunteers.R;
import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.Admin;
import butterknife.BindView;
import butterknife.ButterKnife;


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
import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.Feed;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amrith on 6/28/17.
 */

public class VoltListAdapter extends RecyclerView.Adapter<VoltListAdapter.ItemViewHolder> {
    private List<Admin> adminList;
    private Context context;
    private ItemClickListener clickListener;

    public VoltListAdapter(Context con, List<Admin> admins) {
        context = con;
        adminList = admins;
    }

    @Override
    public VoltListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.volunteer, parent, false);
        return new VoltListAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VoltListAdapter.ItemViewHolder holder, final int position) {
        Admin admin=adminList.get(position);
        holder.voltName.setText(admin.name);
        Picasso.with(context).load(admin.picture).fit().error(R.drawable.icon).into(holder.voltImage);
        holder.addVolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                removeAdmin(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(adminList!=null)
            return adminList.size();
        else return 0;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void removeAdmin(int position)
    {
        adminList.remove(position);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.volt_image)
        ImageView voltImage;

        @BindView(R.id.volt_name)
        TextView voltName;

        @BindView(R.id.add_volt)
        Button addVolt;

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


package amrith.com.volunteers.Adapters;

/**
 * Created by amrith on 7/11/17.
 */

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import amrith.com.volunteers.Helper;
import amrith.com.volunteers.R;
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.Global;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.College;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import amrith.com.volunteers.R;
import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.Feed;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                showPopup(position);
            }
        });
    }

    public void showPopup(int pos){

        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.team_access_dialog);
        dialog.setTitle("Team-Access Dialog");
        final Spinner teamSpinner=(Spinner)dialog.findViewById(R.id.spinner_team);
        final Spinner accessSpinner=(Spinner)dialog.findViewById(R.id.spinner_access);
        Button submit=(Button)dialog.findViewById(R.id.addToTeam);
        final EditText etWork=(EditText)dialog.findViewById(R.id.et_work);
        final int currentPosition=pos;

        ArrayAdapter<String> team_list=new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item, Global.teamList);
        teamSpinner.setAdapter(team_list);

        ArrayAdapter<String> access_list=new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item, Global.accessList);
        accessSpinner.setAdapter(access_list);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String team=teamSpinner.getSelectedItem().toString();
                String access=accessSpinner.getSelectedItem().toString();
                String work=etWork.getText().toString();
                if(work==null || work.isEmpty())
                {
                    etWork.setError("Please Assign Some Duty!");
                    return;
                }
                String table="";
                int level=8;

                switch (access){
                    case "Super Admin":
                        level=10;
                        break;

                    case "Admin":
                        level=9;
                        break;

                    case "Volunteer":
                        level=8;
                        break;

                    default:
                        level=0;
                        break;
                }

                switch (team){
                    case "Accomodation":
                        table="Accomodation";
                        break;

                    case "Food and Venue":
                        table="Food_and_Venue";
                        break;

                    case "Publicity":
                        table="Publicity";
                        break;

                    case "Registration":
                        table="Registration";
                        break;

                    case "Sessions":
                        table="Sessions";
                        break;

                    case "Sponsorship":
                        table="Sponsorship";
                        break;

                    default:
                        Toast.makeText(context,"No Team Selected",Toast.LENGTH_SHORT);
                        return;
                }
                Toast.makeText(context,table,Toast.LENGTH_SHORT).show();
                addVolunteer(table,level,currentPosition,work);
            }
        });

        dialog.show();

    }

    public void addVolunteer(final String table, final int level, int pos,String work)
    {
        final Admin admin=adminList.get(pos);
        final String desc=work;
        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                RestApiInterface service = ApiClient.getService();
                Call<String> call=service.addVolunteerToTeam(table,token,admin.uid,Global.eventId,level,desc,Global.name,Global.picture,0);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code()==200){
                            Toast.makeText(context,"Added To Team",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context,"Network Error",Toast.LENGTH_SHORT).show();
                    }
                });
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


package amrith.com.volunteers.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.Global;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.Event;
import amrith.com.volunteers.models.EventVolt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by amrith on 7/13/17.
 */

public class EventVoltAdapter extends RecyclerView.Adapter<EventVoltAdapter.ItemViewHolder> {

    List<EventVolt> voltList;
    Context context;
    Admin currentAdmin;
    String tableName;

    public EventVoltAdapter(Context con, List<EventVolt> eventVoltList,String table) {
        context = con;
        voltList = eventVoltList;
        tableName=table;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.team_volt, parent, false);
        return new EventVoltAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final EventVolt eventVolt=voltList.get(position);
        Admin admin=getAdminDetails(eventVolt.uid);
        holder.voltName.setText(admin.name);
        Picasso.with(context).load(admin.picture).into(holder.voltImage);
        holder.viewVolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(context).setTitle("Task").setMessage(eventVolt.work).show();
            }
        });

        holder.finishTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(context).setTitle("Confirm").setMessage("Are you sure the task is complete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
                                    @Override
                                    public void tokenObtained(String token) {
                                        RestApiInterface service = ApiClient.getService();
                                        Call<String> call=service.updateCompletion(tableName,token,eventVolt.id);
                                        call.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                if(response.code()==200)
                                                {
                                                    Toast.makeText(context,"Task Completed",Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {

                                            }
                                        });
                                    }
                                });
                            }
                        }).show();
            }
        });

    }

    public Admin getAdminDetails(String uid){

        final String Uid=uid;
        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                RestApiInterface service = ApiClient.getService();
                Call<Admin> call=service.getAdmin(Uid,token);
                call.enqueue(new Callback<Admin>() {
                    @Override
                    public void onResponse(Call<Admin> call, Response<Admin> response) {
                        if(response.code()==200)
                        {
                           currentAdmin=response.body();
                        }
                    }
                    @Override
                    public void onFailure(Call<Admin> call, Throwable t) {

                    }
                });
            }
        });
        return currentAdmin;
    }

    @Override
    public int getItemCount() {
        if(voltList!=null)
            return voltList.size();
        else return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.volt_name)
        TextView voltName;

        @BindView(R.id.volt_image)
        ImageView voltImage;

        @BindView(R.id.view_volt)
        Button viewVolt;

        @BindView(R.id.finish)
        Button finishTask;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}

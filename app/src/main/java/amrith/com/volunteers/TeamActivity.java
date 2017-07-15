package amrith.com.volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import amrith.com.volunteers.Adapters.EventVoltAdapter;
import amrith.com.volunteers.Adapters.VoltListAdapter;
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.Global;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.College;
import amrith.com.volunteers.models.EventVolt;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamActivity extends AppCompatActivity {

    @BindView(R.id.team_name)
    TextView teamName;

    @BindView(R.id.rv_volunteers)
    RecyclerView rvVolunteers;

    List<EventVolt> eventVoltList;

    EventVoltAdapter eventVoltAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        int teamId=intent.getIntExtra("team",0);
        setContentView(R.layout.activity_team);
        ButterKnife.bind(this);
        teamName.setText(Global.teamList.get(teamId));
        getTeamDetails(teamId);
    }

    public void getTeamDetails(int teamId)
    {
        final int teamid=teamId;
        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                RestApiInterface service = ApiClient.getService();
                Call<List<EventVolt>> call=service.getVoltsInTeam(Global.teamListApi.get(teamid),Global.eventId,token);
                call.enqueue(new Callback<List<EventVolt>>() {
                    @Override
                    public void onResponse(Call<List<EventVolt>> call, Response<List<EventVolt>> response) {
                        Toast.makeText(getApplicationContext(),"POST",Toast.LENGTH_SHORT).show();
                        Log.d("nothing","Nothing");
                        List<EventVolt> eventVolts =response.body();
                        eventVoltList=eventVolts;
                        Log.d("eventVolt",String.valueOf(eventVolts.get(0).name));
                        eventVoltAdapter=new EventVoltAdapter(getApplicationContext(),eventVoltList,Global.teamListApi.get(teamid),TeamActivity.this);
                        rvVolunteers.setAdapter(eventVoltAdapter);
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                        rvVolunteers.setLayoutManager(linearLayoutManager);
                        eventVoltAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<List<EventVolt>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Failed . Try Later",Toast.LENGTH_SHORT).show();
                        Log.d("Error",t.toString());
                    }
                });
            }
        });

    }
}

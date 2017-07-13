package amrith.com.volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import amrith.com.volunteers.Utils.Global;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamActivity extends AppCompatActivity {

    @BindView(R.id.team_name)
    TextView teamName;

    @BindView(R.id.rv_volunteers)
    RecyclerView rvVolunteers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        int teamId=intent.getIntExtra("team",0);
        setContentView(R.layout.activity_team);
        ButterKnife.bind(this);
        teamName.setText(Global.teamList.get(teamId));

    }
}

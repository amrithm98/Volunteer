package amrith.com.volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Manage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.accomodation)
    public void accomodation()
    {
        Intent intent=new Intent(Manage.this,TeamActivity.class);
        intent.putExtra("team",0);
        startActivity(intent);
    }

    @OnClick(R.id.food_venue)
    public void foodVenue()
    {
        Intent intent=new Intent(Manage.this,TeamActivity.class);
        intent.putExtra("team",1);
        startActivity(intent);
    }

    @OnClick(R.id.publicity)
    public void publicity()
    {
        Intent intent=new Intent(Manage.this,TeamActivity.class);
        intent.putExtra("team",2);
        startActivity(intent);
    }

    @OnClick(R.id.sessions)
    public void sessions()
    {
        Intent intent=new Intent(Manage.this,TeamActivity.class);
        intent.putExtra("team",3);
        startActivity(intent);
    }

    @OnClick(R.id.sponsor)
    public void sponsorship()
    {
        Intent intent=new Intent(Manage.this,TeamActivity.class);
        intent.putExtra("team",4);
        startActivity(intent);
    }

    @OnClick(R.id.reg)
    public void regn()
    {
        Intent intent=new Intent(Manage.this,TeamActivity.class);
        intent.putExtra("team",5);
        startActivity(intent);
    }
}

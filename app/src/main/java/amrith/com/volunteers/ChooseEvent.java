package amrith.com.volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import amrith.com.volunteers.Adapters.EventListAdapter;
import amrith.com.volunteers.Adapters.FeedAdapter;
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.Global;
import amrith.com.volunteers.Utils.ProgressDialog;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.College;
import amrith.com.volunteers.models.Event;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseEvent extends AppCompatActivity implements EventListAdapter.ItemClickListener{

    @BindView(R.id.rv_event)
    RecyclerView rvEvent;

    public List<Event> eList;

    public EventListAdapter eventListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_event);
        ButterKnife.bind(this);
        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                RestApiInterface service = ApiClient.getService();
                Call<List<Event>> call=service.getEventList(token, Global.uid);
                call.enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        eList= (ArrayList<Event>) response.body();
                        if(eList.size()==0)
                        {
                            startActivity(new Intent(ChooseEvent.this,Helper.class));
                        }
                        eventListAdapter=new EventListAdapter(getApplicationContext(),eList);
                        rvEvent.setAdapter(eventListAdapter);
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                        rvEvent.setLayoutManager(linearLayoutManager);
                        eventListAdapter.setClickListener(ChooseEvent.this);
                        eventListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        Log.d("FailItems",t.toString());
                        Toast.makeText(getApplicationContext(),"Unable to Load",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    public void onClick(View view, int position) {
        Event event=eList.get(position);
        int eventId=event.id;
        Global.eventId=eventId;
        startActivity(new Intent(ChooseEvent.this,MainActivity.class));
    }
}

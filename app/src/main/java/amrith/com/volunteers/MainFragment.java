package amrith.com.volunteers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import amrith.com.volunteers.Adapters.FeedAdapter;
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.Global;
import amrith.com.volunteers.Utils.ProgressDialog;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.Feed;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * Created by amrith on 6/18/17.
 */

public class MainFragment extends Fragment implements FeedAdapter.ItemClickListener{

    @BindView(R.id.rv_feed)
    RecyclerView rvFeed;

    List<Feed> feedList;

    FeedAdapter feedAdapter;

    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_feed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Feed");
        feedList=new ArrayList<>();
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.showProgressDialog();
        getFeed();
    }

    @Override
    public void onClick(View view, int position) {

    }

    public void getFeed()
    {
        final RestApiInterface service = ApiClient.getService();
        Global.eventId=2;
        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                Call<List<Feed>> call= service.getAllFeed(token, Global.eventId);
                call.enqueue(new Callback<List<Feed>>() {
                    @Override
                    public void onResponse(Call<List<Feed>> call, Response<List<Feed>> response) {
                        progressDialog.disMissProgressDialog();
                        List<Feed> feeds = (List<Feed>) response.body();
                        feedList=feeds;
                        feedAdapter=new FeedAdapter(getActivity(),feedList);
                        rvFeed.setAdapter(feedAdapter);
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                        rvFeed.setLayoutManager(linearLayoutManager);
                        feedAdapter.setClickListener(MainFragment.this);
                        feedAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<Feed>> call, Throwable t) {
                        progressDialog.disMissProgressDialog();
                        Log.d("FailItems",t.toString());
                        Toast.makeText(getActivity(),"Unable to Load",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}

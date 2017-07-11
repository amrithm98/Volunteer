package amrith.com.volunteers.Fragments;

/**
 * Created by amrith on 7/9/17.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import amrith.com.volunteers.Adapters.FeedAdapter;
import amrith.com.volunteers.Adapters.VoltListAdapter;
import amrith.com.volunteers.R;
import amrith.com.volunteers.RegisterActivity;
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.ProgressDialog;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.Admin;
import amrith.com.volunteers.models.College;
import amrith.com.volunteers.models.Feed;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddVolunteer extends Fragment{

    @BindView(R.id.clg_spinner)
    Spinner spinner;

    @BindView(R.id.rv_volt)
    RecyclerView rvVolt;

    List<Admin> adminList;

    ProgressDialog progressDialog;

    HashMap<String ,Integer> collegeList;

    VoltListAdapter voltListAdapter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Volunteers to Your Event");
        getColleges();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_add_volunteer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void getColleges()
    {
        RestApiInterface service = ApiClient.getService();
        Call<List<College>> call=service.getCollegeList();
        call.enqueue(new retrofit2.Callback<List<College>>() {
            @Override
            public void onResponse(Call<List<College>> call, retrofit2.Response<List<College>> response) {
                ArrayList<College> cList= (ArrayList<College>) response.body();
                ArrayList<String> cnames=new ArrayList<String>();
                collegeList=new HashMap<String, Integer>();
                for(College c: cList){
                    cnames.add(c.name);
                    collegeList.put(c.name,c.id);
                }
                ArrayAdapter<String> list=new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,cnames);
                spinner.setAdapter(list);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String college=spinner.getItemAtPosition(position).toString();
                        getPeople(collegeList.get(college));
                        adminList=new ArrayList<>();
                        progressDialog=new ProgressDialog(getActivity());
                        progressDialog.showProgressDialog();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<College>> call, Throwable t) {
            }
        });
    }

    public void getPeople(final int collegeId)
    {

        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                RestApiInterface service = ApiClient.getService();
                Call<List<Admin>> call=service.getPeopleInCollege(token,collegeId);
                call.enqueue(new Callback<List<Admin>>() {
                    @Override
                    public void onResponse(Call<List<Admin>> call, Response<List<Admin>> response) {
                        progressDialog.disMissProgressDialog();
                        if(response.code()==200)
                        {
                            Toast.makeText(getActivity(),"Got Volunteers in the college",Toast.LENGTH_SHORT).show();
                            List<Admin> admins = (List<Admin>) response.body();
                            adminList=admins;
                            voltListAdapter=new VoltListAdapter(getActivity(),adminList);
                            rvVolt.setAdapter(voltListAdapter);
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                            rvVolt.setLayoutManager(linearLayoutManager);
                            voltListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Admin>> call, Throwable t) {
                        progressDialog.disMissProgressDialog();

                    }
                });
            }
        });

    }
}

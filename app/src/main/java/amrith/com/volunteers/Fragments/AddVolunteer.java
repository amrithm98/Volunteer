package amrith.com.volunteers.Fragments;

/**
 * Created by amrith on 7/9/17.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import amrith.com.volunteers.R;
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.models.College;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;


public class AddVolunteer extends Fragment{

    @BindView(R.id.clg_spinner)
    Spinner spinner;

    HashMap<String ,Integer> collegeList;

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
                ArrayAdapter<String> list=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,cnames);
                spinner.setAdapter(list);
            }

            @Override
            public void onFailure(Call<List<College>> call, Throwable t) {
            }
        });
    }
}

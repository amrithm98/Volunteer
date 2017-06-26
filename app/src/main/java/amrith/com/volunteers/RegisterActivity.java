package amrith.com.volunteers;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.ProgressDialog;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.College;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.onClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.phone)
    EditText etPhone;

    @BindView(R.id.cname)
    AutoCompleteTextView collegeName;

    @BindView(R.id.addClg)
    Button addClg;

    ProgressDialog progressDialog;
    HashMap<String,Integer> collegeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        progressDialog=new ProgressDialog(this);
        loadTextView();


    }
    public void loadTextView()
    {
        progressDialog.showProgressDialog();
        RestApiInterface service =ApiClient.getService();
        Call<List<College>> call=service.getCollegeList();
        call.enqueue(new retrofit2.Callback<List<College>>() {
            @Override
            public void onResponse(Call<List<College>> call, retrofit2.Response<List<College>> response) {
                addClg.setVisibility(View.GONE);
                progressDialog.disMissProgressDialog();
                ArrayList<College> cList= (ArrayList<College>) response.body();
                ArrayList<String> cnames=new ArrayList<String>();
                collegeList=new HashMap<String, Integer>();
                for(College c: cList){
                    cnames.add(c.name);
                    collegeList.put(c.name,c.id);
                }
                ArrayAdapter<String> list=new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown,cnames);
                collegeName.setDropDownBackgroundResource(R.color.colorPrimary);
                collegeName.setAdapter(list);
                collegeName.setThreshold(0);
            }

            @Override
            public void onFailure(Call<List<College>> call, Throwable t) {
                progressDialog.disMissProgressDialog();
            }
        });
    }

    @OnClick(R.id.addClg)
    public void addCollege()
    {
        final String college =collegeName.getText().toString();
        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                progressDialog.showProgressDialog();
                RestApiInterface service =ApiClient.getService();
                Call<College> call=service.addCollege(token,college);
                call.enqueue(new retrofit2.Callback<College>() {
                    @Override
                    public void onResponse(Call<College> call, retrofit2.Response<College>response) {
                        Toast.makeText(getApplicationContext(),"Added College Name",Toast.LENGTH_SHORT).show();
                        loadTextView();
                    }
                    @Override
                    public void onFailure(Call<College> call, Throwable t) {
                        progressDialog.disMissProgressDialog();
                    }
                });
            }
        });
    }

    @OnClick(R.id.register)
    public void register()
    {
        final String college =collegeName.getText().toString();
        if(collegeList.get(college)==null){
            addClg.setVisibility(View.VISIBLE);
            return;
        }

        final String number=etPhone.getText().toString();
        if(number.length()!=10){
            etPhone.setError("Enter a valid number");
            return;
        }

        final RestApiInterface service = ApiClient.getService();

        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                Call<String> call=service.register(token,number,collegeList.get(college),true);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code()==200)
                        {
                            Toast.makeText(getApplicationContext(),"Successfully Registed",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error while posting",Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Failed to register",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

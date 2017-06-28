package amrith.com.volunteers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

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

public class ChooseEvent extends AppCompatActivity {

    @BindView(R.id.rv_event)
    RecyclerView rvEvent;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_event);
        ButterKnife.bind(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.showProgressDialog();
        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                RestApiInterface service = ApiClient.getService();
                Call<List<Event>> call=service.getEventList(token, Global.uid);

            }
        });


    }
}

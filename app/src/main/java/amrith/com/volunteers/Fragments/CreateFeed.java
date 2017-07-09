package amrith.com.volunteers.Fragments;

/**
 * Created by amrith on 7/9/17.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import amrith.com.volunteers.R;
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.Global;
import amrith.com.volunteers.Utils.ProgressDialog;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.College;
import amrith.com.volunteers.models.Feed;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFeed extends Fragment{

    @BindView(R.id.description)
    EditText etDesc;

    private FirebaseAuth mAuth;

    public ProgressDialog progressDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Give an Update!");
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_create_feed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.post)
    public void post(){
        progressDialog.showProgressDialog();
        final String desc=etDesc.getText().toString();
        if(desc.isEmpty()){
            etDesc.setError("Cannot Be Empty");
            return;
        }
        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                progressDialog.showProgressDialog(R.string.posting,false);
                RestApiInterface service = ApiClient.getService();
                Call<Feed> call=service.newFeed(token,desc,Global.name ,Global.picture,Global.uid,Global.eventId);
                call.enqueue(new Callback<Feed>() {
                    @Override
                    public void onResponse(Call<Feed> call, Response<Feed> response) {
                        progressDialog.disMissProgressDialog();
                        if(response.code()==200){
                            Snackbar.make(getView(), "Posted Update", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                        else {
                            Snackbar.make(getView(), "Couldn't Post Update. Try later!", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<Feed> call, Throwable t) {
                        progressDialog.disMissProgressDialog();
                        Snackbar.make(getView(), "Network Error. Try Later!", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                });
            }
        });

    }
}

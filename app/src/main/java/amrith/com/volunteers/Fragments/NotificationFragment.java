package amrith.com.volunteers.Fragments;

/**
 * Created by amrith on 7/15/17.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import amrith.com.volunteers.R;
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.ProgressDialog;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment{

    @BindView(R.id.et_topic)
    EditText etTopic;

    @BindView(R.id.et_message)
    EditText etMessage;

    ProgressDialog progressDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Send Notification");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_notification_frag, container, false);
        ButterKnife.bind(this, view);
        progressDialog=new ProgressDialog(getActivity());
        return view;

    }
    @OnClick(R.id.notif)
    public void sendNotification()
    {
        final String topic=etTopic.getText().toString();
        if(topic ==null || topic.isEmpty())
        {
            etTopic.setError("Please Enter a Topic");
            return;
        }

        final String message=etMessage.getText().toString();
        if(message ==null || topic.isEmpty())
        {
            etMessage.setError("Please Enter some Text");
            return;
        }
        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                RestApiInterface service= ApiClient.getService();
                Call<String> call=service.sendNotification(token,topic,message);
                progressDialog.showProgressDialog(R.string.notif,false);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        progressDialog.disMissProgressDialog();
                        if(response.code()==200)
                        {
                            Snackbar.make(getView(), "Notification Sent", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.disMissProgressDialog();
                        Snackbar.make(getView(), "Failed To Send", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                });

            }
        });
    }




}

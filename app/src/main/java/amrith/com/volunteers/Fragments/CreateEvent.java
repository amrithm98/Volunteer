package amrith.com.volunteers.Fragments;

/**
 * Created by amrith on 7/9/17.
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import amrith.com.volunteers.MainActivity;
import amrith.com.volunteers.NewEvent;
import amrith.com.volunteers.R;
import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.Event;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class CreateEvent extends Fragment{

    @BindView(R.id.et_name)
    EditText eventName;

    @BindView(R.id.et_fee)
    EditText eventFee;

    @BindView(R.id.et_date)
    EditText eventDate;

    @BindView(R.id.et_time)
    EditText eventTime;

    @BindView(R.id.et_desc)
    EditText eventDesc;

    @BindView(R.id.addImg)
    Button addImage;

    @BindView(R.id.upimage)
    ImageView upimage;

    private static final int RESULT_LOAD_IMAGE=1;

    private FirebaseAuth mAuth;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Feed");
        mAuth = FirebaseAuth.getInstance();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_create_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //URI HERE!!!!
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                upimage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.newEvent)
    public void addEvent()
    {
        final String name =eventName.getText().toString();
        if(name.isEmpty()){
            eventName.setError("Required Field");
            return;
        }

        final String fee =eventFee.getText().toString();
        if(fee.isEmpty()){
            eventFee.setError("Required Field");
            return;
        }

        final String desc =eventDesc.getText().toString();
        if(desc.isEmpty()){
            eventDesc.setError("Required Field");
            return;
        }

        final String date =eventDate.getText().toString();
        if(date.isEmpty()){
            eventDate.setError("Required Field");
            return;
        }

        final String time =eventTime.getText().toString();
        if(time.isEmpty()){
            eventTime.setError("Required Field");
            return;
        }

        final RestApiInterface service = ApiClient.getService();

        TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
            @Override
            public void tokenObtained(String token) {
                final FirebaseUser user = mAuth.getCurrentUser();
                String uid=user.getUid();
                Log.d("Userid",uid);
                Call<Event> call=service.createEvent(token,name,fee,desc,date,time,uid);
                call.enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        if(response.code()==200)
                        {
                            Snackbar.make(getCurrentFocus(), "Successfully Created!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            startActivity(new Intent(getActivity(),MainActivity.class));
                        }
                        else {
                            Snackbar.make(getCurrentFocus(), "Creating an Event Failed", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                        Snackbar.make(getCurrentFocus(), "Network Error", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });
    }

    @OnClick(R.id.addImg)
    public void setEventImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
    }
}

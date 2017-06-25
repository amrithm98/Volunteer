package amrith.com.volunteers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

public class NewEvent extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();


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
                            startActivity(new Intent(NewEvent.this,MainActivity.class));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //URI HERE!!!!
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                upimage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package amrith.com.volunteers;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
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

    @BindView(R.id.college)
    EditText etCollege;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.register)
    public void register()
    {
        final String college =etCollege.getText().toString();
        if(college.isEmpty()){
            etCollege.setError("Required Field");
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
                Call<String> call=service.register(token,number,college,true);
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

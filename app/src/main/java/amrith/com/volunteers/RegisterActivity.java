package amrith.com.volunteers;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.onClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.phone)
    TextInputEditText etPhone;

    @BindView(R.id.college)
    TextInputEditText etCollege;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_register);
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
    }
}

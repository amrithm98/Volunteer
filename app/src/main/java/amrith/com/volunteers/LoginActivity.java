package amrith.com.volunteers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void login(View v)
    {
        Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
}

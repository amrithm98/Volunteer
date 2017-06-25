package amrith.com.volunteers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import amrith.com.volunteers.Utils.ApiClient;
import amrith.com.volunteers.Utils.Global;
import amrith.com.volunteers.Utils.RestApiInterface;
import amrith.com.volunteers.Utils.TokenUtil;
import amrith.com.volunteers.models.Admin;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    @BindView(R.id.gPlus)
    ImageView gPlus;

    @BindView(R.id.et_user)
    TextInputLayout userName;

    @BindView(R.id.et_pass)
    TextInputLayout passWord;

    @BindView(R.id.login)
    Button login;

    boolean flag = true;
    public static boolean autoLogin=true;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "GoogleActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//
        FirebaseMessaging.getInstance().subscribeToTopic("volunteers");
        FirebaseMessaging.getInstance().subscribeToTopic("events");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.g_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    Global.name=user.getDisplayName();
                    Global.picture=user.getPhotoUrl().toString();
                    Global.uid=user.getUid();
                    Global.email=user.getEmail();
                    Log.d("emailID",user.getEmail());
                    // Admin is signed in
                    Global.uid=user.getUid();
                    Global.user=user.getDisplayName();
                    final RestApiInterface service = ApiClient.getService();
                    TokenUtil.getFirebaseToken(new TokenUtil.Listener() {
                        @Override
                        public void tokenObtained(String token) {
                            Log.d("idToken",token);
                            Call<Admin> call=service.login(token);
                            call.enqueue(new Callback<Admin>() {
                                @Override
                                public void onResponse(Call <Admin> call, Response<Admin> response) {
                                    if(response.code()==200) {
                                        Admin user = response.body();
                                        autoLogin=true;
                                        Log.d("user","success");
                                        Toast.makeText(LoginActivity.this,"Logged In",Toast.LENGTH_SHORT).show();
                                        SharedPreferences sharedPreferences = getSharedPreferences("drishti", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.commit();
                                        Log.d("registed",String.valueOf(user.registered));
                                        if (user.registered) {
//                                            Global.college = user.college;
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        } else {
//                                            if (autoLogin) {
//                                                FirebaseAuth.getInstance().signOut();
//                                                autoLogin = false;
//                                            } else {
                                                //Register
                                                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                                                finish();
//                                            }
                                        }
                                    }else{
                                        Toast.makeText(LoginActivity.this,"Network Error",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Admin> call, Throwable t) {
                                    Log.d("Login","Fail");
                                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                }
                            });
                        }
                    });

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // Admin is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void login(View v)
    {
        Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_SHORT).show();
        autoLogin=false;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
//        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                Log.d(TAG, "sucess");
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.d(TAG, result.getStatus().toString());
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

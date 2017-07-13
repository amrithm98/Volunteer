package amrith.com.volunteers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import amrith.com.volunteers.Fragments.ChatFragment;
import amrith.com.volunteers.Fragments.MainFragment;
import amrith.com.volunteers.Utils.Global;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
        public NavigationView navigationView;
        public TextView navName,navMail;
        public ImageView navImage;
        public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSharedPref();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        navImage=(ImageView)hView.findViewById(R.id.imageView);
        navName=(TextView)hView.findViewById(R.id.nav_name);
        navMail=(TextView)hView.findViewById(R.id.nav_mail);
        navName.setText(Global.name);
        navMail.setText(Global.email);
        Picasso.with(getApplicationContext()).load(Global.picture).into(navImage);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Create An Event", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this,Helper.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_feed);
    }

    public void loadSharedPref()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(Global.SHARED_PREF, Context.MODE_PRIVATE);
        Global.uid=sharedPreferences.getString("uid","UID");
        Global.name=sharedPreferences.getString("name","name");
        Global.picture=sharedPreferences.getString("picture","picture");
        Global.email=sharedPreferences.getString("email","email");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id)
    {
        Fragment fragment=null;

        switch (id)
        {
            case R.id.nav_feed:
                fragment=new MainFragment();
                break;
            case R.id.nav_myEvents:
                startActivity(new Intent(MainActivity.this,ChooseEvent.class));
                break;
            case R.id.nav_forum:
                fragment=new ChatFragment();
                break;
            case R.id.nav_manage:
                break;
        }

        if(fragment!=null)
        {
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id=item.getItemId();

        if(id==R.id.nav_logout)
        {
            LoginActivity.autoLogin=false;
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));

        }
        else
        {
            displaySelectedScreen(id);
        }

        return true;
    }


}

package amrith.com.volunteers;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import amrith.com.volunteers.Fragments.AddVolunteer;
import amrith.com.volunteers.Fragments.CreateEvent;
import amrith.com.volunteers.Fragments.CreateFeed;

public class Helper extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment=null;
                        switch (item.getItemId()) {

                            case R.id.action_event:
                                fragment=new CreateEvent();
                                break;
                            case R.id.action_feed:
                                fragment=new CreateFeed();
                                break;
                            case R.id.action_addVol:
                                fragment=new AddVolunteer();
                                break;
                        }
                        if(fragment!=null)
                        {
                            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_helper,fragment);
                            ft.commit();
                        }
                        return true;
                    }
                });
    }
}

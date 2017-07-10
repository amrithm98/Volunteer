package amrith.com.volunteers.Fragments;

/**
 * Created by amrith on 7/9/17.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import amrith.com.volunteers.R;
import butterknife.ButterKnife;

public class AddVolunteer extends Fragment{
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_add_volunteer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}

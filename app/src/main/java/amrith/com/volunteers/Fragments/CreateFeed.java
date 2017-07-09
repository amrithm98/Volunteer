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

import com.google.firebase.auth.FirebaseAuth;

import amrith.com.volunteers.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateFeed extends Fragment{

    @BindView(R.id.description)
    private FirebaseAuth mAuth;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Give an Update!");
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_create_feed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.post)
    public void post(){

    }
}

package com.demo.smokingcontrolapp.fragments.profile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.fragments.home.HomeFragment;

public class ProfileFragment extends Fragment {

    public static Fragment getInstance(){
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
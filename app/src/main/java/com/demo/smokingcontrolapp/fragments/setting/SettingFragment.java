package com.demo.smokingcontrolapp.fragments.setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.fragments.profile.ProfileFragment;

public class SettingFragment extends Fragment {

    public static Fragment getInstance(){
        return new SettingFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}
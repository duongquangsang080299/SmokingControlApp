package com.demo.smokingcontrolapp.activities.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.fragments.chat.ChatFragment;
import com.demo.smokingcontrolapp.fragments.home.HomeFragment;
import com.demo.smokingcontrolapp.fragments.profile.ProfileFragment;
import com.demo.smokingcontrolapp.fragments.setting.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private String currentFragment = "";
    private ConstraintLayout itemHome, itemProfile, itemChat, itemSetting;
    private String chatTitle, profileTitle, settingTitle, homeTitle;
    private ImageView ovalHome, ovalSetting, ovalProfile, ovalChat;
    private TextView txvHome, txvSetting, txvChat, txvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // khởi tạo view
        init();
        // mở màn hình home đầu tiên
        initNavigation();
        // lắng nghe sự kiến click vào item menu
        menuListening();
    }

    private void menuListening() {
        itemHome.setOnClickListener(v -> {
            if (!currentFragment.equals(homeTitle)) {
                Fragment listFragment = HomeFragment.getInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_view, listFragment);
                transaction.commit();
                currentFragment = homeTitle;

                ovalHome.setVisibility(View.VISIBLE);
                txvHome.setTextColor(getResources().getColor(R.color.yellow));
                ovalProfile.setVisibility(View.INVISIBLE);
                txvProfile.setTextColor(getResources().getColor(R.color.white));
                ovalSetting.setVisibility(View.INVISIBLE);
                txvSetting.setTextColor(getResources().getColor(R.color.white));
                ovalChat.setVisibility(View.INVISIBLE);
                txvChat.setTextColor(getResources().getColor(R.color.white));
            }
            mDrawerLayout.closeDrawers();
        });

        itemProfile.setOnClickListener(v -> {
            if (!currentFragment.equals(profileTitle)) {
                Fragment listFragment = ProfileFragment.getInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_view, listFragment);
                transaction.commit();
                currentFragment = profileTitle;

                ovalHome.setVisibility(View.INVISIBLE);
                txvHome.setTextColor(getResources().getColor(R.color.white));
                ovalProfile.setVisibility(View.VISIBLE);
                txvProfile.setTextColor(getResources().getColor(R.color.yellow));
                ovalSetting.setVisibility(View.INVISIBLE);
                txvSetting.setTextColor(getResources().getColor(R.color.white));
                ovalChat.setVisibility(View.INVISIBLE);
                txvChat.setTextColor(getResources().getColor(R.color.white));
            }
            mDrawerLayout.closeDrawers();
        });

        itemSetting.setOnClickListener(v -> {
            if (!currentFragment.equals(settingTitle)) {
                Fragment listFragment = SettingFragment.getInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_view, listFragment);
                transaction.commit();
                currentFragment = settingTitle;

                ovalHome.setVisibility(View.INVISIBLE);
                txvHome.setTextColor(getResources().getColor(R.color.white));
                ovalProfile.setVisibility(View.INVISIBLE);
                txvProfile.setTextColor(getResources().getColor(R.color.white));
                ovalSetting.setVisibility(View.VISIBLE);
                txvSetting.setTextColor(getResources().getColor(R.color.yellow));
                ovalChat.setVisibility(View.INVISIBLE);
                txvChat.setTextColor(getResources().getColor(R.color.white));
            }
            mDrawerLayout.closeDrawers();
        });

        itemChat.setOnClickListener(v -> {
            if (!currentFragment.equals(chatTitle)) {
                Fragment listFragment = ChatFragment.getInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_view, listFragment);
                transaction.commit();
                currentFragment = chatTitle;

                ovalHome.setVisibility(View.INVISIBLE);
                txvHome.setTextColor(getResources().getColor(R.color.white));
                ovalProfile.setVisibility(View.INVISIBLE);
                txvProfile.setTextColor(getResources().getColor(R.color.white));
                ovalSetting.setVisibility(View.INVISIBLE);
                txvSetting.setTextColor(getResources().getColor(R.color.white));
                ovalChat.setVisibility(View.VISIBLE);
                txvChat.setTextColor(getResources().getColor(R.color.yellow));
            }
            mDrawerLayout.closeDrawers();
        });

    }

    private void init() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        itemChat = findViewById(R.id.item_chat);
        itemHome = findViewById(R.id.item_home);
        itemProfile = findViewById(R.id.item_profile);
        itemSetting = findViewById(R.id.item_setting);
        ovalChat = findViewById(R.id.img_oval_chat);
        ovalHome = findViewById(R.id.img_oval_home);
        ovalSetting = findViewById(R.id.img_oval_setting);
        ovalProfile = findViewById(R.id.img_oval_profile);
        txvChat = findViewById(R.id.txv_chat);
        txvSetting = findViewById(R.id.txv_setting);
        txvHome = findViewById(R.id.txv_home);
        txvProfile = findViewById(R.id.txv_profile);

        chatTitle = getResources().getString(R.string.chat);
        profileTitle = getResources().getString(R.string.profile_title);
        settingTitle = getResources().getString(R.string.setting_title);
        homeTitle = getResources().getString(R.string.home_title);
    }

    private void initNavigation() {
        Fragment listFragment = HomeFragment.getInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_view, listFragment);
        transaction.commit();
        currentFragment = "Home";
    }
}
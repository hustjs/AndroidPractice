package com.example.radical.myapplication;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.radical.myapplication.adapter.FragmentPagerAdapter;
import com.example.radical.myapplication.fragment.BlankFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabTestActivity extends AppCompatActivity {
    private FragmentPagerAdapter pagerAdapter;
//    private String[] tabTitle = {"Tab1", "Tab2", "Tab3","Tab4", "Tab5", "Tab6","Tab7", "Tab8", "Tab9"};
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
//    private ViewPager viewPager;
//    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);
        ButterKnife.bind(this);
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.addTabView(i));
        }
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    public static String POSITION = "POSITION";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }
}

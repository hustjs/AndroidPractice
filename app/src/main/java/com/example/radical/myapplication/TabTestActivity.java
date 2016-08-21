package com.example.radical.myapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.radical.myapplication.fragment.BlankFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabTestActivity extends AppCompatActivity {
    private FragmentPagerAdapter pagerAdapter;
    private String[] tabTitle = {"Tab1", "Tab2", "Tab3","Tab4", "Tab5", "Tab6","Tab7", "Tab8", "Tab9"};
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
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitle[position];
            }

            @Override
            public Fragment getItem(int position) {
                return BlankFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 9;
            }
        };
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

}

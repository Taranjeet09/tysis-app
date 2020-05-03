package com.example.rawin.taa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    static FloatingActionButton b;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b = findViewById(R.id.fab);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.addfra(new Home(), "Plant Scan");
        mSectionsPagerAdapter.addfra(new Two(), "Soil Scan");
        mSectionsPagerAdapter.addfra(new Three(), "News");
        mSectionsPagerAdapter.addfra(new Four(), "e-Mandi");
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> frag = new ArrayList<>();
        ArrayList<String> tabti = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addfra(Fragment frag, String title) {
            this.frag.add(frag);
            this.tabti.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return frag.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabti.get(position);
        }

        @Override
        public int getCount() {
            return frag.size();
        }
    }
}

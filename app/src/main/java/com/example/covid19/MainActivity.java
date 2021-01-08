package com.example.covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

 import android.os.Bundle;
 import android.view.View;

import android.widget.AdapterView;


import com.google.android.material.tabs.TabLayout;


import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.Selector);
        viewPager=findViewById(R.id.view_selector);
        toolbar=findViewById(R.id.c_toolbar);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        viewPager.setAdapter(new newAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
     }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public class newAdapter extends FragmentPagerAdapter{

         String []data={"OTHERS","INDIA"};
        public newAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
         if (position==0)
             return  new Other_Detail();
         else if(position==1)
             return  new India_Detail();
            return null;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return data[position];
        }
    }
}
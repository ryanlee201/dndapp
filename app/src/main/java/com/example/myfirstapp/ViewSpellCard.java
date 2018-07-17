package com.example.myfirstapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ViewSpellCard extends AppCompatActivity {
    ViewPager viewPager;
    int images[] = {R.drawable.guidance, R.drawable.light, R.drawable.mending};
    SlidingImage_Adapter myCustomPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spellcard_slider);

        viewPager = (ViewPager)findViewById(R.id.pager);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        Bundle extras = getIntent().getExtras();

        int [] filterLvls = new int[10];
        String[] filterClasses = new String[12];

        if (extras != null) {
             filterLvls = Arrays.copyOf(extras.getIntArray("Spell Level"),10);
             filterClasses = Arrays.copyOf(extras.getStringArray("Spell Class"),12);
        }
        else
        {
            myCustomPagerAdapter = new SlidingImage_Adapter(ViewSpellCard.this, databaseAccess.getSpellCards());
            viewPager.setAdapter(myCustomPagerAdapter);
            databaseAccess.close();
        }



//        int[] = {}
//        databaseAccess.filterByClass(filterClasses);


        myCustomPagerAdapter = new SlidingImage_Adapter(ViewSpellCard.this, databaseAccess.filterByLevel(filterLvls));
        viewPager.setAdapter(myCustomPagerAdapter);
        databaseAccess.close();
    }
}


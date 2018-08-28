package com.example.dndapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dndapp.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ViewSpellCard extends AppCompatActivity {
    ViewPager viewPager;
    String images[] = {"guidance", "light", "mending"};
    SlidingImage_Adapter myCustomPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spellcard_slider);

        viewPager = (ViewPager)findViewById(R.id.pager);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        Bundle extras = getIntent().getExtras();

        Character character;
        String [] spells; // = databaseAccess.getSpellCards();

//        if(extras != null)
//        {
//            if(extras.get("Character") instanceof java.lang.Character)
//            {
                character = extras.getParcelable("Character");
                spells = databaseAccess.characterFilter(character);
//            }
//        }

        myCustomPagerAdapter = new SlidingImage_Adapter(ViewSpellCard.this, spells); // need to create a else option for this
        viewPager.setAdapter(myCustomPagerAdapter);
        databaseAccess.close();
    }
}


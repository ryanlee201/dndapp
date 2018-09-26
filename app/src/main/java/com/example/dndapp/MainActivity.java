package com.example.dndapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dndapp.R;


public class MainActivity extends AppCompatActivity {


    private Button btn_characterview;
    private Button test;

    private DatabaseOpenHelper dbhelper;
    private
    Context context;


    private View.OnClickListener onclick_character = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            characterview();
        }
    };

    private View.OnClickListener click_test = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            test();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();


        btn_characterview = findViewById(R.id.characterview);
        btn_characterview.setOnClickListener(onclick_character);

        test = findViewById(R.id.test);
        test.setOnClickListener(click_test);

        dbhelper = new DatabaseOpenHelper(this);
    }

    private void characterview(){
        startActivity(new Intent(MainActivity.this, CharacterView.class));
    }

    private void test() {
        startActivity(new Intent(MainActivity.this, CharacterProfile.class));
    }


}

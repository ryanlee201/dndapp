package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button button_filter;
    private Button button_spell;
    DatabaseOpenHelper dbhelper;
    Context context;


    private View.OnClickListener onclick_viewSpell = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            viewSpellCard();
        }
    };

    private View.OnClickListener onclick_filter = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            filterSpellCards();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        button_spell = (Button) findViewById(R.id.Spell);
        button_spell.setOnClickListener((onclick_viewSpell));

        button_filter = (Button) findViewById(R.id.filter);
        button_filter.setOnClickListener((onclick_filter));

        dbhelper = new DatabaseOpenHelper(this);

    }

    private void viewSpellCard(){
        startActivity(new Intent(MainActivity.this, ViewSpellCard.class));
    }

    private void filterSpellCards(){
        startActivity(new Intent(MainActivity.this, ViewFilter.class));
    }


}

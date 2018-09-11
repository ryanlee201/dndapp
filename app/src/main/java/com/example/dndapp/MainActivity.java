package com.example.dndapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dndapp.R;


public class MainActivity extends AppCompatActivity {

    private Button button_filter;
    private Button button_spell;
    private Button btn_characterview;
    private Button btn_charactercreation;
    private DatabaseOpenHelper dbhelper;
    private
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

    private View.OnClickListener onclick_character = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            characterview();
        }
    };

    private View.OnClickListener onclick_charactercreation = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            charactercreate();
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

        btn_characterview = findViewById(R.id.characterview);
        btn_characterview.setOnClickListener((onclick_character));

        btn_charactercreation = findViewById(R.id.main_charactercreation);
        btn_charactercreation.setOnClickListener((onclick_charactercreation));

        dbhelper = new DatabaseOpenHelper(this);

    }

    private void characterview(){
        startActivity(new Intent(MainActivity.this, CharacterView.class));
    }

    private void viewSpellCard(){
        startActivity(new Intent(MainActivity.this, ViewSpellCard.class));
    }

    private void filterSpellCards(){
        startActivity(new Intent(MainActivity.this, ViewFilter.class));
    }

    private void charactercreate () {
        startActivity(new Intent(MainActivity.this, CharacterCreation.class));
    }

}

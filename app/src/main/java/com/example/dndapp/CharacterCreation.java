package com.example.dndapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CharacterCreation extends AppCompatActivity{

    Button addCharacter;
    EditText name;
    Spinner race;
    Spinner spinner_class;
    Spinner level;
    DatabaseAccess databaseAccess;

    private View.OnClickListener onclick_AddCharacter = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            addCharacter();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_creation);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        addCharacter = findViewById(R.id.createcharacter);
        name = findViewById(R.id.edit_name);
        race = findViewById(R.id.dropdown_race);
        spinner_class = findViewById(R.id.dropdown_class);
        level = findViewById(R.id.dropdown_level);

        addCharacter.setOnClickListener((onclick_AddCharacter));

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,databaseAccess.getRaces());
        race.setAdapter(adapter2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,databaseAccess.getClasses());
        spinner_class.setAdapter(adapter1);

        String [] numbers = new String[]{"1","2","3","4","5","6","7","8","9","10"};

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,numbers);
        level.setAdapter(adapter3);
    }

    private void addCharacter(){

        String character_name = name.getText().toString();
        String character_race = race.getSelectedItem().toString();
        String character_class = spinner_class.getSelectedItem().toString();
        String character_level = level.getSelectedItem().toString();

        Character newCharacter = new Character(character_name);
        newCharacter.setCharacterclass(character_class);
        newCharacter.setLevel(character_level);
        newCharacter.setName(character_name);
        newCharacter.setRace(character_race);

        if(databaseAccess.addCharacter(newCharacter) == -1)
        {
            Log.e("Error", "On character insert to db");
        }

        databaseAccess.close();
        startActivity(new Intent(CharacterCreation.this, CharacterView.class));
    }

}

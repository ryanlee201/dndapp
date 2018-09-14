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
import android.widget.Toast;

public class CharacterCreation extends AppCompatActivity{

    Button nextStep;
    EditText name;
    Spinner race;
    Spinner spinner_class;
    Spinner level;
    DatabaseAccess databaseAccess;

    private View.OnClickListener onclick_NextStep = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            nextStep();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_creation);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        nextStep = findViewById(R.id.nextstep);
        name = findViewById(R.id.edit_name);
        race = findViewById(R.id.dropdown_race);
        spinner_class = findViewById(R.id.dropdown_class);
        level = findViewById(R.id.dropdown_level);

        nextStep.setOnClickListener((onclick_NextStep));

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,databaseAccess.getRaces());
        race.setAdapter(adapter2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,databaseAccess.getClasses());
        spinner_class.setAdapter(adapter1);

        String [] numbers = new String[]{"1","2","3","4","5","6","7","8","9","10"};

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,numbers);
        level.setAdapter(adapter3);
    }

    private void nextStep(){

        String character_name = name.getText().toString();
        String character_race = race.getSelectedItem().toString();
        String character_class = spinner_class.getSelectedItem().toString();
        String character_level = level.getSelectedItem().toString();

        Character newCharacter = new Character(character_name);
        newCharacter.setCharacterclass(character_class);
        newCharacter.setLevel(character_level);
        newCharacter.setName(character_name);
        newCharacter.setRace(character_race);

        Intent intent = new Intent(getApplicationContext(), SpellCardGallery.class);
        intent.putExtra("Source","CharacterCreation");
        intent.putExtra("Character", newCharacter);
        startActivity(intent);
    }

}

package com.example.dndapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

        TextView title = (TextView)findViewById(R.id.creation_title);
        TextView txt_name = (TextView)findViewById(R.id.creation_name);
        TextView txt_race = (TextView)findViewById(R.id.creation_race);
        TextView txt_class = (TextView)findViewById(R.id.creation_class);
        TextView txt_level = (TextView)findViewById(R.id.creation_level);

        Typeface vecna = Typeface.createFromAsset(getAssets(), "fonts/VecnaItalic.otf");
        Typeface dungeon = Typeface.createFromAsset(getAssets(), "fonts/DUNGRG.ttf");

        title.setTypeface(vecna);

        txt_name.setTypeface(dungeon);
        txt_race.setTypeface(dungeon);
        txt_class.setTypeface(dungeon);
        txt_level.setTypeface(dungeon);

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

        if(name.getText().toString().matches("")){
            Toast.makeText(this, "You did not enter a name", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), SpellCardGallery.class);
            intent.putExtra("Source","CharacterCreation");
            intent.putExtra("Character", newCharacter);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), CharacterView.class);
        startActivity(intent);
    }

}

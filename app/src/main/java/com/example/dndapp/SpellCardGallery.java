package com.example.dndapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;

public class SpellCardGallery extends AppCompatActivity{

    DatabaseAccess databaseAccess;
    Character character;
    SpellCardGalleryAdapter adapter;
    RecyclerView recyclerView;
    Button createCharacter;
    String[] spells;

    private View.OnClickListener onclick_CreateCharacter = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            createCharacter();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        character = extras.getParcelable("Character");

        if(extras.get("Source").equals("CharacterCreation"))
        {
            setContentView(R.layout.spellcard_gallery_charactercreation);
            recyclerView = (RecyclerView) findViewById(R.id.imagegallery_charactercreation);
            createCharacter = findViewById(R.id.createcharacter);
            createCharacter.setOnClickListener(onclick_CreateCharacter);
            spells = databaseAccess.spellcardCharacterFilter(character);


        }
        else if(extras.get("Source").equals("CharacterView"))
        {
            setContentView(R.layout.spellcard_gallery_characterview);
            recyclerView = (RecyclerView)findViewById(R.id.imagegallery_characterview);
            spells = databaseAccess.getCharacterSpellCards(Integer.parseInt(databaseAccess.getCharacterId(character.getName())));
        }

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SpellCardGalleryAdapter(getApplicationContext(), spells);
        recyclerView.setAdapter(adapter);
    }

    private void createCharacter(){

        Log.d("Deck", "Choosen Spells:" + Arrays.toString(adapter.getSpells()));
        String [] characterSpells = adapter.getSpells();

        long newRowId = databaseAccess.addCharacter(character);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving character info", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Character saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

        databaseAccess.addCharacterSpells(databaseAccess.getCharacterId(character.getName()),characterSpells);

        databaseAccess.close();
        Intent intent = new Intent(getApplicationContext(), CharacterView.class);
        startActivity(intent);
    }

}

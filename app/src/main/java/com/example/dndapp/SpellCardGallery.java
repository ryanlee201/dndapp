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
    String[] spells;

    Button createCharacter;
    Button goToAddSpellLayout;
    Button goToDeleteSpellLayout;
    Button addSpells;
    Button deleteSpells;

    private View.OnClickListener onclick_CreateCharacter = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            createCharacter();
        }
    };

    private View.OnClickListener onclick_GoToAddSpellLayout= new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            goToAddSpellLayout();
        }
    };

    private View.OnClickListener onclick_GoToDeleteSpellLayout = new View.OnClickListener()
    {

        @Override
        public void onClick(View v){
            goToDeleteSpellLayout();
        }
    };

    private View.OnClickListener onclick_AddSpells = new View.OnClickListener()
    {
      @Override
      public void onClick(View v){
          addToDeck();
      }

    };

    private View.OnClickListener onclick_DeleteSpells = new View.OnClickListener()
    {
        @Override
        public void onClick(View v){
            deleteFromDeck();
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

            goToAddSpellLayout = findViewById(R.id.gotoaddspelllayout);
            goToAddSpellLayout.setOnClickListener(onclick_GoToAddSpellLayout);
            goToDeleteSpellLayout = findViewById(R.id.gotodeletespelllayout);
            goToDeleteSpellLayout.setOnClickListener((onclick_GoToDeleteSpellLayout));

            spells = databaseAccess.getCharacterSpellCards(Integer.parseInt(databaseAccess.getCharacterId(character.getName())));
        }
        else if(extras.get("Source").equals("AddToDeck"))
        {
            setContentView(R.layout.spellcard_gallery_addtodeck);
            recyclerView = (RecyclerView)findViewById(R.id.imagegallery_addotdeck);

            addSpells = findViewById(R.id.btn_add_spells_to_deck);
            addSpells.setOnClickListener(onclick_AddSpells);

            spells = databaseAccess.getSpellCards();
        }
        else if(extras.get("Source").equals("DeleteFromDeck"))
        {
            setContentView(R.layout.spellcard_gallery_deletefromdeck);
            recyclerView = (RecyclerView)findViewById(R.id.imagegallery_deletefromdeck);

            deleteSpells = findViewById(R.id.delete_fromDeck);
            deleteSpells.setOnClickListener(onclick_DeleteSpells);

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

    private void goToAddSpellLayout()
    {
        Intent intent = new Intent(getApplicationContext(), SpellCardGallery.class);
        intent.putExtra("Source", "AddToDeck");
        intent.putExtra("Character", character);
        startActivity(intent);
    }

    private void addToDeck()
    {
        String [] characterSpells = adapter.getSpells();
        databaseAccess.addCharacterSpells(databaseAccess.getCharacterId(character.getName()),characterSpells);
        Intent intent = new Intent(getApplicationContext(),SpellCardGallery.class);
        intent.putExtra("Source", "CharacterView");
        intent.putExtra("Character", character);
        startActivity(intent);
    }

    private void goToDeleteSpellLayout()
    {
        Intent intent = new Intent(getApplicationContext(), SpellCardGallery.class);
        intent.putExtra("Source", "DeleteFromDeck");
        intent.putExtra("Character", character);
        startActivity(intent);
    }


    private void deleteFromDeck()
    {
        String [] characterSpells = adapter.getSpells();
        databaseAccess.deleteCharacterSpells(databaseAccess.getCharacterId(character.getName()),characterSpells,character.getName());
        Intent intent = new Intent(getApplicationContext(),SpellCardGallery.class);
        intent.putExtra("Source", "CharacterView");
        intent.putExtra("Character", character);
        startActivity(intent);

    }



}

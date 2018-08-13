package com.example.dndapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class CharacterView extends AppCompatActivity {

    private Button button_addCharacter;
    private Button button_deleteCharacter;
    ArrayList<Character> characters;
    DatabaseOpenHelper dbhelper;
    Context context;

    private View.OnClickListener onclick_addCharacter = new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
          addCharacter();
      }
    };

    private View.OnClickListener onclick_deleteCharacter = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            deleteCharacter();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_view);
        context = getApplicationContext();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        RecyclerView rvCharacter = (RecyclerView) findViewById(R.id.character_rview);

        button_addCharacter = (Button) findViewById(R.id.add_character);
        button_addCharacter.setOnClickListener((onclick_addCharacter));

        button_deleteCharacter = (Button) findViewById(R.id.delete_character);
        button_deleteCharacter.setOnClickListener((onclick_deleteCharacter));

        // Initialize contacts
        characters = databaseAccess.getCharacters();
        // Create adapter passing in the sample user data
        CharacterViewAdapter adapter = new CharacterViewAdapter(characters, context);
        // Attach the adapter to the recyclerview to populate items
        rvCharacter.setAdapter(adapter);
        // Set layout manager to position the items
        rvCharacter.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

    }

    private void addCharacter()
    {
        startActivity(new Intent(CharacterView.this, CharacterCreation.class ));
    }

    private void deleteCharacter()
    {

    }


}

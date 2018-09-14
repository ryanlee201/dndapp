package com.example.dndapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class CharacterView extends AppCompatActivity implements TestSwipeController.TestSwipeControllerListener{

    private Button button_addCharacter;
    ArrayList<Character> characters;
    DatabaseOpenHelper dbhelper;
    DatabaseAccess databaseAccess;
    Context context;
    private CharacterViewAdapter adapter;
    SwipeController swipeController = null;

    private View.OnClickListener onclick_addCharacter = new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
          addCharacter();
      }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_view);
        context = getApplicationContext();

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        button_addCharacter = (Button) findViewById(R.id.add_character);
        button_addCharacter.setOnClickListener((onclick_addCharacter));

        setupRecyclerView();
    }

    private void addCharacter()
    {
        startActivity(new Intent(CharacterView.this, CharacterCreation.class ));
    }

    private void setupRecyclerView(){

        RecyclerView rvCharacter = (RecyclerView) findViewById(R.id.character_rview);

        // Initialize contacts
        characters = databaseAccess.getCharacters();
        // Create adapter passing in the sample user data
        adapter = new CharacterViewAdapter(characters, context);
        // Attach the adapter to the recyclerview to populate items
        rvCharacter.setAdapter(adapter);
        // Set layout manager to position the items
        rvCharacter.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new TestSwipeController(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvCharacter);

//        swipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onRightClicked(int position) {
//                databaseAccess.deleteCharacter(adapter.mCharacter.get(position).getName());
//
//                adapter.mCharacter.remove(position);
//                adapter.notifyItemRemoved(position);
//                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
//
//            }
//        });
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(rvCharacter);
//
//        rvCharacter.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                swipeController.onDraw(c);
//            }
//        });
    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CharacterViewAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
//            String name = .get(viewHolder.getAdapterPosition()).getName();

//            // backup of removed item for undo purpose (can create undo button)
//            final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
//            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            databaseAccess.deleteCharacter(adapter.mCharacter.get(position).getName());
            adapter.removeCharacter(viewHolder.getAdapterPosition());

        }
    }

}

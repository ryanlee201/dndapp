package com.example.dndapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class CharacterViewAdapter extends RecyclerView.Adapter<CharacterViewAdapter.ViewHolder> {

    private Context context;
    //private int currentPosition;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView raceTextView;
        public TextView classTextView;
        public TextView levelTextView;
        public Button viewspellsButton;
        public ConstraintLayout viewBackground, viewForeground;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.characteritem_name);
            raceTextView = (TextView) itemView.findViewById(R.id.characteritem_race);
            classTextView = (TextView) itemView.findViewById(R.id.characteritem_class);
            levelTextView = (TextView) itemView.findViewById(R.id.characteritem_level);
            viewspellsButton = (Button) itemView.findViewById(R.id.item_btn_viewspells);
            viewBackground = itemView.findViewById(R.id.background);
            viewForeground = itemView.findViewById(R.id.foreground);
        }
    }

    public List<Character> mCharacter;

    // Pass in the contact array into the constructor
    public CharacterViewAdapter(List<Character> characters, Context context) {
        mCharacter = characters;
        this.context = context;
    }

    @Override
    public CharacterViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.character_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CharacterViewAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        //currentPosition = position;
        final Character character = mCharacter.get(position);

        TextView textView;
        Button btn;

        btn = viewHolder.viewspellsButton;
        btn.setText("View Spells");
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //viewSpells();
                Intent intent = new Intent(context, SpellCardGallery.class);
                intent.putExtra("Source", "CharacterView");
                intent.putExtra("Character", character);
                v.getContext().startActivity(intent);
            }
        });

        String level = "Lvl. " + character.getLevel();
        String race = " - " + character.getRace();

        textView = viewHolder.nameTextView;
        textView.setText(character.getName());

        textView = viewHolder.raceTextView;
        textView.setText(race);

        textView = viewHolder.classTextView;
        textView.setText(character.getCharacterclass());

        textView = viewHolder.levelTextView;
        textView.setText(level);
    }

    public void removeCharacter(int position){
        mCharacter.remove(position);
        notifyItemRemoved(position);
    }



    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mCharacter.size();
    }

//    private void viewSpells()
//    {
//        startActivity(new Intent(CharacterView.this, ViewSpellCard.class ));
//    }
//
//    public String getCharacterName()
//    {
//       return mCharacter.get(currentPosition).getName();
//
//    }

}

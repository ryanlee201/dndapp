package com.example.dndapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Random;

public class CharacterProfile extends AppCompatActivity implements View.OnClickListener{

    ToggleButton btn_rolldie;
    Button d4, d6, d8, d10, d00, d12, d20;
    Button btn_inventory, btn_skills, btn_viewSpells;
    TextView name, hp, ac, number;
    Character character;
    Bundle extras;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_profile);

        //character = extras.getParcelable("Character");

        name = (TextView)findViewById(R.id.profile_name);
        name.setText("Zorug");
        hp = (TextView)findViewById(R.id.profile_hp);
        hp.setText("45");
        ac = (TextView)findViewById(R.id.profile_ac);
        ac.setText("18");
        number = (TextView)findViewById(R.id.profile_number);

        btn_rolldie = (ToggleButton)findViewById(R.id.profile_rolldie);
        d4 = (Button)findViewById(R.id.profile_d4);
        d6 = (Button)findViewById(R.id.profile_d6);
        d8 = (Button)findViewById(R.id.profile_d8);
        d10 = (Button)findViewById(R.id.profile_d10);
        d00 = (Button)findViewById(R.id.profile_d00);
        d12 = (Button)findViewById(R.id.profile_d12);
        d20 = (Button)findViewById(R.id.profile_d20);
        btn_inventory = (Button)findViewById(R.id.profile_inventory);
        btn_skills = (Button)findViewById(R.id.profile_skills);
        btn_viewSpells = (Button)findViewById(R.id.profile_spells);

        btn_rolldie.setOnCheckedChangeListener(toggle_RollDie);
        d4.setOnClickListener(this);
        d6.setOnClickListener(this);
        d8.setOnClickListener(this);
        d10.setOnClickListener(this);
        d00.setOnClickListener(this);
        d12.setOnClickListener(this);
        d20.setOnClickListener(this);
        btn_inventory.setOnClickListener(this);
        btn_skills.setOnClickListener(this);
        btn_viewSpells.setOnClickListener(this);

        d4.setVisibility(View.GONE);
        d6.setVisibility(View.GONE);
        d8.setVisibility(View.GONE);
        d10.setVisibility(View.GONE);
        d00.setVisibility(View.GONE);
        d12.setVisibility(View.GONE);
        d20.setVisibility(View.GONE);
        number.setVisibility(View.GONE);


    }

    private CompoundButton.OnCheckedChangeListener toggle_RollDie = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                d4.setVisibility(View.VISIBLE);
                d6.setVisibility(View.VISIBLE);
                d8.setVisibility(View.VISIBLE);
                d10.setVisibility(View.VISIBLE);
                d00.setVisibility(View.VISIBLE);
                d12.setVisibility(View.VISIBLE);
                d20.setVisibility(View.VISIBLE);
                // The toggle is enabled
            } else {
                // The toggle is disabled
                d4.setVisibility(View.GONE);
                d6.setVisibility(View.GONE);
                d8.setVisibility(View.GONE);
                d10.setVisibility(View.GONE);
                d00.setVisibility(View.GONE);
                d12.setVisibility(View.GONE);
                d20.setVisibility(View.GONE);
                number.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onClick(View v) {
        boolean clicked = false;
        final Random r = new Random();

        switch (v.getId() /*to get clicked view id**/) {
            case R.id.profile_skills:

                break;
            case R.id.profile_spells:

                break;

            case R.id.profile_inventory:

                break;

            case R.id.profile_d4:
                number.setVisibility(View.VISIBLE);
                number.setText(Integer.toString(r.nextInt(4)+1));
                break;
            case R.id.profile_d6:
                number.setVisibility(View.VISIBLE);
                number.setText(Integer.toString(r.nextInt(6)+1));
                break;
            case R.id.profile_d8:
                number.setVisibility(View.VISIBLE);
                number.setText(Integer.toString(r.nextInt(8)+1));
                break;
            case R.id.profile_d10:
                number.setVisibility(View.VISIBLE);
                number.setText(Integer.toString(r.nextInt(10)+1));
                break;
            case R.id.profile_d00:
                number.setVisibility(View.VISIBLE);
                number.setText(Integer.toString((r.nextInt(10)+1)*10));
                break;
            case R.id.profile_d12:
                number.setVisibility(View.VISIBLE);
                number.setText(Integer.toString(r.nextInt(12)+1));
                break;
            case R.id.profile_d20:
                number.setVisibility(View.VISIBLE);
                number.setText(Integer.toString(r.nextInt(20)+1));
                break;

            default:
                break;
        }
    }





    public void roll_die() {

    }

}

package com.example.dndapp;


import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.dndapp.R;

public class ViewFilter extends AppCompatActivity{

    private Button btn_applyall;

    private View.OnClickListener onclick_applyall = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //call method that gets which checkboxes are checked
            //then pass the list to filterByLevel(int[])
            //then pass the cursor into SlidePageAdapter() (Look at ViewSpellCard for complete function call)
            Intent intent = new Intent(ViewFilter.this, ViewSpellCard.class);
            Bundle extras = new Bundle();
            extras.putIntArray("Spell Level", spellLvlFilter());
            extras.putIntArray("Spell Class", spellClassFilter());
            intent.putExtras(extras);
            startActivity(intent);

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_spellcards);

        btn_applyall = (Button) findViewById(R.id.applyall);
        btn_applyall.setOnClickListener((onclick_applyall));

    }

    public int[] spellLvlFilter(){
        int[] spellchoice = new int[10];

        LinearLayout lvl0_4 = (LinearLayout) findViewById(R.id.lvl0_4);
        System.arraycopy(checkbox_check(lvl0_4),0,spellchoice,0,5);
        LinearLayout lvl5_9 = (LinearLayout) findViewById(R.id.lvl5_9);
        System.arraycopy(checkbox_check(lvl5_9),0,spellchoice,5,5);

        return spellchoice;
    }

    public int[] spellClassFilter(){
       int [] spellclass = new int[12];

        LinearLayout classes1 = (LinearLayout) findViewById(R.id.Classes1);
        System.arraycopy(checkbox_check(classes1),0,spellclass,0,4);
        LinearLayout classes2 = (LinearLayout) findViewById(R.id.Classes2);
        System.arraycopy(checkbox_check(classes2),0,spellclass,4,4);
        LinearLayout classes3 = (LinearLayout) findViewById(R.id.Classes3);
        System.arraycopy(checkbox_check(classes3),0,spellclass,8,4);

        return spellclass;
    }

    public int[] checkbox_check(LinearLayout layout) {
        int[] lvls = new int[5];
        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof CheckBox) {
                if (((CheckBox) v).isChecked()) {
                    lvls[i] = 1;
                }
            }

        }
        return lvls;
    }

}

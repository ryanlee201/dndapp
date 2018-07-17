package com.example.myfirstapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private static final String TAG = "MyActivity";


    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     *
     * @return
     */
    public String[] getSpellCards() {
        List<String> listString = new ArrayList<>();
        String query = "SELECT name FROM SpellCards";
        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listString.add(cursor.getString(0));
            cursor.moveToNext();
        }

        String[] spells = listString.toArray(new String[0]);

        return spells;
    }

    /**
     *
     * @param lvls
     * @return
     */
    public String[] filterByLevel(int[] lvls, String[] deck){

        List<String> listString = new ArrayList<>();

        String inClause0 = "(";

        for(int i = 0; i < lvls.length; i++)
        {
            if(lvls[i] == 1)
            {
                if(inClause0.length() == 1)
                {
                    inClause0 = inClause0 + i;
                }
                else
                {
                    inClause0 = inClause0 + "," + i ;
                }

            }
        }

        inClause0 = inClause0 + ')';

//        String inClause = Arrays.toString(lvls);
//
//        //replacing [] with ()
//        inClause = inClause.replace("[","(");
//        inClause = inClause.replace("]",")");

        Log.d("Test array",inClause0);

        String query = "SELECT name FROM SpellCards " +
                        "WHERE level IN " + inClause0 +
                        " AND name In" + deck;

        Cursor cursor = database.rawQuery(query,null);
4
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listString.add(cursor.getString(0));
            cursor.moveToNext();
        }

        String[] spells = listString.toArray(new String[0]);

        Log.d("Spells", spells.toString());

        return spells;
    }

    public String[] filterByClass(int[] dndclasses, String[] deck) {

        List<String> listString = new ArrayList<>();

        String inClause0 = "(";

        for(int i = 1; i <= dndclasses.length; i++)
        {
            if(dndclasses[i] == 1)
            {
                if(inClause0.length() == 1)
                {
                    inClause0 = inClause0 + i;
                }
                else
                {
                    inClause0 = inClause0 + "," + i ;
                }

            }
        }

        inClause0 = inClause0 + ')';



        String query = "SELECT DISTINCT s.name " +
                        "FROM SpellClass sc " +
                        "INNER JOIN SpellCards s ON s.spellid = sc.spellid " +
                        "WHERE sc.classid IN" + inClause0 + " AND s.name In" + deck;

        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listString.add(cursor.getString(0));
            cursor.moveToNext();
        }

        String[] spells = listString.toArray(new String[0]);

        Log.d("Spells", spells.toString());

        return spells;

    }






}

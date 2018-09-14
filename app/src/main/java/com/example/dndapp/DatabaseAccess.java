package com.example.dndapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

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
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    private static final String LOG_TAG = "Database";


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
        this.db = openHelper.getWritableDatabase();
        Log.i(LOG_TAG,"Database Opened");
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (db != null) {
            this.db.close();
            Log.i(LOG_TAG,"Database Closed");
        }
    }

    /**
     * retrieves all dnd spell names from db
     * @return string[] of spell name
     */
    public String[] getSpellCards() {
        List<String> listString = new ArrayList<>();
        String [] columns = new String [] {"name"};
        Cursor cursor = db.query("SpellCards",columns,null,null, null, null , null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listString.add(cursor.getString(0));
            cursor.moveToNext();
        }

        cursor.close();

        String[] spells = listString.toArray(new String[0]);

        return spells;
    }

    /**
     * retrieve all dnd classes from db
     * @return string[] of classes
     */
    public String[] getClasses() {
        List<String> listString = new ArrayList<>();
        String [] columns = new String[] {"characterclass"};
        Cursor cursor = db.query("Classes",columns,null,null, null, null , null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listString.add(cursor.getString(0));
            cursor.moveToNext();
        }

        cursor.close();

        String[] classes = listString.toArray(new String[0]);

        return classes;

    }

    /**
     * retrieve all dnd races from db
     * @return string[] of races
     */
    public String[] getRaces() {
        List<String> listString = new ArrayList<>();
        String [] columns = new String[] {"race"};
        Cursor cursor = db.query("Races",columns,null,null, null, null , null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listString.add(cursor.getString(0));
            cursor.moveToNext();
        }

        cursor.close();

        String[] races = listString.toArray(new String[0]);

        return races;

    }

    public ArrayList<Character> getCharacters() {

        ArrayList<Character> allCharacters = new ArrayList<>();
        String[] columns = {"characterid", "name", "level", "race", "characterclass"};
        Cursor cursor = db.query("Characters", columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Character character = new Character();
                character.setId(cursor.getString(cursor.getColumnIndex("characterid")));
                character.setName(cursor.getString(cursor.getColumnIndex("name")));
                character.setLevel(cursor.getString(cursor.getColumnIndex("level")));
                character.setRace(cursor.getString(cursor.getColumnIndex("race")));
                character.setCharacterclass(cursor.getString(cursor.getColumnIndex("characterclass")));


                allCharacters.add(character);

                //Log.i(LOG_TAG, "Character names: " + character.getName());
            } while (cursor.moveToNext());
        }

        cursor.close();

        return allCharacters;

    }

    public String getCharacterId(String name){
        open();
        String characterid = "";
        Cursor cursor = db.query("Characters", new String[] {"characterid"}, "name = ?", new String [] {name}, null, null, null);

        if(cursor.moveToFirst())
        {
            characterid = cursor.getString(0);
        }

        cursor.close();

        return characterid;
    }

    public long addCharacter(Character newcharacter) {

        open();

        ContentValues charactervalues = new ContentValues();
        charactervalues.put("name",newcharacter.getName());
        charactervalues.put("level",newcharacter.getLevel());
        charactervalues.put("race",newcharacter.getRace());
        charactervalues.put("characterclass",newcharacter.getCharacterclass());

        long newRowId = db.insert("Characters",null, charactervalues);

        return newRowId;
    }

    public void deleteCharacter(String characterName)
    {
        db.execSQL("DELETE FROM Characters WHERE name='" + characterName + "'");

        db.execSQL("DELETE FROM Decks WHERE characterid='" + getCharacterId(characterName)+ "'");
    }

    public void addCharacterSpells(String characterid, String[] characterSpells) {

        open();

        ArrayList <String> listString = new ArrayList<>();

        String [] columns = new String[] {"spellcardid"};

//        Cursor cursor =
//                db.query("SpellCards", columns, "name = (" + makePlaceholders(characterSpells.length) + ")", characterSpells, null, null, null);
        String query1 = "SELECT spellcardid " +
                "FROM SpellCards AS s " +
                "Where s.name IN ("+ makePlaceholders(characterSpells.length) + ")";

        Cursor cursor = db.rawQuery(query1, characterSpells);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listString.add(cursor.getString(0));
            cursor.moveToNext();
        }

        String[] spellIds = listString.toArray(new String[0]);

        Log.i(LOG_TAG,"Spellids: " + Arrays.toString(spellIds));
        Log.i(LOG_TAG, "Characterid: " + characterid);

        String query2 = "INSERT INTO Decks (characterid,spellcardid) " +
                "VALUES  (?,?)";

        ContentValues deckvalues = new ContentValues();
        deckvalues.put("characterid",characterid);

        for(int i = 0; i < spellIds.length; i++)
        {
            //String [] values = new String[] {characterid,spellIds[i]};
            deckvalues.put("spellcardid",spellIds[i]);
            long rowid = db.insert("Decks", null, deckvalues);
            Log.i(LOG_TAG,"Decks insert row id: " + rowid);
            //db.rawQuery(query2, values);
        }

        cursor.close();
    }

    public void deleteCharacterSpells(String characterid, String[] characterspells, String name)
    {
       db.execSQL("DELETE FROM Decks WHERE characcterid=" + characterid);
       db.execSQL("DELETE FROM Characters WHERE name='" + name + "'");
    }

    public String[] getCharacterSpellCards(int characterid)
    {
        ArrayList <String> listString = new ArrayList<>();

        String query = "SELECT DISTINCT s.name " +
                        "FROM Spellcards as s " +
                        "INNER JOIN Decks as d on d.spellcardid = s.spellcardid " +
                        "INNER JOIN Characters as c on d.characterid = ?";

//        String test_query = "Select characterid " +
//                            "From Decks ";

//        Cursor test_cursor = db.rawQuery(test_query,null);
//        test_cursor.moveToFirst();
//        while(!test_cursor.isAfterLast()) {
//            listString.add(test_cursor.getString(0));
//            test_cursor.moveToNext();
//        }
//        String[] test = listString.toArray(new String[0]);
//        Log.i(LOG_TAG,"Decks Info: " + Arrays.toString(test));

        String [] args = new String[]{Integer.toString(characterid)};

        Cursor cursor = db.rawQuery(query, args);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listString.add(cursor.getString(0));
            cursor.moveToNext();
        }

        String[] characterspells = listString.toArray(new String[0]);

        Log.i(LOG_TAG,"Character spells: " + Arrays.toString(characterspells));

        cursor.close();

        return characterspells;
    }

    /**
     * for use of spell card view from the character
     * @param character
     * @return
     */
    public String[] spellcardCharacterFilter(Character character)
    {

        ArrayList <String> listString = new ArrayList<>();
        int level = Integer.parseInt(character.getLevel());

        String [] args = new String [level+2];
        for(int i = 0; i <= level; i++)
        {
            args[i+1] = String.valueOf(i);

        }

        args[0] = character.getCharacterclass();

        //String [] args = {character.getCharacterclass(), "0", "1", "2", "3"};

        String query = "SELECT s.name " +
                        "FROM SpellCards as s " +
                        "INNER JOIN SpellClass as sc on sc.spellcardid = s.spellcardid " +
                        "INNER JOIN Classes as c on c.classid = sc.classid and c.characterclass = ? " +
                        "WHERE s.level IN ("+ makePlaceholders(args.length) + ")";

        Cursor cursor = db.rawQuery(query, args);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listString.add(cursor.getString(0));
            cursor.moveToNext();
        }

        String[] filterdspells = listString.toArray(new String[0]);

        cursor.close();

        return filterdspells;
    }

    /**
     * creates placeholders for query
     * @param len
     * @return
     */
    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }


//    /**
//     * get spellcards filtered by level
//     * @param lvls
//     * @return
//     */
//    public String[] filterByLevel(int[] lvls, String[] deck){
//
//        List<String> listString = new ArrayList<>();
//
//        String inClause0 = "(";
//
//        for(int i = 0; i < lvls.length; i++)
//        {
//            if(lvls[i] == 1)
//            {
//                if(inClause0.length() == 1)
//                {
//                    inClause0 = inClause0 + i;
//                }
//                else
//                {
//                    inClause0 = inClause0 + "," + i ;
//                }
//
//            }
//        }
//
//        inClause0 = inClause0 + ')';
//
////        String inClause = Arrays.toString(lvls);
////
////        //replacing [] with ()
////        inClause = inClause.replace("[","(");
////        inClause = inClause.replace("]",")");
//
//        Log.d("Test array",inClause0);
//
//        String query = "SELECT name FROM SpellCards " +
//                        "WHERE level IN " + inClause0 +
//                        " AND name In" + deck;
//
//        Cursor cursor = db.rawQuery(query,null);
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()) {
//            listString.add(cursor.getString(0));
//            cursor.moveToNext();
//        }
//
//        String[] spells = listString.toArray(new String[0]);
//
//        Log.d("Spells", spells.toString());
//
//        return spells;
//    }
//
//    /**
//     * get spellcards filtered by class
//     *
//     * @param dndclasses
//     * @param deck
//     * @return
//     */
//    public String[] filterByClass(int[] dndclasses, String[] deck) {
//
//        List<String> listString = new ArrayList<>();
//
//        String inClause0 = "(";
//
//        for(int i = 1; i <= dndclasses.length; i++)
//        {
//            if(dndclasses[i] == 1)
//            {
//                if(inClause0.length() == 1)
//                {
//                    inClause0 = inClause0 + i;
//                }
//                else
//                {
//                    inClause0 = inClause0 + "," + i ;
//                }
//
//            }
//        }
//
//        inClause0 = inClause0 + ')';
//
//
//
//        String query = "SELECT DISTINCT s.name " +
//                        "FROM SpellClass sc " +
//                        "INNER JOIN SpellCards s ON s.spellid = sc.spellid " +
//                        "WHERE sc.classid IN" + inClause0 + " AND s.name In" + deck;
//
//        Cursor cursor = db.rawQuery(query,null);
//
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()) {
//            listString.add(cursor.getString(0));
//            cursor.moveToNext();
//        }
//
//        String[] spells = listString.toArray(new String[0]);
//
//        Log.d("Spells", spells.toString());
//
//        return spells;
//
//    }







}

package com.example.dndapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Character implements Parcelable{
    private String id;
    private String name;
    private String level;
    private String race;
    private String characterclass;

    public Character()
    {

    }

    public Character(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setCharacterclass(String characterclass) {
        this.characterclass = characterclass;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getRace() {
        return race;
    }

    public String getCharacterclass() {
        return characterclass;
    }

    public Character (Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id = data[0];
        this.name = data[1];
        this.level = data[2];
        this.race = data[3];
        this.characterclass = data[4];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.id,
                this.name,
                this.level,
                this.race,
                this.characterclass});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        public Character[] newArray(int size) {
            return new Character[size];
        }
    };
}

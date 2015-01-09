package com.example.victorarcasrios.drawmethis;

import android.os.Parcelable;

/**
 * Created by mati on 9/01/15.
 */
public abstract class Figure implements Parcelable{

    protected char type;

    protected Figure(char type){
        this.type = type;
    }

    public char getType(){
        return type;
    }
}

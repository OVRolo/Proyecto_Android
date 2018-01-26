package com.example.juancarlosmuoz.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Juan Carlos Muñoz on 24/01/2018.
 */

public class Usuario implements Serializable {

    private String pais;
    private String habitante;

    public Usuario(String p, String hab){
        this.pais=p;
        this.habitante=hab;

    }

   public  String getPAis(){
        return  pais;
   }

    public String getHabitante(){
        return habitante;
    }


}

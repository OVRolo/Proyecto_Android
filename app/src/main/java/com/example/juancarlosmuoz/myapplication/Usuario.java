package com.example.juancarlosmuoz.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;


import java.io.Serializable;

/**
 * Created by Juan Carlos Muñoz on 24/01/2018.
 */

public class Usuario implements Serializable  {

    public String nombre;
//    public String apellido;
//    public String puesto;
//    int  organizacion_id;
//    public String email;
//    public int telefono;

    public Usuario()
    {
        nombre = "";
//        apellido="";
//        telefono = 0;
//        puesto = "";
//        organizacion_id=0;
//        email ="";

    }

    public Usuario(String nom)//, String ape, int telf, String pues, String ema,int org_id)
    {
        this.nombre=nom;
//        this.apellido=ape;
//        this.telefono=telf;
//        this.puesto=pues;
//        this.email=ema;
//        this.organizacion_id=org_id;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public String getApellido() {
//        return apellido;
//    }
//
//    public void setApellido(String apellido) {
//        this.apellido = apellido;
//    }
//
//    public String getPuesto() {
//        return puesto;
//    }
//
//    public void setPuesto(String puesto) {
//        this.puesto = puesto;
//    }
//
//    public int getOrganizacion_id() {
//        return organizacion_id;
//    }
//
//    public void setOrganizacion_id(int organizacion_id) {
//        this.organizacion_id = organizacion_id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public int getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(int telefono) {
//        this.telefono = telefono;
//    }
}
package com.example.juancarlosmuoz.myapplication;


import java.io.Serializable;

/**
 * Created by Juan Carlos Muñoz on 24/01/2018.
 */

public class Usuario implements Serializable  {
    public int id;
    public String nombre;
    public String apellido;
    public String puesto;
    int  organizacion_id;
    public String email;
    public String telefono;



    public Usuario(int id,String nom, String ape, String telf, String pues, String ema,int org_id)
    {
        this.id=id;
        this.nombre=nom;
        this.apellido=ape;
        this.telefono=telf;
        this.puesto=pues;
        this.email=ema;
        this.organizacion_id=org_id;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getOrganizacion_id() {
        return organizacion_id;
    }

    public void setOrganizacion_id(int organizacion_id) {
        this.organizacion_id = organizacion_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}
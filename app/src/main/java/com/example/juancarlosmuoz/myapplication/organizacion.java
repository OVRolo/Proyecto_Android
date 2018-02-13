package com.example.juancarlosmuoz.myapplication;

import java.io.Serializable;

/**
 * Created by Juan Carlos Muñoz on 13/02/2018.
 */

public class organizacion  implements Serializable {

    int id;
    String organizacion;

    public  organizacion(int i, String org)
    {
        id=i;
        organizacion=org;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

}

package com.example.juancarlosmuoz.myapplication;

import android.drm.DrmStore;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class pagina2 extends AppCompatActivity {

    String llave;
    Usuario usu;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etEmail;
    private EditText etPuesto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina2_);

        etNombre=(EditText)findViewById(R.id.etNombre);
        etApellido=(EditText)findViewById(R.id.etApellido);
        etTelefono=(EditText)findViewById(R.id.etTelefono);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPuesto=(EditText)findViewById(R.id.etPuesto);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                llave=key;
            }
        }

        switch (llave){
            case "editar":{
                usu =(Usuario) getIntent().getSerializableExtra("editar");
                etNombre.setText(usu.getNombre());
                etNombre.setEnabled(false);
                etApellido.setText(usu.getApellido());
                etApellido.setEnabled(false);
                etTelefono.setText(usu.getTelefono());
                etTelefono.setEnabled(false);
                etEmail.setText(usu.getEmail());
                etEmail.setEnabled(false);
                etPuesto.setText(usu.getPuesto());
                etPuesto.setEnabled(false);
            }

            case "añadir":{}
        }




       // int numero = (int)getIntent().getSerializableExtra("vacio");
    }

    protected void onResume() {
        super.onResume();

    }

    public void borrar(View v) {
    setDelete delete=new setDelete();

        int id= usu.id;
        String ID=Integer.toString(id);

        delete.execute(ID);

    }

    public void salir(View v) {
        finish();
    }

    /**
     * Created by Juan Carlos Muñoz on 24/01/2018.
     */


}



    class setDelete extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            BufferedReader rd;
            String response="";

            try {
                url = new URL("http://10.21.101.24:8080/CRUD.asmx/delete");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("POST");

                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(params.toString());
                wr.flush();
                // Get the response
                 rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    response=line;
                }
                wr.close();
                rd.close();


            } catch (IOException e) {
                e.printStackTrace();
            }


            return response.toString();
        }
    }
package com.example.juancarlosmuoz.myapplication;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class pagina2 extends AppCompatActivity {

    String llave;
    Usuario usu;
    int prue=0;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etEmail;
    private EditText etPuesto;
    private Spinner spinner;


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


            spinner = (Spinner) findViewById(R.id.spinner);
            String []opciones={"MH07","CID","ESCUELA","AYUNTAMIENTO","TDA"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
            spinner.setAdapter(adapter);

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
                spinner.setSelection(usu.getOrganizacion_id());
                spinner.setEnabled(false);
            }

            case "añadir":{
                /*setDelete delete=new setDelete();
                String nombre= etNombre.getText().toString();
                String apellido= etApellido.getText().toString();
                String telefono= etTelefono.getText().toString();
                String email= etEmail.getText().toString();
                String puesto= etPuesto.getText().toString();
                delete.execute(llave,nombre,apellido,telefono,email,puesto);*/

            }
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
        llave="borrar";
        delete.execute(llave,ID);

    }

    public void actualizar(View v){

        setDelete delete=new setDelete();
        if(prue==0) {
            etNombre.setEnabled(true);
            etApellido.setEnabled(true);
            etTelefono.setEnabled(true);
            etEmail.setEnabled(true);
            etPuesto.setEnabled(true);
            spinner.setEnabled(true);
            prue=1;
        }
        else {
            org_desplegable();
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String telefono = etTelefono.getText().toString();
            String email = etEmail.getText().toString();
            String puesto = etPuesto.getText().toString();
            String organizacion_id = Integer.toString(usu.getOrganizacion_id());
            int id= usu.id;
            String ID=Integer.toString(id);
            llave = "actualizar";
            delete.execute(llave,ID, nombre, apellido, telefono, email, puesto,organizacion_id);
            prue=0;
        }
    }

    public void  org_desplegable() {

        String selec=spinner.getSelectedItem().toString();
        switch(selec){
            case "MH07": usu.setOrganizacion_id(1); break;
            case "CID": usu.setOrganizacion_id(2); break;
            case "ESCUELA": usu.setOrganizacion_id(3); break;
            case "AYUNTAMIENTO": usu.setOrganizacion_id(4); break;
            case "TDA": usu.setOrganizacion_id(4); break;
        }

    }

    public void salir(View v) {
        Intent parametro=new Intent(this,MainActivity.class);
      //  parametro.putExtra("editar", datos);
        startActivity(parametro);
        finish();
    }

    /**
     * Created by Juan Carlos Muñoz on 24/01/2018.
     */


}



    class setDelete extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {



            try {


                URL url=null;
                JSONObject postDataParams = new JSONObject();



                switch (params[0]){
                    case "actualizar":{
                        url = new URL("http://10.21.101.24:8080/CRUD.asmx/update"); // here is your URL path
                        postDataParams.put("id", params[1]);
                        postDataParams.put("nom", params[2]);
                        postDataParams.put("ape", params[3]);
                        postDataParams.put("tlfn", params[4]);
                        postDataParams.put("ema", params[5]);
                        postDataParams.put("puest", params[6]);
                        postDataParams.put("org",params[7] );
                        break;
                    }
                    case "añadir":{
                        url = new URL("http://10.21.101.24:8080/CRUD.asmx/delete"); // here is your URL path
                        postDataParams.put("nombre", params[1]);
                        postDataParams.put("apellido", params[2]);
                        postDataParams.put("telefono", params[3]);
                        postDataParams.put("email", params[4]);
                        postDataParams.put("puesto", params[5]);
                        break;
                    }
                    case "borrar":{
                        url = new URL("http://10.21.101.24:8080/CRUD.asmx/delete"); // here is your URL path
                        postDataParams.put("id", params[1]);
                        break;
                    }
                }


                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
//
                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return null;

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while(itr.hasNext()){

                String key= itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }


    }
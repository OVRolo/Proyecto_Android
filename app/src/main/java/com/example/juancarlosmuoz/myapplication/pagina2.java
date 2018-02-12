package com.example.juancarlosmuoz.myapplication;

import android.content.Intent;
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

                URL url = new URL("http://10.21.101.24:8080/CRUD.asmx/delete"); // here is your URL path

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("id", params[0]);
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
                    return sb.toString();

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
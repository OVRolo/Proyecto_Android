package com.example.juancarlosmuoz.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


public class MainActivity extends AppCompatActivity {

    private String[] paises = { "Argentina", "Chile", "Paraguay", "Bolivia",
            "Peru", "Ecuador", "Brasil", "Colombia", "Venezuela", "Uruguay" };
    private String[] habitantes = { "40000000", "17000000", "6500000",
            "10000000", "30000000", "14000000", "183000000", "44000000",
            "29000000", "3500000" };
    private TextView tv1;
    private ListView lv1;
    public Usuario usu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        new LongOperation().execute("");
    }
    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL("http://10.21.101.24:8080/CRUD.asmx/getTable");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestMethod("POST");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
               InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                String res = out.toString();
                System.out.println(out.toString()); //Prints the string content read from input stream
                reader.close();

                JSONObject jsonObj = new JSONObject(res);

              /*  JSONObject jsonObj = null;
                try {
                    jsonObj = XML.toJSONObject(res);
                } catch (JSONException e) {
                    Log.e("JSON exception", e.getMessage());
                    e.printStackTrace();
                }*/
                Iterator<String> keys = jsonObj.keys();
                String key = keys.next();

               JSONArray data= jsonObj.getJSONArray(key);
               JSONObject dat=data.getJSONObject(0);
                String  nombre=dat.get("nombre").toString();
                Log.i("nombre", "dato "+nombre);


               /*Object datos;

                try {

                    //Obtenemos los objetos dentro del objeto principal.
                    Iterator<String> keys = jsonObj.keys();

                    while (keys.hasNext())
                    {
                        // obtiene el nombre del objeto.
                        String key = keys.next();
                        Log.i("Parser", "objeto : " + key);
                        JSONObject jsonObject = jsonObj.getJSONObject(key);

                        //obtiene valores dentro del objeto.
                       datos = jsonObject.get("Usuario");


                        //Imprimimos los valores.
                        Log.i("Parser", ""+datos);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Parser", e.getMessage());
                }*/



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}

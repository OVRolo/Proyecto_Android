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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


public class MainActivity extends AppCompatActivity {

    private TextView tv1;
    private ListView lv1;
    ArrayAdapter<String> adaptador;

    getInformacion getInfo=new getInformacion();
    List<Usuario>usu;
    String[] nombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv1 = (ListView)findViewById(R.id.list1);
        getInfo.execute("");
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            usu= getInfo.get();
            nombres = new String[usu.size()];

            for (int i=0; i<usu.size(); i++){
             nombres[i]=usu.get(i).nombre;
             System.out.println("indice"+i);
            }

            adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nombres);

            lv1.setAdapter(adaptador);

            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                    Usuario datos=usu.get(i);

                    Intent parametro=new Intent(MainActivity.this,pagina2.class);
                    parametro.putExtra("usu", datos);
                    startActivity(parametro);
                }
            });

        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }

    }













    private class getInformacion extends AsyncTask<String, Void, List<Usuario>> {
        @Override
        protected List<Usuario> doInBackground(String... params) {
            URL url = null;
            List<Usuario> usu=new ArrayList<Usuario>();

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
             //  JSONObject dat=data.getJSONObject(0);
               // String  nombre=dat.get("nombre").toString();
                //Log.i("nombre", "dato "+nombre);



                for (int i=0; i<data.length(); i++){
                    JSONObject dat=data.getJSONObject(i);

                    int  id=(int)dat.get("Id");
                    String  nombre=dat.get("nombre").toString();
                    String  apellido=dat.get("apellido").toString();
                    String  telefono=dat.get("telefono").toString();
                    String  puesto=dat.get("puesto").toString();
                    String  email=dat.get("email").toString();
                    int  organizacion_id=(int)dat.get("organizacion_id");

                    usu.add(new Usuario(id,nombre,apellido,telefono,puesto,email,organizacion_id));
                }


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
            return usu;
        }

     /*   @Override
        protected void onPostExecute(List<Usuario> result) {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }*/
    }
}

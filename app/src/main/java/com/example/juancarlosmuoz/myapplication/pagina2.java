package com.example.juancarlosmuoz.myapplication;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class pagina2 extends AppCompatActivity {

    String llave;
    Usuario usu;
    setDelete delete;
    int prue=0;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etEmail;
    private EditText etPuesto;
    private Spinner spinner;
    getOrganizacion getOrg;
    List<organizacion>listaOrg;
    private Button btn_insertar;
    private Button btnActualizar;
    private Button btn_Borrar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina2_);

        etNombre=(EditText)findViewById(R.id.etNombre);
        etApellido=(EditText)findViewById(R.id.etApellido);
        etTelefono=(EditText)findViewById(R.id.etTelefono);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPuesto=(EditText)findViewById(R.id.etPuesto);
        btn_insertar=(Button)findViewById(R.id.btn_insertar);
        btnActualizar=(Button)findViewById(R.id.btnActualizar);
        btn_Borrar=(Button)findViewById(R.id.btnBorrar);

        getOrg=new getOrganizacion();
        getOrg.execute();
        try {
            listaOrg=getOrg.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                llave=key;
            }


            spinner = (Spinner) findViewById(R.id.spinner);
            String []opciones=new String[listaOrg.size()];
            for (int i=0; i<listaOrg.size(); i++){
                opciones[i]=listaOrg.get(i).organizacion;
            }
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
                spinner.setSelection(usu.getOrganizacion_id()-1);
                spinner.setEnabled(false);
                btn_insertar.setVisibility(View.INVISIBLE);
                btn_insertar.setEnabled(false);
                break;
            }

            case "añadir":{
                btn_Borrar.setVisibility(View.INVISIBLE);
                btn_Borrar.setEnabled(false);
                btnActualizar.setVisibility(View.INVISIBLE);
                btnActualizar.setEnabled(false);


                /*setDelete delete=new setDelete();
                String nombre= etNombre.getText().toString();
                String apellido= etApellido.getText().toString();
                String telefono= etTelefono.getText().toString();
                String email= etEmail.getText().toString();
                String puesto= etPuesto.getText().toString();
                delete.execute(llave,nombre,apellido,telefono,email,puesto);*/
                break;
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

        delete=new setDelete();
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
            etNombre.setEnabled(false);
            etApellido.setEnabled(false);
            etTelefono.setEnabled(false);
            etEmail.setEnabled(false);
            etPuesto.setEnabled(false);
            spinner.setEnabled(false);
        }
    }

    public void  org_desplegable() {

        String selec=spinner.getSelectedItem().toString();

        switch(selec){
            case "MH07": usu.setOrganizacion_id(listaOrg.get(0).id); break;
            case "CID": usu.setOrganizacion_id(listaOrg.get(1).id); break;
            case "ESCUELA": usu.setOrganizacion_id(listaOrg.get(2).id); break;
            case "AYUNTAMIENTO": usu.setOrganizacion_id(listaOrg.get(3).id); break;
            case "TDA": usu.setOrganizacion_id(listaOrg.get(4).id); break;
        }

    }

    public void añadir(View v) {
        delete=new setDelete();
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String telefono = etTelefono.getText().toString();
        String email = etEmail.getText().toString();
        String puesto = etPuesto.getText().toString();
        String organizacion_id =Integer.toString(spinner.getSelectedItemPosition()+1) ;
        int id= 0;
        String ID=Integer.toString(id);
        llave = "añadir";
        delete.execute(llave,ID, nombre, apellido, telefono, email, puesto,organizacion_id);
    }

    public void salir(View v) {
        Intent parametro=new Intent(this,MainActivity.class);
      //  parametro.putExtra("editar", datos);
        startActivity(parametro);
        finish();
    }

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
                        url = new URL("http://10.21.101.24:8080/CRUD.asmx/insert"); // here is your URL path
                        postDataParams.put("id", params[1]);
                        postDataParams.put("nom", params[2]);
                        postDataParams.put("ape", params[3]);
                        postDataParams.put("tlfn", params[4]);
                        postDataParams.put("ema", params[5]);
                        postDataParams.put("puest", params[6]);
                        postDataParams.put("org",params[7] );
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

    class getOrganizacion extends AsyncTask<String, Void, List<organizacion>> {
    @Override
    protected List<organizacion> doInBackground(String... params) {
        URL url = null;
        List<organizacion> organizacion=new ArrayList<organizacion>();

        try {
            url = new URL("http://10.21.101.24:8080/CRUD.asmx/orgConsulta");
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

                int  id=(int)dat.get("id");
                String  org=dat.get("org").toString();


                organizacion.add(new organizacion(id,org));
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
        return organizacion;
    }
}
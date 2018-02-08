package com.example.juancarlosmuoz.myapplication;

import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class pagina2 extends AppCompatActivity {


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

        usu =(Usuario) getIntent().getSerializableExtra("usu");
    }

    protected void onResume() {
        super.onResume();

        etNombre.setText(usu.getNombre());
        etApellido.setText(usu.getApellido());
        etTelefono.setText(usu.getTelefono());
        etEmail.setText(usu.getEmail());
        etPuesto.setText(usu.getPuesto());

    }
    public void salir(View v) {
        finish();
    }

    /**
     * Created by Juan Carlos Muñoz on 24/01/2018.
     */


}

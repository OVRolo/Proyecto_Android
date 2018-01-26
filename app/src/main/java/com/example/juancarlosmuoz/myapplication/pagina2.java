package com.example.juancarlosmuoz.myapplication;

import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class pagina2 extends AppCompatActivity {

    private TextView tv_datos;
    Usuario usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina2_);

        tv_datos=(TextView)findViewById(R.id.tv_datos);


        usu =(Usuario) getIntent().getSerializableExtra("usu");
    }

    protected void onResume() {
        super.onResume();

        tv_datos.setText(usu.getHabitante());


    }
    public void salir(View v) {
        finish();
    }

    /**
     * Created by Juan Carlos Muñoz on 24/01/2018.
     */


}

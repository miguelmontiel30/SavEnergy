package com.example.miguelangel.savenergy;

import android.content.Intent;
import android.R.*;
import android.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registrarse extends AppCompatActivity implements View.OnClickListener{

    //Declaracion de variables
    Button registrarse;
    //Fin de declaración de variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        registrarse = (Button) findViewById(R.id.registrarse);
        registrarse.setOnClickListener(this);
    }

    public void registrarse_OnClick() {
        Intent intent = new Intent(Registrarse.this,Configuracion_f_corte.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view == registrarse){
            registrarse_OnClick();
        }
    }
}

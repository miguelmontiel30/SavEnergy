package com.example.miguelangel.savenergy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    //Declaración de variables para componentes del archivo XML
    Button tarifa;
    Button iniciar_sesion;
    Button registrarse;
    //Fin de la declaración de variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        iniciar_sesion = (Button) findViewById(R.id.inicio_sesion);         //Asignación de variable a componente en XML
        iniciar_sesion.setOnClickListener(this);

        registrarse = (Button) findViewById(R.id.registrarse);              //Asignación de variable a componente en XML
        registrarse.setOnClickListener(this);

        tarifa = (Button) findViewById(R.id.tarifas);
        tarifa.setOnClickListener(this);
    }

    public void inicio_sesion_OnClick() {
        Intent intent = new Intent(Inicio.this, Inicio_Sesion.class);
        startActivity(intent);
        finish();
    }

    public void registrarse_OnClick() {
        Intent intent = new Intent(Inicio.this, Registrarse.class);
        startActivity(intent);
        finish();
    }

    public void tarifas_OnClick() {
        Intent intent = new Intent(Inicio.this, Tarifas.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == iniciar_sesion) {
            inicio_sesion_OnClick();
        }else if (view == registrarse){
            registrarse_OnClick();
        }else if (view == tarifa) {
            tarifas_OnClick();
        }
    }
}


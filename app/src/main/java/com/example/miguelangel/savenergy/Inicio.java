package com.example.miguelangel.savenergy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

                                //Inicio de la declaración de variables
    Button iniciar_sesion;
    Button registrarse;
                                //Fin de la declaración de variables

            //Método para asignar toolbar
    public void startDua(View view) {
        //startActivity(new Intent(this, Registrarse.class));
        startActivity(new Intent(this, Inicio_Sesion.class));
    }

            //Método para cambiar a la interfaz Inicio de Sesion
    public void inicio_sesion_OnClick() {
        Intent intent = new Intent(Inicio.this, Inicio_Sesion.class);
        startActivity(intent);
        finish();
    }

            //Método para cambiar a la interfaz Registrarse
    public void registrarse_OnClick() {
        Intent intent = new Intent(Inicio.this, Registrarse.class);
        startActivity(intent);
        finish();
    }

    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        iniciar_sesion = (Button) findViewById(R.id.inicio_sesion);         //Asignación de variable a componente en XML
        iniciar_sesion.setOnClickListener(this);

        registrarse = (Button) findViewById(R.id.registrarse);              //Asignación de variable a componente en XML
        registrarse.setOnClickListener(this);
    }

    //Método onClick
    @Override
    public void onClick(View view) {
        if (view == iniciar_sesion) {
            inicio_sesion_OnClick();
        }else if (view == registrarse){
            registrarse_OnClick();
        }
    }
}


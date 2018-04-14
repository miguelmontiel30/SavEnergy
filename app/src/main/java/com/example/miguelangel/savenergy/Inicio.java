package com.example.miguelangel.savenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

                                //Inicio de la declaración de variables
    Button iniciar_sesion;
    Button registrarse;
    String correo,password;
                                //Fin de la declaración de variables

            //Método para validar user en caso de que haya uno con sesión iniciada
    public void invocarDatos(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        correo = preferences.getString("correo","Null");
        password = preferences.getString("contrasenia","Null");
        if (!correo.equals("Null")&&!password.equals("Null")) {
            cargarPrincipal();
            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_LONG).show();
        }
    }
        //Metodo para pasar a Activity Principal
    public void cargarPrincipal(){
        Intent intent = new Intent(Inicio.this, Principal.class);
        startActivity(intent);
        finish();
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
        iniciar_sesion.setOnClickListener(this);                            //Asignación de evento a componente en XML

        registrarse = (Button) findViewById(R.id.registrarse);              //Asignación de variable a componente en XML
        registrarse.setOnClickListener(this);

        invocarDatos();
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


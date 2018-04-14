package com.example.miguelangel.savenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Button iniciar_sesion,registrarse;
    String user,password;
                                //Fin de la declaración de variables

            //Método para conectar y validar user en la BD en caso de que haya uno con sesión iniciada
    public String validacionUser(){
                            //Carga de preferencias del usuario
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        user = preferences.getString("usuario","No hay nada guardado");
        password = preferences.getString("contrasenia","No hay nada guardado");

        URL url = null;
        String line = "";
        String webServiceResult="";

        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/Login.php?contra="+password+"&email="+user);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }

                                //Método para iniciar sesion en caso de que la validación sea correcta
    public void cargarSesion(){
        try {
            String resultJSON="";
            JSONObject respuestaJSON = new JSONObject  (validacionUser());//Se guarda el resultado obtenido del JSON
            resultJSON = respuestaJSON.getString("estado");//guarda el registro del arreglo estado
            if (resultJSON.equals("1")) {      // el correo y contraseña son correctas
                cargarPrincipal();
                Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG).show();
            }
            else if (resultJSON.equals("2")){//el ususario no existe
                Toast.makeText(getApplicationContext(),"Bienvenido a SavEnergy",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

                                //Metodo para pasar a Activity Principal
    public void cargarPrincipal(){
        Intent intent = new Intent(Inicio.this, Principal.class);
        startActivity(intent);
        finish();
    }

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

        cargarSesion();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        iniciar_sesion = (Button) findViewById(R.id.inicio_sesion);         //Asignación de variable a componente en XML
        iniciar_sesion.setOnClickListener(this);                            //Asignación de evento a componente en XML

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


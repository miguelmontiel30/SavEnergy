package com.example.miguelangel.savenergy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Inicio_Sesion extends AppCompatActivity implements View.OnClickListener{

                                    //Inicio de la declaracion de variables
    TextInputLayout til_correo,til_password;
    EditText email, pass;
    Button iniciar;
    String correo_cache,password_cache,nombre_cache;
                                    //Fin de la declaración de variables


                            //Metodo para lanzar el Activity Principal)
    public void iniciar_sesionOnclick() {
        Intent intent = new Intent(Inicio_Sesion.this, Principal.class);
        startActivity(intent);
        finish();
    }

                            //Método para regresar si el usuario presiono atras -->
    public void onBackPressed(){
        Intent intent = new Intent(Inicio_Sesion.this, Inicio.class);
        startActivity(intent);
        finish();
    }

                            //Metodo con 2 parametros para conectar con servidor y devuelve datos
    public String validar(String contra, String email){//Metodo que devuelve dos objetos - estado y consulta, convertidos en JSON
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/Login.php?contra="+contra+"&email="+email);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }
    public boolean sesion(){
        boolean ses= false;
        try {
            String resultJSON="";
            JSONObject respuestaJSON = new JSONObject  (validar(pass.getText().toString(), email.getText().toString()));//Se guarda el resultado obtenido del JSON
            resultJSON = respuestaJSON.getString("estado");//guarda el registro del arreglo estado
            if (resultJSON.equals("1")) {      // el correo y contraseña son correctas
                correo_cache = respuestaJSON.getJSONObject("usuario").getString("email");
                password_cache = respuestaJSON.getJSONObject("usuario").getString("contrasenia");
                nombre_cache = respuestaJSON.getJSONObject("usuario").getString("nombre");
                ses = true;
                guardarUser();
                Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG).show();
            }
            else if (resultJSON.equals("2")){//el ususario no existe
                til_correo.setError(getResources().getString(R.string.datos_inv));
                til_password.setError(getResources().getString(R.string.datos_inv));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ses;
    }

                            //Método para guardar o recordar al usuario
    public void guardarUser(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo", correo_cache);
        editor.putString("contrasenia",password_cache);
        editor.putString("nombre",nombre_cache);

        editor.commit();
    }

                            //Método para regresar cuando pulsan boton back (toolbar)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

                            //Metodo onCreate
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio__sesion);

        //Se asigna la variable que corresponde a cada componente declarado en el archivo XML

        email = (EditText) findViewById(R.id.correo);                //Asignación de variables de tipo EditText
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til_correo.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });       //Asignación de evento al teclear texto el usuario

        pass = (EditText) findViewById(R.id.password);               //Asignación de variables de tipo EditText
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til_password.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });       //Asignación de evento al teclear texto el usuario

        iniciar = (Button) findViewById(R.id.iniciar_sesion);  //Asignación de variables de tipo Button
        iniciar.setOnClickListener(this);                       //Asignación de evento a boton

        til_correo = (TextInputLayout) findViewById(R.id.til_correo);       //Asignación de variables de tipo TextInputLayout
        til_password = (TextInputLayout) findViewById(R.id.til_password);   //Asignación de variables de tipo TextInputLayout

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }


    @Override
    public void onClick(View view) {
        if (sesion()){
            iniciar_sesionOnclick();
        }
    }
}
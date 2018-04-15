package com.example.miguelangel.savenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class Configuracion_Perfil extends AppCompatActivity implements View.OnClickListener {

                            // Inicio de la declaración de variables
    EditText nombre,correo;
    Button guardar;
    TextInputLayout til_correo,til_nombre;
                            //Fin de la declaración de variables

                            //Método que guarda los cambios que realiza el usuario
    public String inserta(String nombre, String correo){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        String id = preferences.getString("id_usuario","Null");
        String line = "";
        String webServiceResult="";
        URL url = null;
        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/change_profile_user.php?id_user="+id+"&nombre="+nombre+"&correo="+correo);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;
    }

    public void guardar(){
        Toast.makeText(getApplicationContext(),"Configuración Guardada",Toast.LENGTH_LONG).show();
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String name = nombre.getText().toString();
        String email = correo.getText().toString();
        if (validacion_Correo(email) && validacion_Nombre(name)){
            if(inserta(name,email).equals("Cambios Guardados"));

            editor.putString("correo", email);
            editor.putString("nombre",name);
            editor.commit();

            onBackPressed();
        }
    }

                            //Método para validar los campos de texto
    public boolean validacion_Correo(String correo){
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            til_correo.setError(getResources().getString(R.string.correo_no_valido));
            return false;
        } else {
            til_correo.setError(null);
        }
        return true;
    }

    public boolean validacion_Nombre(String name){
        if (!String.valueOf(nombre.getText()).equals("")) {
            til_nombre.setError(null);
            return true;
        }else{
            til_nombre.setError(getResources().getString(R.string.campo_obligatorio));
            return false;
        }
    }
                            //Método para setear campos de UI con la carga de datos
    public void usuarioCache(){
        String user_name,email;
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        email = preferences.getString("correo","Null");
        user_name = preferences.getString("nombre","Null");
        setCampos(user_name,email);
    }

                            //Método que llena de datos los EditText
    public void setCampos(String name_set, String email_set){
        nombre.setText(name_set);
        correo.setText(email_set);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion__perfil);

                                //Asignación de variable a componente en XML

        nombre = (EditText) findViewById(R.id.et_nombre);               //Asignación de variable de tipo EditText
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validacion_Nombre(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });         //Asignación de evento al teclear texto el usuario

        correo = (EditText) findViewById(R.id.et_correo);               //Asignación de variable de tipo EditText
        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validacion_Correo(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });         //Asignación de evento al teclear texto el usuario

        til_correo = (TextInputLayout) findViewById(R.id.txt_correo);   //Asignación de variable de tipo TextInputLayout
        til_nombre = (TextInputLayout) findViewById(R.id.txt_nombre);   //Asignación de variable de tipo TextInputLayout

        guardar = (Button) findViewById(R.id.btn_guardar);              //Asignación de variable de tipo Button
        guardar.setOnClickListener(this);

                                //Metodos que carga la Interfaz

        //Cargar datos en la interfaz
        usuarioCache();
    }

    //Método si el usuario presiono ir hacia atrás
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Configuracion_Perfil.this,Menu_Configuraciones.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view == guardar){
            guardar();
        }
    }
}

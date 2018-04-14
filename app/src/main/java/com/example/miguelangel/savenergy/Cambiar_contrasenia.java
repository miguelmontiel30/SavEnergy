package com.example.miguelangel.savenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Cambiar_contrasenia extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout contra, contra2;
    Button guardar;
    EditText pass, pass2;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    //Método Si presiona en boton "Atrás"
    @Override
    public  void onBackPressed(){
        Intent intent = new Intent(Cambiar_contrasenia.this,Menu_Configuraciones.class);
        startActivity(intent);
        finish();
    }

    public void cambiar(String pass, String id_user){//Metodo que devuelve dos objetos - estado y consulta, convertidos en JSON
        URL url = null;
        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/changed_password.php?pass="+pass+"&id_user="+id_user);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
        } catch (Exception e) {}

    }

    public void ejecutar(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        if (!pass.getText().toString().equals("")){
            if (!pass.getText().toString().equals("")){

                cambiar(pass.getText().toString(),preferences.getString("id_usuario","Null"));
            }else {
                contra2.setError("Obligatorio llenar este campo");
            }
        }else{
            contra.setError("Obligatorio llenar este campo");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasenia);

        guardar = (Button) findViewById(R.id.btn_guardar_pass);
        guardar.setOnClickListener(this);

        pass = (EditText) findViewById(R.id.et_contraseñia);
        pass2 = (EditText) findViewById(R.id.repeat_contraseñia);

        contra = (TextInputLayout) findViewById(R.id.til_contraseñia);
        contra2 = (TextInputLayout) findViewById(R.id.til_repetir_contraseñia);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    @Override
    public void onClick(View view) {
        if(view == guardar){
            ejecutar();
        }

    }
}

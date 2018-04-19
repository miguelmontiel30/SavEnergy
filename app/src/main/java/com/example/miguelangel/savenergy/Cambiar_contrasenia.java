package com.example.miguelangel.savenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class Cambiar_contrasenia extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout til_pass, til_pass2;
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

    public String cambiar(String pass, String id_user){//Metodo que devuelve dos objetos - estado y consulta, convertidos en JSON
        String line = "";
        String webServiceResult="";
        URL url = null;
        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/changed_password.php?pass="+pass+"&id_user="+id_user);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;
    }

    public boolean ejecutar(){
        boolean opc = false;
        String contr, id_user;
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        id_user = preferences.getString("id_usuario","Null");
        contr = pass.getText().toString();
        if (!pass.getText().toString().equals("")){
            if (!pass2.getText().toString().equals("")){
                if (validar_contrasenia(pass2.getText().toString())){
                    if(cambiar(contr,id_user).equals("Cambios realizados!")){
                        opc = true;
                    }
                }
            }else {
                til_pass2.setError("Obligatorio llenar este campo");
            }
        }else{
            til_pass.setError("Obligatorio llenar este campo");
        }
        return opc;
    }

    //Método para validar que las contraseñas sean iguales
    public boolean validar_contrasenia(String pass2){
        String pass_1 = String.valueOf(pass.getText());
        if (pass2.equals(pass_1)) {
            til_pass2.setError(null);
            return true;
        }else{
            til_pass2.setError(getResources().getString(R.string.contrasenias));
        }
        return false;
    }
    //Metodo para validar los caracteres usados en la contraseña
    public boolean validar_car_contrasenia(String pass){
        Pattern patron = Pattern.compile("^[A-Za-z0-9 ]+$");
        if (!patron.matcher(pass).matches() || pass.length() > 30) {
            til_pass.setError(getResources().getString(R.string.pass_inc));
            return false;
        } else {
            til_pass.setError(null);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasenia);

        guardar = (Button) findViewById(R.id.btn_guardar_pass);
        guardar.setOnClickListener(this);

        pass = (EditText) findViewById(R.id.et_contraseñia);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>15){
                    til_pass.setError("Máximo 15 caracteres");
                }else{
                    validar_car_contrasenia(String.valueOf(s));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pass2 = (EditText) findViewById(R.id.repeat_contraseñia);
        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>15){
                    til_pass2.setError("Maximo 15 caracteres");
                }else{
                    validar_contrasenia(String.valueOf(s));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        til_pass = (TextInputLayout) findViewById(R.id.til_contraseñia);
        til_pass2 = (TextInputLayout) findViewById(R.id.til_repetir_contraseñia);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    @Override
    public void onClick(View view) {
        if(view == guardar){
            if(ejecutar()==true) {
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Cambios realizados", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

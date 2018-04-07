package com.example.miguelangel.savenergy;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Registrarse extends AppCompatActivity implements View.OnClickListener{
    // Declaracion de varuables
    TextInputEditText clave, correo, pass, pass2;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        //Asignacion de variables con componentes
        clave = (TextInputEditText) findViewById(R.id.et_clave);
        correo = (TextInputEditText) findViewById(R.id.et_correo);
        pass = (TextInputEditText) findViewById(R.id.et_contrasenia);
        pass2 = (TextInputEditText) findViewById(R.id.repeat_password);
        registrar = (Button) findViewById(R.id.registrarse);
        registrar.setOnClickListener(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    public void registrarse_OnClick() {//Metodo que abre el siguiente Activity
        Intent intent = new Intent(Registrarse.this,Configuracion_f_corte.class);
        intent.putExtra("clave",clave.getText().toString());
        intent.putExtra("correo",correo.getText().toString());
        intent.putExtra("pass",pass.getText().toString());
        startActivity(intent);
        finish();
    }
    public String validar(int code, String data){//Metodo que devuelve dos objetos - estado y consulta, convertidos en JSON
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/registro.php?code="+code+"&data="+data);//url en donde esta guardado el archivo PHP
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null){//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;

            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }
    public boolean validarClave(){//Método para validar la clave del producto
        String est;
        String id;
        boolean clv=false;
        try {
            JSONObject respuestaJSON = new JSONObject  (validar(1, clave.getText().toString()));//Se guarda el resultado obtenido del JSON
            String resultJSON = respuestaJSON.getString("estado");//guarda el registro del arreglo estado
            if (resultJSON.equals("1")) {      // hay registros
                est = respuestaJSON.getJSONObject("consulta").getString("estado");
                id = respuestaJSON.getJSONObject("consulta").getString("id_usuario");
                if(est.equals("Activo")||est.equals("activo")) {//si el el producto esta activado
                    if(id.equals("null")||id=="null"){  // si el producto no tiene cliente asignado
                        clv = true;
                    }else {//El producto ya tiene un cliente asignado
                        Toast.makeText(getApplicationContext(), "Clave incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }else{//El producto no esta activado
                    Toast.makeText(getApplicationContext(), "Clave incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
            else if (resultJSON.equals("2")){//la clave del producto es incorrecta
                Toast.makeText(getApplicationContext(), "Clave incorrecta", Toast.LENGTH_SHORT).show();
                clv=false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clv;
    }
    public boolean validarCorreo(){//Método para validar el correo
        boolean clv=false;
        try {
            JSONObject respuestaJSON = new JSONObject  (validar(2, correo.getText().toString()));//Se guarda el resultado obtenido del JSON
            String resultJSON = respuestaJSON.getString("estado");//guarda el registro del arreglo estado
            if (resultJSON.equals("1")) {      // ese correo ya existe
                Toast.makeText(getApplicationContext(), "Ese correo ya esta registrado", Toast.LENGTH_SHORT).show();
            }
            else if (resultJSON.equals("2")){//el correo no esta en la BD, lo cual es correcto para permitir el registro
                clv=true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clv;
    }
    public boolean validarPass(){
        boolean clv=false;
        if(pass.getText().toString().equals(pass2.getText().toString())){
            clv=true;
        }else{
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        return  clv;
    }

    @Override
    public void onClick(View view) {
        if (validarClave() && validarCorreo() && validarPass()){
            registrarse_OnClick();
        }
    }
}

package com.example.miguelangel.savenergy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    //Declaracion de variables
    EditText email, pass;
    Button iniciar;
    //Fin de la declaración de variables

                                        //Metodo para lanzar un nuevo Activity
    public void iniciar_sesionOnclick() {
        Intent intent = new Intent(Inicio_Sesion.this, Principal.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio__sesion);

        //Se asigna la variable que corresponde a cada componente declarado en el archivo XML

        email = (EditText) findViewById(R.id.correo);
        pass = (EditText) findViewById(R.id.password);
        iniciar = (Button) findViewById(R.id.iniciar_sesion);
        iniciar.setOnClickListener(this);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
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
                ses = true;
                Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG).show();
            }
            else if (resultJSON.equals("2")){//el ususario no existe
                Toast.makeText(getApplicationContext(),"Datos incorrectos",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ses;
    }
    @Override
    public void onClick(View view) {
        if (sesion()){
            iniciar_sesionOnclick();
        }
    }
}
package com.example.miguelangel.savenergy;

import android.annotation.SuppressLint;
import android.content.Intent;
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
        Intent intent = new Intent(Inicio_Sesion.this, Tarifas.class);
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
    }

                            //Metodo con 2 parametros para conectar con servidor y devuelve datos

    public String getDatos(String contra, String email){
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder result=null;

        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/Login.php?contra="+contra+"&email="+email);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            respuesta=connection.getResponseCode();
            result = new StringBuilder();
            if(respuesta==200){
                InputStream in=new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while((linea=reader.readLine())!=null){
                    result.append(linea);
                }
            }
        } catch (Exception e) {}
        return result.toString();
    }
    @Override
    public void onClick(View view) {
        Thread tr=new Thread(){
            @Override
            public void run() {
                final String resultado=getDatos(pass.getText().toString(),email.getText().toString());//se guarda el resultado de
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject respuestaJSON = null;   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        try {
                            respuestaJSON = new JSONObject(resultado.toString());
                            String resultJSON = respuestaJSON.getString("estado");
                            if (resultJSON.equals("1")){      // hay un alumno que mostrar
                                iniciar_sesionOnclick();
                            }else if (resultJSON.equals("2")){
                                Toast.makeText(getApplicationContext(),"User incorrect",Toast.LENGTH_LONG).show();
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        tr.start();
    }
}
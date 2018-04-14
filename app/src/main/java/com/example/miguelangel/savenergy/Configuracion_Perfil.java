package com.example.miguelangel.savenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Configuracion_Perfil extends AppCompatActivity {

                            // Inicio de la declaración de variables
    EditText nombre,correo,tarifa;
                            //Fin de la declaración de variables

            //Método para setear campos de UI con la carga de datos
    public String usuarioCache(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        String user = preferences.getString("correo","Null");
        return user;
    }

    public String consultar(){//Metodo que devuelve dos objetos - estado y consulta, convertidos en JSON
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url= new URL("https://savenergy.000webhostapp.com/savenergy/datos.php?correo=" + usuarioCache() );
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }

    public void setCampos(){
        String email_set,name_set,tarifa_set;
        try {
            String resultJSON="";
            JSONObject respuestaJSON = new JSONObject  (consultar());//Se guarda el resultado obtenido del JSON
            resultJSON = respuestaJSON.getString("estado");//guarda el registro del arreglo estado
            if (resultJSON.equals("1")) {      // el correo y contraseña son correctas
                email_set = respuestaJSON.getJSONObject("consulta").getString("email");
                name_set = respuestaJSON.getJSONObject("consulta").getString("nombre");
                tarifa_set = respuestaJSON.getJSONObject("consulta").getString("Tarifa");

                nombre.setText(name_set);
                correo.setText(email_set);
                tarifa.setText(tarifa_set);
            }
            else if (resultJSON.equals("2")){//el ususario no existe
                Toast.makeText(getApplicationContext(),"Error al cargar datos",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        correo = (EditText) findViewById(R.id.et_correo);               //Asignación de variable de tipo EditText
        tarifa = (EditText) findViewById(R.id.et_tarifa);               //Asignación de variable de tipo EditText

        //Metodos que carga la Interfaz

        //Cargar datos en la interfaz
        setCampos();
    }

    //Método si el usuario presiono ir hacia atrás
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Configuracion_Perfil.this,Menu_Configuraciones.class);
        startActivity(intent);
        finish();
    }
}

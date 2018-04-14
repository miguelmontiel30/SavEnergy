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
    String user;
                            //Fin de la declaración de variables

            //Método para cargar consulta en BD
    public String llamarDatos(){
                        //Carga de preferencias del usuario
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        user = preferences.getString("usuario","No hay nada guardado");
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url = new URL("https://savenergy.000webhostapp.com/savenergy/datos.php?correo=" + user);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }

            //Método para setear campos de UI con la carga de datos
    public void setCampos(){
        String correo_user_set,nombre_user_set,tarifa_user_set;
        try {
            String resultJSON="";
            JSONObject respuestaJSON = new JSONObject  (llamarDatos());   //Se guarda el resultado obtenido del JSON
            resultJSON = respuestaJSON.getString("estado"); //Consulta al arreglo "Estado"
            if (resultJSON.equals("1")) {      // Existen registros en BD
                correo_user_set = respuestaJSON.getJSONObject("consulta").getString("email");
                nombre_user_set = respuestaJSON.getJSONObject("consulta").getString("nombre");
                tarifa_user_set = respuestaJSON.getJSONObject("id_tarifa").getString("id_tarifa");

                nombre.setText(nombre_user_set);
                correo.setText(correo_user_set);
                tarifa.setText(tarifa_user_set);
            }
            else if (resultJSON.equals("2")){   //No se encuentran registros
                Toast.makeText(getApplicationContext(),"Error al cargar datos del usuario",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

package com.example.miguelangel.savenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Facturacion extends AppCompatActivity {

    Spinner spFecha;
    String id_user;
    ArrayList<String> lista_fechas = new ArrayList<String>();

    //Método si el usuario presiono ir hacia atrás
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Facturacion.this,Principal.class);
        startActivity(intent);
        finish();
    }

    private String conectar() {
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        id_user = preferences.getString("id_usuario","Null");
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url = new URL("https://savenergy.000webhostapp.com/savenergy/select_recibos.php?id_user=" + id_user);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }

    public void llenarSpCuota(){//metodo para llenar el arreglo lista de cuotas
        String[] t=null;
        try {
            String resultJSON="";
            JSONObject respuestaJSON = new JSONObject (conectar());//Se guarda el resultado obtenido del JSON
            JSONArray jsonArray = null;
                jsonArray = respuestaJSON.getJSONArray("consulta");
                for (int i=0;1<respuestaJSON.length();i++){
                    lista_fechas.add(jsonArray.getJSONObject(i).getString("fecha_inicio"));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturacion);

        spFecha = (Spinner) findViewById(R.id.sp_fecha);

        llenarSpCuota();

    }
}

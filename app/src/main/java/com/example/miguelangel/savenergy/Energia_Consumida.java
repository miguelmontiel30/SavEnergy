package com.example.miguelangel.savenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Energia_Consumida extends AppCompatActivity {

                                //Inicio de la declaración de variables
    Spinner spPeriodos;
    String id_periodo,periodo;

                // Se declaran las listas en donde se almacenan los datos que se enviaran a los Spinner

    ArrayList<String> lista_fechas = new ArrayList<String>();
    ArrayList<String> lista_id_fechas = new ArrayList<String>();

                                //Fin de la declaración de variables

                                //Método para conectar con el servidor y que devuelva datos del PHP (SELECT_RECIBOS)
    public void getPeriodos(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        String id = preferences.getString("id_usuario","Null");
        try{
            URL url = new URL("https://savenergy.000webhostapp.com/savenergy/select_recibos.php?id_user=" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            setPeriodos(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

                                //Metodo para convertir y asignar datos que muestra el PHP(SELECT_RECIBOS)
    public void setPeriodos(String webServiceResult){
        JSONArray jsonArray = null;
        try{
            jsonArray = new JSONArray(webServiceResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                lista_id_fechas.add(jsonArray.getJSONObject(i).getString("id_recibo"));
                lista_fechas.add("De: " + jsonArray.getJSONObject(i).getString("fecha_inicio") +
                        "A: " + jsonArray.getJSONObject(i).getString("fecha_corte"));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

                                //Métodos para el usuario (Presionar "Back"}

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Energia_Consumida.this,Principal.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

                                //Fin de los metodos para el usuario

                                //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energia__consumida);

        Toast.makeText(getApplicationContext(),"Entro a este metodo 2",Toast.LENGTH_SHORT).show();

                                //Se Asigna permiso para mantener abierta la conexion
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

                                //Se asignan variables a los componentes XML

        //spPeriodos = (Spinner) findViewById(R.id.sp_fecha);

                                //Fin de la asignación de variables


                                //Métodos implementados para llenar Spinner

                    //Método para llenar las listas con datos de la BD
        //getPeriodos();

                    //Se llena un ArrayAdapter con el contenido de las listas
        //ArrayAdapter<String> periodos = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,lista_fechas);

                    //Se llena el Spinner con el contenido del ArrayAdapter
        //spPeriodos.setAdapter(periodos);

                    //Se le asigna un evento al Spinner
        //spPeriodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//funcion del spinner cuota
           /*@Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_periodo = lista_id_fechas.get(i);
                periodo = lista_fechas.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});*/
    }
}

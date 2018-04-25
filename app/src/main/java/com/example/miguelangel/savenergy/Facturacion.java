package com.example.miguelangel.savenergy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    TextView periodo_selec;
    EditText fecha_inicio,fecha_fin,dias;
    String id_user,id_periodo,fecha_ini,fecha_cor,periodo,dias_trans;

    ArrayList<String> lista_fechas = new ArrayList<String>();
    ArrayList<String> lista_id_fechas = new ArrayList<String>();
    ArrayList<String> lista_fecha_ini = new ArrayList<String>();
    ArrayList<String> lista_fecha_cor = new ArrayList<String>();

    //Método si el usuario presiono ir hacia atrás
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Facturacion.this,Principal.class);
        startActivity(intent);
        finish();
    }
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
                lista_fecha_ini.add(jsonArray.getJSONObject(i).getString("fecha_inicio"));
                lista_fecha_cor.add(jsonArray.getJSONObject(i).getString("fecha_corte"));
                lista_fechas.add("De:  " + jsonArray.getJSONObject(i).getString("fecha_inicio") +
                        "  A:  " + jsonArray.getJSONObject(i).getString("fecha_corte"));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    //Método para conectar con el servidor y que devuelva datos del PHP (SELECT_RECIBOS)
    public void getDias(){
        try{
            URL url = new URL("https://savenergy.000webhostapp.com/savenergy/select_dias_trans.php?fecha_ini=" + fecha_ini +
                    "&fecha_cor=" + fecha_cor);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            setDias(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Metodo para convertir y asignar datos que muestra el PHP(SELECT_RECIBOS)
    public void setDias(String webServiceResult){
        JSONArray jsonArray = null;
        String fecha;
        try{
            jsonArray = new JSONArray(webServiceResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                fecha = jsonObject.getString("dias");
                dias_trans = fecha;
                dias.setText(dias_trans);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturacion);

        spFecha = (Spinner) findViewById(R.id.sp_fecha);
        periodo_selec = (TextView) findViewById(R.id.tv_periodo_selec);
        fecha_inicio = (EditText) findViewById(R.id.txt_inicio);
        fecha_fin = (EditText) findViewById(R.id.txt_fin);
        dias = (EditText) findViewById(R.id.txt_dias);


        //Métodos implementados para llenar Spinner

        //Método para llenar las listas con datos de la BD
        getPeriodos();

        //Se llena un ArrayAdapter con el contenido de las listas
        ArrayAdapter<String> periodos = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,lista_fechas);

        //Se llena el Spinner con el contenido del ArrayAdapter
        spFecha.setAdapter(periodos);

        //Se le asigna un evento al Spinner
        spFecha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//funcion del spinner cuota
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_periodo = lista_id_fechas.get(i);
                fecha_ini = lista_fecha_ini.get(i);
                fecha_cor = lista_fecha_cor.get(i);
                periodo = lista_fechas.get(i);
                periodo_selec.setText(periodo);
                fecha_inicio.setText((fecha_ini));
                fecha_fin.setText(fecha_cor);
                getDias();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});

    }
}

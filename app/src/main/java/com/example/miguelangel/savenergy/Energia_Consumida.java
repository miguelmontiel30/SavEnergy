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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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
    private LineChart grafica;
    Spinner spPeriodos;
    String id_periodo,periodo,fecha_ini,fecha_cor;

                // Se declaran las listas en donde se almacenan los datos que se enviaran a los Spinner

    ArrayList<String> lista_fechas = new ArrayList<String>();
    ArrayList<String> lista_id_fechas = new ArrayList<String>();
    ArrayList<String> lista_fecha_ini = new ArrayList<String>();
    ArrayList<String> lista_fecha_cor = new ArrayList<String>();
    ArrayList<Entry> yValues_1 = new ArrayList<>();
    ArrayList<Entry> yValues_2 = new ArrayList<>();

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
                lista_fecha_ini.add(jsonArray.getJSONObject(i).getString("fecha_inicio"));
                lista_fecha_cor.add(jsonArray.getJSONObject(i).getString("fecha_corte"));
                lista_fechas.add("De:  " + jsonArray.getJSONObject(i).getString("fecha_inicio") +
                        "  A:  " + jsonArray.getJSONObject(i).getString("fecha_corte"));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

                                //Método para conectar con el servidor y que devuelva datos del PHP (SELECT_CONSUMO_ELECTRICA)
    public void getConsumoElectrica(String fecha_ini, String fecha_cor){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        String id = preferences.getString("id_usuario","Null");
        try{
            URL url = new URL("https://savenergy.000webhostapp.com/savenergy/select_consumo_periodo_electrica.php?id_user="
                    + id + "&fecha_ini=" + fecha_ini + "&fecha_cor=" + fecha_cor );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            setConsumoElectrica(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

                                //Metodo para convertir y asignar datos que muestra el PHP(SELECT_CONSUMO_ELECTRICA)
    public void setConsumoElectrica(String webServiceResult){
        JSONArray jsonArray = null;
        String consumo;
        try{
            jsonArray = new JSONArray(webServiceResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                consumo = jsonObject.getString("volts");
                yValues_1.add(new Entry(i,Float.parseFloat(consumo)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

                                //Método para conectar con el servidor y que devuelva datos del PHP (SELECT_CONSUMO_SUSTENTABLE)
    public void getConsumoSustentable(String fecha_ini, String fecha_cor){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        String id = preferences.getString("id_usuario","Null");
        try{
            URL url = new URL("https://savenergy.000webhostapp.com/savenergy/select_consumo_periodo_sustentable.php?id_user="
                    + id + "&fecha_ini=" + fecha_ini + "&fecha_cor=" + fecha_cor );
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            setConsumoSustentable(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

                                //Metodo para convertir y asignar datos que muestra el PHP(SELECT_CONSUMO_SUSTENTABLE)
    public void setConsumoSustentable(String webServiceResult){
        JSONArray jsonArray = null;
        String consumo;
        try{
            jsonArray = new JSONArray(webServiceResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                consumo = jsonObject.getString("volts");
                yValues_2.add(new Entry(i,Float.parseFloat(consumo)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
                                //Método para devolver datos que se asignarán a la Gráfica

                    //Devuelve datos de Energía Electrica
    private LineDataSet setDataElectrica(){
        LineDataSet set_1;

        set_1 = new LineDataSet(yValues_1, "Energía Electrica");
        set_1.setColor(getResources().getColor(R.color.electrica));
        set_1.setValueTextSize(8f);
        set_1.setCircleColor(getResources().getColor(R.color.electrica));
        set_1.setCircleRadius(5f);


        return set_1;
    }

                    //Devuelve datos de Energía Sustentable
    private LineDataSet setDataSustentable(){
        LineDataSet set_2;

        set_2 = new LineDataSet(yValues_2, "Energía Sustentable");
        set_2.setColor(getResources().getColor(R.color.sustentable));
        set_2.setValueTextSize(8f);
        set_2.setCircleColor(getResources().getColor(R.color.sustentable));
        set_2.setCircleRadius(5f);

        return set_2;
    }

    private void setConsumo(){
        //yValues_1 = null;
        //yValues_2 = null;
        grafica.setData(null);
        getConsumoElectrica(fecha_ini,fecha_cor);
        getConsumoSustentable(fecha_ini,fecha_cor);
        LineData data = new LineData(setDataElectrica(),setDataSustentable());
        grafica.setData(data);
                                    //Se le agregan más propiedades a la gráfica
        grafica.animateX(3000);                             //Método que indica el tiempo de animación
        grafica.getAxisRight().setEnabled(false);                       //Método que inhabilita el eje derecho de la gráfica
        grafica.getAxisLeft().setAxisMaximum(30);                       //Método que le da un valor máximo al eje izquierdo
        grafica.getAxisLeft().setAxisMinimum(0);                        //Método que le asigna un valor minimo al eje izquierdo
    }

                                //Fin de los métdodos para setear gráfica

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

                                //Se Asigna permiso para mantener abierta la conexion
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

                                //Se asignan variables a los componentes XML

        spPeriodos = (Spinner) findViewById(R.id.sp_fecha);

        grafica = (LineChart) findViewById(R.id.grafica);

                                //Fin de la asignación de variables


                                //Métodos implementados para llenar Spinner

                    //Método para llenar las listas con datos de la BD
        getPeriodos();

                    //Se llena un ArrayAdapter con el contenido de las listas
        ArrayAdapter<String> periodos = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,lista_fechas);

                    //Se llena el Spinner con el contenido del ArrayAdapter
        spPeriodos.setAdapter(periodos);

                    //Se le asigna un evento al Spinner
        spPeriodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//funcion del spinner cuota
           @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_periodo = lista_id_fechas.get(i);
                fecha_ini = lista_fecha_ini.get(i);
                fecha_cor = lista_fecha_cor.get(i);
                periodo = lista_fechas.get(i);
                setConsumo();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});
    }
}

package com.example.miguelangel.savenergy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tarifas extends AppCompatActivity {

    //Declaración de variables
    private ListView lv_tarifas;
    private ArrayAdapter adapter;
    private String getAllTarifasURL = "https://savenergy.000webhostapp.com/savenergy/get_Tarifas.php";
    //Fin de la declaración de variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarifas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

                            //Se Asigna permiso para mantener abierta la conexion
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());


                                        //Inicialización de variables declaradas
        lv_tarifas = (ListView)findViewById(R.id.lv_tarifas);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_tarifas.setAdapter(adapter);


        CargaDatos(getAllTarifasURL);
    }

                        //Metodo para cargar los datos desde el servidor

    private void CargaDatos(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

                    //Metodo que convierte los datos y los separa
    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_tarifa;
        String tarifa;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_tarifa = jsonObject.getString("id_tarifa");
                tarifa = jsonObject.getString("tarifa");
                adapter.add(id_tarifa + ':' + tarifa);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}

package com.example.miguelangel.savenergy;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Configuracion_f_corte extends AppCompatActivity implements View.OnClickListener{
    private Button btn_fin;
    Spinner spTarifas;
    String[] c = {"1","1A","1B","1C","1D","1E","1F"};
    String clave, correo, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_f_corte);

        spTarifas = (Spinner) findViewById(R.id.sp_tarifa);
        btn_fin = (Button) findViewById(R.id.b_finalizar);
        btn_fin.setOnClickListener(this);

        clave = (String) getIntent().getExtras().getSerializable("clave");
        correo = (String) getIntent().getExtras().getSerializable("correo");
        pass = (String) getIntent().getExtras().getSerializable("pass");

        //Se Asigna permiso para mantener abierta la conexion
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        ArrayAdapter<String> arra = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,c);
        spTarifas.setAdapter(arra);
    }

    private List<String> llenarSp() {
        URL url = null;
        List<String> b = null;
        try {
            url = new URL("https://savenergy.000webhostapp.com/savenergy/get_Tarifas.php");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        String webServiceResult=null;
        while ((line = bufferedReader.readLine()) != null){
            webServiceResult += line;
        }
        JSONArray jsonArray = new JSONArray(webServiceResult);
        for (int i=0;1<jsonArray.length();i++){
            b.add(jsonArray.getJSONObject(i).getString("tarifa"));
        }
        bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "Clv: "+clave+"Crr: "+correo+"Pss: "+pass, Toast.LENGTH_SHORT).show();
    }
}

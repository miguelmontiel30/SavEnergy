package com.example.miguelangel.savenergy;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Configuracion_f_corte extends AppCompatActivity implements View.OnClickListener{
    //Declaracion de variables
    Button btn_fin;
    TextView fecha;
    Calendar date;
    int day, month, year;
    Spinner spTarifas, spCuotas;
            // Se declaran las listas en donde se almacenan los datos que se enviaran a los Spinner
    ArrayList<String> lista_tarifa = new ArrayList<String>();
    ArrayList<String> lista_cuota = new ArrayList<String>();
    ArrayList<String> lista_id_tarifa = new ArrayList<String>();
    ArrayList<String> lista_id_cuota = new ArrayList<String>();
    String clave, correo, pass, id_tarifa, id_cuota;

    private String conectar(int r) {
        URL url = null;
        String lru="";
        if (r==1){
            lru="https://savenergy.000webhostapp.com/savenergy/get_Tarifas.php";
        }
        if(r==2){
            lru="https://savenergy.000webhostapp.com/savenergy/get_Cuotas.php";
        }
        String line = "";
        String webServiceResult="";
        try {
            url = new URL(lru);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }

    public void llenarSpTarifa(){//metodo para llenar el arreglo lista de tarifas
        String[] t=null;
        try {
            String resultJSON="";
            JSONObject respuestaJSON = new JSONObject (conectar(1));//Se guarda el resultado obtenido del JSON
            resultJSON = respuestaJSON.getString("estado");//guarda el registro del arreglo estado
            JSONArray jsonArray = null;
            if (resultJSON.equals("1")) {      // el correo y contraseña son correctas
                jsonArray = respuestaJSON.getJSONArray("consulta");
                for (int i=0;1<respuestaJSON.length();i++){
                    lista_tarifa.add(jsonArray.getJSONObject(i).getString("tarifa"));
                    lista_id_tarifa.add(jsonArray.getJSONObject(i).getString("id_tarifa"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void llenarSpCuota(){//metodo para llenar el arreglo lista de cuotas
        String[] t=null;
        try {
            String resultJSON="";
            JSONObject respuestaJSON = new JSONObject (conectar(2));//Se guarda el resultado obtenido del JSON
            resultJSON = respuestaJSON.getString("estado");//guarda el registro del arreglo estado
            JSONArray jsonArray = null;
            if (resultJSON.equals("1")) {      // el correo y contraseña son correctas
                jsonArray = respuestaJSON.getJSONArray("consulta");
                for (int i=0;1<respuestaJSON.length();i++){
                    lista_cuota.add(jsonArray.getJSONObject(i).getString("cuota"));
                    lista_id_cuota.add(jsonArray.getJSONObject(i).getString("id_cuotas"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String registrar(String pass, String correo, String fecha, String id_t, String id_c) {
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url = new URL("https://savenergy.000webhostapp.com/savenergy/insert_user.php?" +
                    "pass="+pass+"&email="+correo+"&date="+fecha+"&tar="+id_t+"&cuo="+id_c);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }

    public void cargarPrincipal() {
        Intent intent = new Intent(Configuracion_f_corte.this, Principal.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View view) {
        registrar(pass,correo,fecha+"",id_tarifa,id_cuota);
            cargarPrincipal();

    }

                        //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_f_corte);

        spTarifas = (Spinner) findViewById(R.id.sp_tarifa);
        spCuotas = (Spinner) findViewById(R.id.sp_cuota);
        btn_fin = (Button) findViewById(R.id.b_finalizar);
        btn_fin.setOnClickListener(this);
        fecha = (TextView) findViewById(R.id.txt_fecha);
        date = Calendar.getInstance();

        clave = (String) getIntent().getExtras().getSerializable("clave");
        correo = (String) getIntent().getExtras().getSerializable("correo");
        pass = (String) getIntent().getExtras().getSerializable("pass");

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        month = month+1;
        fecha.setText(day+"/"+month+"/"+year);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog date = new DatePickerDialog(Configuracion_f_corte.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1+1;
                        fecha.setText(i+"-"+i1+"-"+i2);
                    }
                }, year, month, day);
                date.show();
            }
        });

        //Se Asigna permiso para mantener abierta la conexion
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        //Se define el tipo de contenido y la lista de arreglos... Para tarifa y cuota
        llenarSpTarifa();//metodo para llenar el arreglo lista de tarifas
        ArrayAdapter<String> tar = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,lista_tarifa);
        spTarifas.setAdapter(tar);//Se asigna el contenido al spiner de tarifas
        spTarifas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//funcion del spinner tarifa
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_tarifa = lista_id_tarifa.get(i); // Se asigna el valor seleccionado en el Spinner, a la variable tarifa
            }
            @Override public void onNothingSelected(AdapterView<?> adapterView) { }});
        llenarSpCuota();//metodo para llenar el arreglo lista de cuotas
        ArrayAdapter<String> cuo = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,lista_cuota);
        spCuotas.setAdapter(cuo);//Se asigna el contenido al spiner de cuotas
        spCuotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//funcion del spinner cuota
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_cuota = lista_id_cuota.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});
    }
}

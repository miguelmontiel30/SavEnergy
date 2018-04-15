package com.example.miguelangel.savenergy;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.ToIntFunction;

public class Cambiar_periodo_corte extends AppCompatActivity implements View.OnClickListener{
    //Inicio de la declaracion de variables
    Button btn_guardar,btn_edit_tar,btn_edit_cuo;
    ImageButton ib_calendar;
    EditText fecha;
    Calendar date;
    int day, month, year;
    Spinner spTarifas, spCuotas;
    String correo_cache,password_cache,nombre_cache,fecha_cache,tarifa_cache,cuota_cache;
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
                    spinner_tar();
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
                    spinner_cuo();
                }
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
    //Método Si presiona en boton "Atrás"
    @Override
    public  void onBackPressed(){
        Intent intent = new Intent(Cambiar_periodo_corte.this,Menu_Configuraciones.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_periodo_corte);

        //Asignación de variable a componente en XML

        spTarifas = (Spinner) findViewById(R.id.sp_tarifa_corte);
        spCuotas = (Spinner) findViewById(R.id.sp_cuota_corte);

        btn_guardar = (Button) findViewById(R.id.btn_guardar_corte);
        btn_guardar.setOnClickListener(this);
        btn_edit_tar = (Button) findViewById(R.id.btn_edit_tarifa);
        btn_edit_tar.setOnClickListener(this);
        btn_edit_cuo = (Button) findViewById(R.id.btn_edit_cuota);
        btn_edit_cuo.setOnClickListener(this);

        ib_calendar = (ImageButton) findViewById(R.id.im_calendar_corte);
        ib_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog date = new DatePickerDialog(Cambiar_periodo_corte.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1+1;
                        fecha.setText(i+"-"+i1+"-"+i2);
                    }
                }, year, month, day);
                date.show();
            }
        });

        fecha = (EditText) findViewById(R.id.txt_fecha_corte);
        date = Calendar.getInstance();

        //Fin de la obtención de variables

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        month = month+1;
        getdatos();
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog date = new DatePickerDialog(Cambiar_periodo_corte.this, new DatePickerDialog.OnDateSetListener() {
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
    }
    public void spinner_tar(){//Se define el tipo de contenido y la lista de arreglos... Para el spinner de tarifa
        ArrayAdapter<String> tar = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,lista_tarifa);
        spTarifas.setAdapter(tar);//Se asigna el contenido al spiner de tarifas
        spTarifas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//funcion del spinner tarifa
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_tarifa = lista_id_tarifa.get(i); // Se asigna el valor seleccionado en el Spinner, a la variable tarifa
            }
            @Override public void onNothingSelected(AdapterView<?> adapterView) { }});
    }
    public void spinner_cuo(){  //Se define el tipo de contenido y la lista de arreglos... Para el spinner de cuotas
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

    public void getdatos(){
        String tar,cuo,id_tar,id_cuo;
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        fecha_cache = preferences.getString("fecha","Null");
        tar = preferences.getString("tarifa","Null");
        cuo = preferences.getString("cuota","Null");
        id_tar = preferences.getString("id_tarifa","Null");
        id_cuo = preferences.getString("id_cuota","Null");
        fecha.setText(fecha_cache.toString());
        lista_id_tarifa.add(id_tar);
        lista_id_cuota.add(id_cuo);
        lista_tarifa.add(tar);
        lista_cuota.add(cuo);
        spinner_cuo();
        spinner_tar();
    }

    @Override
    public void onClick(View view) {
        if (view==btn_edit_tar){
            lista_tarifa.clear();
            lista_id_tarifa.clear();
            llenarSpTarifa();//metodo para llenar el arreglo lista de tarifas
            ArrayAdapter<String> tar = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,lista_tarifa);
            spTarifas.setAdapter(tar);//Se asigna el contenido al spiner de tarifas
        }else if(view==btn_edit_cuo){
            lista_cuota.clear();
            lista_id_cuota.clear();
            llenarSpCuota();//metodo para llenar el arreglo lista de cuotas
            ArrayAdapter<String> cuo = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,lista_cuota);
            spCuotas.setAdapter(cuo);//Se asigna el contenido al spiner de cuotas
        }

    }
}

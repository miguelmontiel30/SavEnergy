package com.example.miguelangel.savenergy;

                        //Librerias importadas para el funcionamiento
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

                                    //Inicio de Declaración de variables
    private LineChart grafica;
    private long backPressedTime;
    private View header;
    Inicio_Sesion sesion = new Inicio_Sesion();
    TextView nombre,correo,user,fecha;
    String fecha_actual="";
    ArrayList<Entry> yValues_1 = new ArrayList<>();
    ArrayList<Entry> yValues_2 = new ArrayList<>();

                                    //Fin de la declaración de variables

                                    //Método que setea campos para funcionamiento dinamico
    public void setCampos(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        String correo_cache = preferences.getString("correo","Null");
        String nombre_cache = preferences.getString("nombre","Null");
        nombre.setText(nombre_cache);
        correo.setText(correo_cache);
        user.setText(nombre_cache);
        fecha.setText(fecha_actual);
        }

                                    //Metodo para conectar con el servidor y que devuelva datos del PHP (FECHA)
   public void getFecha(){
       try{
           URL url = new URL("https://savenergy.000webhostapp.com/savenergy/fecha_actual.php");
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

           String line = "";
           String webServiceResult="";
           while ((line = bufferedReader.readLine()) != null){
               webServiceResult += line;
           }
           bufferedReader.close();
           setFecha(webServiceResult);
       }catch(Exception e){
           e.printStackTrace();
       }
   }

                                    //Método para convertir datos que arroja el PHP (FECHA)
    public void setFecha(String webServiceResult){
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
                fecha = jsonObject.getString("fecha");
                fecha_actual = fecha;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

                                    //Método para conectar con el servidor y que devuelva datos del PHP (SELECT_CONSUMO_ELECTRICA)
    public void getConsumoElectrica(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        String id = preferences.getString("id_usuario","Null");
        try{
            URL url = new URL("https://savenergy.000webhostapp.com/savenergy/select_consumo_electrica.php?id_user=" + id + "&date= " + fecha_actual);
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
        String id_user;
        String fecha;
        String consumo;
        String id_tipo;
        try{
            jsonArray = new JSONArray(webServiceResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_user = jsonObject.getString("id_usuario");
                fecha = jsonObject.getString("fecha");
                consumo = jsonObject.getString("volts");
                yValues_1.add(new Entry(i,Float.parseFloat(consumo)));
                id_tipo = jsonObject.getString("id_tipo_consumo");
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

                                    //Método para conectar con el servidor y que devuelva datos del PHP (SELECT_CONSUMO_SUSTENTABLE)
    public void getConsumoSustentable(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        String id = preferences.getString("id_usuario","Null");
        try{
            URL url = new URL("https://savenergy.000webhostapp.com/savenergy/select_consumo_sustentable.php?id_user=" + id + "&date= " + fecha_actual);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
        String id_user;
        String fecha;
        String consumo;
        String id_tipo;
        try{
            jsonArray = new JSONArray(webServiceResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_user = jsonObject.getString("id_usuario");
                fecha = jsonObject.getString("fecha");
                consumo = jsonObject.getString("volts");
                yValues_2.add(new Entry(i,Float.parseFloat(consumo)));
                id_tipo = jsonObject.getString("id_tipo_consumo");
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

                                    //Método para borrar sesión guardada del usuario
    public void eliminarSesion(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
        cargarLogin();
    }

                                    //Método para pasar al Login cuando cierras sesion
    public void cargarLogin(){
        Intent intent = new Intent(Principal.this, Inicio_Sesion.class);
        startActivity(intent);
        finish();
    }

                                    //Metodo para insertar los datos en la Gráfica

    private void setData(){
        LineDataSet set_1,set_2;

        set_1 = new LineDataSet(yValues_1, "Energía Electrica");
        set_1.setColor(getResources().getColor(R.color.electrica));
        set_1.setValueTextSize(8f);
        set_1.setCircleColor(getResources().getColor(R.color.electrica));

        set_2 = new LineDataSet(yValues_2, "Energía Sustentable");
        set_2.setColor(getResources().getColor(R.color.sustentable));
        set_2.setValueTextSize(8f);
        set_2.setCircleColor(getResources().getColor(R.color.sustentable));

        LineData data = new LineData(set_1,set_2);

        grafica.setData(data);
    }

                                    //Método para pasar de Intent a otro con el menu

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            Intent intent = new Intent(Principal.this,Principal.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_configuracion_perfil) {
            Intent intent = new Intent(Principal.this,Menu_Configuraciones.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_consumida) {
            Intent intent = new Intent(Principal.this,Energia_Consumida.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_generada) {
            Intent intent = new Intent(Principal.this,Energia_Generada.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_facturacion) {
            Intent intent = new Intent(Principal.this,Facturacion.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_salir) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
            builder.setMessage("Deseas cerrar sesión?");
            builder.setCancelable(true);
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    eliminarSesion();
                }
            });
            AlertDialog alertDialog =  builder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

                                    //Metodo OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Se Asigna permiso para mantener abierta la conexion
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

                //Creas un objeto de tipo NavigationView que es para encontrar los omponentes del HeaderNAV
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);  //Asignación de variable para obtención de los compnentes del header

        correo = (TextView) header.findViewById(R.id.correo_user);     //Asignación de variables de tipo TextView
        nombre = (TextView) header.findViewById(R.id.nombre_user);     //Asignación de variables de tipo TextView
        user = (TextView) findViewById(R.id.txt_user);                 //Asignación de variables de tipo TextView
        fecha = (TextView) findViewById(R.id.txt_fecha);               //Asignación de variables de tipo TextView
        //imagen_usuario = (CircleImageView) header.findViewById(R.id.imageViewUsuario);

        getFecha();

        //Método que carga las preferencias del usuario
        setCampos();

        //Metodo para cargar el consumo de la fecha actual
        getConsumoElectrica();
        getConsumoSustentable();

                                                    //Métodos para llenar Gráfica con Datos

        grafica = (LineChart) findViewById(R.id.grafica);             //Asignación de variables de tipo LineChart
        setData();                               //Método que llama la insersión de datos en la gráfica
        grafica.animateX(3000);                          //Método que indica el tiempo de animación a la
        grafica.getAxisRight().setEnabled(false);
        grafica.getAxisLeft().setAxisMaximum(30);
        grafica.getAxisLeft().setAxisMinimum(0);
    }

                                    //Metodo Si se ha presionado Back
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(getBaseContext(), "Pulsa de nuevo para salir", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}
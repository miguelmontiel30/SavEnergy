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

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

                                    //Inicio de Declaración de variables
    private LineChart grafica;
    private long backPressedTime;
    String user_cache,nombre_user;
    private View header;
    Inicio_Sesion sesion = new Inicio_Sesion();
    TextView nombre,correo,user,fecha;


                //Método que setea campos para funcionamiento dinamico
    public void setCampos(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        String correo_cache = preferences.getString("correo","Null");
        String nombre_cache = preferences.getString("nombre","Null");
        nombre.setText(nombre_cache);
        correo.setText(correo_cache);
        user.setText(nombre_cache);
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
                                //Método que consulta fecha en la BD
    public String consulta_fecha(){
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url = new URL("https://savenergy.000webhostapp.com/savenergy/fecha_actual.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
        }
                                 //Método para insertar las fecha de corte

    private void setFecha() {
        try {
            String fecha_actual="";
            JSONArray jsonArray = null;
            Toast.makeText(getApplicationContext(),"Entro al metodo",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),consulta_fecha(),Toast.LENGTH_SHORT).show();
            jsonArray = new JSONArray(consulta_fecha());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            Toast.makeText(getApplicationContext(),"Continua aqui",Toast.LENGTH_SHORT).show();
            fecha_actual = jsonObject.getString("fecha");
            fecha.setText(fecha_actual);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

                                //Metodo para insertar los datos en la Gráfica

    private void setData(int count, int range){
        ArrayList<Entry> yValues_1 = new ArrayList<>();
        for (int i = 0; i < count; i++){
            float value = (float) (Math.random()*range)+250;
            yValues_1.add(new Entry(i,value));
        }

        ArrayList<Entry> yValues_2 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * range) + 150;
            yValues_2.add(new Entry(i, value));
        }

        LineDataSet set_1, set_2;

        set_1 = new LineDataSet(yValues_1, "Energía Sustentable");
        set_1.setColor(getResources().getColor(R.color.sustentable));
        set_1.setValueTextSize(5f);

        set_2 = new LineDataSet(yValues_2, "Energía Electrica");
        set_2.setColor(getResources().getColor(R.color.electrica));
        set_2.setValueTextSize(5f);
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

                //Creas un objeto de tipo NavigationView que es para encontrar los omponentes del HeaderNAV
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        correo = (TextView) header.findViewById(R.id.correo_user);                                  //Asignación de variables de tipo TextView
        nombre = (TextView) header.findViewById(R.id.nombre_user);                                 //Asignación de variables de tipo TextView
        user = (TextView) findViewById(R.id.txt_user);

        fecha = (TextView) findViewById(R.id.txt_fecha);
        //imagen_usuario = (CircleImageView) header.findViewById(R.id.imageViewUsuario);

        //Método que carga la fecha actual
        setFecha();

        //Método que carga las preferencias del usuario
        setCampos();

                                                    //Métodos para llenar Gráfica con Datos
        grafica = (LineChart) findViewById(R.id.grafica);           //Asignación de variables de tipo LineChart
        setData(100, 60);                               //Método que llama la insersión de datos en la gráfica
        grafica.animateX(3000);                         //Método que indica el tiempo de animación a la
        grafica.getAxisRight().setEnabled(false);

        //Se Asigna permiso para mantener abierta la conexion
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
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
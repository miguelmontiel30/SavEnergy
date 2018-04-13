package com.example.miguelangel.savenergy;

                        //Librerias importadas para el funcionamiento
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
    String user_cache,nombre_user;
    private View header;
    TextView nombre,correo;

                                    //Fin de la Declaración de variables

                //Metodo que devuelve dos objetos - estado y consulta, convertidos en JSON
    public String llamarDatos(String email){
        URL url = null;
        String line = "";
        String webServiceResult="";
        try {
            url = new URL("https://savenergy.000webhostapp.com/savenergy/datos.php?correo=" + email);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();//Se abre la conexion
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {//mientras exista un resultado los ira almacenando en la variable
                webServiceResult += line;
            }
            bufferedReader.close();
        } catch (Exception e) {}
        return webServiceResult;//Resultado del servidor (convertido en JSON)
    }

                //Método que setea campos para funcionamiento dinamico
    public void setCampos(String email){
            String correo_user_set,nombre_user_set;
            try {
                String resultJSON="";
                JSONObject respuestaJSON = new JSONObject  (llamarDatos(email));   //Se guarda el resultado obtenido del JSON
                resultJSON = respuestaJSON.getString("estado"); //Consulta al arreglo "Estado"
                if (resultJSON.equals("1")) {      // Existen registros en BD
                    correo_user_set = respuestaJSON.getJSONObject("consulta").getString("email");
                    nombre_user_set = respuestaJSON.getJSONObject("consulta").getString("nombre");
                    nombre.setText(nombre_user_set);
                    correo.setText(correo_user_set);
                }
                else if (resultJSON.equals("2")){   //No se encuentran registros
                    Toast.makeText(getApplicationContext(),user_cache,Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

                //Método para seleccionar al usuario de la cache
    public String cargarCache(){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);

        String user = preferences.getString("usuario","No hay nada guardado");

        return user;
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


        } else if (id == R.id.nav_generada) {

        } else if (id == R.id.nav_facturacion) {

        } else if (id == R.id.nav_salir) {
            eliminarSesion();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        //imagen_usuario = (CircleImageView) header.findViewById(R.id.imageViewUsuario);

        //Método que carga las preferencias del usuario
        setCampos(String.valueOf(cargarCache()));

                                                    //Métodos para llenar Gráfica con Datos
        grafica = (LineChart) findViewById(R.id.grafica);           //Asignación de variables de tipo LineChart
        setData(100, 60);                               //Método que llama la insersión de datos en la gráfica
        grafica.animateX(3000);                         //Método que indica el tiempo de animación a la
        grafica.getAxisRight().setEnabled(false);
    }

                                //Metodo Si se ha presionado Back
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
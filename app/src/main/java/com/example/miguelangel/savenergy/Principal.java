package com.example.miguelangel.savenergy;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Configuracion_Perfil.OnFragmentInteractionListener,
Bienvenido.OnFragmentInteractionListener, Menu_configuracion.OnFragmentInteractionListener{

    private CombinedChart grafica;
    private String []horas = new String[]{"1","2","3","4","5","6","7","8"};
    private int[]volts = new int[]{100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,};
    private int[]colores = new int[]{Color.rgb(13,13,13)};

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        grafica = (CombinedChart) findViewById(R.id.grafica);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;


        int id = item.getItemId();

        if (id == R.id.nav_inicio) {

            fragment = new Bienvenido();

            getSupportFragmentManager().beginTransaction().replace(R.id.Principal,fragment).commit();
            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());


        } else if (id == R.id.nav_configuracion_perfil) {

            fragment = new Menu_configuracion();

            getSupportFragmentManager().beginTransaction().replace(R.id.Principal,fragment).commit();
            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());

        } else if (id == R.id.nav_consumida) {


        } else if (id == R.id.nav_generada) {

        } else if (id == R.id.nav_facturacion) {

        } else if (id == R.id.nav_salir) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private Chart getSameChart(){
        return null;
    }
}

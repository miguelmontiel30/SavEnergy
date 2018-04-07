package com.example.miguelangel.savenergy;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.Dataset;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Configuracion_Perfil.OnFragmentInteractionListener,
Bienvenido.OnFragmentInteractionListener, Menu_configuracion.OnFragmentInteractionListener{


    private LineChart grafica;


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

        grafica = (LineChart) findViewById(R.id.grafica);

            setData(100, 60);
            grafica.animateX(3000);




        grafica.getAxisRight().setEnabled(false);
        /*
        String[] horas = new String[] {"12","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};

        XAxis xAxis = grafica.getXAxis();
        xAxis.setValueFormatter(new Etiquetas_Eje_X(horas));
        xAxis.setGranularity(1);*/
    }


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
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
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

}
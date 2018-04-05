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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Configuracion_Perfil.OnFragmentInteractionListener,
Bienvenido.OnFragmentInteractionListener, Menu_configuracion.OnFragmentInteractionListener{


    private LineChart grafica;
    private String []tipo_energía = new String[]{"Sustentable","Electrica"};
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

        grafica = (LineChart) findViewById(R.id.grafica);

        setData(40,60);
        grafica.animateX(3000);
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

    /*
    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animation){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animation);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry>entries = new ArrayList<>();
        for (int i=0; i<tipo_energía.length; i++){
            LegendEntry entry = new LegendEntry();
            entry.label=tipo_energía[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i=0; i<volts.length; i++) {
            entries.add(new BarEntry(i,volts[i]));
        }
        return entries;
    }

    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(tipo_energía));
    }

    private void axisLeft(YAxis axis){
        axis.setSpaceTop(1000);
        axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public void createCharts(){
        grafica = (BarChart)getSameChart(grafica,"Consumo de Energías",Color.BLACK,Color.WHITE,3000);
        grafica.setData(getBarData());
        grafica.invalidate();
        axisX(grafica.getXAxis());
        axisLeft(grafica.getAxisLeft());
        axisRight(grafica.getAxisRight());
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colores);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData(){
        BarDataSet barDataSet = (BarDataSet) getData(new BarDataSet(getBarEntries(),""));
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(1);
        return barData;
    }*/

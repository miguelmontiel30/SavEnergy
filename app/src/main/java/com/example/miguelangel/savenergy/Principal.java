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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Configuracion_Perfil.OnFragmentInteractionListener,
Bienvenido.OnFragmentInteractionListener, Menu_configuracion.OnFragmentInteractionListener{

    /*
    private BarChart grafica;
    private String []horas = new String[]{"1","2","3","4","5","6","7","8"};
    private int[]volts = new int[]{100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,};
    private int[]colores = new int[]{Color.rgb(13,13,13)};
    */

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

     //   grafica = (BarChart) findViewById(R.id.grafica);
    //    createCharts();
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
        for (int i=0; i<horas.length; i++){
            LegendEntry entry = new LegendEntry();
            entry.label=horas[i];
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
        axis.setValueFormatter(new IndexAxisValueFormatter(horas));
    }

    private void axisLeft(YAxis axis){
        axis.setSpaceTop(1000);
        axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public void createCharts(){
        grafica = (BarChart)getSameChart(grafica,"Consumo de Energías",Color.WHITE,Color.BLACK,3000);
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
}

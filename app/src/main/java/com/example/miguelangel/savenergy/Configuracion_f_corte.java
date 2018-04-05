package com.example.miguelangel.savenergy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class Configuracion_f_corte extends AppCompatActivity {

    private AsyncHttpClient cliente;
    private Spinner spTarifas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_f_corte);

        cliente = new AsyncHttpClient();
        spTarifas = (Spinner) findViewById(R.id.sp_tarifa);
    }

    private void llenarSp(){
        String url = "https://savenergy.000webhostapp.com/savenergy/get_Tarifas.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    private void cargarSpinner(String respuesta){

    }
}

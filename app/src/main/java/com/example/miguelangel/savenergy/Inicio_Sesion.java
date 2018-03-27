package com.example.miguelangel.savenergy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio_Sesion extends AppCompatActivity implements View.OnClickListener{


    Button iniciar;


    public void iniciar_sesionOnclick() {
        Intent intent = new Intent(Inicio_Sesion.this, Principal.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio__sesion);

        iniciar = (Button) findViewById(R.id.iniciar_sesion);
        iniciar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == iniciar){
            iniciar_sesionOnclick();

        }
    }
}

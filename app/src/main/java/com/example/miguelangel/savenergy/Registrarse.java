package com.example.miguelangel.savenergy;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registrarse extends AppCompatActivity implements View.OnClickListener{
    TextInputEditText clave, correo, pass, pass2;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        clave = (TextInputEditText) findViewById(R.id.et_clave);
        correo = (TextInputEditText) findViewById(R.id.et_correo);

        registrar = (Button) findViewById(R.id.registrarse);
        registrar.setOnClickListener(this);
    }

    public void registrarse_OnClick() {
        Intent intent = new Intent(Registrarse.this,Configuracion_f_corte.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view == registrar){
            registrarse_OnClick();
        }
    }
}

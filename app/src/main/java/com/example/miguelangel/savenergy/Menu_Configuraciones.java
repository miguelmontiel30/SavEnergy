package com.example.miguelangel.savenergy;

                        //Clases importadas para el funcionamiento del proyecto
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Menu_Configuraciones extends AppCompatActivity {

                            //Método Si presiona en boton "Atrás"
    @Override
    public  void onBackPressed(){
        Intent intent = new Intent(Menu_Configuraciones.this,Principal.class);
        startActivity(intent);
        finish();
    }

                            //Método OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__configuracion);
    }
}

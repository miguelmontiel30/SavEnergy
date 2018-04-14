package com.example.miguelangel.savenergy;

                        //Clases importadas para el funcionamiento del proyecto
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Menu_Configuraciones extends AppCompatActivity implements View.OnClickListener {

    Button perfil,corte,contraseña;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
                            //Método Si presiona en boton "Atrás"
    @Override
    public  void onBackPressed(){
        Intent intent = new Intent(Menu_Configuraciones.this,Principal.class);
        startActivity(intent);
        finish();
    }

                            //Método para cargar UI Configuración de perfil
    public void cargarCPerfil(){
        Intent intent = new Intent(Menu_Configuraciones.this,Configuracion_Perfil.class);
        startActivity(intent);
        finish();
    }

                            //Método para cargar UI Cambiar contraseña
    public void cargarCcontraseña(){
        Intent intent = new Intent(Menu_Configuraciones.this,Cambiar_contrasenia.class);
        startActivity(intent);
        finish();
    }

                            //Método para cargar UI Configuración de fecha de corte
    public void cargarCCorte(){
        Intent intent = new Intent(Menu_Configuraciones.this,Configuracion_f_corte.class);
        startActivity(intent);
        finish();
    }

                            //Método OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__configuracion);

        //Asignación de variable a componente en XML

        perfil = (Button) findViewById(R.id.btn_configurar_perfil);               //Asignación de variable de tipo Button
        perfil.setOnClickListener(this);

        corte = (Button) findViewById(R.id.btn_tarifa);                           //Asignación de variable de tipo Button
        corte.setOnClickListener(this);

        contraseña = (Button) findViewById(R.id.btn_contrasenia);                 //Asignación de variable de tipo Button
        contraseña.setOnClickListener(this);
    }

    //Método onClick
    @Override
    public void onClick(View view){
        if (view == perfil) {
            cargarCPerfil();
        }else if (view == corte){
            cargarCCorte();
        }else if (view == contraseña){
            cargarCcontraseña();
        }
    }

}
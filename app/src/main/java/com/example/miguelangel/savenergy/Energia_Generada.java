package com.example.miguelangel.savenergy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Energia_Generada extends AppCompatActivity {

    //Método si el usuario presiono ir hacia atrás
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Energia_Generada.this,Principal.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energia__generada);
    }
}

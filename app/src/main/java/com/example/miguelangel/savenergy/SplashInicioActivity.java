package com.example.miguelangel.savenergy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;



public class SplashInicioActivity extends AppCompatActivity{
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_inicio);

        logo = (ImageView) findViewById(R.id.imageView);

        Animation myanimation = AnimationUtils.loadAnimation(this, R.anim.mytransation);
        logo.startAnimation(myanimation);
        Thread tiempo = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent activity_principal = new Intent(SplashInicioActivity.this, Inicio.class);
                    startActivity(activity_principal);
                    finish();
                }
            }
        };
        tiempo.start();
    }
}

package com.example.jksa.redsocial;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView img_imagen_activos = (ImageView)findViewById(R.id.img_imagen_principal);
        ImageView img_logo_ucsm = (ImageView)findViewById(R.id.img_logo_ucsm);
        ImageView img_logo_epis = (ImageView)findViewById(R.id.img_logo_epis);
        TextView tv_version = (TextView)findViewById(R.id.tv_version);

        Animation uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        Animation downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        img_imagen_activos.setAnimation(uptodown);
        img_logo_ucsm.setAnimation(downtoup);
        img_logo_epis.setAnimation(downtoup);
        tv_version.setAnimation(uptodown);

        //Hilo que permitira y validara el acceso a la Aplicacion
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },1200);
    }

}

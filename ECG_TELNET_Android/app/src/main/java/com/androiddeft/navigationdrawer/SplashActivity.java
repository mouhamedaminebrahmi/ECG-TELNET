package com.androiddeft.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


//splash screen de lancement de l'application
public class SplashActivity extends AppCompatActivity {

    // Splash screen conteur
    private static int SPLASH_TIME_OUT = 4000;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Masquer la barre d'action
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        logo=(ImageView)findViewById(R.id.imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*
                 * Nous sommes maintenant sur le vue splash screen

                 * Intent pour démarrer la menu activité.
                 */

                Intent i = new Intent(SplashActivity.this,MenuActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.anim_down);
        logo.startAnimation(myanim);
    }
}
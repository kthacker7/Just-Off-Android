package com.justoff.webviewapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


/**
 * Created by kunalthacker on 12/22/164
 */

public class SplashScreen extends Activity{
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.justoff.webviewapp.R.layout.activity_splash);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(213, 162, 71));
        ImageView backgroundImg = (ImageView) findViewById(com.justoff.webviewapp.R.id.imgLogo);
//        backgroundImg.setBackgroundColor(Color.rgb(237, 157, 64));
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

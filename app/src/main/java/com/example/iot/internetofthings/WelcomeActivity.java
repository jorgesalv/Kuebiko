package com.example.iot.internetofthings;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    //Duration of the splash
    private static int SPLASH_TIME_OUT=2000;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the Action Bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);


        //Instantiate the enterprise logo
        logo = findViewById(R.id.logo);
        //Logo animation
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransaction);
        logo.startAnimation(myanim);
        //Create the splash in the first screen of our app
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent homeIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    startActivity(homeIntent);
                    finish();
            }
        },SPLASH_TIME_OUT);

    }
}

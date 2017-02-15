package com.coffeemeetsbagel.praneethambati.meettheteam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    //Variables
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main/landing activity
                    Intent i = new Intent(SplashScreenActivity.this, LandingActivity.class);
                    startActivity(i);

                finish();

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.activity_toast,
                        (ViewGroup) findViewById(R.id.custom_toast_container));

                TextView text = (TextView) layout.findViewById(R.id.text);


                text.setText("Welcome!!");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }


        }, SPLASH_TIME_OUT);
    }
}

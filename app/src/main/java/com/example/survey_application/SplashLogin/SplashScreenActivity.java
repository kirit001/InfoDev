package com.example.survey_application.SplashLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survey_application.BottomNavigation.Survey.PersonalInfoActivity;
import com.example.survey_application.Login.LoginActivity;
import com.example.survey_application.R;

public class SplashScreenActivity extends AppCompatActivity {

    protected boolean _active = true;
    protected int _splashTime = 1000; // time to display the splash screen in ms
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        final Boolean islogged = sharedPreferences.getBoolean("islogged", false);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    if (islogged) {
                        startActivity(new Intent(SplashScreenActivity.this,
                                PersonalInfoActivity.class));
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this,
                                LoginActivity.class));
                    }
                }
            }
        };

        splashTread.start();

    }
}
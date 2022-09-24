package com.example.dule2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class AuthorisedCheckActivity extends AppCompatActivity {

    private static final int LOGO_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorised_check);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn", false);

                /*if (hasLoggedIn) {
                    startActivity(new Intent(AuthorisedCheckActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(AuthorisedCheckActivity.this, LoginActivity.class));
                }*/

                startActivity(new Intent(AuthorisedCheckActivity.this, LoadingActivity.class));
            }
        }, LOGO_TIME_OUT);
    }
}
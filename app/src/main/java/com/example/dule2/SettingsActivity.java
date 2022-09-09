package com.example.dule2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.RelativeLayout;

public class SettingsActivity extends AppCompatActivity {
    RelativeLayout[] button_menu = new RelativeLayout[4];
    String name_menu[] = new String[4];
    RelativeLayout[] buttons_menu = new RelativeLayout[4];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Intent[] intents_settings = new Intent[4];
        intents_settings[0] = new Intent(this, SettingsActivity_setgroups.class);
        intents_settings[1] = new Intent(this, SettingsActivity_setgroups.class);
        intents_settings[2] = new Intent(this, SettingsActivity_setgroups.class);
        intents_settings[3] = new Intent(this, ProfileActivity.class);
        Intent[] intents = new Intent[4];
        buttons_menu[0] = findViewById(R.id.BotNavButton_search);
        buttons_menu[1] = findViewById(R.id.BotNavButton_news);
        buttons_menu[2] = findViewById(R.id.BotNavButton_note);
        buttons_menu[3] = findViewById(R.id.BotNavButton_home);
        intents[0] = new Intent(this, SearchActivity.class);
        intents[1] = new Intent(this, NewsActivity.class);
        intents[2] = new Intent(this, NotesActivity.class);
        intents[3] = new Intent(this, HomeActivity.class);
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            buttons_menu[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityIfNeeded(intents[finalI], 0);

                    /*try{

                        intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(intents[finalI], 0);

                    } catch (Exception e) {
                        startActivity(intents[finalI]);
                        overridePendingTransition(0, 0);
                        e.printStackTrace();
                    }*/

                }
            });
        }


        button_menu[0] = findViewById(R.id.Button_settings_first);
        button_menu[1] = findViewById(R.id.Button_settings_second);
        button_menu[2] = findViewById(R.id.Button_settings_third);
        button_menu[3] = findViewById(R.id.Button_settings_four);




        for (int i = 0; i < 4; i++) {
            int finalI = i;
            button_menu[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intents_settings[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(intents_settings[finalI], 0);

                    /*for (int m = 0; m < 4; m++) {

                        intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityIfNeeded(intents_settings[m], 0);

                        *//*try{
                            intents_settings[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            overridePendingTransition(0,0);
                            startActivityIfNeeded(intents_settings[finalI], 0);
                        } catch (Exception e) {
                            startActivity(intents_settings[finalI]);
                            overridePendingTransition(0, 0);
                            e.printStackTrace();
                        }*//*
                    }*/

                }
            });
        }


    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(0,0);
        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }




}
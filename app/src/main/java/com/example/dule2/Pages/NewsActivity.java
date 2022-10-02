package com.example.dule2.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.dule2.R;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        {
            RelativeLayout[] buttons_menu = new RelativeLayout[4];
            Intent[] intents = new Intent[4];
            buttons_menu[0] = findViewById(R.id.BotNavButton_search);
            buttons_menu[1] = findViewById(R.id.BotNavButton_home);
            buttons_menu[2] = findViewById(R.id.BotNavButton_note);
            buttons_menu[3] = findViewById(R.id.BotNavButton_settings);
            intents[0] = new Intent(this, SearchActivity.class);
            intents[1] = new Intent(this, HomeActivity.class);
            intents[2] = new Intent(this, NotesActivity.class);
            intents[3] = new Intent(this, SettingsActivity.class);
            for(int i = 0;i<4;i++){
                int finalI = i;
                buttons_menu[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityIfNeeded(intents[finalI], 0);

                        /*try{

                            intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                            startActivityIfNeeded(intents[finalI], 0);
                            overridePendingTransition(0,0);
                        } catch (Exception e) {
                            startActivity(intents[finalI]);
                            overridePendingTransition(0, 0);
                            e.printStackTrace();
                        }*/
                    }
                });
            }




        }





    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(0,0);
        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
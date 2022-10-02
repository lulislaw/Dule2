package com.example.dule2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.dule2.Pages.HomeActivity;
import com.example.dule2.Settings_menu.SettingsActivity_setgroups;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadingActivity extends AppCompatActivity {




    private final static String FILE_NAME_SELECTION = "selection.txt";



                //Загрузить файлы эксель в кэш.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);




        FileInputStream fins = null;
        String text_selection = "";
        try {
            fins = openFileInput(FILE_NAME_SELECTION);
            Intent intent = new Intent(LoadingActivity.this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);

        } catch (FileNotFoundException e) {
            Intent intent = new Intent(LoadingActivity.this, SettingsActivity_setgroups.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }



    }


}


package com.example.dule2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItemArrayList;
    AsyncHttpClient client;
    DBHelper dbHelper= new DBHelper(HomeActivity.this);
    FileInputStream fis;
    XSSFWorkbook workbook;
    ContentValues contentValues = new ContentValues();
    ProgressBar progressBar;
    String[] weekeven = new String[50];
    String[] times_1 = new String[12];
    String[] times_2 = new String[12];
    String[] times_3 = new String[12];
    String[] times_4 = new String[12];
    String[] names_1 = new String[12];
    String[] names_2 = new String[12];
    String[] names_3 = new String[12];
    String[] names_4 = new String[12];
    String[] types_1 = new String[12];
    String[] types_2 = new String[12];
    String[] types_3 = new String[12];
    String[] types_4 = new String[12];
    String[] teachers_1 = new String[12];
    String[] teachers_2 = new String[12];
    String[] teachers_3 = new String[12];
    String[] teachers_4 = new String[12];
    String[] rooms_1 = new String[12];
    String[] rooms_2 = new String[12];
    String[] rooms_3 = new String[12];
    String[] rooms_4 = new String[12];
    String[] week_vp = new String[12];
    String[] date_vp = new String[12];


        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            progressBar = findViewById(R.id.progressBar);

            {
                RelativeLayout[] buttons_menu = new RelativeLayout[4];
                Intent[] intents = new Intent[4];
                buttons_menu[0] = findViewById(R.id.BotNavButton_search);
                buttons_menu[1] = findViewById(R.id.BotNavButton_news);
                buttons_menu[2] = findViewById(R.id.BotNavButton_note);
                buttons_menu[3] = findViewById(R.id.BotNavButton_settings);
                intents[0] = new Intent(this, SearchActivity.class);
                intents[1] = new Intent(this, NewsActivity.class);
                intents[2] = new Intent(this, NoteActivity.class);
                intents[3] = new Intent(this, SettingsActivity.class);
                for(int i = 0;i<4;i++){
                    int finalI = i;
                    buttons_menu[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(intents[finalI]);
                            overridePendingTransition(0,0);
                        }
                    });
                }




            }




            //dowloaddata("https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak1.xlsx?raw=true", 5);














        }

        private void loaddate(){

            viewPager2 = findViewById(R.id.viewpager);
            SQLiteDatabase database2 = dbHelper.getWritableDatabase();
            Cursor cursor = database2.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                int IdIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int NameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                int TimeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
                int WeekIndex = cursor.getColumnIndex(DBHelper.KEY_WEEK);
                int i = 0;

                do {

                    weekeven[i] = cursor.getString(NameIndex);
                    i++;

                }
                while (cursor.moveToNext());

            }
            cursor.close();

            for(int i = 0; i< 6;i++)
            {
                int a = i+6;
                names_1[i] = weekeven[0+(8*i)];
                names_2[i] = weekeven[2+(8*i)];
                names_3[i] = weekeven[4+(8*i)];
                names_4[i] = weekeven[6+(8*i)];
                names_1[a] = weekeven[1+(8*i)];
                names_2[a] = weekeven[3+(8*i)];
                names_3[a] = weekeven[5+(8*i)];
                names_4[a] = weekeven[7+(8*i)];
            }

            viewPagerItemArrayList = new ArrayList<>();
            for(int i = 0; i < names_1.length; i++)
            {
                ViewPagerItem viewPagerItem = new ViewPagerItem(week_vp[i],date_vp[i],names_1[i],names_2[i],names_3[i],names_4[i],names_1[i],names_2[i],names_3[i],names_4[i],names_1[i],names_2[i],names_3[i],names_4[i],names_1[i],names_2[i],names_3[i],names_4[i],names_1[i],names_2[i],names_3[i],names_4[i]);
                viewPagerItemArrayList.add(viewPagerItem);
            }
            VPAdapder vpAdapder = new VPAdapder(viewPagerItemArrayList);
            viewPager2.setAdapter(vpAdapder);
            viewPager2.setClipToPadding(false);
            viewPager2.setClipChildren(false);
            viewPager2.setOffscreenPageLimit(2);
            viewPager2.getChildAt(0).setOverScrollMode(viewPager2.OVER_SCROLL_NEVER);
            progressBar.setVisibility(View.GONE);



        };




        // download excel -> convert to dbSQL save
        private void dowloaddata(String url_file, Integer id_group){
            progressBar.setVisibility(View.VISIBLE);
            client = new AsyncHttpClient();
            client.get(url_file, new FileAsyncHttpResponseHandler(this) {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    progressBar.setVisibility(View.GONE);
                }
                public void onSuccess(int statusCode, Header[] headers, File file) {

                    if(file != null) {
                        String sizefile = "";
                        try {
                            BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class);
                            sizefile = attr.size()+"";
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SQLiteDatabase database1 = dbHelper.getWritableDatabase();
                        Cursor cursor1 = database1.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                        cursor1.moveToLast();
                        int NameIndex1 = cursor1.getColumnIndex(DBHelper.KEY_NAME);

                        if(1 != 0) { // добавить сравенение группы    Integer.parseInt(sizefile)
                            try {
                                ZipSecureFile.setMinInflateRatio(0);
                                fis = new FileInputStream(file);
                                workbook = new XSSFWorkbook(fis);
                                XSSFSheet sheet = workbook.getSheetAt(0);
                                int lenmergedregion = sheet.getMergedRegions().size();
                                int[] CellStart = new int[lenmergedregion];
                                int[] CellEnd = new int[lenmergedregion];
                                int[] RowStart = new int[lenmergedregion];
                                int[] RowEnd = new int[lenmergedregion];
                                for (int i = 0; i < lenmergedregion; i++) {
                                    CellStart[i] = sheet.getMergedRegions().get(i).getFirstColumn();
                                    RowStart[i] = sheet.getMergedRegions().get(i).getFirstRow();
                                    CellEnd[i] = sheet.getMergedRegions().get(i).getLastColumn();
                                    RowEnd[i] = sheet.getMergedRegions().get(i).getLastRow();
                                }

                                for (int i = 0; i < lenmergedregion; i++) {
                                    String mergedstring = "";
                                    for (int r = RowStart[i]; r <= RowEnd[i]; r++) {

                                        for (int c = CellStart[i]; c <= CellEnd[i]; c++) {
                                            if (sheet.getRow(r).getCell(c).toString().length() > 1) {
                                                mergedstring = sheet.getRow(r).getCell(c).toString();
                                            }

                                        }

                                    }
                                    for (int r = RowStart[i]; r <= RowEnd[i]; r++) {

                                        for (int c = CellStart[i]; c <= CellEnd[i]; c++) {
                                            sheet.getRow(r).getCell(c).setCellValue(mergedstring);
                                        }
                                    }
                                }


                                SQLiteDatabase database = dbHelper.getWritableDatabase();
                                database.delete(DBHelper.TABLE_CONTACTS, null, null);


                                for (int i = 8; i < 56; i++) {

                                    String name = sheet.getRow(i).getCell(id_group).toString();
                                    String week = sheet.getRow(i).getCell(3).toString();
                                    String time = sheet.getRow(i).getCell(2).toString();

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(DBHelper.KEY_NAME, name);
                                    contentValues.put(DBHelper.KEY_TIME, time);
                                    contentValues.put(DBHelper.KEY_WEEK, week);
                                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                                }
                                String name = sizefile;


                                contentValues.put(DBHelper.KEY_NAME, name);
                                contentValues.put(DBHelper.KEY_TIME, "");
                                contentValues.put(DBHelper.KEY_WEEK, "");
                                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                                Log.d("mylog", "success save");
                                loaddate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        cursor1.close();


                    }
                }
            });




        }



}
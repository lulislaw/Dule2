package com.example.dule2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;

public class SettingsActivity_setgroups extends AppCompatActivity {
    private final static String FILE_NAME_SELECTION = "selection.txt";
    AsyncHttpClient client;
    DBHelper dbHelper = new DBHelper(SettingsActivity_setgroups.this);
    FileInputStream fis;
    XSSFWorkbook workbook;
    ContentValues contentValues = new ContentValues();
    Button BUTTON_SAVE_SELECTION;
    Sheet SHEET;
    LocalDate date_start, date_end, date_current;
    File file_workbook;
    String sizefile;
    int diff_date_int;
    String name_menu[] = new String[4];
    RelativeLayout[] buttons_menu = new RelativeLayout[5];


    Spinner SPINNER_SELECT_COURSE, SPINNER_SELECT_GROUP, SPINNER_SELECT_INSTITUTE;
    String[] url = new String[5];

    int WORKBOOK_COURSE_ID, SHEET_INSTITUTE_ID, COLLUMN_GROUP_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_setgroups);

        date_start = LocalDate.of(2022, 2, 7);
        date_end = LocalDate.of(2022, 5, 30);
        date_current = LocalDate.now();
        diff_date_int = (int) (date_end.toEpochDay() - date_start.toEpochDay());
        Intent[] intents = new Intent[5];
        buttons_menu[0] = findViewById(R.id.BotNavButton_search);
        buttons_menu[1] = findViewById(R.id.BotNavButton_news);
        buttons_menu[2] = findViewById(R.id.BotNavButton_note);
        buttons_menu[3] = findViewById(R.id.BotNavButton_home);
        buttons_menu[4] = findViewById(R.id.BotNavButton_settings);
        intents[0] = new Intent(this, SearchActivity.class);
        intents[1] = new Intent(this, NewsActivity.class);
        intents[2] = new Intent(this, NoteActivity.class);
        intents[3] = new Intent(this, HomeActivity.class);
        intents[4] = new Intent(this, SettingsActivity.class);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            buttons_menu[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{

                        intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(intents[finalI], 0);
                        overridePendingTransition(0,0);
                    } catch (Exception e) {
                        startActivity(intents[finalI]);
                        overridePendingTransition(0, 0);
                        e.printStackTrace();
                    }

                }
            });
        }
        firstmenu_create();
    }


    private void firstmenu_create() {
        SPINNER_SELECT_COURSE = findViewById(R.id.SPINNER_SELECT_COURSE);
        SPINNER_SELECT_GROUP = findViewById(R.id.SPINNER_SELECT_GROUP);
        SPINNER_SELECT_INSTITUTE = findViewById(R.id.SPINNER_SELECT_INSTITUTE);
        BUTTON_SAVE_SELECTION = findViewById(R.id.BUTTON_SAVE_SELECTION);
        url[0] = "https://github.com/lulislaw/excelfilesguu/blob/main/bak1.xlsx?raw=true";
        url[1] = "https://github.com/lulislaw/excelfilesguu/blob/main/bak2.xlsx?raw=true";
        url[2] = "https://github.com/lulislaw/excelfilesguu/blob/main/bak3.xlsx?raw=true";
        url[3] = "https://github.com/lulislaw/excelfilesguu/blob/main/bak4.xlsx?raw=true";



        String[] ARRAYSPINNER_1 = new String[]
                {
                        "1 Курс", "2 Курс", "3 Курс", "4 Курс"
                };
        ArrayAdapter<String> ADAPTER_1 = new ArrayAdapter<String>(SettingsActivity_setgroups.this, android.R.layout.simple_spinner_item, ARRAYSPINNER_1);
        ADAPTER_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPINNER_SELECT_COURSE.setAdapter(ADAPTER_1);

        SPINNER_SELECT_COURSE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                WORKBOOK_COURSE_ID = position;
                client = new AsyncHttpClient();
                client.get(url[position], new FileAsyncHttpResponseHandler(SettingsActivity_setgroups.this) {
                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, File file) {

                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, File file) {


                        SPINNER_SELECT_INSTITUTE.setClickable(true);

                        if (file != null) {
                            file_workbook = file;
                            sizefile = "";
                            try {
                                BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class);
                                sizefile = attr.size() + "";
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                ZipSecureFile.setMinInflateRatio(0);
                                fis = new FileInputStream(file);
                                workbook = new XSSFWorkbook(fis);

                                String[] ARRAYSPINNER_2;

                                String[] sheetsname = new String[workbook.getNumberOfSheets()];
                                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

                                    sheetsname[i] = workbook.getSheetName(i);
                                }
                                ARRAYSPINNER_2 = sheetsname;

                                ArrayAdapter<String> ADAPTER_2 = new ArrayAdapter<String>(SettingsActivity_setgroups.this, android.R.layout.simple_spinner_item, ARRAYSPINNER_2);
                                ADAPTER_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                SPINNER_SELECT_INSTITUTE.setAdapter(ADAPTER_2);

                            } catch (IOException e) {
                                e.printStackTrace();

                            }

                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SPINNER_SELECT_INSTITUTE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SPINNER_SELECT_GROUP.setClickable(true);
                SHEET_INSTITUTE_ID = position;
                SHEET = workbook.getSheetAt(SHEET_INSTITUTE_ID);

                int a = 0;
                for (int i = 0; i < 100; i++) {
                    if (SHEET.getRow(5).getCell(i + 4) != null)
                        a++;
                    else
                        break;
                }

                String[] GROUPS = new String[a];
                for (int i = 0; i < a; i++) {
                    String numtos = SHEET.getRow(6).getCell(i + 4).toString();
                    GROUPS[i] = numtos + " | "
                            + SHEET.getRow(5).getCell(i + 4).toString();
                }


                String[] ARRAYSPINNER_3 = GROUPS;
                ArrayAdapter<String> ADAPTER_3 = new ArrayAdapter<String>(SettingsActivity_setgroups.this, android.R.layout.simple_spinner_item, ARRAYSPINNER_3);
                ADAPTER_3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPINNER_SELECT_GROUP.setAdapter(ADAPTER_3);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SPINNER_SELECT_GROUP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                COLLUMN_GROUP_ID = position;
                BUTTON_SAVE_SELECTION.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        BUTTON_SAVE_SELECTION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileOutputStream fos = null;
                try {

                    String text = WORKBOOK_COURSE_ID + "x" + SHEET_INSTITUTE_ID + "x" + COLLUMN_GROUP_ID;
                    fos = openFileOutput(FILE_NAME_SELECTION, MODE_PRIVATE);
                    fos.write(text.getBytes());


                    //////////////////////////////////////


                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    database.delete(DBHelper.TABLE_CONTACTS, null, null);




                        for (int i = 0; i < 48; i++) {


                            String name = "null";
                            String time = "null";


                            try {
                                name = SHEET.getRow(i + 8).getCell(COLLUMN_GROUP_ID + 4).toString();
                                time = SHEET.getRow(i + 8).getCell(2).toString();
                            } catch (Exception e) {
                                name = "null";
                                time = "null";

                            }


                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DBHelper.KEY_NAME, name);
                            contentValues.put(DBHelper.KEY_TIME, time);
                            database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);






                        }
                    for(int e = 0; e <8; e++)
                    {
                        contentValues.put(DBHelper.KEY_NAME, "null");
                        contentValues.put(DBHelper.KEY_TIME, "null");
                        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                    }

                    Log.d("mylog", "success save");
                    contentValues.put(DBHelper.KEY_NAME, "1");
                    contentValues.put(DBHelper.KEY_TIME,
                    SHEET.getRow(6).getCell(COLLUMN_GROUP_ID+4).toString().substring(0,1).toString()  + " | " +
                    SHEET.getRow(5).getCell(COLLUMN_GROUP_ID + 4).toString());
                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                    buttons_menu[3].callOnClick();
                    /////////////////////////////



                } catch (IOException ex) {

                } finally {
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException ex) {

                    }
                }


            }
        });


    }


}
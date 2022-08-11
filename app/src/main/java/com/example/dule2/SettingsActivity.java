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
import android.widget.TextView;

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

public class SettingsActivity extends AppCompatActivity {
    private final static String FILE_NAME_SELECTION = "selection.txt";
    AsyncHttpClient client;
    DBHelper dbHelper = new DBHelper(SettingsActivity.this);
    FileInputStream fis;
    XSSFWorkbook workbook;
    ContentValues contentValues = new ContentValues();
    Button BUTTON_SAVE_SELECTION;
    RelativeLayout[] button_menu = new RelativeLayout[4];
    RelativeLayout[] menu_settings = new RelativeLayout[4];
    RelativeLayout back_rl;
    String text;
    Sheet SHEET;
    LocalDate date_start, date_end, date_current;
    File file_workbook;
    String sizefile;
    int diff_date_int;
    String name_menu[] = new String[4];
    RelativeLayout[] buttons_menu = new RelativeLayout[4];
    TextView back_name, notification;
    RelativeLayout notification_rl;
    Spinner SPINNER_SELECT_COURSE, SPINNER_SELECT_GROUP, SPINNER_SELECT_INSTITUTE;
    String[] url = new String[5];
    String[] daysofweeks_string = {
            "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"
    };
    int WORKBOOK_COURSE_ID, SHEET_INSTITUTE_ID, COLLUMN_GROUP_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        date_start = LocalDate.of(2022, 2, 7);
        date_end = LocalDate.of(2022, 5, 30);
        date_current = LocalDate.now();
        diff_date_int = (int) (date_end.toEpochDay() - date_start.toEpochDay());


        Intent[] intents = new Intent[4];
        buttons_menu[0] = findViewById(R.id.BotNavButton_search);
        buttons_menu[1] = findViewById(R.id.BotNavButton_news);
        buttons_menu[2] = findViewById(R.id.BotNavButton_note);
        buttons_menu[3] = findViewById(R.id.BotNavButton_home);
        intents[0] = new Intent(this, SearchActivity.class);
        intents[1] = new Intent(this, NewsActivity.class);
        intents[2] = new Intent(this, NoteActivity.class);
        intents[3] = new Intent(this, HomeActivity.class);
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            buttons_menu[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intents[finalI]);
                    overridePendingTransition(0, 0);
                }
            });
        }


        name_menu[0] = "Группа";
        name_menu[1] = "Настройки";
        name_menu[2] = "Помощь";
        name_menu[3] = "FAQ";
        back_name = findViewById(R.id.back_name);
        button_menu[0] = findViewById(R.id.Button_select_day_first);
        button_menu[1] = findViewById(R.id.Button_settings_second);
        button_menu[2] = findViewById(R.id.Button_settings_third);
        button_menu[3] = findViewById(R.id.Button_settings_four);
        menu_settings[0] = findViewById(R.id.menu_first);
        menu_settings[1] = findViewById(R.id.menu_second);
        menu_settings[2] = findViewById(R.id.menu_third);
        menu_settings[3] = findViewById(R.id.menu_four);
        back_rl = findViewById(R.id.back_relativeLayout);
        notification = findViewById(R.id.notification);
        notification_rl = findViewById(R.id.notification_rl);


        for (int i = 0; i < 4; i++) {
            int finalI = i;
            button_menu[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int m = 0; m < 4; m++) {
                        button_menu[m].setVisibility(View.GONE);
                        menu_settings[m].setVisibility(View.GONE);

                    }
                    if (finalI == 0)
                        firstmenu_create();
                    menu_settings[finalI].setVisibility(View.VISIBLE);
                    back_rl.setVisibility(View.VISIBLE);
                    back_name.setText(name_menu[finalI]);

                }
            });
        }
        back_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int m = 0; m < 4; m++) {
                    button_menu[m].setVisibility(View.VISIBLE);
                    menu_settings[m].setVisibility(View.GONE);

                }

                back_rl.setVisibility(View.GONE);
            }
        });


        FileInputStream fin = null;
        try {

            fin = openFileInput(FILE_NAME_SELECTION);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            text = new String(bytes);
            if (text.length() < 5)
                button_menu[0].callOnClick();


        } catch (Exception e) {
            button_menu[0].callOnClick();
            e.printStackTrace();
        }


    }

    private void firstmenu_create() {
        SPINNER_SELECT_COURSE = findViewById(R.id.SPINNER_SELECT_COURSE);
        SPINNER_SELECT_GROUP = findViewById(R.id.SPINNER_SELECT_GROUP);
        SPINNER_SELECT_INSTITUTE = findViewById(R.id.SPINNER_SELECT_INSTITUTE);
        BUTTON_SAVE_SELECTION = findViewById(R.id.BUTTON_SAVE_SELECTION);
        url[0] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak1.xlsx?raw=true";
        url[1] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak2.xlsx?raw=true";
        url[2] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak3.xlsx?raw=true";
        url[3] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak4.xlsx?raw=true";


        String[] ARRAYSPINNER_1 = new String[]
                {
                        "1 Курс", "2 Курс", "3 Курс", "4 Курс"
                };
        ArrayAdapter<String> ADAPTER_1 = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_spinner_item, ARRAYSPINNER_1);
        ADAPTER_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPINNER_SELECT_COURSE.setAdapter(ADAPTER_1);

        SPINNER_SELECT_COURSE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setNotification("Поиск институтов");
                WORKBOOK_COURSE_ID = position;
                client = new AsyncHttpClient();
                client.get(url[position], new FileAsyncHttpResponseHandler(SettingsActivity.this) {
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

                                ArrayAdapter<String> ADAPTER_2 = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_spinner_item, ARRAYSPINNER_2);
                                ADAPTER_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                SPINNER_SELECT_INSTITUTE.setAdapter(ADAPTER_2);
                                setNotification("Поиск групп");
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
                setNotification("Поиск групп");
                SPINNER_SELECT_GROUP.setClickable(true);
                SHEET_INSTITUTE_ID = position;
                SHEET = workbook.getSheetAt(SHEET_INSTITUTE_ID);
                int lenmergedregion = SHEET.getMergedRegions().size();
                int[] CellStart = new int[lenmergedregion];
                int[] CellEnd = new int[lenmergedregion];
                int[] RowStart = new int[lenmergedregion];
                int[] RowEnd = new int[lenmergedregion];
                for (int i = 0; i < lenmergedregion; i++) {
                    CellStart[i] = SHEET.getMergedRegions().get(i).getFirstColumn();
                    RowStart[i] = SHEET.getMergedRegions().get(i).getFirstRow();
                    CellEnd[i] = SHEET.getMergedRegions().get(i).getLastColumn();
                    RowEnd[i] = SHEET.getMergedRegions().get(i).getLastRow();
                }

                for (int i = 0; i < lenmergedregion; i++) {
                    String mergedstring = "";
                    for (int r = RowStart[i]; r <= RowEnd[i]; r++) {

                        for (int c = CellStart[i]; c <= CellEnd[i]; c++) {
                            if (SHEET.getRow(r).getCell(c).toString().length() > 1) {
                                mergedstring = SHEET.getRow(r).getCell(c).toString();
                            }

                        }

                    }
                    for (int r = RowStart[i]; r <= RowEnd[i]; r++) {

                        for (int c = CellStart[i]; c <= CellEnd[i]; c++) {
                            SHEET.getRow(r).getCell(c).setCellValue(mergedstring);
                        }
                    }
                }


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
                ArrayAdapter<String> ADAPTER_3 = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_spinner_item, ARRAYSPINNER_3);
                ADAPTER_3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPINNER_SELECT_GROUP.setAdapter(ADAPTER_3);

                setNotification("");
                notification_rl.setVisibility(View.GONE);
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
                setNotification("Сохранение данных");
                FileOutputStream fos = null;
                try {

                    String text = WORKBOOK_COURSE_ID + "x" + SHEET_INSTITUTE_ID + "x" + COLLUMN_GROUP_ID;
                    fos = openFileOutput(FILE_NAME_SELECTION, MODE_PRIVATE);
                    fos.write(text.getBytes());


                    //////////////////////////////////////


                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    database.delete(DBHelper.TABLE_CONTACTS, null, null);

                    for (int d = 0; d < diff_date_int; d++) {


                        for (int s = 0; s < 8; s++) {

                            int i = s + 8 * (d % 7);
                            String name = "null";

                            String day = daysofweeks_string[LocalDate.ofEpochDay(date_start.toEpochDay() + d).getDayOfWeek().getValue() - 1] + " " + LocalDate.ofEpochDay(date_start.toEpochDay() + d).getDayOfMonth();
                            String month = LocalDate.ofEpochDay(date_start.toEpochDay() + d).getMonth().getValue() + "";
                            String year = LocalDate.ofEpochDay(date_start.toEpochDay() + d).getYear() + "";
                            String week = "null";
                            String time = "null";


                            try {
                                name = SHEET.getRow(i + 8).getCell(COLLUMN_GROUP_ID + 4).toString();
                                week = SHEET.getRow(i + 8).getCell(3).toString();
                                time = SHEET.getRow(i + 8).getCell(2).toString();
                            } catch (Exception e) {
                                name = "null";
                                week = "null";
                                time = "null";

                                e.printStackTrace();
                            }


                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DBHelper.KEY_DAY, day);
                            contentValues.put(DBHelper.KEY_MONTH, month);
                            contentValues.put(DBHelper.KEY_YEAR, year);
                            contentValues.put(DBHelper.KEY_NAME, name);
                            contentValues.put(DBHelper.KEY_TIME, time);
                            contentValues.put(DBHelper.KEY_WEEK, week);
                            database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);


                        }




                    }
                    Log.d("mylog", "success save");
                    contentValues.put(DBHelper.KEY_DAY, "1");
                    contentValues.put(DBHelper.KEY_MONTH, "1");
                    contentValues.put(DBHelper.KEY_YEAR, "1");
                    contentValues.put(DBHelper.KEY_NAME, "1");
                    contentValues.put(DBHelper.KEY_TIME,
                            SHEET.getRow(6).getCell(COLLUMN_GROUP_ID+4).toString().substring(0,1).toString()  + " | " +
                                    SHEET.getRow(5).getCell(COLLUMN_GROUP_ID + 4).toString());
                    contentValues.put(DBHelper.KEY_WEEK, "1");
                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                    //////////////////////////////////////
                    setNotification("");
                    notification_rl.setVisibility(View.GONE);
                    buttons_menu[3].callOnClick();

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
    private void setNotification(String note)
    {
        notification_rl.setVisibility(View.VISIBLE);
        notification.setText(note);
    }


}
package com.example.dule2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import android.database.sqlite.SQLiteDatabase;

import android.view.View;
import android.widget.CalendarView;
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
import java.time.LocalDate;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity {

    private final static String FILE_NAME_SELECTION = "selection.txt";

    ViewPager2 viewPager2_mainpage;
    ArrayList<ViewPagerItemMainPage> viewPagerItemMainPageArrayList;

    AsyncHttpClient client;
    DBHelper dbHelper = new DBHelper(HomeActivity.this);
    FileInputStream fis;
    XSSFWorkbook workbook;
    ContentValues contentValues = new ContentValues();

    ProgressBar progressBar;
    Integer id_workbook, id_sheet, id_collumn, diff_date_int;

    RelativeLayout[] buttons_menu = new RelativeLayout[4];
    RelativeLayout calendarButton, disableCalendar;
    CalendarView calendarView;

    TextView current_week_textview, current_group_textview;
    LocalDate date_start, date_end, date_current;

    Intent[] intents = new Intent[4];

    String text;
    String[] urls = new String[4];
    String[] names_1, names_2, names_3, names_4, date_vp, nameseven;
    String[] _monthru = {
            "Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.progressBar);

        // date start(y,m,d) 2022,02,06
        // date end(y,m,d) 2022,05,29
        date_start = LocalDate.of(2022, 2, 7);
        date_end = LocalDate.of(2022, 5, 30);
        date_current = LocalDate.now();
        diff_date_int = (int) (date_end.toEpochDay() - date_start.toEpochDay());
        int current_epoch_day = (int) (diff_date_int - ((date_end).toEpochDay() - date_current.toEpochDay()));

        names_1 = new String[14 * diff_date_int];
        names_2 = new String[14 * diff_date_int];
        names_3 = new String[14 * diff_date_int];
        names_4 = new String[14 * diff_date_int];
        date_vp = new String[14 * diff_date_int];
        nameseven = new String[58 * diff_date_int];
        current_group_textview = findViewById(R.id.gruppi);
        current_week_textview = findViewById(R.id.current_week_textview);
        calendarButton = findViewById(R.id.calendar_button);
        calendarView = findViewById(R.id.calendarView);
        disableCalendar = findViewById(R.id.disableCalendar);
        buttons_menu[0] = findViewById(R.id.BotNavButton_search);
        buttons_menu[1] = findViewById(R.id.BotNavButton_news);
        buttons_menu[2] = findViewById(R.id.BotNavButton_note);
        buttons_menu[3] = findViewById(R.id.BotNavButton_settings);
        intents[0] = new Intent(this, SearchActivity.class);
        intents[1] = new Intent(this, NewsActivity.class);
        intents[2] = new Intent(this, NoteActivity.class);
        intents[3] = new Intent(this, SettingsActivity.class);


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


        FileInputStream fin = null;
        try {

            fin = openFileInput(FILE_NAME_SELECTION);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            text = new String(bytes);
            if (text.length() >= 5) {
                String[] split_text = text.split("x");
                id_workbook = Integer.parseInt(split_text[0]);
                id_sheet = Integer.parseInt(split_text[1]);
                id_collumn = Integer.parseInt(split_text[2]);
            } else
                buttons_menu[3].callOnClick();


        } catch (Exception e) {
            buttons_menu[3].callOnClick();
            e.printStackTrace();
        }


        urls[0] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak1.xlsx?raw=true";
        urls[1] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak2.xlsx?raw=true";
        urls[2] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak3.xlsx?raw=true";
        urls[3] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak4.xlsx?raw=true";

        try {
            //dowloaddata(urls[id_workbook]);
            loaddata();
            if (current_epoch_day < diff_date_int && current_epoch_day > 0)
                viewPager2_mainpage.setCurrentItem(current_epoch_day);
            else
                viewPager2_mainpage.setCurrentItem(0);


        } catch (Exception e) {
            e.printStackTrace();
        }


        if ((viewPager2_mainpage.getCurrentItem() / 7) % 2 == 0)
            current_week_textview.setText("Нечетная (" + ((viewPager2_mainpage.getCurrentItem() / 7) + 1) + ")");
        else
            current_week_textview.setText("Четная (" + ((viewPager2_mainpage.getCurrentItem() / 7) + 1) + ")");
        viewPager2_mainpage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < 6; i++) {

                    if ((position / 7) % 2 == 0)
                        current_week_textview.setText("Нечетная (" + ((position / 7) + 1) + ")");
                    else
                        current_week_textview.setText("Четная (" + ((position / 7) + 1) + ")");

                }

                super.onPageSelected(position);
            }
        });
        disableCalendar.setVisibility(View.GONE);
        calendarView.setVisibility(View.GONE);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setVisibility(View.VISIBLE);
                disableCalendar.setVisibility(View.VISIBLE);
            }
        });

        disableCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableCalendar.setVisibility(View.GONE);
                calendarView.setVisibility(View.GONE);
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                int _month = month + 1;
                LocalDate tempLocalDate = LocalDate.of(year, _month, dayOfMonth);
                int tempdiff = (int) (diff_date_int - ((date_end.toEpochDay() - tempLocalDate.toEpochDay())));
                if (tempdiff >= 0 && tempdiff <= diff_date_int)
                    viewPager2_mainpage.setCurrentItem(tempdiff);

            }
        });

    }



    private void loaddata() {
        viewPager2_mainpage = findViewById(R.id.viewpagermain);
        SQLiteDatabase database2 = dbHelper.getWritableDatabase();
        Cursor cursor = database2.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int NameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int TimeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int WeekIndex = cursor.getColumnIndex(DBHelper.KEY_WEEK);
            int DayIndex = cursor.getColumnIndex(DBHelper.KEY_DAY);
            int MonthIndex = cursor.getColumnIndex(DBHelper.KEY_MONTH);
            int YearIndex = cursor.getColumnIndex(DBHelper.KEY_YEAR);
            int i = 0;

            do {

                nameseven[i] =
                                cursor.getString(TimeIndex) + "x" +
                                cursor.getString(WeekIndex) + "x" +
                                cursor.getString(NameIndex) + "x" +
                                cursor.getString(DayIndex) + " " +
                                _monthru[Integer.parseInt(cursor.getString(MonthIndex)) - 1] + " " +
                                cursor.getString(YearIndex);
                i++;

            }
            while (cursor.moveToNext());
            cursor.moveToLast();
            current_group_textview.setText(cursor.getString(TimeIndex) + "");

        }

        cursor.close();

        for (int i = 0; i < diff_date_int / 7; i++) {
            for (int w = 0; w < 7; w++) {
                int sp = w + (i * 7);
                if (i % 2 == 0) {
                    names_1[sp] = nameseven[0 + (8 * sp)];
                    names_2[sp] = nameseven[2 + (8 * sp)];
                    names_3[sp] = nameseven[4 + (8 * sp)];
                    names_4[sp] = nameseven[6 + (8 * sp)];
                } else {
                    names_1[sp] = nameseven[1 + (8 * sp)];
                    names_2[sp] = nameseven[3 + (8 * sp)];
                    names_3[sp] = nameseven[5 + (8 * sp)];
                    names_4[sp] = nameseven[7 + (8 * sp)];
                }
            }
        }

        viewPagerItemMainPageArrayList = new ArrayList<>();
        for (int i = 0; i < names_1.length; i++) {
            ViewPagerItemMainPage viewPagerItemMainPage = new ViewPagerItemMainPage(names_1[i], names_2[i], names_3[i], names_4[i]);
            viewPagerItemMainPageArrayList.add(viewPagerItemMainPage);
        }
        VPAdapderMainPage vpAdapderMainPage = new VPAdapderMainPage(viewPagerItemMainPageArrayList);
        viewPager2_mainpage.setAdapter(vpAdapderMainPage);
        viewPager2_mainpage.setClipToPadding(false);
        viewPager2_mainpage.setClipChildren(false);
        viewPager2_mainpage.setOffscreenPageLimit(2);
        viewPager2_mainpage.getChildAt(0).setOverScrollMode(viewPager2_mainpage.OVER_SCROLL_NEVER);
        progressBar.setVisibility(View.GONE);


    }

    private void dowloaddata(String url_file) {
        progressBar.setVisibility(View.VISIBLE);
        client = new AsyncHttpClient();
        client.get(url_file, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progressBar.setVisibility(View.GONE);
            }

            public void onSuccess(int statusCode, Header[] headers, File file) {

                if (file != null) {
                    String sizefile = "";
                    try {
                        BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class);
                        sizefile = attr.size() + "";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SQLiteDatabase database1 = dbHelper.getWritableDatabase();
                    Cursor cursor1 = database1.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                    cursor1.moveToLast();
                    int NameIndex1 = cursor1.getColumnIndex(DBHelper.KEY_NAME);


                    try {
                        ZipSecureFile.setMinInflateRatio(0);
                        fis = new FileInputStream(file);
                        workbook = new XSSFWorkbook(fis);
                        XSSFSheet sheet = workbook.getSheetAt(id_sheet);
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

                        for (int d = 0; d < diff_date_int; d++) {


                            for (int s = 0; s < 8; s++) {

                                int i = s + 8 * (d % 7);
                                String name = "null";

                                String day = LocalDate.ofEpochDay(date_start.toEpochDay() + d).getDayOfMonth() + "" + LocalDate.ofEpochDay(date_start.toEpochDay() + d).getDayOfWeek();
                                String month = LocalDate.ofEpochDay(date_start.toEpochDay() + d).getMonth().getValue() + "";
                                String year = LocalDate.ofEpochDay(date_start.toEpochDay() + d).getYear() + "";
                                String week = "null";
                                String time = "null";


                                try {
                                    name = sheet.getRow(i + 8).getCell(id_collumn + 4).toString();
                                    week = sheet.getRow(i + 8).getCell(3).toString();
                                    time = sheet.getRow(i + 8).getCell(2).toString();
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
                        String name = sizefile;

                        contentValues.put(DBHelper.KEY_DAY, "0");
                        contentValues.put(DBHelper.KEY_MONTH, "0");
                        contentValues.put(DBHelper.KEY_YEAR, "0");
                        contentValues.put(DBHelper.KEY_NAME, name);
                        contentValues.put(DBHelper.KEY_TIME, "0");
                        contentValues.put(DBHelper.KEY_WEEK, "0");
                        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                        loaddata();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

        });

    }

}
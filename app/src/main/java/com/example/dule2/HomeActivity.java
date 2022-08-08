package com.example.dule2;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItemArrayList;
    AsyncHttpClient client;
    DBHelper dbHelper = new DBHelper(HomeActivity.this);
    FileInputStream fis;
    XSSFWorkbook workbook;
    ContentValues contentValues = new ContentValues();
    ProgressBar progressBar;
    Integer id_workbook, id_sheet, id_collumn, diff_date_int;
    String text;
    RelativeLayout[] buttons_menu = new RelativeLayout[4];
    RelativeLayout[] set_day_buttons_selector = new RelativeLayout[6];
    TextView[] set_day_buttons_selector_day_text = new TextView[6];
    TextView[] set_day_buttons_selector_month_text = new TextView[6];
    Intent[] intents = new Intent[4];
    String[] urls = new String[4];

    String[] names_1, names_2, names_3, names_4, date_vp, nameseven;


    LocalDate date_start, date_end, date_current;
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

        Log.d("mylog", diff_date_int + " diff " + current_epoch_day + " today");
        names_1 = new String[14 * diff_date_int];
        names_2 = new String[14 * diff_date_int];
        names_3 = new String[14 * diff_date_int];
        names_4 = new String[14 * diff_date_int];
        date_vp = new String[14 * diff_date_int];
        nameseven = new String[58 * diff_date_int];
        set_day_buttons_selector_day_text[0] = findViewById(R.id.day_monday);
        set_day_buttons_selector_day_text[1] = findViewById(R.id.day_tuesday);
        set_day_buttons_selector_day_text[2] = findViewById(R.id.day_wednesday);
        set_day_buttons_selector_day_text[3] = findViewById(R.id.day_thursday);
        set_day_buttons_selector_day_text[4] = findViewById(R.id.day_friday);
        set_day_buttons_selector_day_text[5] = findViewById(R.id.day_saturday);
        set_day_buttons_selector_month_text[0] = findViewById(R.id.month_monday);
        set_day_buttons_selector_month_text[1] = findViewById(R.id.month_tuesday);
        set_day_buttons_selector_month_text[2] = findViewById(R.id.month_wednesday);
        set_day_buttons_selector_month_text[3] = findViewById(R.id.month_thursday);
        set_day_buttons_selector_month_text[4] = findViewById(R.id.month_friday);
        set_day_buttons_selector_month_text[5] = findViewById(R.id.month_saturday);
        set_day_buttons_selector[0] = findViewById(R.id.button_select_day_monday);
        set_day_buttons_selector[1] = findViewById(R.id.button_select_day_tuesday);
        set_day_buttons_selector[2] = findViewById(R.id.button_select_day_wednesday);
        set_day_buttons_selector[3] = findViewById(R.id.button_select_day_thursday);
        set_day_buttons_selector[4] = findViewById(R.id.button_select_day_friday);
        set_day_buttons_selector[5] = findViewById(R.id.button_select_day_saturday);
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
                viewPager2.setCurrentItem(current_epoch_day);
            else
                viewPager2.setCurrentItem(0);


        } catch (Exception e) {
            e.printStackTrace();
        }


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
            @Override
            public void onPageSelected(int position) {

                for(int i = 0;i <6;i++)
                {
                  //  set_day_buttons_selector[i].setEnabled(false);
                    String day_text = LocalDate.ofEpochDay(date_start.toEpochDay() + ((position / 7) * 7) + i).getDayOfMonth() + "";
                    String month_text = _monthru[LocalDate.ofEpochDay(date_start.toEpochDay() + ((position / 7) * 7) + i).getMonthValue()] + "";
                    set_day_buttons_selector_day_text[i].setText(day_text);
                    set_day_buttons_selector_month_text[i].setText(month_text);
                }



/*
                set_day_buttons_selector_day_text[0].setText(LocalDate.ofEpochDay(date_start.toEpochDay() + position - 1).getDayOfMonth() + "");
                set_day_buttons_selector_month_text[0].setText(_monthru[LocalDate.ofEpochDay(date_start.toEpochDay() + position - 1).getMonthValue() - 1] + "");
                set_day_buttons_selector_day_text[0].setTextColor(getColor(R.color.grey));
                set_day_buttons_selector_month_text[0].setTextColor(getColor(R.color.grey));
                set_day_buttons_selector[0].setBackground(getDrawable(R.drawable.clicklayout_grey));
                set_day_buttons_selector[0].setEnabled(true);


                set_day_buttons_selector_day_text[1].setText(LocalDate.ofEpochDay(date_start.toEpochDay() + position).getDayOfMonth() + "");
                set_day_buttons_selector_month_text[1].setText(_monthru[LocalDate.ofEpochDay(date_start.toEpochDay() + position).getMonthValue() - 1] + "");
                set_day_buttons_selector_day_text[1].setTextColor(getColor(R.color.black));
                set_day_buttons_selector_month_text[1].setTextColor(getColor(R.color.black));
                set_day_buttons_selector[1].setBackground(getDrawable(R.drawable.clicklayout));
                set_day_buttons_selector[1].setEnabled(false);


                set_day_buttons_selector_day_text[2].setText(LocalDate.ofEpochDay(date_start.toEpochDay() + position + 1).getDayOfMonth() + "");
                set_day_buttons_selector_month_text[2].setText(_monthru[LocalDate.ofEpochDay(date_start.toEpochDay() + position + 1).getMonthValue() - 1] + "");
                set_day_buttons_selector[2].setBackground(getDrawable(R.drawable.clicklayout_grey));
                set_day_buttons_selector[2].setEnabled(true);
                set_day_buttons_selector_day_text[2].setTextColor(getColor(R.color.grey));
                set_day_buttons_selector_month_text[2].setTextColor(getColor(R.color.grey));
*/
                super.onPageSelected(position);
            }
        });


    }


    private void loaddata() {

        viewPager2 = findViewById(R.id.viewpager);
        SQLiteDatabase database2 = dbHelper.getWritableDatabase();
        Cursor cursor = database2.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int IdIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int NameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int TimeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int WeekIndex = cursor.getColumnIndex(DBHelper.KEY_WEEK);
            int DayIndex = cursor.getColumnIndex(DBHelper.KEY_DAY);
            int MonthIndex = cursor.getColumnIndex(DBHelper.KEY_MONTH);
            int YearIndex = cursor.getColumnIndex(DBHelper.KEY_YEAR);
            int i = 0;

            do {

                nameseven[i] = cursor.getString(TimeIndex) + "x" + cursor.getString(WeekIndex) + "x" + cursor.getString(NameIndex) + "x" + cursor.getString(YearIndex) + "." + cursor.getString(MonthIndex) + "." + cursor.getString(DayIndex);
                i++;

            }
            while (cursor.moveToNext());

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

        viewPagerItemArrayList = new ArrayList<>();
        for (int i = 0; i < names_1.length; i++) {
            ViewPagerItem viewPagerItem = new ViewPagerItem(date_vp[i], names_1[i], names_2[i], names_3[i], names_4[i]);
            viewPagerItemArrayList.add(viewPagerItem);
        }
        VPAdapder vpAdapder = new VPAdapder(viewPagerItemArrayList);
        viewPager2.setAdapter(vpAdapder);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getChildAt(0).setOverScrollMode(viewPager2.OVER_SCROLL_NEVER);
        progressBar.setVisibility(View.GONE);


    }


    // download excel -> convert to dbSQL save


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

                    if (1 != 0) { // добавить сравенение группы    Integer.parseInt(sizefile)
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
                            Log.d("mylog", "success save");
                            loaddata();
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
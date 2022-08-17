package com.example.dule2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.database.sqlite.SQLiteDatabase;

import android.view.View;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    ViewPager2 viewPager2_mainpage;
    ArrayList<ViewPagerItemMainPage> viewPagerItemMainPageArrayList;

    DBHelper dbHelper = new DBHelper(HomeActivity.this);
    ProgressBar progressBar;
    Integer diff_date_int, current_epoch_day;

    RelativeLayout[] buttons_menu = new RelativeLayout[4];
    RelativeLayout calendarButton, disableCalendar;
    CalendarView calendarView;

    TextView current_week_textview, current_group_textview;
    LocalDate date_start, date_end, date_current;

    Intent[] intents = new Intent[4];

    String[] names_1, names_2, names_3, names_4, date_vp, nameseven, nameswithdate;
    String[] _monthru = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
    String[] daysofweeks_string = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};


    @SuppressLint("SetTextI18n")
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
        current_epoch_day = (int) (diff_date_int - ((date_end).toEpochDay() - date_current.toEpochDay()));
        names_1 = new String[14*diff_date_int];
        names_2 = new String[14*diff_date_int];
        names_3 = new String[14*diff_date_int];
        names_4 = new String[14*diff_date_int];
        date_vp = new String[14*diff_date_int];
        nameseven = new String[56];
        nameswithdate = new String[56 * diff_date_int];
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
                    try {
                        intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(intents[finalI], 0);
                        overridePendingTransition(0, 0);
                    } catch (Exception e) {
                        startActivity(intents[finalI]);
                        overridePendingTransition(0, 0);
                        e.printStackTrace();
                    }
                }
            });
        }





        try {
            loaddata();
            if (current_epoch_day < diff_date_int && current_epoch_day > 0)
                viewPager2_mainpage.setCurrentItem(current_epoch_day);
            else
                viewPager2_mainpage.setCurrentItem(0);


        } catch (Exception e) {

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
            for (int i = 0; i < 56; i++) {

                nameseven[i] =
                        cursor.getString(TimeIndex) + "x" +
                                cursor.getString(NameIndex);

                cursor.moveToNext();
            }
            cursor.moveToLast();
            current_group_textview.setText(cursor.getString(TimeIndex) + "");

        }

        cursor.close();
        for (int i = 0; i < nameswithdate.length; i++)
            nameswithdate[i] = nameseven[i % 56] +  "x";


        for (int sp = 0; sp < diff_date_int; sp++) {

            if(sp % 2 == 0)
            for (int i = 0; i < 7; i++) {
                int tmp = i + (sp*7);
                String date_tmp = daysofweeks_string[LocalDate.ofEpochDay(date_start.toEpochDay() + i).getDayOfWeek().getValue() - 1] +
                        "\n" + LocalDate.ofEpochDay(date_start.toEpochDay() + tmp).getDayOfMonth()
                        + " " + _monthru[LocalDate.ofEpochDay(date_start.toEpochDay() + tmp).getMonth().getValue()-1]
                        + " " + LocalDate.ofEpochDay(date_start.toEpochDay() + tmp).getYear() + "x";
                names_1[tmp] = checkweek(nameswithdate[0 + (tmp * 8)] + date_tmp);
                names_2[tmp] = checkweek(nameswithdate[2 + (tmp * 8)] + date_tmp);
                names_3[tmp] = checkweek(nameswithdate[4 + (tmp * 8)] + date_tmp);
                names_4[tmp] = checkweek(nameswithdate[6 + (tmp * 8)] + date_tmp);

            }
            else
            for (int i = 0; i < 7; i++) {
                int tmp = i + (sp*7);

                String date_tmp = daysofweeks_string[LocalDate.ofEpochDay(date_start.toEpochDay() + i).getDayOfWeek().getValue() - 1] +
                        "\n" +  LocalDate.ofEpochDay(date_start.toEpochDay() + tmp).getDayOfMonth()
                        + " " + _monthru[LocalDate.ofEpochDay(date_start.toEpochDay() + tmp).getMonth().getValue()-1]
                        + " " + LocalDate.ofEpochDay(date_start.toEpochDay() + tmp).getYear() + "x";
                names_1[tmp] = checkweek(nameswithdate[1 + (tmp * 8)] + date_tmp );
                names_2[tmp] = checkweek(nameswithdate[3 + (tmp * 8)] + date_tmp );
                names_3[tmp] = checkweek(nameswithdate[5 + (tmp * 8)] + date_tmp );
                names_4[tmp] = checkweek(nameswithdate[7 + (tmp * 8)] + date_tmp );
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


    private String checkweek(String source)
    {
        String returnstring = source;
        for (Integer w = 8; w < 20; w++) {

            int week = current_epoch_day / 7;
            String s1 = w.toString() + " н";
            String s2 = w.toString() + " нед.";
            String s3 = w.toString() + "н";
            if (source.contains(s1) | source.contains(s2) | source.contains(s3)) {
                if (w < week) {
                  //  returnstring = "nullxnullxnullxnull";                                             Не забыть убрать // *заглушка*
                }

            }
        }
            return returnstring;
    }


}
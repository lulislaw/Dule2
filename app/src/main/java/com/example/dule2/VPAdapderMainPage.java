package com.example.dule2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class VPAdapderMainPage extends RecyclerView.Adapter<VPAdapderMainPage.ViewHolder> {

    ArrayList<ViewPagerItemMainPage> viewPagerItemMainPageArrayList;
    private Context context;


    public VPAdapderMainPage(ArrayList<ViewPagerItemMainPage> viewPagerItemMainPageArrayList) {
        this.viewPagerItemMainPageArrayList = viewPagerItemMainPageArrayList;


    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewpageritemmainpage, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerItemMainPage viewPagerItemMainPage = viewPagerItemMainPageArrayList.get(position);


        String[] names = new String[4];
        names[0] = viewPagerItemMainPage.name_1;
        names[1] = viewPagerItemMainPage.name_2;
        names[2] = viewPagerItemMainPage.name_3;
        names[3] = viewPagerItemMainPage.name_4;
        int[] thtime_hours = {
                8, 9, 11, 13, 13, 15, 17, 18, 20
        };
        int[] thtime_min = {
                15, 55, 35, 15, 45, 25, 5, 50, 30
        };
        String[] timedef_dermo = {
                "8.15", "9.55","11.35","13.15", "13.45", "15.25", "17.05","18.50", "20.30"
        };
        int _limitchars = 5;





        for (int i = 0; i < 4; i++) {
            if (names[i] != null) {
                if(names[i].split("x")[2].length() != 4)
                    holder.tcdate.setText(names[i].split("x")[2]);



                if (names[i].split("x")[1].length() < _limitchars)
                    holder._tcblocks[i].setVisibility(View.GONE);
                else {

                    try {
                        holder._tc_th_times[i].setText(names[i].split("x")[3]);
                        if (holder._tc_th_times[i].getText().toString().equals("") || holder._tc_th_times[i].getText().toString().equals(" ")) {
                            holder._tc_th_times[i].setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                    }


                    String s = names[i].split("x")[0];
                    s = s.replaceAll("[- ]", "-");
                    if (s.contains("---"))
                        s = s.replace("--", "");
                    else if (s.contains("--"))
                        s = s.replaceFirst("-", "");
                    holder._tctimes[i].setText(s);

//                    System.out.println(i);
//                    System.out.println(decomposition(names[i].split("x")[1])[2]);

//                    System.out.println(i);
//                    System.out.println((names[i].split("x")[1]));


                    String new_name_new = (names[i].split("x")[1]);

                    if ((names[i].split("x")[1]).contains("Актовый зал /")) {
                        new_name_new = new_name_new.substring(new_name_new.indexOf("/") + 1);
                        new_name_new.trim();
                    }
                    else if ((names[i].split("x")[1]).contains("Актовый зал")) {
                        new_name_new = new_name_new.substring(new_name_new.indexOf("зал") + 3);
                        new_name_new.trim();
                    }
                    System.out.println(i);
                    System.out.println(new_name_new);


                    /*String strung = "the text=text";
                    String s1 = strung.substring(strung.indexOf("=") + 1);
                    s1.trim();

                    System.out.println("String s1 = strung.substring(strung.indexOf(\"=\") + 1);");
                    System.out.println(s1);
*/
                    //decomposition(names[i].split("x")[1])[0] - вывод предметов
                    //decomposition(names[i].split("x")[1])[1] - вывод типа пары
                    //decomposition(names[i].split("x")[1])[2] - должно выводить имена, но выводит говно в том числе

                    holder._tcnames[i].setText(decomposition(new_name_new)[0].replaceAll("[\n]", ""));
                    //holder._tcnames[i].setText(decomposition(names[i].split("x")[1])[0].replaceAll("[\n]", ""));

                    if (decomposition(names[i].split("x")[1])[1].contains("Лекция")) {
                        holder._tctypes[i].setBackgroundResource(R.drawable.background_lesson_type_red);
                        holder._tctypes[i].setText("Лекция");

                    }
                    else if (decomposition(names[i].split("x")[1])[1].contains("Практическое занятие")) {
                        holder._tctypes[i].setBackgroundResource(R.drawable.background_lesson_type_blue);
                        holder._tctypes[i].setText("Практика");

                    }
                    else if (decomposition(names[i].split("x")[1])[1].contains("Лабораторное занятие")) {
                        holder._tctypes[i].setBackgroundResource(R.drawable.background_lesson_type_purple);
                        holder._tctypes[i].setText("Лаба");

                    }

                    else {
                        holder._tctypes[i].setText("Проект");
                        holder._tctypes[i].setBackgroundResource(R.drawable.background_lesson_type_pink);
                    }


                    if (decomposition(names[i].split("x")[1])[2].contains("нед")) {
                        String new_name = names[i].substring(names[i].lastIndexOf(")") + 1);
                        String replace_name = names[i].substring(names[i].lastIndexOf(".") + 1);
                        new_name = new_name.replace(replace_name, "");
                        new_name = new_name.replace("\n", "");
                        String checkFirstLetter = String.valueOf(new_name.charAt(0));
                        if (checkFirstLetter.equals(" ")) {
                            new_name = new_name.replaceFirst(" ", "");
                        }
                        holder._tcteachers[i].setText(new_name);
                    }

                    if (decomposition(names[i].split("x")[1])[2].matches(".*\\d.*"))
                        holder._tcteachers[i].setVisibility(View.GONE);

                    else
                        holder._tcteachers[i].setText(decomposition(names[i].split("x")[1])[2]);

                    /*if (holder._tcteachers[i].getText().toString().contains("Л")
                            || holder._tcteachers[i].getText().toString().contains("нед")
                            || holder._tcteachers[i].getText().toString().matches(".*\\d.*")) {
                        holder._tcteachers[i].setVisibility(View.GONE);
                    }*/

                    ///Экономика и управление инв. крашит при просмотре четной недели, а именно пн и чт
                    //char firstCharOf_tcrooms = decomposition(names[i].split("x")[1])[3].charAt(0);

                    //Если изменить, всё рабоатет вроде. Зачем это?
                    String firstCharOf_tcrooms = decomposition(names[i].split("x")[1])[3];


                    if (decomposition(names[i].split("x")[1])[3].contains("Спортивный комплекс")) {
                        holder._tcrooms[i].setText("СК");
                    } else if ((firstCharOf_tcrooms + "").equals("-")) {
                        holder._tcrooms[i].setText(decomposition(names[i].split("x")[1])[3].substring(1));
                    }
                    else holder._tcrooms[i].setText(decomposition(names[i].split("x")[1])[3]);

                    holder._tcblocks[i].setVisibility(View.VISIBLE);
                    holder._tctypes[i].setVisibility(View.VISIBLE);
                    holder._tcteachers[i].setVisibility(View.VISIBLE);
                    holder._tcrooms[i].setVisibility(View.VISIBLE);

                    /*if (decomposition(names[i].split("x")[1])[1].length() < 3)
                        holder._tctypes[i].setVisibility(View.GONE);*/
                    if (decomposition(names[i].split("x")[1])[2].length() < 3)
                        holder._tcteachers[i].setVisibility(View.GONE);
                    if (decomposition(names[i].split("x")[1])[3].length() < 3)
                        holder._tcrooms[i].setVisibility(View.GONE);


                }
            } else
                holder._tcblocks[i].setVisibility(View.GONE);

            holder._tcteachers[i].setVisibility(View.GONE);

        }



        new CountDownTimer(3600000, 5000){
            Integer _timepar = 0;

            @Override

            public void onTick(long l) {
                Date date = new Date();
                for(int i = 0;i < 4; i++)
                {
                    for(int t=0;t<9;t++)
                    {
                        if(names[i].contains(timedef_dermo[t]))
                            _timepar = (thtime_hours[t] * 60) + thtime_min[t];

                    }


                    Integer difftime_inmin = _timepar - ((date.getHours() * 60) + date.getMinutes());
                    String tmp_th = "Через " + (difftime_inmin / 60) + "ч. " + (difftime_inmin%60) + "м.";
                    Integer abs_difftime_inmin = Math.abs(difftime_inmin);
                    if(difftime_inmin > 0) {
                        if(difftime_inmin < 60)
                            tmp_th = "Через "+ (difftime_inmin%60) + "м.";
                        if(difftime_inmin % 60 == 0)
                            tmp_th = "Через " + (difftime_inmin / 60) + "ч.";
                        if(difftime_inmin <= 10) {
                            holder._tc_th_times[i].setTextColor(Color.RED);
                        }

                    }
                        else {
                            if (abs_difftime_inmin <= 90) {
                                tmp_th = "Идет " + (abs_difftime_inmin / 60) + "ч. " + (abs_difftime_inmin % 60) + "м.";
                                holder._tc_th_times[i].setTextColor(Color.parseColor("#000000"));
                                if (abs_difftime_inmin < 60)
                                    tmp_th = "Идет " + (abs_difftime_inmin % 60) + "м.";
                                if (abs_difftime_inmin % 60 == 0)
                                    tmp_th = "Идет " + (abs_difftime_inmin / 60) + "ч.";
                            }
                            if (abs_difftime_inmin > 90) {
                                tmp_th = " ";
                            }
                        }

                    holder._tc_th_times[i].setText("" + tmp_th);
                        if (holder._tc_th_times[i].getText().toString().equals("") || holder._tc_th_times[i].getText().toString().equals(" ")) {
                            holder._tc_th_times[i].setVisibility(View.GONE);
                        }

                    }


                }


            @Override
            public void onFinish() {

                start();
            }
        }.start();



    }





    @Override
    public int getItemCount() {
        return viewPagerItemMainPageArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView[] _tctimes = new TextView[4];
        TextView[] _tc_th_times = new TextView[4];
        TextView[] _tcnames = new TextView[4];
        TextView[] _tctypes = new TextView[4];
        TextView[] _tcteachers = new TextView[4];
        TextView[] _tcrooms = new TextView[4];
        ConstraintLayout[] _tcblocks = new ConstraintLayout[4];
        TextView tcdate;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tcdate = itemView.findViewById(R.id.Date);
            _tc_th_times[0] = itemView.findViewById(R.id.th_Time_1);
            _tc_th_times[1] = itemView.findViewById(R.id.th_Time_2);
            _tc_th_times[2] = itemView.findViewById(R.id.th_Time_3);
            _tc_th_times[3] = itemView.findViewById(R.id.th_Time_4);
            _tcblocks[0] = itemView.findViewById(R.id.block_1);
            _tcblocks[1] = itemView.findViewById(R.id.block_2);
            _tcblocks[2] = itemView.findViewById(R.id.block_3);
            _tcblocks[3] = itemView.findViewById(R.id.block_4);
            _tctimes[0] = itemView.findViewById(R.id.Time_1);
            _tctimes[1] = itemView.findViewById(R.id.Time_2);
            _tctimes[2] = itemView.findViewById(R.id.Time_3);
            _tctimes[3] = itemView.findViewById(R.id.Time_4);
            _tcnames[0] = itemView.findViewById(R.id.Name);
            _tcnames[1] = itemView.findViewById(R.id.Name_2);
            _tcnames[2] = itemView.findViewById(R.id.Name_3);
            _tcnames[3] = itemView.findViewById(R.id.Name_4);
            _tctypes[0] = itemView.findViewById(R.id.Type_1);
            _tctypes[1] = itemView.findViewById(R.id.Type_2);
            _tctypes[2] = itemView.findViewById(R.id.Type_3);
            _tctypes[3] = itemView.findViewById(R.id.Type_4);
            _tcteachers[0] = itemView.findViewById(R.id.Teacher_1);
            _tcteachers[1] = itemView.findViewById(R.id.Teacher_2);
            _tcteachers[2] = itemView.findViewById(R.id.Teacher_3);
            _tcteachers[3] = itemView.findViewById(R.id.Teacher_4);
            _tcrooms[0] = itemView.findViewById(R.id.Room_1);
            _tcrooms[1] = itemView.findViewById(R.id.Room_2);
            _tcrooms[2] = itemView.findViewById(R.id.Room_3);
            _tcrooms[3] = itemView.findViewById(R.id.Room_4);


        }


    }


    private String[] decomposition(String SPLIT) {

        String[] tempstring = new String[4];
        /*
         *  tempstring[0] - Название
         *  tempstring[1] - Тип
         *  tempstring[2] - Препод
         *  tempstring[3] - Аудитория
         */
        if (SPLIT.contains("(ЛЗ"))
            tempstring[1] = "Лабораторное занятие";
        else if (SPLIT.contains("(ПЗ"))
            tempstring[1] = "Практическое занятие";
        else if (SPLIT.contains("(Л "))
            tempstring[1] = "Лекция";
        else
            tempstring[1] = "";

        for (int le = 0; le < SPLIT.length(); le++) {
            if (SPLIT.charAt(le) == '(') {
                tempstring[0] = SPLIT.substring(0, le - 1);
                break;
            }
        }

        int tempsc = 0;

        for (int le = 0; le < SPLIT.length(); le++) {
            if (SPLIT.charAt(le) == ')') {
                tempsc = le + 2;
                break;
            }
        }
        tempstring[3] = "";
        tempstring[2] = "";
        try {

            if (SPLIT.contains("ЛК-")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 6, SPLIT.length());
                tempstring[2] = SPLIT.substring(tempsc, SPLIT.length() - 7);
            } else if (SPLIT.contains("У-") || SPLIT.contains("А-") || SPLIT.contains("ПА-")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 5, SPLIT.length());
                tempstring[2] = SPLIT.substring(tempsc, SPLIT.length() - 6);
            } else if (SPLIT.contains("этаж")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 9, SPLIT.length());

            } else if (SPLIT.contains("Спортивный комплекс")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 19, SPLIT.length());
            } else
                tempstring[3] = "";


            if (tempstring[2].contains("\n")) {
                tempstring[2] = tempstring[2].substring(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return tempstring;
    }

}

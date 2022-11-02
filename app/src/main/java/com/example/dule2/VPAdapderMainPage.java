package com.example.dule2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class VPAdapderMainPage extends RecyclerView.Adapter<VPAdapderMainPage.ViewHolder> {

    ArrayList<ViewPagerItemMainPage> viewPagerItemMainPageArrayList;


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

        ArrayList<String[]> dayOfNames = new ArrayList<>();

        holder.tcdate.setText(names[0].split("x")[1]);

        for (int i = 0; i < 4; ++i) {


            System.out.println(i);
            System.out.println(names[i]);

           /* System.out.println(names[i].split("s.").length);

            if (names[i].split("s.").length > 2) {
                System.out.println(Arrays.toString(names[i].split("s.")));
                holder._tcnames[i].setText(names[i].split("s.")[1].trim());
            } else
                holder._tcblocks[i].setVisibility(View.GONE);*/



            /*else {
                holder._tcblocks[i].setVisibility(View.GONE);
//                Toast.makeText(holder.itemView.getContext(), "afaf", Toast.LENGTH_SHORT).show();
            }*/

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

        //TextView[] borders = new TextView[4];


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



//            borders[0] = itemView.findViewById(R.id.border_1);
//            borders[1] = itemView.findViewById(R.id.border_2);
//            borders[2] = itemView.findViewById(R.id.border_3);
//            borders[3] = itemView.findViewById(R.id.border_4);

        }


    }


}

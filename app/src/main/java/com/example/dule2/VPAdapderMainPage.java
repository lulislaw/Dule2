package com.example.dule2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerItemMainPage viewPagerItemMainPage = viewPagerItemMainPageArrayList.get(position);


        int _limitchars = 5;

        if (viewPagerItemMainPage.name_1 != null) {
            holder.tcdate.setText(viewPagerItemMainPage.name_1.split("x")[3]);
            if (viewPagerItemMainPage.name_1.split("x")[2].length() < _limitchars)
                holder.tcblock_1.setVisibility(View.GONE);
            else {
                holder.tctime_1.setText(viewPagerItemMainPage.name_1.split("x")[0]);
                holder.tcname_1.setText(decomposition(viewPagerItemMainPage.name_1.split("x")[2])[0]);
                holder.tctype_1.setText(decomposition(viewPagerItemMainPage.name_1.split("x")[2])[1]);
                holder.tcteacher_1.setText(decomposition(viewPagerItemMainPage.name_1.split("x")[2])[2]);
                holder.tcroom_1.setText(decomposition(viewPagerItemMainPage.name_1.split("x")[2])[3]);
                holder.tcblock_1.setVisibility(View.VISIBLE);
                holder.tctype_1.setVisibility(View.VISIBLE);
                holder.tcteacher_1.setVisibility(View.VISIBLE);
                holder.tcroom_1.setVisibility(View.VISIBLE);
                if(decomposition(viewPagerItemMainPage.name_1.split("x")[2])[1].length() < 3)
                    holder.tctype_1.setVisibility(View.GONE);
                if(decomposition(viewPagerItemMainPage.name_1.split("x")[2])[2].length() < 3)
                    holder.tcteacher_1.setVisibility(View.GONE);
                if(decomposition(viewPagerItemMainPage.name_1.split("x")[2])[3].length() < 3)
                    holder.tcroom_1.setVisibility(View.GONE);


            }
        } else
            holder.tcblock_1.setVisibility(View.GONE);


        if (viewPagerItemMainPage.name_2 != null) {
            holder.tcdate.setText(viewPagerItemMainPage.name_1.split("x")[3]);
            if (viewPagerItemMainPage.name_2.split("x")[2].length() < _limitchars)
                holder.tcblock_2.setVisibility(View.GONE);
            else {
                holder.tctime_2.setText(viewPagerItemMainPage.name_2.split("x")[0]);
                holder.tcname_2.setText(decomposition(viewPagerItemMainPage.name_2.split("x")[2])[0]);
                holder.tctype_2.setText(decomposition(viewPagerItemMainPage.name_2.split("x")[2])[1]);
                holder.tcteacher_2.setText(decomposition(viewPagerItemMainPage.name_2.split("x")[2])[2]);
                holder.tcroom_2.setText(decomposition(viewPagerItemMainPage.name_2.split("x")[2])[3]);
                holder.tcblock_2.setVisibility(View.VISIBLE);
                holder.tctype_2.setVisibility(View.VISIBLE);
                holder.tcteacher_2.setVisibility(View.VISIBLE);
                holder.tcroom_2.setVisibility(View.VISIBLE);
                if(decomposition(viewPagerItemMainPage.name_2.split("x")[2])[1].length() < 3)
                    holder.tctype_2.setVisibility(View.GONE);
                if(decomposition(viewPagerItemMainPage.name_2.split("x")[2])[2].length() < 3)
                    holder.tcteacher_2.setVisibility(View.GONE);
                if(decomposition(viewPagerItemMainPage.name_2.split("x")[2])[3].length() < 3)
                    holder.tcroom_2.setVisibility(View.GONE);
            }
        } else
            holder.tcblock_2.setVisibility(View.GONE);


        if (viewPagerItemMainPage.name_3 != null) {
            holder.tcdate.setText(viewPagerItemMainPage.name_1.split("x")[3]);
            if (viewPagerItemMainPage.name_3.split("x")[2].length() < _limitchars)
                holder.tcblock_3.setVisibility(View.GONE);
            else {
                holder.tctime_3.setText(viewPagerItemMainPage.name_3.split("x")[0]);
                holder.tcname_3.setText(decomposition(viewPagerItemMainPage.name_3.split("x")[2])[0]);
                holder.tctype_3.setText(decomposition(viewPagerItemMainPage.name_3.split("x")[2])[1]);
                holder.tcteacher_3.setText(decomposition(viewPagerItemMainPage.name_3.split("x")[2])[2]);
                holder.tcroom_3.setText(decomposition(viewPagerItemMainPage.name_3.split("x")[2])[3]);
                holder.tcblock_3.setVisibility(View.VISIBLE);
                holder.tctype_3.setVisibility(View.VISIBLE);
                holder.tcteacher_3.setVisibility(View.VISIBLE);
                holder.tcroom_3.setVisibility(View.VISIBLE);
                if(decomposition(viewPagerItemMainPage.name_3.split("x")[2])[1].length() < 3)
                    holder.tctype_3.setVisibility(View.GONE);
                if(decomposition(viewPagerItemMainPage.name_3.split("x")[2])[2].length() < 3)
                    holder.tcteacher_3.setVisibility(View.GONE);
                if(decomposition(viewPagerItemMainPage.name_3.split("x")[2])[3].length() < 3)
                    holder.tcroom_3.setVisibility(View.GONE);
            }
        } else
            holder.tcblock_3.setVisibility(View.GONE);


        if (viewPagerItemMainPage.name_4 != null) {
            holder.tcdate.setText(viewPagerItemMainPage.name_1.split("x")[3]);
            if (viewPagerItemMainPage.name_4.split("x")[2].length() < _limitchars)
                holder.tcblock_4.setVisibility(View.GONE);
            else {
                holder.tctime_4.setText(viewPagerItemMainPage.name_4.split("x")[0]);
                holder.tcname_4.setText(decomposition(viewPagerItemMainPage.name_4.split("x")[2])[0]);
                holder.tctype_4.setText(decomposition(viewPagerItemMainPage.name_4.split("x")[2])[1]);
                holder.tcteacher_4.setText(decomposition(viewPagerItemMainPage.name_4.split("x")[2])[2]);
                holder.tcroom_4.setText(decomposition(viewPagerItemMainPage.name_4.split("x")[2])[3]);
                holder.tcblock_4.setVisibility(View.VISIBLE);
                holder.tctype_4.setVisibility(View.VISIBLE);
                holder.tcteacher_4.setVisibility(View.VISIBLE);
                holder.tcroom_4.setVisibility(View.VISIBLE);
                if(decomposition(viewPagerItemMainPage.name_4.split("x")[2])[1].length() < 3)
                    holder.tctype_4.setVisibility(View.GONE);
                if(decomposition(viewPagerItemMainPage.name_4.split("x")[2])[2].length() < 3)
                    holder.tcteacher_4.setVisibility(View.GONE);
                if(decomposition(viewPagerItemMainPage.name_4.split("x")[2])[3].length() < 3)
                    holder.tcroom_4.setVisibility(View.GONE);
            }
        } else {
            holder.tcblock_4.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {
        return viewPagerItemMainPageArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tctime_1, tctime_2, tctime_3, tctime_4;
        TextView tcname_1, tcname_2, tcname_3, tcname_4;
        TextView tctype_1, tctype_2, tctype_3, tctype_4;
        TextView tcteacher_1, tcteacher_2, tcteacher_3, tcteacher_4;
        TextView tcroom_1, tcroom_2, tcroom_3, tcroom_4;
        LinearLayout tcblock_1, tcblock_2, tcblock_3, tcblock_4;
        TextView tcdate;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tcdate = itemView.findViewById(R.id.Date);
            tcblock_1 = itemView.findViewById(R.id.block_1);
            tcblock_2 = itemView.findViewById(R.id.block_2);
            tcblock_3 = itemView.findViewById(R.id.block_3);
            tcblock_4 = itemView.findViewById(R.id.block_4);
            tctime_1 = itemView.findViewById(R.id.Time_1);
            tctime_2 = itemView.findViewById(R.id.Time_2);
            tctime_3 = itemView.findViewById(R.id.Time_3);
            tctime_4 = itemView.findViewById(R.id.Time_4);
            tcname_1 = itemView.findViewById(R.id.Name_1);
            tcname_2 = itemView.findViewById(R.id.Name_2);
            tcname_3 = itemView.findViewById(R.id.Name_3);
            tcname_4 = itemView.findViewById(R.id.Name_4);
            tctype_1 = itemView.findViewById(R.id.Type_1);
            tctype_2 = itemView.findViewById(R.id.Type_2);
            tctype_3 = itemView.findViewById(R.id.Type_3);
            tctype_4 = itemView.findViewById(R.id.Type_4);
            tcteacher_1 = itemView.findViewById(R.id.Teacher_1);
            tcteacher_2 = itemView.findViewById(R.id.Teacher_2);
            tcteacher_3 = itemView.findViewById(R.id.Teacher_3);
            tcteacher_4 = itemView.findViewById(R.id.Teacher_4);
            tcroom_1 = itemView.findViewById(R.id.Room_1);
            tcroom_2 = itemView.findViewById(R.id.Room_2);
            tcroom_3 = itemView.findViewById(R.id.Room_3);
            tcroom_4 = itemView.findViewById(R.id.Room_4);


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


            if(tempstring[2].contains("\n"))
            {
                tempstring[2] = tempstring[2].substring(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return tempstring;
    }

}

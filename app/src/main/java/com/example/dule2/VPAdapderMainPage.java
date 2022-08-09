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
                holder.tcname_1.setText(viewPagerItemMainPage.name_1.split("x")[2]);
                holder.tcblock_1.setVisibility(View.VISIBLE);
            }
        } else
            holder.tcblock_1.setVisibility(View.GONE);


        if (viewPagerItemMainPage.name_2 != null) {
            holder.tcdate.setText(viewPagerItemMainPage.name_1.split("x")[3]);
            if (viewPagerItemMainPage.name_2.split("x")[2].length() < _limitchars)
                holder.tcblock_2.setVisibility(View.GONE);
            else {
                holder.tctime_2.setText(viewPagerItemMainPage.name_2.split("x")[0]);
                holder.tcname_2.setText(viewPagerItemMainPage.name_2.split("x")[2]);
                holder.tcblock_2.setVisibility(View.VISIBLE);
            }
        } else
            holder.tcblock_2.setVisibility(View.GONE);


        if (viewPagerItemMainPage.name_3 != null) {
            holder.tcdate.setText(viewPagerItemMainPage.name_1.split("x")[3]);
            if (viewPagerItemMainPage.name_3.split("x")[2].length() < _limitchars)
                holder.tcblock_3.setVisibility(View.GONE);
            else {
                holder.tctime_3.setText(viewPagerItemMainPage.name_3.split("x")[0]);
                holder.tcname_3.setText(viewPagerItemMainPage.name_3.split("x")[2]);
                holder.tcblock_3.setVisibility(View.VISIBLE);
            }
        } else
            holder.tcblock_3.setVisibility(View.GONE);


        if (viewPagerItemMainPage.name_4 != null) {
            holder.tcdate.setText(viewPagerItemMainPage.name_1.split("x")[3]);
            if (viewPagerItemMainPage.name_4.split("x")[2].length() < _limitchars)
                holder.tcblock_4.setVisibility(View.GONE);
            else {
                holder.tctime_4.setText(viewPagerItemMainPage.name_4.split("x")[0]);
                holder.tcname_4.setText(viewPagerItemMainPage.name_4.split("x")[2]);
                holder.tcblock_4.setVisibility(View.VISIBLE);
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

}

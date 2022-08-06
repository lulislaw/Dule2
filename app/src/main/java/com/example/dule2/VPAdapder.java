package com.example.dule2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VPAdapder extends RecyclerView.Adapter<VPAdapder.ViewHolder> {

    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    public VPAdapder(ArrayList<ViewPagerItem> viewPagerItemArrayList) {
        this.viewPagerItemArrayList = viewPagerItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewpageritem,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerItem viewPagerItem = viewPagerItemArrayList.get(position);

        holder.tcweek.setText(viewPagerItem.week);
        holder.tcdate.setText(viewPagerItem.date);
        holder.tctime_1.setText(viewPagerItem.time_1);
        holder.tctime_2.setText(viewPagerItem.time_2);
        holder.tctime_3.setText(viewPagerItem.time_3);
        holder.tctime_4.setText(viewPagerItem.time_4);
        holder.tcname_1.setText(viewPagerItem.name_1);
        holder.tcname_2.setText(viewPagerItem.name_2);
        holder.tcname_3.setText(viewPagerItem.name_3);
        holder.tcname_4.setText(viewPagerItem.name_4);
        holder.tctype_1.setText(viewPagerItem.type_1);
        holder.tctype_2.setText(viewPagerItem.type_2);
        holder.tctype_3.setText(viewPagerItem.type_3);
        holder.tctype_4.setText(viewPagerItem.type_4);
        holder.tcteacher_1.setText(viewPagerItem.teacher_1);
        holder.tcteacher_2.setText(viewPagerItem.teacher_2);
        holder.tcteacher_3.setText(viewPagerItem.teacher_3);
        holder.tcteacher_4.setText(viewPagerItem.teacher_4);
        holder.tcroom_1.setText(viewPagerItem.room_1);
        holder.tcroom_2.setText(viewPagerItem.room_2);
        holder.tcroom_3.setText(viewPagerItem.room_3);
        holder.tcroom_4.setText(viewPagerItem.room_4);




        if(viewPagerItem.name_1.length() < 2)
            holder.tcblock_1.setVisibility(View.GONE);
        if(viewPagerItem.name_2.length() < 2)
            holder.tcblock_2.setVisibility(View.GONE);
        if(viewPagerItem.name_3.length() < 2)
            holder.tcblock_3.setVisibility(View.GONE);
        if(viewPagerItem.name_4.length() < 2)
            holder.tcblock_4.setVisibility(View.GONE);






    }

    @Override
    public int getItemCount() {
        return viewPagerItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
            TextView tctime_1,tctime_2,tctime_3,tctime_4;
            TextView tcname_1,tcname_2,tcname_3,tcname_4;
            TextView tctype_1,tctype_2,tctype_3,tctype_4;
            TextView tcteacher_1,tcteacher_2,tcteacher_3,tcteacher_4;
            TextView tcroom_1,tcroom_2,tcroom_3,tcroom_4;
            LinearLayout tcblock_1,tcblock_2,tcblock_3,tcblock_4;
            TextView tcweek, tcdate;



        public ViewHolder(@NonNull View itemView)
        {

            super(itemView);
            tcweek = itemView.findViewById(R.id.Week);
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

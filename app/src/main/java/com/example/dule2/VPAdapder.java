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


        int _limitchars = 10;


        if(viewPagerItem.name_1 != null) {
            if (viewPagerItem.name_1.length() < _limitchars)
                holder.tcblock_1.setVisibility(View.GONE);
            else
            {
                holder.tctime_1.setText(viewPagerItem.name_1.split("x")[0]);
                holder.tcname_1.setText(viewPagerItem.name_1.split("x")[2]);
                holder.tcdate.setText(viewPagerItem.name_1.split("x")[3]);
            }
        }else
            holder.tcblock_1.setVisibility(View.GONE);


        if(viewPagerItem.name_2 != null) {
            if (viewPagerItem.name_2.length() < _limitchars)
                holder.tcblock_2.setVisibility(View.GONE);
            else
            {
                holder.tctime_2.setText(viewPagerItem.name_2.split("x")[0]);
                holder.tcname_2.setText(viewPagerItem.name_2.split("x")[2]);
                holder.tcdate.setText(viewPagerItem.name_1.split("x")[3]);
            }
        }else
            holder.tcblock_2.setVisibility(View.GONE);


        if(viewPagerItem.name_3 != null) {
            if (viewPagerItem.name_3.length() < _limitchars)
                holder.tcblock_3.setVisibility(View.GONE);
            else
            {
                holder.tctime_3.setText(viewPagerItem.name_3.split("x")[0]);
                holder.tcname_3.setText(viewPagerItem.name_3.split("x")[2]);
                holder.tcdate.setText(viewPagerItem.name_1.split("x")[3]);
            }
        }else
            holder.tcblock_3.setVisibility(View.GONE);


        if(viewPagerItem.name_4 != null) {
            if (viewPagerItem.name_4.length() < _limitchars)
                holder.tcblock_4.setVisibility(View.GONE);
            else
            {
                holder.tctime_4.setText(viewPagerItem.name_4.split("x")[0]);
                holder.tcname_4.setText(viewPagerItem.name_4.split("x")[2]);
                holder.tcdate.setText(viewPagerItem.name_1.split("x")[3]);
            }
        }else
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
            TextView tcdate;



        public ViewHolder(@NonNull View itemView)
        {

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

package com.example.zimpelman.finalprojectcit399;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Christopher on 5/3/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder>{

    private List<Task> taskList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvMain);
            date = (TextView) view.findViewById(R.id.tvDate);

        }
    }


    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        long milliseconds = task.getTime();
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
        String ampm = "am";
        String extra = "";
        if(hours > 12){
            ampm = "pm";
            hours -= 12;
        }
        if(hours == 0){
            hours = 12;
        }
        if(minutes < 10){
            extra = "0";
        }

        holder.name.setText(task.getName() + " " + hours + ":" + extra + minutes + ampm);
        holder.date.setText(task.getCal().get(Calendar.YEAR) + "/" + (task.getCal().get(Calendar.MONTH)+1) + "/" + task.getCal().get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}

package com.example.zimpelman.finalprojectcit399;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Christopher on 5/3/2017.
 */

public class TaskDisplayActivity extends AppCompatActivity {

    EditText etTaskName;
    TextView tvDate;
    TextView tvTime;



    Button btnBack;

    TaskDB taskDB;
    Task curTask;

    int userId, taskId;
    int mHour, mMinute;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        etTaskName = (EditText)findViewById(R.id.etTaskName);
        tvTime = (TextView)findViewById(R.id.tvEditTime);
        tvDate = (TextView)findViewById(R.id.tvEditDate);

        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("User");
        taskId = extras.getInt("Task");



        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), UserAlarms.class);
                i.putExtra("User", userId);
                startActivity(i);
            }
        });

        taskDB = new TaskDB(this);
        List<Task> tempList = taskDB.getAllTasks();
        int count = 1;
        for(Task t : tempList){
            if(count == taskId){
                curTask = t;
            }
            count++;
        }
        etTaskName.setText(curTask.getName());

        long milliseconds = curTask.getTime();
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

        tvTime.setText(hours + ":" + extra + minutes + ampm);
        tvTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mHour=0;
                mMinute=0;
                TimePickerDialog timeDialog = new TimePickerDialog(TaskDisplayActivity.this, mTimeSetListener, mHour, mMinute, false);
                timeDialog.show();
            }
        });

        tvDate.setText(curTask.getCal().get(Calendar.YEAR) + "/" + curTask.getCal().get(Calendar.MONTH) + "/" + curTask.getCal().get(Calendar.DAY_OF_MONTH));
        tvDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new DatePickerDialog(TaskDisplayActivity.this, date, cal
                        .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    TimePickerDialog.OnTimeSetListener mTimeSetListener =  new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker v, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, Calendar.YEAR);
            c.set(Calendar.MONTH, Calendar.MONTH);
            c.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH);
            c.set(Calendar.HOUR_OF_DAY, mHour);
            c.set(Calendar.MINUTE, mMinute);
            c.set(Calendar.SECOND, 0);

            Date now = new Date(System.currentTimeMillis());
            long nowL = ((now.getHours() * 60 + now.getMinutes()) * 60 + now.getSeconds()) * 1000;

            long timeInMills = (long) (mHour * 60 + mMinute) * 60000;
            curTask.setTime(timeInMills);
            long milliseconds = curTask.getTime();
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

            tvTime.setText(hours + ":" + extra + minutes + ampm);
        }
    };

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            curTask.setCal(cal.getTimeInMillis());
            tvDate.setText(curTask.getCal().get(Calendar.YEAR) + "/" + (curTask.getCal().get(Calendar.MONTH)+1) + "/" + curTask.getCal().get(Calendar.DAY_OF_MONTH));
        }

    };
}

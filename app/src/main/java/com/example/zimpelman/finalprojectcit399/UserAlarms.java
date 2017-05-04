package com.example.zimpelman.finalprojectcit399;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Christopher on 5/3/2017.
 */

public class UserAlarms extends AppCompatActivity {

    RecyclerView rvTasks;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter tAdapter;
    private TaskDB taskDB;
    String taskName;
    Button btnLogout;
    Button btnAdd;
    private int id = 0;
    int mHour = 0;
    int mMinute = 0;
    Calendar cal = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getTaskName();
            }
        });

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("User");


        taskDB = new TaskDB(this);

        rvTasks = (RecyclerView)findViewById(R.id.rvTasks);
        tAdapter = new TaskAdapter(taskList);
        RecyclerView.LayoutManager aLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvTasks.setLayoutManager(aLayoutManager);
        rvTasks.setItemAnimator(new DefaultItemAnimator());
        rvTasks.setAdapter(tAdapter);
        List<Task> tempList = taskDB.getAllTasks();
        for(Task t : tempList){
            if(t.getUserId() == id){
                taskList.add(t);
            }
        }
        tAdapter.notifyDataSetChanged();

        rvTasks.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvTasks, new ClickListener(){
            @Override
            public void onClick(View view, int position){
                Intent i = new Intent(getApplicationContext(), TaskDisplayActivity.class);
                i.putExtra("Task", position+1);
                i.putExtra("User", id);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void getTaskName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Task Name: ");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                taskName = input.getText().toString();
                new DatePickerDialog(UserAlarms.this, date, cal
                        .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        builder.show();
    }

    TimePickerDialog.OnTimeSetListener mTimeSetListener =  new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker v, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
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
            Task newTask = new Task(taskName, cal, timeInMills, id);
            taskList.add(newTask);
            taskDB.addTask(newTask);
            tAdapter.notifyDataSetChanged();
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
            mHour=0;
            mMinute=0;
            TimePickerDialog timeDialog = new TimePickerDialog(UserAlarms.this, mTimeSetListener, mHour, mMinute, false);
            timeDialog.show();
        }

    };

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

}

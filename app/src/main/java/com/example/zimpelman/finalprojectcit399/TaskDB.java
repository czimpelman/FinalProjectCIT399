package com.example.zimpelman.finalprojectcit399;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 5/3/2017.
 */

public class TaskDB extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "taskDatabase";
    private static final String TASK_TABLE = "taskTable";

    //column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CAL = "cal";
    private static final String KEY_TIME = "time";
    private static final String KEY_USERID = "userID";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LONGI = "longi";


    public TaskDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TASK_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT,"
                + KEY_CAL + " LONG,"
                + KEY_TIME + " LONG,"
                + KEY_USERID + " INTEGER,"
                + KEY_LAT + " FLOAT,"
                + KEY_LONGI + " FLOAT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(db);
    }

    public void deleteDB(Context context){
        context.deleteDatabase(DB_NAME);
    }

    //CRUD

    //Add new task
    void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_CAL, task.getCal().getTimeInMillis());
        values.put(KEY_TIME, task.getTime());
        values.put(KEY_USERID, task.getUserId());
        values.put(KEY_LAT, 152.522);
        values.put(KEY_LONGI, 152.522);
        db.insert(TASK_TABLE, null, values);
        db.close();
    }

    // Getting single task
    Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TASK_TABLE, new String[] { KEY_ID,
                        KEY_NAME, KEY_CAL, KEY_TIME, KEY_USERID, KEY_LAT, KEY_LONGI }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getLong(2), cursor.getLong(3), cursor.getInt(4), cursor.getFloat(5), cursor.getFloat(6));
        // return user
        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TASK_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Task task = new Task();
                task.setTaskId(cursor.getInt(0));
                task.setName(cursor.getString(1));
                task.setCal(cursor.getLong(2));
                task.setTime(cursor.getLong(3));
                task.setUserId(cursor.getInt(4));
                task.setLatLong(cursor.getFloat(5), cursor.getFloat(6));
                // Adding task to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return task list
        return taskList;
    }

    // Updating single task
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_CAL, task.getCal().getTimeInMillis());
        values.put(KEY_TIME, task.getTime());
        values.put(KEY_USERID, task.getUserId());

        // updating row
        return db.update(TASK_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getTaskId()) });
    }

    // Deleting single task
    public void deleteUser(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASK_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(task.getUserId()) });
        db.close();
    }

    // Getting tasks Count
    public int getTaskCount() {
        String countQuery = "SELECT  * FROM " + TASK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}

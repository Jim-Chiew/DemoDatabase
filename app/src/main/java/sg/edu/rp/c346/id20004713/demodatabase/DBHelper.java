package sg.edu.rp.c346.id20004713.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TableLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //Start version is 1
    //increment by 1 whenever db schema changes
    private static final int DATABASE_VER = 2;
    //Filename of the database
    private static final String DATABASE_NAME = "task.db";

    //Initialising for onCreate()
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);  //Modified for ease of use
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //We need to create the database here
        //if the database do not already exist it will create one from here

        String createTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT )";

        db.execSQL(createTableSql);
        Log.i("info", "created table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int date) {
        //Drop the tale id existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

        //Create table(s) again
        onCreate(db);
    }

    public void insertTask(String description, String date){

        //Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        //We use contentValue object to store the value for the db operation
        ContentValues value = new ContentValues();
        //Store the column name as key and description as value
        value.put(COLUMN_DESCRIPTION, description);
        //Store the column name as key and the date as value
        value.put(COLUMN_DATE, date);
        //insert the row into the table
        db.insert(TABLE_TASK, null, value);
        //close the data connection
        db.close();
    }

    public ArrayList<String> getTaskContent(){
        // Create an ArrayList that holds String objects
        ArrayList<String> tasks = new ArrayList<String>();
        // Select all the tasks' description
        String selectQuery = "SELECT " + COLUMN_DESCRIPTION
                + " FROM " + TABLE_TASK;

        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        // Run the SQL query and get back the Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);

        // moveToFirst() moves to first row, null if no records
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row
            //  and returns true; moveToNext() returns false
            //  when no more next row to move to
            do {
                // Add the task content to the ArrayList object
                //  getString(0) retrieves first column data
                //  getString(1) return second column data
                //  getInt(0) if data is an integer value
                tasks.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return tasks;
    }

    public ArrayList<Task> getTasks(int ascOrDec) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_DESCRIPTION + ", "
                + COLUMN_DATE
                + " FROM " + TABLE_TASK
                + " ORDER BY " + COLUMN_DESCRIPTION + " ";

        if(ascOrDec == 0){
            selectQuery += "ASC;";
        } else {
            selectQuery += "DESC;";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                String date = cursor.getString(2);
                Task obj = new Task(id, description, date);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
}

package com.example.goseppe.androidproject_jb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by goseppe on 2/15/2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;


    public DBHelper(Context context) {
        super(context, "mymovies.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                + DBConstants.TABLE_NAME + "("
                + DBConstants.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConstants.COLUMN_SUBJECT + " TEXT NOT NULL, "
                + DBConstants.COLUMN_BODY + " TEXT, "
                + DBConstants.COLUMN_URL + " TEXT, "
                + DBConstants.COLUMN_IMAGE + " TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + DBConstants.TABLE_NAME);
        onCreate(db);

    }

    public void onDeleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM *"+DBConstants.TABLE_NAME); //delete all rows in a table
        //db.close();
    }


    public void insertData(String subject, String body, String url, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.COLUMN_SUBJECT, subject);
        contentValues.put(DBConstants.COLUMN_BODY, body);
        contentValues.put(DBConstants.COLUMN_URL, url);
        contentValues.put(DBConstants.COLUMN_IMAGE, image);
        db.insert(DBConstants.TABLE_NAME, null,contentValues);
    }

    public void updateData(int id,String subject, String body, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.COLUMN_SUBJECT, subject);
        contentValues.put(DBConstants.COLUMN_BODY, body);
        contentValues.put(DBConstants.COLUMN_URL, url);
       // contentValues.put(DBConstants.COLUMN_IMAGE, image);
       db.update(DBConstants.TABLE_NAME, contentValues, "_id = ?", new String[]{"" + id});
    }


    }



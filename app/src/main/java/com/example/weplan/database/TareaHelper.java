package com.example.weplan.database;

import static com.example.weplan.database.TareaContract.SQL_CREATE_ENTRIES;
import static com.example.weplan.database.TareaContract.SQL_CREATE_ENTRIES;
import static com.example.weplan.database.TareaContract.SQL_CREATE_ENTRIES_P;
import static com.example.weplan.database.TareaContract.SQL_DELETE_ENTRIES;
import static com.example.weplan.database.TareaContract.SQL_DELETE_ENTRIES_P;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TareaHelper extends SQLiteOpenHelper {
   public static final int DATABASE_VERSION=2;
   public static final String DATABASE_NAME="Tasks.db";

    public TareaHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES_P);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES_P);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

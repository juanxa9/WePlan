package com.example.weplan.database;

import android.provider.BaseColumns;

public class TareaContract {

    private TareaContract(){

    }

    public static class TareaEntry implements BaseColumns {
        public static final String COLUMN_TASKNAME = "taskname";
        public static final String TABLE_NAME="Tasks";
        public static final String COLUMN_DONE = "isdone";
        public static final String COLUMN_DATE ="date";

    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TareaEntry.TABLE_NAME + "("
                +TareaEntry._ID + " INTEGER PRIMARY KEY,"
                +TareaEntry.COLUMN_TASKNAME + " TEXT,"
                +TareaEntry.COLUMN_DONE + " INTEGER,"
                +TareaEntry.COLUMN_DATE + " TEXT" + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS "+ TareaEntry.TABLE_NAME;
}

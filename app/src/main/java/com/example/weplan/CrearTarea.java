package com.example.weplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;

import com.example.weplan.database.TareaContract;
import com.example.weplan.database.TareaHelper;

import java.util.Calendar;
import java.util.Date;

public class CrearTarea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);
    }

    public void writeToDataBase(View view){
        String app = getResources().getString(R.string.app_name);

        TareaHelper dbHelper = new TareaHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EditText editText = (EditText) findViewById(R.id.et_taskName);
        String name = editText.getText().toString();

        Date currentTime = Calendar.getInstance().getTime();
        String stringTime = currentTime.toString();

        ContentValues values = new ContentValues();
        values.put(TareaContract.TareaEntry.COLUMN_TASKNAME, name);
        values.put(TareaContract.TareaEntry.COLUMN_DONE, 0);
        values.put(TareaContract.TareaEntry.COLUMN_DATE, stringTime);

        long newRowId = db.insert(TareaContract.TareaEntry.TABLE_NAME, null, values);
        Intent intent = new Intent(this, TareasParaHoy.class);
        startActivity(intent);
    }
}
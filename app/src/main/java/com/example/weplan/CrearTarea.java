package com.example.weplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;

import com.example.weplan.database.TareaContract;
import com.example.weplan.database.TareaHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CrearTarea extends AppCompatActivity {

    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);
        date = getIntent().getStringExtra("adddate");
        TextInputEditText editText = (TextInputEditText) findViewById(R.id.et_taskName);
        TextInputLayout layoutText = (TextInputLayout) findViewById(R.id.tv_addTaskTitle);

        MaterialButton anyadirtarea = findViewById(R.id.bt_saveTaskName);
        anyadirtarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(editText.getText().toString())){
                    layoutText.setError("Tienes que añadir una tarea");
                }
                else{
                    String name = editText.getText().toString();
                    writeToDataBase(name);
                    layoutText.setError(null);
                }

            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    layoutText.setError(null); //Clear the error

                }
                return false;
            }
        });


    }

    public void writeToDataBase(String name){
        String app = getResources().getString(R.string.app_name);

        TareaHelper dbHelper = new TareaHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(TareaContract.TareaEntry.COLUMN_TASKNAME, name);
        values.put(TareaContract.TareaEntry.COLUMN_DONE, 0);
        values.put(TareaContract.TareaEntry.COLUMN_DATE, date);

        long newRowId = db.insert(TareaContract.TareaEntry.TABLE_NAME, null, values);
        Intent intent = new Intent(this, TareasParaHoy.class);
        startActivity(intent);
    }
}
package com.example.weplan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;

import com.example.weplan.database.TareaContract;
import com.example.weplan.database.TareaHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CrearTareaDiaSeleccionado extends AppCompatActivity {

    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crear_tarea);
        date = getIntent().getStringExtra("dateadd");
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
                    //CalendarView calendario = (CalendarView) findViewById(R.id.cv_calendar);
                    //calendario
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
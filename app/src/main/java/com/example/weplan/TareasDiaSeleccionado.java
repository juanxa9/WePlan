package com.example.weplan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weplan.adapters.AdapterTareasHoy;
import com.example.weplan.database.TareaContract;
import com.example.weplan.database.TareaHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TareasDiaSeleccionado extends AppCompatActivity {
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_para_hoy);
        displayDatabase();

         date = getIntent().getStringExtra("selected_date");





        //crear una tarea nueva para el dia seleccionado
        FloatingActionButton nuevatarea = (FloatingActionButton) findViewById(R.id.anyadirnuevatarea);
        nuevatarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TareasDiaSeleccionado.this, CrearTareaDiaSeleccionado.class);
                intent.putExtra("dateadd", date);
                startActivity(intent);
            }
        });

        Button volver = (Button) findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(TareasDiaSeleccionado.this, MainActivity.class);
                startActivity(intent2);
            }
        });

    }

    public void displayDatabase(){
        TareaHelper dbHelper = new TareaHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                TareaContract.TareaEntry.COLUMN_TASKNAME,
                TareaContract.TareaEntry.COLUMN_DONE
        };



        String selection = TareaContract.TareaEntry.COLUMN_DATE + " = ?";
        String[] selectionArgs = {date};

        Cursor cursor = db.query(
                TareaContract.TareaEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        //setup cursor adapter
        AdapterTareasHoy adapter = new AdapterTareasHoy();
        adapter.setCursor(cursor);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_tareas_hoy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}


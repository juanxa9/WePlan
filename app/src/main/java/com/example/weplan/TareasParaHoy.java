package com.example.weplan;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;

import com.example.weplan.adapters.AdapterTareasHoy;
import com.example.weplan.database.TareaContract;
import com.example.weplan.database.TareaHelper;

public class TareasParaHoy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_para_hoy);
        displayDatabase();

    }

    public void displayDatabase(){
        TareaHelper dbHelper = new TareaHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                TareaContract.TareaEntry.COLUMN_TASKNAME,
                TareaContract.TareaEntry.COLUMN_DONE
        };

        Date currentTime = Calendar.getInstance().getTime();
        String stringTime = currentTime.toString();

        String selection = TareaContract.TareaEntry.COLUMN_DATE + " = ?";
        String[] selectionArgs = {stringTime};

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
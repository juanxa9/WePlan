package com.example.weplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.weplan.database.TareaContract;
import com.example.weplan.database.TareaHelper;

public class PuntuarDia extends AppCompatActivity {

    String dia_puntuado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuar_dia);
        dia_puntuado = getIntent().getStringExtra("dia_puntuar");

        String className = getIntent().getStringExtra("Class");
        if(className.equals("TareasSeleccion")){
            dia_puntuado = getIntent().getStringExtra("dia_para_puntuar");
        }
        else{
            dia_puntuado = getIntent().getStringExtra("dia_puntuar");
        }


        ImageButton mal = (ImageButton) findViewById(R.id.mal);
        ImageButton regular = (ImageButton) findViewById(R.id.regular);
        ImageButton excelente = (ImageButton) findViewById(R.id.excelente);

        mal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               writePuntuacionToDataBase(dia_puntuado, 1);
               finish();

            }
        });

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writePuntuacionToDataBase(dia_puntuado, 2);
                finish();

            }
        });

        excelente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writePuntuacionToDataBase(dia_puntuado, 3);
                finish();

            }
        });
    }

    public void writePuntuacionToDataBase(String date, int puntos){
        String app = getResources().getString(R.string.app_name);

        TareaHelper dbHelper = new TareaHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(TareaContract.PuntuacionEntry.COLUMN_DATE, date);
        values.put(TareaContract.PuntuacionEntry.COLUMN_PUNTOS, puntos);

        long newRowId = db.insert(TareaContract.PuntuacionEntry.TABLE_NAME, null, values);

    }


}


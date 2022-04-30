package com.example.weplan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
    private AdapterTareasHoy adapter = new AdapterTareasHoy();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_para_hoy);
        date = getIntent().getStringExtra("selected_date");
        displayDatabase();


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

        //el boton para volver al main activity, que luego se sustituirá por deslizamiento
        Button volver = (Button) findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(TareasDiaSeleccionado.this, MainActivity.class);
                startActivity(intent2);
            }
        });



        /*CheckBox checkboxtarea =(CheckBox) findViewById(R.id.checkboxtarea);


        if(checkboxtarea.isChecked()){
            String nombre_tarea = checkboxtarea.getText().toString();
                    setCheckedTask(nombre_tarea);
        }

        checkboxtarea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    String nombre_tarea = checkboxtarea.getText().toString();
                    setCheckedTask(nombre_tarea);
                }
            }
        });*/

       /* View.OnClickListener onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

                Cursor cursor = adapter.getCursor();

                cursor.moveToPosition(viewHolder.getAdapterPosition());

                int columna_nombre = cursor.getColumnIndex(TareaContract.TareaEntry.COLUMN_TASKNAME);
                String nombre_tarea = cursor.getString(columna_nombre);
                setCheckedTask(nombre_tarea);



            }
        };*/
    }

    //funcion para mostrar las tareas de ese día
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

        adapter.setCursor(cursor);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_tareas_hoy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void setCheckedTask(String nombre_tarea){

        String app = getResources().getString(R.string.app_name);

        TareaHelper dbHelper = new TareaHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TareaContract.TareaEntry.COLUMN_DONE, 1);

        String selection = TareaContract.TareaEntry.COLUMN_TASKNAME + " LIKE ?";
        String[] selectionArgs = {nombre_tarea};

        int count = db.update(
                TareaContract.TareaEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

    }
}


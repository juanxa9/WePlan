package com.example.weplan;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weplan.adapters.AdapterTareasHoy;
import com.example.weplan.database.TareaContract;
import com.example.weplan.database.TareaHelper;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TareasDiaSeleccionado extends AppCompatActivity {
    private String date;
    private AdapterTareasHoy adapter = new AdapterTareasHoy();
    private CheckBox checkboxtarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas_para_hoy);
        date = getIntent().getStringExtra("selected_date");
        Cursor cursor = displayDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerview_tareas_hoy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setCursor(getApplicationContext(),cursor);



        //crear una tarea nueva para el dia seleccionado
        FloatingActionButton nuevatarea = (FloatingActionButton) findViewById(R.id.anyadirnuevatarea);
        nuevatarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TareasDiaSeleccionado.this, CrearTareaDiaSeleccionado.class);
                intent.putExtra("dateadd", date);
                startActivity(intent);

                /*DialogFragment newFragment = new NuevaTareaFragment();
                Bundle args = new Bundle();
                args.putString("dateadd", date);
                args.putString("contexto", String.valueOf(getApplicationContext()));
                newFragment.setArguments(args);
                newFragment.show(getSupportFragmentManager(), "nueva_tarea");*/

            }
        });
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.calificar_dia:
                        Intent intent = new Intent(TareasDiaSeleccionado.this, PuntuarDia.class);
                        intent.putExtra("dia_para_puntuar", date);
                        intent.putExtra("Class", "TareasSeleccion");
                        startActivity(intent);
                    default:
                        return false;
                }
            }
        });



    }


    //funcion para mostrar las tareas de ese día
    public Cursor displayDatabase(){
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

        return cursor;
        /*adapter.setCursor(cursor);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_tareas_hoy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);*/
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


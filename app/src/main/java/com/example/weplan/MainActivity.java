package com.example.weplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private GestureDetector gestos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_WePlan);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




       /* // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // set an exit transition
        getWindow().setExitTransition(new Explode());*/

        /*ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(MainActivity.this, TareasParaHoy.class);
        startActivity(intent, options.toBundle());*/




        gestos = new GestureDetector(this, new EscuchaGestos());

        //clickando en algun día del calendario
        CalendarView calendarView = (CalendarView) findViewById(R.id.cv_calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {


                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, i1);
                cal.set(Calendar.DAY_OF_MONTH, i2);
                cal.set(Calendar.YEAR, i);
                String stringTime = sdf.format(cal.getTime());


                Intent intent = new Intent(MainActivity.this, TareasDiaSeleccionado.class);
                intent.putExtra("selected_date", stringTime);
                startActivity(intent);

                //Toast.makeText(getApplicationContext(), ""+stringTime, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tareas_completadas:
                Intent intent = new Intent(MainActivity.this, TareasTotales.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    class EscuchaGestos extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            String stringTime = sdf.format(c.getTime());

            Intent intent = new Intent(MainActivity.this, TareasParaHoy.class);
            intent.putExtra("currentdate", stringTime);
            startActivity(intent);
            if(v < v1){
                overridePendingTransition(0, R.anim.trans_left);
            }
            else{
                overridePendingTransition(0, R.anim.trans_right);
            }

            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestos.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
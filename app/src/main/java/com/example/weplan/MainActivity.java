package com.example.weplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GestureDetectorCompat;

import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import android.widget.TextView;
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
        createNotificationChannel();



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

            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "canalNotificaciones";
            String description = "canal de notificaciones para las tareas pendientes";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("canalmio", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
            case R.id.musicaMenu:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/5qap5aO4i9A"));
                startActivity(browserIntent);
                return true;
            case R.id.pomodoro:
                Intent intent = new Intent(MainActivity.this, MetodoPomodoro.class);
                startActivity(intent);
                return true;
            case R.id.productividad:
                Intent intentHOla = new Intent(MainActivity.this, GraficoPuntuarDia.class);
                startActivity(intentHOla);
                return true;
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
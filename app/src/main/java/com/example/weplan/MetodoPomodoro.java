package com.example.weplan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MetodoPomodoro extends AppCompatActivity {
    private static final long TIME_MILLI_SECONDS = 1500000; //25mins

    private TextView countdownText;
    private Button countdownButton;
    private Button countdownButtonReset;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMilliSeconds = TIME_MILLI_SECONDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_pomodoro);

        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdown_button);
        countdownButtonReset = findViewById(R.id.countdown_button_reset);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });

        countdownButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateTimer();
    }

    public void startStop(){
        if(timerRunning){
            stopTimer();
        }
        else{
            startTimer();
        }
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliSeconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                countdownButton.setText("START");
                countdownButton.setVisibility(View.INVISIBLE);
                countdownButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        countdownButton.setText("PAUSE");
        countdownButtonReset.setVisibility(View.INVISIBLE);
        timerRunning = true;
    }

    public void stopTimer(){
        countDownTimer.cancel();
        countdownButton.setText("START");
        countdownButtonReset.setVisibility(View.VISIBLE);
        timerRunning = false;
    }

    public void resetTimer(){
        timeLeftInMilliSeconds = TIME_MILLI_SECONDS;
        updateTimer();
        countdownButton.setVisibility(View.VISIBLE);
        countdownButtonReset.setVisibility(View.INVISIBLE);
    }

    public void updateTimer(){
        int minutes = (int) (timeLeftInMilliSeconds / 1000) / 60;
        int seconds = (int) (timeLeftInMilliSeconds / 1000) % 60;

        String timeLeftText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        countdownText.setText(timeLeftText);
    }
}
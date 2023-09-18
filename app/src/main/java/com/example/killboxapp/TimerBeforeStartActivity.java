package com.example.killboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class TimerBeforeStartActivity extends AppCompatActivity {
    Bundle appConfig;

    TextView timerTextView;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_before_start);

        // Убрать панель навигации и кнопки навигации
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Установить landscape ориентацию
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();

        appConfig = intent.getBundleExtra("appConfig");

        timerTextView = findViewById(R.id.textViewTimer);

        // Запуск таймера
        int startDelay = appConfig.getInt(MainActivity.startDelaySettingsKey);
        timer = new CountDownTimer(1000 + 1000L * startDelay, 1000) {
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                if (appConfig.getBoolean(MainActivity.isFakeStartSettingsKey)) {
                    Intent intent = new Intent(TimerBeforeStartActivity.this, FakeWorkingScreenActivity.class);
                    startActivity(intent);
                    return;
                }

                Intent intent = new Intent(TimerBeforeStartActivity.this, WorkingScreenActivity.class);
                intent.putExtra("appConfig", appConfig);
                startActivity(intent);
            }
        };

        timer.start();
    }

    @Override
    public void onBackPressed() {
        if (timer != null) {
            timer.cancel();
        }

        Intent intent = new Intent(TimerBeforeStartActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
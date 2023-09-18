package com.example.killboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class WorkingScreenActivity extends AppCompatActivity {

    Bundle appConfig;

    Button btnRed;
    Button btnBlue;

    String leaderTeam; // Кто на текущий момент удерживает точку RED | BLUE

    CountDownTimer captureTimer;
    CountDownTimer retainTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_screen);

        this.hideNavigation();

        btnRed = findViewById(R.id.buttonRed);
        btnBlue = findViewById(R.id.buttonBlue);

        Intent intent = getIntent();

        appConfig = intent.getBundleExtra("appConfig");

        setupBlueButton();
        setupRedButton();
    }

    @Override
    public void onBackPressed() {
        // TODO: Сохранить историю
        Intent intent = new Intent(WorkingScreenActivity.this, MainActivity.class);
        startActivity(intent);
    }

    void hideNavigation() {
        // Убрать панель навигации и кнопки навигации
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Установить landscape ориентацию
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    void startRetainTimer(String team) {
        int retainTime = appConfig.getInt(MainActivity.retentionTimeSettingsKey);
        retainTimer = new CountDownTimer(1000L * retainTime, 1000) {
            @Override
            public void onTick(long l) {
                Log.d("RedTimer", String.valueOf(l / 1000));
                if (Objects.equals(team, MainActivity.teamRed)) {
                    btnRed.setText(String.valueOf(l / 1000 + 1));
                } else {
                    btnBlue.setText(String.valueOf(l / 1000 + 1));
                }
            }

            @Override
            public void onFinish() {
                leaderTeam = team;

                startCaptureTimer(leaderTeam, false);

                if (Objects.equals(team, MainActivity.teamRed)) {
                    btnRed.setEnabled(false);
                    btnBlue.setEnabled(true);
                } else {
                    btnRed.setEnabled(true);
                    btnBlue.setEnabled(false);
                }
            }
        };

        retainTimer.start();
    }

    void cancelRetainTimer() {
        if (retainTimer != null) {
            retainTimer.cancel();
        }
    }

    void startCaptureTimer(String team, boolean isResume) {
        btnRed.setText("");
        btnBlue.setText("");

        if (captureTimer != null) {
            captureTimer.cancel();
        }

        int captureTime = appConfig.getInt(MainActivity.captureTimeSettingsKey);
        captureTimer = new CountDownTimer(1000L * captureTime, 1000) {
            @Override
            public void onTick(long l) {
                if (Objects.equals(team, MainActivity.teamRed)) {
                    btnRed.setText(String.valueOf(l / 1000 + 1));
                } else {
                    btnBlue.setText(String.valueOf(l / 1000 + 1));
                }

                Log.d("CaptureTimer", String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                if (retainTimer != null) {
                    retainTimer.cancel();
                }

                Intent intent = new Intent(WorkingScreenActivity.this, ResultsActivity.class);
                intent.putExtra("winner", leaderTeam);
                // todo: put journal
                startActivity(intent);
            }
        };

        captureTimer.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    void setupRedButton() {
        btnRed.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                buttonRedPress();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                buttonRedRelease();
            }

            return true;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    void setupBlueButton() {
        btnBlue.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                buttonBluePress();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                buttonBlueRelease();
            }

            return true;
        });
    }

    void buttonBluePress() {
        btnRed.setEnabled(false);
        startRetainTimer(MainActivity.teamBlue);
        hideNavigation();
    }

    void buttonBlueRelease() {
        btnBlue.setText("");
        cancelRetainTimer();
        if (!Objects.equals(leaderTeam, MainActivity.teamRed)) {
            btnRed.setEnabled(true);
        }
        hideNavigation();
    }

    void buttonRedPress() {
        btnBlue.setEnabled(false);
        startRetainTimer(MainActivity.teamRed);
        hideNavigation();
    }

    void buttonRedRelease() {
        btnRed.setText("");
        cancelRetainTimer();
        if (!Objects.equals(leaderTeam, MainActivity.teamBlue)) {
            btnBlue.setEnabled(true);
        }
        hideNavigation();
    }
}
package com.example.killboxapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String isFakeStartSettingsKey = "isFakeStart";
    static final String startDelaySettingsKey = "startDelay";
    static final String captureTimeSettingsKey = "captureTime";
    static final String retentionTimeSettingsKey = "retentionTime";
    static final String teamRed = "RED";
    static final String teamBlue = "BLUE";

    Button btnStart;
    Button btnFakeStart;

    EditText editTextStartDelay;
    EditText editTextCaptureTime;
    EditText editTextRetentionTime;

    Bundle appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideNavigation();

        appConfig = new Bundle();

        btnStart = findViewById(R.id.buttonStart);
        btnFakeStart = findViewById(R.id.buttonFakeStart);

        editTextStartDelay = findViewById(R.id.editTextStartDelay);
        editTextCaptureTime = findViewById(R.id.editTextCaptureTime);
        editTextRetentionTime = findViewById(R.id.editTextRetentionTime);

        btnFakeStart.setOnClickListener(view -> {
            appConfig.putBoolean(isFakeStartSettingsKey, true);
            Intent intent = new Intent(MainActivity.this, TimerBeforeStartActivity.class);
            intent.putExtra("appConfig", appConfig);
            startActivity(intent);
        });

        btnStart.setOnClickListener(view -> {
            appConfig.putBoolean(isFakeStartSettingsKey, false);
            Intent intent = new Intent(MainActivity.this, TimerBeforeStartActivity.class);
            intent.putExtra("appConfig", appConfig);
            startActivity(intent);
        });

        editTextStartDelay.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTextStartDelay.getText().toString().equals("")) {
                    appConfig.putInt(startDelaySettingsKey, Integer.parseInt(editTextStartDelay.getText().toString()));
                } else {
                    appConfig.putInt(startDelaySettingsKey, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editTextCaptureTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTextCaptureTime.getText().toString().equals("")) {
                    appConfig.putInt(captureTimeSettingsKey, Integer.parseInt(editTextCaptureTime.getText().toString()));
                } else {
                    appConfig.putInt(captureTimeSettingsKey, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editTextRetentionTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTextRetentionTime.getText().toString().equals("")) {
                    appConfig.putInt(retentionTimeSettingsKey, Integer.parseInt(editTextRetentionTime.getText().toString()));
                } else {
                    appConfig.putInt(retentionTimeSettingsKey, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    void hideNavigation() {
        // Убрать панель навигации и кнопки навигации
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Установить landscape ориентацию
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
package com.example.killboxapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FakeWorkingScreenActivity extends AppCompatActivity {

    TextView textViewFakeStartMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_working_screeen);

        hideNavigation();

        textViewFakeStartMessage = findViewById(R.id.textViewFakeStartMessage);
        textViewFakeStartMessage.setText("ЛОЖНАЯ\nТОЧКА");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FakeWorkingScreenActivity.this, MainActivity.class);
        startActivity(intent);
    }

    void hideNavigation() {
        // Убрать панель навигации и кнопки навигации
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Установить landscape ориентацию
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
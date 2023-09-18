package com.example.killboxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class ResultsActivity extends AppCompatActivity {

    TextView textViewWinnerTeam;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        hideNavigation();

        Intent intent = getIntent();

        //Toast.makeText(getApplicationContext(), intent.getStringExtra("winner"), Toast.LENGTH_LONG).show();

        textViewWinnerTeam = findViewById(R.id.textViewWinnerTeam);

        int winnerTextColor = ContextCompat.getColor(getApplicationContext(), R.color.textLight);
        String winnerText = "NULL";

        if (Objects.equals(intent.getStringExtra("winner"), MainActivity.teamRed)) {
            winnerTextColor = ContextCompat.getColor(getApplicationContext(), R.color.buttonRed);
            winnerText = "КРАСНЫЕ";
        }

        if (Objects.equals(intent.getStringExtra("winner"), MainActivity.teamBlue)) {
            winnerTextColor = ContextCompat.getColor(getApplicationContext(), R.color.buttonBlue);
            winnerText = "СИНИЕ";
        }

        textViewWinnerTeam.setTextColor(winnerTextColor);
        textViewWinnerTeam.setText(winnerText);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
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
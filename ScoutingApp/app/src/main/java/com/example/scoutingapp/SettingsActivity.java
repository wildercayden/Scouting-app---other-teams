package com.example.scoutingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText NameTextSetting = findViewById(R.id.Name);
        EditText TBATextSetting = findViewById(R.id.TBA);
        EditText SheetsTextSetting = findViewById(R.id.Sheets);
        EditText MatchTextSetting = findViewById(R.id.Match);
        Button saveButton = findViewById(R.id.buttonSave);
        Button NextButton = findViewById(R.id.buttonNext);
        Button resetButton = findViewById(R.id.buttonReset);
        SharedPreferences sharedPreferences = getSharedPreferences("MySettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeIntent();
            }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameToSave = NameTextSetting.getText().toString();
                String TBAToSave = TBATextSetting.getText().toString();
                String SheetsToSave = SheetsTextSetting.getText().toString();
                String MatchToSave = MatchTextSetting.getText().toString();
                editor.putString("NameText", NameToSave);
                editor.putString("TBAText", TBAToSave);
                editor.putString("SheetsText", SheetsToSave);
                editor.putString("MatchText", MatchToSave);
                editor.apply(); // or commit() if you want it synchronous
                Toast.makeText(SettingsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String defaultName = "";
                NameTextSetting.setText(defaultName);
                editor.putString("NameText", defaultName);
                String defaultTBA = "https://www.thebluealliance.com/api/v3/match/2025melew_qm";
                TBATextSetting.setText(defaultTBA);
                editor.putString("TBAText", defaultTBA);
                String defaultSheets = "1R_OzzZ_XlIWicaYkEfWyzmEe0VN3u2ggoJCFPavsZTM";
                SheetsTextSetting.setText(defaultSheets);
                editor.putString("SheetsText", defaultSheets);
                String defaultMatch = "MELEW";
                MatchTextSetting.setText(defaultMatch);
                editor.putString("MatchText", defaultMatch);
                editor.apply();


                Toast.makeText(SettingsActivity.this, "Reset to default!", Toast.LENGTH_SHORT).show();
            }
        });


        String NameText = sharedPreferences.getString("NameText", ""); // "" is the default
        NameTextSetting.setText(NameText);
        String TBAText = sharedPreferences.getString("TBAText", ""); // "" is the default
        TBATextSetting.setText(TBAText);
        String SheetsText = sharedPreferences.getString("SheetsText", ""); // "" is the default
        SheetsTextSetting.setText(SheetsText);
        String MatchText = sharedPreferences.getString("MatchText", ""); // "" is the default
        MatchTextSetting.setText(MatchText);




    }

    private void makeIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
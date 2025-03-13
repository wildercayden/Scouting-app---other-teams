package com.example.scoutingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class startingActivity extends AppCompatActivity {
    private MatchData matchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_starting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Match), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        matchData = MainActivity.matchData;

        Intent intentInput = getIntent();
        TextView textViewTeam = findViewById(R.id.teamnumber);
        textViewTeam.setText("Team " + matchData.getTeamNumber());
        TextView textViewMatch = findViewById(R.id.matchNumber);
        textViewMatch.setText("Match " + matchData.getMatchNumber());
        if (!matchData.isBlueAlliance()) {
            textViewTeam.setBackgroundColor(Color.parseColor("#F71000")); //red
            textViewMatch.setBackgroundColor(Color.parseColor("#F71000"));
        } else {
            textViewTeam.setBackgroundColor(Color.parseColor("#0084ff"));
            textViewMatch.setBackgroundColor(Color.parseColor("#0084ff"));//blue
        }

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (matchData.getStartingPosition() == 0){
                    Toast.makeText(getApplicationContext(), "Press a button", Toast.LENGTH_SHORT).show();
                }else {
                    makeIntent();
                }
            }
        });

        RadioButton Rbutton1 = findViewById(R.id.Rbutton1);
        Rbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchData.setStartingPosition(1);
            }
        });
        RadioButton Rbutton2 = findViewById(R.id.Rbutton2);
        Rbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchData.setStartingPosition(2);
            }
        });
        RadioButton Rbutton3 = findViewById(R.id.Rbutton3);
        Rbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchData.setStartingPosition(3);
            }
        });
        RadioButton Rbutton4 = findViewById(R.id.Rbutton4);
        Rbutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchData.setStartingPosition(4);
            }
        });
        RadioButton Rbutton5 = findViewById(R.id.Rbutton5);
        Rbutton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchData.setStartingPosition(5);
            }
        });

        Rbutton1.setChecked(false);

    }
    private void makeIntent()
    {
        Intent intent = new Intent(this, AutoActivity.class);
        startActivity(intent);
        Log.d("TESTER", "WE WENT TO AUTO");
    }
}
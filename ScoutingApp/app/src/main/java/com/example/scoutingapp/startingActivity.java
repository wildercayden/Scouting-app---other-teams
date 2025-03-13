package com.example.scoutingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class startingActivity extends AppCompatActivity {
    private String eventString, matchString, teamString, startingPostionString, allianceString;
    private boolean alliance = true;
    public static final String Event_Key = "EVENTCONFIRM";
    public static final String Match_key = "MATCHCONFIRM";
    public static final String Team_key = "TEAMCONFIRM";
    public static final String Postion_key = "POSTIONKEY";
    public static final String Alliance_key = "ALLIANCECONFIRM";
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
        Intent intentInput = getIntent();
        eventString = intentInput.getStringExtra(MainActivity.eventKey);
        matchString = intentInput.getStringExtra(MainActivity.matchKey);
        teamString = intentInput.getStringExtra(MainActivity.teamKey);
        allianceString = intentInput.getStringExtra(MainActivity.allianceKey);
        TextView textViewTeam = findViewById(R.id.teamnumber);
        textViewTeam.setText("Team " + teamString);
        TextView textViewMatch = findViewById(R.id.matchNumber);
        textViewMatch.setText("Match " + matchString);
        if ("red".equals(allianceString)) {
            textViewTeam.setBackgroundColor(Color.parseColor("#F71000")); //red
            textViewMatch.setBackgroundColor(Color.parseColor("#F71000"));
        } else {
            textViewTeam.setBackgroundColor(Color.parseColor("#0084ff"));
            textViewMatch.setBackgroundColor(Color.parseColor("#0084ff"));//blue
            alliance = false;
        }

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((startingPostionString == null || startingPostionString.isEmpty())){
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
                startingPostionString = "1";
            }
        });
        RadioButton Rbutton2 = findViewById(R.id.Rbutton2);
        Rbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startingPostionString = "2";
            }
        });
        RadioButton Rbutton3 = findViewById(R.id.Rbutton3);
        Rbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startingPostionString = "3";
            }
        });
        RadioButton Rbutton4 = findViewById(R.id.Rbutton4);
        Rbutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startingPostionString = "4";
            }
        });
        RadioButton Rbutton5 = findViewById(R.id.Rbutton5);
        Rbutton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startingPostionString = "5";
            }
        });

    }
    private void makeIntent()
    {
        Intent intent = new Intent(this, AutoActivity.class);
        intent.putExtra(Event_Key, eventString);
        intent.putExtra(Match_key, matchString);
        intent.putExtra(Team_key, teamString);
        intent.putExtra(Postion_key, startingPostionString);
        intent.putExtra(Alliance_key, allianceString);
        startActivity(intent);
    }
}
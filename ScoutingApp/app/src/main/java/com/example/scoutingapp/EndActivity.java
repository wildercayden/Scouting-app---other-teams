package com.example.scoutingapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import java.io.File;

public class EndActivity extends AppCompatActivity {
    private String eventString, matchString, TeamString, noteString;
    private int climbLevel = 0;
    private EditText noteText;
    public static final String Team_key = "TEAMCONFIRM";
    public static final String Event_Key = "EVENTCONFIRM";
    public static final String Match_key = "MATCHCONFIRM";
    private Boolean alliance = true; //true = red, false = blue
    private Button deepClimbButton, shallowClimbButton, parkButton, noClimbButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = new Intent(this, MainActivity.class);
        noteText = (EditText) findViewById(R.id.Notes);
        Intent intentinput = getIntent();
        eventString = intentinput.getStringExtra(TeleActivity.Event_Key);
        matchString = intentinput.getStringExtra(TeleActivity.Match_key);
        TeamString = intentinput.getStringExtra(TeleActivity.Team_key);
        alliance = intentinput.getBooleanExtra(TeleActivity.Alliance_key, false);
        TextView textViewTeam = findViewById(R.id.teamnumber);
        textViewTeam.setText("Team " + TeamString);
        TextView textViewMatch = findViewById(R.id.matchNumber);
        textViewMatch.setText("Match " + matchString);
        if (alliance == true) {
            textViewTeam.setBackgroundColor(Color.parseColor("#F71000")); //red
            textViewMatch.setBackgroundColor(Color.parseColor("#F71000"));
        } else {
            textViewTeam.setBackgroundColor(Color.parseColor("#0084ff"));
            textViewMatch.setBackgroundColor(Color.parseColor("#0084ff"));//blue
        }

        deepClimbButton = (Button) findViewById(R.id.RB_DeepClimb);
        shallowClimbButton = (Button) findViewById(R.id.RB_ShallowClimb);
        parkButton = (Button) findViewById(R.id.RB_Park);
        noClimbButton = (Button) findViewById(R.id.RB_NoClimb);

        Button submit = (Button) findViewById(R.id.Submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                noteString = noteText.getText().toString();
                csvMake();
                String csvFileString = eventString+matchString+TeamString+".csv";
                Submit submit = new Submit();
                //Writes data to file to make google sheet read it as a list
                File csvFilefile = new File(getFilesDir(), csvFileString);
                List<List<Object>> data = submit.parseCSVToList(csvFilefile);
                submit.parseCSVToList(csvFilefile);
                //Uploads the Data to the Google sheet
                submit.uploadSheets(EndActivity.this, csvFileString);
                //submit.renameFile(EndActivity.this, csvFileString);
                startActivity(intent);

            }
        });
        Button backButton = (Button) findViewById(R.id.BackButton);

        backButton.setOnLongClickListener((v) -> {
            Intent backIntent = new Intent(EndActivity.this, MainActivity.class);
            startActivity(backIntent);
            return true;
        });

        Button Sheet = (Button) findViewById(R.id.Sheet);
        Sheet.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1ky5LBTpnEeBEEaaF7z6UWdh-E7YmOSeij4dYdR2PU4A/edit?gid=0#gid=0"));
                startActivity(browserIntent);
            }
        });

        deepClimbButton.setOnClickListener((v) -> {
            climbLevel = 3;
        });

        shallowClimbButton.setOnClickListener((v) -> {
            climbLevel = 2;
        });

        parkButton.setOnClickListener((v) -> {
            climbLevel = 1;
        });

        noClimbButton.setOnClickListener((v) -> {
            climbLevel = 0;
        });
    }

    public void csvMake() {
        //adds the strings
        String timestamp = getTimestamp();
        String CSVLine = String.format(
                "%s, %s, %s", climbLevel, noteString, timestamp
        );

        //makes the file
        File csvFile = new File(this.getFilesDir(), eventString + matchString + TeamString + ".csv");
        Log.d("CSVFile", "File created/written at: " + csvFile.getAbsolutePath());
        //writes to file
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append(CSVLine).append("\n");
            Log.d("CSVFilePath", csvFile.getAbsolutePath());
        } catch (IOException e) {
            Log.d("CSVFail", "CSV didn't make");
        }

    }

    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss.SSS", Locale.getDefault());
        return sdf.format(new Date());
    }
}


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
    private EditText noteText;
    private MatchData matchData;

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
        matchData = MainActivity.matchData;

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

        Intent intent = new Intent(this, MainActivity.class);
        noteText = (EditText) findViewById(R.id.Notes);

        Button deepClimbButton = (Button) findViewById(R.id.RB_DeepClimb);
        Button shallowClimbButton = (Button) findViewById(R.id.RB_ShallowClimb);
        Button parkButton = (Button) findViewById(R.id.RB_Park);
        Button noClimbButton = (Button) findViewById(R.id.RB_NoClimb);

        Button submit = (Button) findViewById(R.id.Submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.matchData.seteNote(String.valueOf(noteText.getText()));
                csvMake();
                String csvFileString = MainActivity.matchData.getCSVFileName();
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
            Intent backIntent = new Intent(EndActivity.this, TeleActivity.class);
            startActivity(backIntent);
            return true;
        });

        deepClimbButton.setOnClickListener((v) -> {
            MainActivity.matchData.seteClimb(3);
        });

        shallowClimbButton.setOnClickListener((v) -> {
            MainActivity.matchData.seteClimb(2);
        });

        parkButton.setOnClickListener((v) -> {
            MainActivity.matchData.seteClimb(1);
        });

        noClimbButton.setOnClickListener((v) -> {
            MainActivity.matchData.seteClimb(0);
        });
    }

    public void csvMake() {
        //adds the strings
        String CSVLine = MainActivity.matchData.makeCSVString();
        Log.d("matchData string", MainActivity.matchData.makeCSVString());

        //makes the file
        File csvFile = new File(this.getFilesDir(), MainActivity.matchData.getCSVFileName());
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

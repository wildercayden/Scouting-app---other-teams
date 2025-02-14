package com.example.scoutingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;


import java.io.File;

public class EndActivity extends AppCompatActivity {
    private String eventString, matchString, TeamString;
    public static final String Event_Key = "EVENTCONFIRM";
    public static final String Match_key = "MATCHCONFIRM";

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
        Intent intentinput = getIntent();
        eventString = intentinput.getStringExtra(TeleActivity.Event_Key);
        matchString = intentinput.getStringExtra(TeleActivity.Match_key);
        TeamString = intentinput.getStringExtra(TeleActivity.Team_key);

        Button submit = (Button) findViewById(R.id.Submit_button);
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String csvFileString = eventString+matchString+TeamString+".csv";
                Submit submit = new Submit();
                //Writes data to file to make google sheet read it as a list
                File csvFilefile = new File(getFilesDir(), csvFileString);
                List<List<Object>> data = submit.parseCSVToList(csvFilefile);
                submit.parseCSVToList(csvFilefile);
                //Uploads the Data to the Google sheet
                submit.uploadSheets(EndActivity.this, csvFileString);
                //submit.renameFile(EndActivity.this, csvFileString);
            }
        });

        Button Sheet = (Button) findViewById(R.id.Sheet);
        Sheet.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheets/d/1ky5LBTpnEeBEEaaF7z6UWdh-E7YmOSeij4dYdR2PU4A/edit?gid=0#gid=0"));
                startActivity(browserIntent);
            }
        });


    }



}


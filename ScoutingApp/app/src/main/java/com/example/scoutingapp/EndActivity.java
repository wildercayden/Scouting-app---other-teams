package com.example.scoutingapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class EndActivity extends AppCompatActivity {
    private String teamNumber, matchNumber, Taxiing, autoCoral, autoAlgea, floorPickup, HPPickup, startingLocation, teleopCoral, teleopAlgea, HPScore, teleopFloorPickup, Endgame, Climb, Notes;


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

        Button submit = (Button) findViewById(R.id.Submit_button);
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            CSVmake(EndActivity.this);
            }
        });


    }

    public String getTeamNumber() {
        teamNumber = "hello world";
        return teamNumber;
    }

    public void CSVmake(Context context) {
        String CSVLine = teamNumber + "," + matchNumber + "," + Taxiing + "," + autoCoral + "," +
                autoAlgea + "," + floorPickup + "," + HPPickup + "," + startingLocation + "," +
                teleopCoral + "," + teleopAlgea + "," + HPScore + "," + teleopFloorPickup + "," +
                Endgame + "," + Climb + "," + Notes; //adds the strings the CSV file (if the strings have info)

        File csvFile = new File(context.getExternalFilesDir(null), "match_data.csv");//makes the CSV file

        try (FileWriter writer = new FileWriter(csvFile, true)) {//writes data to the file from CSVLine
            writer.append(CSVLine).append("\n");
            Log.d("CSVFilePath", csvFile.getAbsolutePath());
        } catch (IOException e) { //if it doesn't work it makes a toast and a log to tell you it didn't work
            Log.d("CSVFail", "CSV didn't make");
            Toast.makeText(this /* MyActivity */, "Failed to make server", Toast.LENGTH_SHORT).show();

        };
    }
}
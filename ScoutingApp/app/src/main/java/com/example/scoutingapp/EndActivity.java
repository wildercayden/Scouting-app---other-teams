package com.example.scoutingapp;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import org.json.JSONException;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;


import java.io.File;





public class EndActivity extends AppCompatActivity {

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
                Submit submit = new Submit();
                //calls test, write data to String getTeamNumber
                submit.getTeamNumberTest();
                //Calls CSVmake which makes the CSV file
                submit.CSVmake(EndActivity.this);
                //Writes data to file to make Google Sheet API take the data
                File csvFile = new File(getExternalFilesDir(null), "match_data.csv");
                List<List<Object>> data = submit.parseCSVToList(csvFile);
                submit.parseCSVToList(csvFile);
                //Uploads the Data to the Google sheet
                submit.uploadCSV(EndActivity.this);
            }
        });

        Button Delete = (Button) findViewById(R.id.deleteButton);
        Delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Submit submit = new Submit();
                //Deletes the CSV file (Currently for testing but feature might stay but with some features to make it harder to do it on accident)
                submit.deleteCSVFile(EndActivity.this);
            }
        });


    }

}

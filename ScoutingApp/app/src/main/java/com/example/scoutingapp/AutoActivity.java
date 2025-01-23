package com.example.scoutingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AutoActivity extends AppCompatActivity {
    private int l4Scored = 0;
    private int l3Scored = 0;
    private int l2Scored = 0;
    private int l1Scored = 0;
    private int processorScored = 0;
    private int netScored = 0;

    private boolean reefPickup = false;
    private boolean canLeave = false;
    private boolean coralPickup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Button l4Button = (Button) findViewById(R.id.button_L4);
        Button l3Button = (Button) findViewById(R.id.button_L3);
        Button l2Button = (Button) findViewById(R.id.button_L2);
        Button l1Button = (Button) findViewById(R.id.button_L1);
        Button processorButton = (Button) findViewById(R.id.button_Processor);
        Button netButton = (Button) findViewById(R.id.button_Net);
        Button nextButton = (Button) findViewById(R.id.button_Next);

        CheckBox coralPickupBox = (CheckBox) findViewById(R.id.cb_coralPickup);
        CheckBox reefPickupBox = (CheckBox) findViewById(R.id.cb_algaeReef);
        CheckBox canLeaveBox = (CheckBox) findViewById(R.id.cb_CanLeave);


        l4Button.setOnClickListener((v) -> l4Scored++);
        l4Button.setOnLongClickListener((v) -> {
            l4Scored--;
            vibrator.vibrate(250);
            return true;
        });

        l3Button.setOnClickListener((v) -> l3Scored++);
        l3Button.setOnLongClickListener((v) -> {
            l3Scored--;
            vibrator.vibrate(250);
            return true;
        });

        l2Button.setOnClickListener((v) -> l2Scored++);
        l2Button.setOnLongClickListener((v) -> {
            l2Scored--;
            vibrator.vibrate(250);
            return true;
        });

        l1Button.setOnClickListener((v) -> l1Scored++);
        l1Button.setOnLongClickListener((v) -> {
            l1Scored--;
            vibrator.vibrate(250);
            return true;
        });

        processorButton.setOnClickListener((v) -> processorScored++);
        processorButton.setOnLongClickListener((v) -> {
            processorScored--;
            vibrator.vibrate(250);
            return true;
        });

        netButton.setOnClickListener((v) -> netScored++);
        netButton.setOnLongClickListener((v) -> {
            netScored--;
            vibrator.vibrate(250);
            return true;
        });

        canLeaveBox.setOnClickListener((v) -> canLeave = canLeaveBox.isChecked());

        reefPickupBox.setOnClickListener((v) -> reefPickup = reefPickupBox.isChecked());

        coralPickupBox.setOnClickListener((v) -> coralPickup = coralPickupBox.isChecked());

        nextButton.setOnLongClickListener((v) -> {
            //submit data
            CSVmake(AutoActivity.this);
            Intent intent = new Intent(this, TeleActivity.class);
            startActivity(intent);
            Log.d("test", intent.toString());
            return true;
        });
    }

    public void CSVmake(Context context) {
        //adds the strings
        String CSVLine = String.format(
                "%s %s %s %s %s %s %s",
                l4Scored,
                l3Scored,
                l2Scored,
                l1Scored,
                reefPickup,
                canLeave,
                coralPickup
                );
        //makes the file
        File csvFile = new File(context.getFilesDir(), "match_data.csv");
        //writes to file
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append(CSVLine).append("\n");
            Log.d("CSVFilePath", csvFile.getAbsolutePath());
        } catch (IOException e) {
            Log.d("CSVFail", "CSV didn't make");
        }
    }
}

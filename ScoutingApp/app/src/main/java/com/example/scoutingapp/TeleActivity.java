package com.example.scoutingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TeleActivity extends AppCompatActivity {
    private String tele = "Teleop";
    private int l4Scored = 0;
    private int l3Scored = 0;
    private int l2Scored = 0;
    private int l1Scored = 0;
    private int processorScored = 0;
    private int netScored = 0;

    private boolean reefPickup = false;
    private boolean canLeave = false;
    private boolean coralPickup = false;

    private TextView l4TV;
    private TextView l3TV;
    private TextView l2TV;
    private TextView l1TV;
    private TextView processorTV;
    private TextView netTV;
    private String eventString, matchString;
    public static final String Event_Key = "EVENTCONFIRM";
    public static final String Match_key = "MATCHCONFIRM";

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
        Intent intentinput = getIntent();
        eventString = intentinput.getStringExtra(AutoActivity.Event_Key);
        matchString = intentinput.getStringExtra(AutoActivity.Match_key);

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

        l4TV = (TextView) findViewById(R.id.tv_L4);
        l3TV = (TextView) findViewById(R.id.tv_L3);
        l2TV = (TextView) findViewById(R.id.tv_L2);
        l1TV = (TextView) findViewById(R.id.tv_L1);
        processorTV = (TextView) findViewById(R.id.tv_Net2);
        netTV = (TextView) findViewById(R.id.tv_Net);

        //When clicked, add 1 to the scored and update the text for L4
        l4Button.setOnClickListener((v) -> {
            l4Scored++;
            updateL4Text();
        });
        //when held, subtract 1 from scored and update the text for L4
        l4Button.setOnLongClickListener((v) -> {
            l4Scored--;
            updateL4Text();
            return true;
        });

        l3Button.setOnClickListener((v) -> {
            l3Scored++;
            updateL3Text();
        });
        l3Button.setOnLongClickListener((v) -> {
            l3Scored--;
            updateL3Text();
            return true;
        });

        l2Button.setOnClickListener((v) -> {
            l2Scored++;
            updateL2Text();
        });
        l2Button.setOnLongClickListener((v) -> {
            l2Scored--;
            updateL2Text();
            return true;
        });

        l1Button.setOnClickListener((v) -> {
            l1Scored++;
            updateL1Text();
        });
        l1Button.setOnLongClickListener((v) -> {
            l1Scored--;
            updateL1Text();
            return true;
        });

        processorButton.setOnClickListener((v) -> {
            processorScored++;
            updateProcessorText();
        });
        processorButton.setOnLongClickListener((v) -> {
            processorScored--;
            updateProcessorText();
            return true;
        });

        netButton.setOnClickListener((v) -> {
            netScored++;
            updateNetText();
        });
        netButton.setOnLongClickListener((v) -> {
            netScored--;
            updateNetText();
            return true;
        });

        canLeaveBox.setOnClickListener((v) -> canLeave = canLeaveBox.isChecked());

        reefPickupBox.setOnClickListener((v) -> reefPickup = reefPickupBox.isChecked());

        coralPickupBox.setOnClickListener((v) -> coralPickup = coralPickupBox.isChecked());

        nextButton.setOnLongClickListener((v) -> {
            //submit data
            csvMake();
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra(Event_Key, eventString);
            intent.putExtra(Match_key, matchString);
            startActivity(intent);
            return true;
        });

        //sets all text boxes to 0
        updateL4Text();
        updateL3Text();
        updateL2Text();
        updateL1Text();
        updateProcessorText();
        updateNetText();
    }

    //Methods that update the score count
    private void updateL4Text() {
        l4TV.setText(String.format(getResources().getString(R.string.coralScored), l4Scored));
    }

    private void updateL3Text() {
        l3TV.setText(String.format(getResources().getString(R.string.coralScored), l3Scored));
    }

    private void updateL2Text() {
        l2TV.setText(String.format(getResources().getString(R.string.coralScored), l2Scored));
    }

    private void updateL1Text() {
        l1TV.setText(String.format(getResources().getString(R.string.coralScored), l1Scored));
    }

    private void updateProcessorText() {
        processorTV.setText(String.format(getResources().getString(R.string.coralScored), processorScored));
    }

    private void updateNetText() {
        netTV.setText(String.format(getResources().getString(R.string.coralScored), netScored));
    }

    public void csvMake() {
        //adds the strings
        String CSVLine = String.format(
                "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                tele,
                l4Scored,
                l3Scored,
                l2Scored,
                l1Scored,
                processorScored,
                netScored,
                reefPickup,
                canLeave,
                coralPickup
        );
        //makes the file
        File csvFile = new File(this.getFilesDir(), eventString+".csv");
        Log.d("CSVFile", "File created/written at: " + csvFile.getAbsolutePath());
        //writes to file
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append(CSVLine).append("\n");
            Log.d("CSVFilePath", csvFile.getAbsolutePath());
        } catch (IOException e) {
            Log.d("CSVFail", "CSV didn't make");
        }
    }
}
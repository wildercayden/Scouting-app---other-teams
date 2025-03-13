package com.example.scoutingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
    private int l4Scored = 0;
    private int l3Scored = 0;
    private int l2Scored = 0;
    private int l1Scored = 0;
    private int processorScored = 0;
    private int netScored = 0;

    private boolean reefPickup = false;
    private boolean canLeave = false;
    private boolean coralPickup = false;
    private boolean alliance = true;

    private Button l4Button;
    private Button l3Button;
    private Button l2Button;
    private Button l1Button;
    private Button processorButton;
    private Button netButton;
    private String eventString, matchString, TeamString, startingPostionString;
    public static final String Event_Key = "EVENTCONFIRM";
    public static final String Match_key = "MATCHCONFIRM";
    public static final String Team_key = "TEAMCONFIRM";
    public static final String Postion_key = "POSTIONKEY";
    public static final String Alliance_key= "ALLIANCECONFIRM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tele);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intentinput = getIntent();
        eventString = intentinput.getStringExtra(AutoActivity.Event_Key);
        matchString = intentinput.getStringExtra(AutoActivity.Match_key);
        TeamString = intentinput.getStringExtra(AutoActivity.Team_key);
        startingPostionString = intentinput.getStringExtra(AutoActivity.Postion_key);
        alliance = intentinput.getBooleanExtra(AutoActivity.Alliance_key, false);
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


        l4Button = (Button) findViewById(R.id.button_L4);
        l3Button = (Button) findViewById(R.id.button_L3);
        l2Button = (Button) findViewById(R.id.button_L2);
        l1Button = (Button) findViewById(R.id.button_L1);
        processorButton = (Button) findViewById(R.id.button_Processor);
        netButton = (Button) findViewById(R.id.button_Net);
        Button nextButton = (Button) findViewById(R.id.button_Next);
        Button backButton = (Button) findViewById(R.id.BackButton);

        CheckBox coralPickupBox = (CheckBox) findViewById(R.id.cb_coralPickup);
        CheckBox reefPickupBox = (CheckBox) findViewById(R.id.cb_algaeReef);
        CheckBox canLeaveBox = (CheckBox) findViewById(R.id.cb_CanLeave);

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
            Intent intent = new Intent(this, TeleActivity.class);
            intent.putExtra(Event_Key, eventString);
            intent.putExtra(Match_key, matchString);
            intent.putExtra(Team_key, TeamString);
            intent.putExtra(Postion_key, startingPostionString);
            intent.putExtra(Alliance_key, alliance);
            startActivity(intent);
            return true;
        });

        backButton.setOnLongClickListener((v) -> {
            Intent intent = new Intent(this, startingActivity.class);
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
        l4Button.setText(String.format(getResources().getString(R.string.coralScored), "L4 : ", l4Scored));
    }

    private void updateL3Text() {
        l3Button.setText(String.format(getResources().getString(R.string.coralScored), "L3 : ", l3Scored));
    }

    private void updateL2Text() {
        l2Button.setText(String.format(getResources().getString(R.string.coralScored), "L2 : ", l2Scored));
    }

    private void updateL1Text() {
        l1Button.setText(String.format(getResources().getString(R.string.coralScored), "L1 : ", l1Scored));
    }

    private void updateProcessorText() {
        processorButton.setText(String.format(getResources().getString(R.string.coralScored), "Processor\n", processorScored));
    }

    private void updateNetText() {
        netButton.setText(String.format(getResources().getString(R.string.coralScored), "Net\n", netScored));
    }

    public void csvMake() {
        //adds the strings
        String CSVLine = String.format(
                "Tele,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                l4Scored,
                l3Scored,
                l2Scored,
                l1Scored,
                processorScored,
                netScored,
                reefPickup,
                canLeave,
                coralPickup,
                startingPostionString
        );

        //makes the file
        File csvFile = new File(this.getFilesDir(), eventString + matchString + TeamString + ".csv");
        Log.d("CSVFile", "File created/written at: " + csvFile.getAbsolutePath());
        //writes to file
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append(CSVLine).append(",");
            Log.d("CSVFilePath", csvFile.getAbsolutePath());
        } catch (IOException e) {
            Log.d("CSVFail", "CSV didn't make");
        }
    }
}

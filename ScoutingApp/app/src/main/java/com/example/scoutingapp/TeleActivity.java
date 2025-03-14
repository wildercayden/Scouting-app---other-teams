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
    private MatchData matchData;

    private Button l4Button;
    private Button l3Button;
    private Button l2Button;
    private Button l1Button;
    private Button processorButton;
    private Button netButton;

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


        this.matchData = MainActivity.matchData;

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
            matchData.settL4(matchData.gettL4() + 1);
            updateL4Text();
        });
        //when held, subtract 1 from scored and update the text for L4
        l4Button.setOnLongClickListener((v) -> {
            matchData.settL4(matchData.gettL4() - 1);
            updateL4Text();
            return true;
        });

        l3Button.setOnClickListener((v) -> {
            matchData.settL3(matchData.gettL3() + 1);
            updateL3Text();
        });
        l3Button.setOnLongClickListener((v) -> {
            matchData.settL3(matchData.gettL3() - 1);
            updateL3Text();
            return true;
        });

        l2Button.setOnClickListener((v) -> {
            matchData.settL2(matchData.gettL2() + 1);
            updateL2Text();
        });
        l2Button.setOnLongClickListener((v) -> {
            matchData.settL2(matchData.gettL2() - 1);
            updateL2Text();
            return true;
        });

        l1Button.setOnClickListener((v) -> {
            matchData.settL1(matchData.gettL1() + 1);
            updateL1Text();
        });
        l1Button.setOnLongClickListener((v) -> {
            matchData.settL1(matchData.gettL1() - 1);
            updateL1Text();
            return true;
        });

        processorButton.setOnClickListener((v) -> {
            matchData.settProcessor(matchData.gettProcessor() + 1);
            updateProcessorText();
        });
        processorButton.setOnLongClickListener((v) -> {
            matchData.settProcessor(matchData.gettProcessor() - 1);
            updateProcessorText();
            return true;
        });

        netButton.setOnClickListener((v) -> {
            matchData.settNet(matchData.gettNet() + 1);
            updateNetText();
        });
        netButton.setOnLongClickListener((v) -> {
            matchData.settNet(matchData.gettNet() - 1);
            updateNetText();
            return true;
        });

        canLeaveBox.setOnClickListener((v) -> matchData.settDied(canLeaveBox.isChecked()));

        reefPickupBox.setOnClickListener((v) -> matchData.settReefPickup(reefPickupBox.isChecked()));

        coralPickupBox.setOnClickListener((v) -> matchData.settCoralPickup(coralPickupBox.isChecked()));

        nextButton.setOnLongClickListener((v) -> {
            //submit data
            //CSVmake();
            Intent intent = new Intent(this, EndActivity.class);
            startActivity(intent);
            return true;
        });

        backButton.setOnLongClickListener((v) -> {
            Intent intent = new Intent(this, AutoActivity.class);
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
        l4Button.setText(String.format(getResources().getString(R.string.coralScored), "L4 : ", matchData.gettL4()));
    }

    private void updateL3Text() {
        l3Button.setText(String.format(getResources().getString(R.string.coralScored), "L3 : ", matchData.gettL3()));
    }

    private void updateL2Text() {
        l2Button.setText(String.format(getResources().getString(R.string.coralScored), "L2 : ", matchData.gettL2()));
    }

    private void updateL1Text() {
        l1Button.setText(String.format(getResources().getString(R.string.coralScored), "L1 : ", matchData.gettL1()));
    }

    private void updateProcessorText() {
        processorButton.setText(String.format(getResources().getString(R.string.coralScored), "Processor\n", matchData.gettProcessor()));
    }

    private void updateNetText() {
        netButton.setText(String.format(getResources().getString(R.string.coralScored), "Net\n", matchData.gettNet()));
    }
}
package com.example.scoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AutoActivity extends AppCompatActivity {
    private int l4Scored = 0;
    private int l3Scored = 0;
    private int l2Scored = 0;
    private int l1Scored = 0;
    private int processorScored = 0;
    private int netScored = 0;
    private int startingLocation;

    private boolean reefPickup = false;
    private boolean canLeave = false;
    private boolean coralPickup = false;

    private Button l4Button;
    private Button l3Button;
    private Button l2Button;
    private Button l1Button;
    private Button processorButton;
    private Button netButton;
    private Button nextButton;

    private CheckBox canLeaveBox;
    private CheckBox reefPickupBox;
    private CheckBox coralPickupBox;

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

        l4Button = (Button) findViewById(R.id.button_L4);
        l3Button = (Button) findViewById(R.id.button_L3);
        l2Button = (Button) findViewById(R.id.button_L2);
        l1Button = (Button) findViewById(R.id.button_L1);
        processorButton = (Button) findViewById(R.id.button_Processor);
        netButton = (Button) findViewById(R.id.button_Net);

        l4Button.setOnClickListener((v) -> l4Scored++);
        l4Button.setOnLongClickListener((v) -> {
            l4Scored--;
            //vibration
            return true;
        });

        l3Button.setOnClickListener((v) -> l3Scored++);
        l3Button.setOnLongClickListener((v) -> {
            l3Scored--;
            //vibration
            return true;
        });

        l2Button.setOnClickListener((v) -> l2Scored++);
        l2Button.setOnLongClickListener((v) -> {
            l2Scored--;
            //vibration
            return true;
        });

        l1Button.setOnClickListener((v) -> l1Scored++);
        l1Button.setOnLongClickListener((v) -> {
            l1Scored--;
            //vibration
            return true;
        });

        processorButton.setOnClickListener((v) -> processorScored++);
        processorButton.setOnLongClickListener((v) -> {
            processorScored--;
            //vibration
            return true;
        });

        netButton.setOnClickListener((v) -> netScored++);
        netButton.setOnLongClickListener((v) -> {
            netScored--;
            //vibration
            return true;
        });

        canLeaveBox.setOnClickListener((v) -> canLeave = canLeaveBox.isChecked());

        reefPickupBox.setOnClickListener((v) -> reefPickup = reefPickupBox.isChecked());

        coralPickupBox.setOnClickListener((v) -> coralPickup = coralPickupBox.isChecked());

        nextButton.setOnLongClickListener((v) -> {
            //submit data
            Intent intent = new Intent(this, TeleActivity.class);
            startActivity(intent);
            return true;
        });
    }
}
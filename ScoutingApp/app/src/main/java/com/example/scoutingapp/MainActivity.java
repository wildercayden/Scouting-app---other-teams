package com.example.scoutingapp;

import android.content.Intent;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    public static MatchData matchData;

    private String red1;
    private String red2;
    private String red3;
    private String blue1;
    private String blue2;
    private String blue3;

    InputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        matchData = new MatchData();

        EditText matchNumber = (EditText) findViewById(R.id.Match);
        EditText scoutNameTextBox = (EditText) findViewById(R.id.scoutNameText);

        Button nextButton = (Button) findViewById(R.id.ButtonNext);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchData.setScoutName(scoutNameTextBox.getText().toString());

                if (matchData.getEventName().isEmpty() ||
                        matchData.getMatchNumber() == 0 ||
                        matchData.getTeamNumber().equals("0000") ||
                        matchData.getScoutName().isEmpty() ||
                        matchData.getScoutName().equals("NO NAME PROVIDED")) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Fill in EVERYTHING",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    makeIntent();
                }
            }

        });

        RadioButton r1Button = findViewById(R.id.red1Button);
        RadioButton r2Button = findViewById(R.id.red2Button);
        RadioButton r3Button = findViewById(R.id.red3Button);

        RadioButton b1Button = findViewById(R.id.blue1Button);
        RadioButton b2Button = findViewById(R.id.blue2Button);
        RadioButton b3Button = findViewById(R.id.blue3Button);

        r1Button.setEnabled(false);
        r2Button.setEnabled(false);
        r3Button.setEnabled(false);

        b1Button.setEnabled(false);
        b2Button.setEnabled(false);
        b3Button.setEnabled(false);

        r1Button.setChecked(false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button getTeamsButton = (Button) findViewById(R.id.getTeamsButton);
        getTeamsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(matchNumber.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill in MATCH NUMBER for teams", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        matchData.setMatchNumber(Integer.parseInt(matchNumber.getText().toString()));

                        stream = getJSON("https://www.thebluealliance.com/api/v3/match/2025melew_qm" + matchData.getMatchNumber());

                        JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(stream));
                        JsonObject teamsJSON = jsonElement.getAsJsonObject();
                        JsonArray blueTeamsArray = teamsJSON.get("alliances")
                                .getAsJsonObject().get("blue").
                                getAsJsonObject().get("team_keys").getAsJsonArray();
                        Log.d("test", "why so serious");
                        //.asList()
                        //.get(0).
                        //toString();

                        blue1 = blueTeamsArray.get(0).getAsString();
                        blue1 = blue1.substring(3);
                        blue2 = blueTeamsArray.get(1).getAsString();
                        blue2 = blue2.substring(3);
                        blue3 = blueTeamsArray.get(2).getAsString();
                        blue3 = blue3.substring(3);

                        JsonArray redTeamsArray = teamsJSON.get("alliances")
                                .getAsJsonObject()
                                .get("red").getAsJsonObject()
                                .get("team_keys")
                                .getAsJsonArray();

                        red1 = redTeamsArray.get(0).getAsString();
                        red1 = red1.substring(3);
                        red2 = redTeamsArray.get(1).getAsString();
                        red2 = red2.substring(3);
                        red3 = redTeamsArray.get(2).getAsString();
                        red3 = red3.substring(3);

                        r1Button.setText(red1);
                        r2Button.setText(red2);
                        r3Button.setText(red3);

                        b1Button.setText(blue1);
                        b2Button.setText(blue2);
                        b3Button.setText(blue3);

                        r1Button.setEnabled(true);
                        r2Button.setEnabled(true);
                        r3Button.setEnabled(true);

                        b1Button.setEnabled(true);
                        b2Button.setEnabled(true);
                        b3Button.setEnabled(true);
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Invalid Match Number", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        r1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1Button.setChecked(true);
                r2Button.setChecked(false);
                r3Button.setChecked(false);
                b1Button.setChecked(false);
                b2Button.setChecked(false);
                b3Button.setChecked(false);

                matchData.setBlueAlliance(false);
                matchData.setTeamNumber(red1);
            }
        });

        r2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1Button.setChecked(false);
                r2Button.setChecked(true);
                r3Button.setChecked(false);
                b1Button.setChecked(false);
                b2Button.setChecked(false);
                b3Button.setChecked(false);
                Log.d("TESTY", "GIT");
                matchData.setBlueAlliance(false);
                Log.d("TESTY", "GITTEST");
                matchData.setTeamNumber(red2);
                Log.d("TESTY", "GITTY");
            }
        });

        r3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1Button.setChecked(false);
                r2Button.setChecked(false);
                r3Button.setChecked(true);
                b1Button.setChecked(false);
                b2Button.setChecked(false);
                b3Button.setChecked(false);

                matchData.setBlueAlliance(false);
                matchData.setTeamNumber(red3);
            }
        });

        b1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1Button.setChecked(false);
                r2Button.setChecked(false);
                r3Button.setChecked(false);
                b1Button.setChecked(true);
                b2Button.setChecked(false);
                b3Button.setChecked(false);

                matchData.setBlueAlliance(true);
                matchData.setTeamNumber(blue1);
            }
        });

        b2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1Button.setChecked(false);
                r2Button.setChecked(false);
                r3Button.setChecked(false);
                b1Button.setChecked(false);
                b2Button.setChecked(true);
                b3Button.setChecked(false);
                Log.d("TESTY", "GIT");
                matchData.setBlueAlliance(true);
                matchData.setTeamNumber(blue2);
            }
        });

        b3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1Button.setChecked(false);
                r2Button.setChecked(false);
                r3Button.setChecked(false);
                b1Button.setChecked(false);
                b2Button.setChecked(false);
                b3Button.setChecked(true);

                matchData.setBlueAlliance(true);
                matchData.setTeamNumber(blue3);
            }
        });
    }

    public InputStream getJSON(String path){

        InputStream stream = null;
        try{

            URL url = new URL(path);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "TBA_API");
            connection.setRequestProperty("X-TBA-Auth-Key", "0zxxGYSvY7xI2onqcWg0NT0sEtmtR6hCpmYJ29nwfxvqrP3Mf1M3lRZO5x6Kc3kt");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches(false);

            stream = (InputStream)connection.getInputStream();
        }
        catch (Exception e){
            Log.d("test", "inside catch");
            e.printStackTrace();
        }
        return stream;
    }

    private void makeIntent() {
        Intent intent = new Intent(this, startingActivity.class);
        startActivity(intent);
    }
}
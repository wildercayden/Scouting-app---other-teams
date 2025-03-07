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
    private EditText matchNumber;
    private String matchNumberString;
    private String eventString = "2025melew";
    private String teamString;
    private String allianceString;
    public static final String postionKey = "POSTIONKEY";
    public static final String eventKey = "EVENTCONFIRM";
    public static final String matchKey = "MATCHCONFIRM";
    public static final String teamKey = "TEAMCONFIRM";
    public static final String allianceKey = "ALLIANCEKEY";
    public String fein;

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
        matchNumber = (EditText) findViewById(R.id.Match);
        // = (EditText) findViewById(R.id.Event);
        Button nextButton = (Button) findViewById(R.id.ButtonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchNumberString = matchNumber.getText().toString();
                if ((matchNumberString == null || matchNumberString.isEmpty()) ||
                        (eventString == null || eventString.isEmpty()) ||
                        (teamString == null || teamString.isEmpty())
                        ){
                    Toast.makeText(getApplicationContext(), "Fill in EVERYTHING", Toast.LENGTH_SHORT).show();
                }else {
                    makeIntent();
                }
            }

        });
        Button submit = (Button) findViewById(R.id.Submit_button);
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Submit submit = new Submit();
                submit.uploadSheets(MainActivity.this, eventString+matchNumberString+teamString+".csv");
            }
        });

        RadioButton r1Button = findViewById(R.id.red1Button);
        RadioButton r2Button = findViewById(R.id.red2Button);
        RadioButton r3Button = findViewById(R.id.red3Button);

        RadioButton b1Button = findViewById(R.id.blue1Button);
        RadioButton b2Button = findViewById(R.id.blue2Button);
        RadioButton b3Button = findViewById(R.id.blue3Button);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button getTeamsButton = (Button) findViewById(R.id.getTeamsButton);
        getTeamsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(matchNumber.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill in EVERYTHING", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        matchNumberString = matchNumber.getText().toString();

                        stream = getJSON("https://www.thebluealliance.com/api/v3/match/2024melew_qm" + matchNumberString);

                        JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(stream));
                        JsonObject teamsJSON = jsonElement.getAsJsonObject();
                        JsonArray blueTeamsArray = teamsJSON.get("alliances")
                                .getAsJsonObject().get("blue").
                                getAsJsonObject().get("team_keys").getAsJsonArray();
                        Log.d("test", "why so serious");
                        //.asList()
                        //.get(0).
                        //toString();

                        String blue1 = blueTeamsArray.get(0).getAsString();
                        blue1 = blue1.substring(3);
                        String blue2 = blueTeamsArray.get(1).getAsString();
                        blue2 = blue2.substring(3);
                        String blue3 = blueTeamsArray.get(2).getAsString();
                        blue3 = blue3.substring(3);

                        JsonArray redTeamsArray = teamsJSON.get("alliances")
                                .getAsJsonObject()
                                .get("red").getAsJsonObject()
                                .get("team_keys")
                                .getAsJsonArray();

                        String red1 = redTeamsArray.get(0).getAsString();
                        red1 = red1.substring(3);
                        String red2 = redTeamsArray.get(1).getAsString();
                        red2 = red2.substring(3);
                        String red3 = redTeamsArray.get(2).getAsString();
                        red3 = red3.substring(3);

                        r1Button.setText(red1);
                        r2Button.setText(red2);
                        r3Button.setText(red3);

                        b1Button.setText(blue1);
                        b2Button.setText(blue2);
                        b3Button.setText(blue3);
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Fill in EVERYTHING", Toast.LENGTH_SHORT).show();
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

                allianceString = "red";
                teamString = r1Button.getText().toString();
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

                allianceString = "red";
                teamString = r2Button.getText().toString();
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

                allianceString = "red";
                teamString = r3Button.getText().toString();
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

                allianceString = "blue";
                teamString = b1Button.getText().toString();
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

                allianceString = "blue";
                teamString = b2Button.getText().toString();
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

                allianceString = "blue";
                teamString = b3Button.getText().toString();
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

    private void makeIntent()
    {
        Intent intent = new Intent(this, AutoActivity.class);
        intent.putExtra(eventKey, eventString);
        intent.putExtra(matchKey, matchNumberString);
        intent.putExtra(teamKey, teamString);
        intent.putExtra(allianceKey, allianceString);
        startActivity(intent);
    }
}
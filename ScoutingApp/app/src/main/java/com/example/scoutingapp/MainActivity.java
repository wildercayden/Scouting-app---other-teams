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
    private EditText Match_number;
    private EditText Event;
    private EditText Team;
    private String Match_numberString;
    private String EventString;
    private String TeamString;
    private String startingPostionString;
    public static final String Postion_key = "POSTIONKEY";
    public static final String Event_Key = "EVENTCONFIRM";
    public static final String Match_key = "MATCHCONFIRM";
    public static final String Team_key = "TEAMCONFIRM";
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
        Match_number = (EditText) findViewById(R.id.Match);
        // = (EditText) findViewById(R.id.Event);
        Button nextButton = (Button) findViewById(R.id.ButtonNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Match_numberString = Match_number.getText().toString();
                EventString = Event.getText().toString();
                TeamString = Team.getText().toString();
                if ((Match_numberString == null || Match_numberString.isEmpty()) ||
                        (EventString == null || EventString.isEmpty()) ||
                        (TeamString == null || TeamString.isEmpty()) ||
                        (startingPostionString == null || startingPostionString.isEmpty())){
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
                submit.uploadSheets(MainActivity.this, EventString+Match_number+TeamString+".csv");
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        stream = getJSON("https://www.thebluealliance.com/api/v3/match/2025melew_qm" + Match_numberString);

        JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(stream));
        JsonObject teamsJSON = jsonElement.getAsJsonObject();
        JsonArray blueTeamsArray = teamsJSON.get("alliances")
                .getAsJsonObject().get("blue").
                getAsJsonObject().get("team_keys").getAsJsonArray();
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

        RadioButton r1Button = findViewById(R.id.red1Button);
        r1Button.setText(red1);
        RadioButton r2Button = findViewById(R.id.red2Button);
        r2Button.setText(red2);
        RadioButton r3Button = findViewById(R.id.red3Button);
        r3Button.setText(red3);

        RadioButton b1Button = findViewById(R.id.blue1Button);
        b1Button.setText(blue1);
        RadioButton b2Button = findViewById(R.id.blue2Button);
        b2Button.setText(blue2);
        RadioButton b3Button = findViewById(R.id.blue3Button);
        b3Button.setText(blue3);

        r1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r1Button.setChecked(true);
                r2Button.setChecked(false);
                r3Button.setChecked(false);
                b1Button.setChecked(false);
                b2Button.setChecked(false);
                b3Button.setChecked(false);
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
            //Log.d("test", "after assigned stream");
            //Log.d("test", stream.toString());
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
        intent.putExtra(Event_Key, EventString);
        intent.putExtra(Match_key, Match_numberString);
        intent.putExtra(Team_key, TeamString);
        intent.putExtra(Postion_key, startingPostionString);
        startActivity(intent);
    }
}
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
        Event = (EditText) findViewById(R.id.Event);
        Team = (EditText) findViewById(R.id.TeamNumber);
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
        stream = getJSON("https://www.thebluealliance.com/api/v3/match/2024melew_qm1");

        JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(stream));
        JsonObject teamsJSON = jsonElement.getAsJsonObject();
        String blueTeams = teamsJSON.get("alliances").getAsJsonObject().get("blue").getAsJsonObject().get("team_keys").toString();
        String redTeams = teamsJSON.get("alliances").getAsJsonObject().get("blue").getAsJsonObject().get("team_keys").toString();

        RadioButton r1Button = findViewById(R.id.red1Button);
        r1Button.setText(blueTeams);
        RadioButton r2Button = findViewById(R.id.red2Button);
        RadioButton r3Button = findViewById(R.id.red3Button);

        RadioButton b1Button = findViewById(R.id.red1Button);
        RadioButton b2Button = findViewById(R.id.red2Button);
        RadioButton b3Button = findViewById(R.id.red3Button);
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
package com.example.scoutingapp;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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

    AsynchronousGet getTBAInfo;

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
        Button nextButton = (Button) findViewById(R.id.nextButton);
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

        RadioButton Rbutton1 = findViewById(R.id.Rbutton1);
        Rbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startingPostionString = "1";
            }
        });
        RadioButton Rbutton2 = findViewById(R.id.Rbutton2);
        Rbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startingPostionString = "2";
            }
        });
        RadioButton Rbutton3 = findViewById(R.id.Rbutton3);
        Rbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startingPostionString = "3";
            }
        });
        RadioButton Rbutton4 = findViewById(R.id.Rbutton4);
        Rbutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startingPostionString = "4";
            }
        });
        RadioButton Rbutton5 = findViewById(R.id.Rbutton5);
        Rbutton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startingPostionString = "5";
            }
        });

        TextView TBAView = (TextView)findViewById(R.id.TBATest);

       // getTBAInfo = new AsynchronousGet();

        //try {

            //TBAView.setText(getTBAInfo.getMatchTeams("melew", 1)[0][1]);
        //} catch (Exception e) {
        //    throw new RuntimeException(e);
        //}

    }
    public final class AsynchronousGet {
        private final OkHttpClient client = new OkHttpClient();

        public String[][] getMatchTeams(String eventKey, int qualMatchNum) throws Exception {
            // https://www.thebluealliance.com/api/v3/event/2024melew/teams?X-TBA-Auth-Key=0zxxGYSvY7xI2onqcWg0NT0sEtmtR6hCpmYJ29nwfxvqrP3Mf1M3lRZO5x6Kc3kt
            // https://www.thebluealliance.com/api/v3/match/2024melew_qm1?X-TBA-Auth-Key=0zxxGYSvY7xI2onqcWg0NT0sEtmtR6hCpmYJ29nwfxvqrP3Mf1M3lRZO5x6Kc3kt

            Request request = new Request.Builder()
                    .url("https://www.thebluealliance.com/api/v3/match/2024melew_qm1?X-TBA-Auth-Key=0zxxGYSvY7xI2onqcWg0NT0sEtmtR6hCpmYJ29nwfxvqrP3Mf1M3lRZO5x6Kc3kt")
                    .build();
/*
            try(Response response = client.newCall(request).enqueue(new Callback())){
                if(!response.isSuccessful()) throw new IOException("unexpected code " + response);
                Log.d("test", "inside if statement");
                Headers responseHeaders = response.headers();
                for(int i = 0; i < responseHeaders.size(); i++){
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                fein = response.body().string();
            }
*/
            Log.d("test", "before enqueue");
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("test", "before try");
                    ResponseBody responseBody = response.body();
                    Log.d("test", responseBody.string());
                   //try (ResponseBody responseBody1 = response.body()) {
                    //    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }

                        fein = responseBody.string();
                        responseBody.close();
                        Log.d("test", "hehehehaw");

                }

                @Override
                public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    Log.d("test", "on fail");
                }

            });

            JSONObject teamsJSON = new JSONObject();
            JSONArray blueTeamsJSON = teamsJSON.getJSONObject("alliances").getJSONObject("blue").getJSONArray("team_keys");
            JSONArray redTeamsJSON = teamsJSON.getJSONObject("alliances").getJSONObject("blue").getJSONArray("team_keys");

            //String[] blueTeams = {blueTeamsJSON.getString(0), blueTeamsJSON.getString(1), blueTeamsJSON.getString(2)};
            //String[] redTeams = {redTeamsJSON.getString(0), redTeamsJSON.getString(1), redTeamsJSON.getString(2)};

            Log.d("test", "before return");

            return new String[][]{{""}, {""}};
        }




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
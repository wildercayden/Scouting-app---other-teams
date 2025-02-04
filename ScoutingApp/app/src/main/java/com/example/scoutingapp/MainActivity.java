package com.example.scoutingapp;

import android.content.Intent;
import static java.sql.DriverManager.println;

import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

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
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeIntent();
            }

        });

        TextView TBAView = (TextView)findViewById(R.id.TBATest);

        getTBAInfo = new AsynchronousGet();

        try {

            TBAView.setText(getTBAInfo.getMatchTeams("MELEW", 1)[0][1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public final class AsynchronousGet {
        private final OkHttpClient client = new OkHttpClient();

        public String[][] getMatchTeams(String eventKey, int qualMatchNum) throws Exception {
            // https://www.thebluealliance.com/api/v3/event/2024melew/teams?X-TBA-Auth-Key=0zxxGYSvY7xI2onqcWg0NT0sEtmtR6hCpmYJ29nwfxvqrP3Mf1M3lRZO5x6Kc3kt
            // https://www.thebluealliance.com/api/v3/match/2024melew_qm1?X-TBA-Auth-Key=0zxxGYSvY7xI2onqcWg0NT0sEtmtR6hCpmYJ29nwfxvqrP3Mf1M3lRZO5x6Kc3kt

            String fein;

            Request request = new Request.Builder()
                    .url("https://www.thebluealliance.com/api/v3/match/2024melew_qm1?X-TBA-Auth-Key=0zxxGYSvY7xI2onqcWg0NT0sEtmtR6hCpmYJ29nwfxvqrP3Mf1M3lRZO5x6Kc3kt")
                    .build();

            try(Response response = client.newCall(request).enqueue(new Callback())){
                if(!response.isSuccessful()) throw new IOException("unexpected code " + response);
                Log.d("test", "inside if statement");
                Headers responseHeaders = response.headers();
                for(int i = 0; i < responseHeaders.size(); i++){
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                fein = response.body().string();
            }

            JSONObject teamsJSON = new JSONObject(fein);
            JSONArray blueTeamsJSON = teamsJSON.getJSONObject("alliances").getJSONObject("blue").getJSONArray("team_keys");
            JSONArray redTeamsJSON = teamsJSON.getJSONObject("alliances").getJSONObject("blue").getJSONArray("team_keys");

            String[] blueTeams = {blueTeamsJSON.getString(0), blueTeamsJSON.getString(1), blueTeamsJSON.getString(2)};
            String[] redTeams = {redTeamsJSON.getString(0), redTeamsJSON.getString(1), redTeamsJSON.getString(2)};

            return new String[][]{blueTeams, redTeams};
        }
        

    }

    private void makeIntent()
    {
        Intent intent = new Intent(this, AutoActivity.class);
        startActivity(intent);
    }
}
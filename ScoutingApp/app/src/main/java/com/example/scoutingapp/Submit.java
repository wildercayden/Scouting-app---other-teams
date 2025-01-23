package com.example.scoutingapp;

import android.content.Context;
import android.util.Log;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.common.collect.Lists;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Submit {
    private String teamNumber;
    private String matchNumber;
    private String Taxiing;
    private String autoCoral;
    private String autoAlgea;
    private String floorPickup;
    private String HPPickup;
    private String startingLocation;
    private String teleopCoral;
    private String teleopAlgea;
    private String HPScore;
    private String teleopFloorPickup;
    private String Endgame;
    private String Climb;
    private String Notes;

    public String getTeamNumberTest()
    {   teamNumber = "2647";
        return teamNumber;
    }

    public void CSVmake(Context context) {
        //adds the strings
        String CSVLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                teamNumber, matchNumber, Taxiing, autoCoral, autoAlgea, floorPickup, HPPickup,
                startingLocation, teleopCoral, teleopAlgea, HPScore, teleopFloorPickup, Endgame, Climb, Notes);
        //makes the file
        File csvFile = new File(context.getExternalFilesDir(null), "match_data.csv");
        //writes to file
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append(CSVLine).append("\n");
            Log.d("CSVFilePath", csvFile.getAbsolutePath());
        } catch (IOException e) {
            Log.d("CSVFail", "CSV didn't make");
        }
    }

    void uploadCSV(Context context) {
        new Thread(() -> {
            try {
                //adds account info
                InputStream serviceAccountStream = context.getResources().openRawResource(R.raw.info);
                ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
                //Srts up the google sheet API and json factury for use later
                Sheets sheetsService = new Sheets.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        new HttpCredentialsAdapter(credentials)
                ).setApplicationName("Scouting App").build();

                //make sure the file is there
                File csvFile = new File(context.getExternalFilesDir(null), "match_data.csv");
                if (!csvFile.exists()) {
                    Log.d("CSVError", "CSV file does not exist.");
                    return;
                }
                //uses parseCSVToList make the CSV file into a list for google sheet
                List<List<Object>> data = parseCSVToList(csvFile);
                //data for the sheet API 
                ValueRange body = new ValueRange().setValues(data);
                //the ID for the google sheet
                String spreadsheetId = "1WqULxMRuXV97sUy01hdy8hiDIud1M4j5oNpWWHDsvcI";
                //starting point
                String range = "Sheet1!a2:O2";
                //inserts data to the sheet 
                sheetsService.spreadsheets().values()
                .append(spreadsheetId, range, body)
                        .setValueInputOption("RAW")
                        .setInsertDataOption("INSERT_ROWS")
                        .execute();

                Log.d("GoogleSheets", "Data uploaded to Google Sheets successfully.");
            } catch (Exception e) {
                Log.d("GoogleSheets", "Error uploading data", e);
            }
        }).start();
    }

    List<List<Object>> parseCSVToList(File csvFile) {
        //makes A array
        List<List<Object>> data = Lists.newArrayList();
        //Reads the info from the CSV file and makes it usedable to upload to the google sheet
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Object> row = Arrays.asList(line.split(","));
                data.add(row);
            }
        } catch (IOException e) {
            Log.d("parseCSVToList", "Error with BufferredReader");
        }
        return data;
    }

    public void deleteCSVFile(Context context) {
        //Finds the file
        File csvFile = new File(context.getExternalFilesDir(null), "match_data.csv");
        
        //Checks if the file exists
        if (csvFile.exists()) {
            //Tries to delete file
            if (csvFile.delete()) {
                Log.d("CSVDelete", "match_data.csv deleted successfully.");
            } else {
                Log.d("CSVDelete", "Failed to delete match_data.csv.");
            }
        } else {
            Log.d("CSVDelete", "match_data.csv does not exist.");
        }
    }
}

package com.example.scoutingapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Submit {

    void uploadSheets(Context context, String csvFileString) {
        new Thread(() -> {
            try {
                //adds account info
                InputStream serviceAccountStream = context.getResources().openRawResource(R.raw.info);
                ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
                //Sets up the google sheet API and json factory for use later
                Sheets sheetsService = new Sheets.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        new HttpCredentialsAdapter(credentials)
                ).setApplicationName("Scouting App").build();

                //make sure the file is there
                File csvFile = new File(context.getFilesDir(), csvFileString);
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
        return data;
    }

    public void deleteCSVFile(Context context) {
        // Get the directory containing the files
        File directory = context.getFilesDir();

        // Get all files in the directory
        File[] files = directory.listFiles();

        if (files != null) {
            // Iterate through each file in the directory
            for (File file : files) {
                // Deletes the file
                if (file.delete()) {
                    Log.d("CSVDelete", file.getName() + " deleted successfully.");
                } else {
                    Log.d("CSVDelete", "Failed to delete " + file.getName());
                }
            }
        } else {
            Log.d("CSVDelete", "No files found in the directory.");
        }
    }
    public void renameFile(Context context,  String csvFileString){
        File csvFile = new File(context.getFilesDir(), csvFileString + ".csv");

// Then perform renaming
        File newFile = new File(context.getFilesDir(), csvFileString+".csv");

        if (csvFile.renameTo(newFile)) {
            Log.d("CSVRename", "File renamed successfully to: " + newFile.getAbsolutePath());
        } else {
            Log.d("CSVRenameFail", "File renaming failed.");
        }

        // Define the new file name
        File renamedFile = new File(context.getFilesDir(), "old"+csvFileString+".csv");

// Rename the original file
        if (csvFile.renameTo(newFile)) {
            Log.d("CSVRename", "File renamed successfully to: " + renamedFile.getAbsolutePath());
        } else {
            Log.d("CSVRenameFail", "File renaming failed.");
        }

    }
}

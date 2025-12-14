package com.example.scoutingapp;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

public class MatchData {
    //Main Page
    private String scoutName;
    private String eventName;
    //Initial Page
    private int matchNumber = 0;
    private String teamNumber;
    private int startingPosition = 0;
    private boolean isBlueAlliance = false;
    //Auto Page
    private int aL1 = 0;
    private int aL2 = 0;
    private int aL3 = 0;
    private int aL4 = 0;
    private int aNet = 0;
    private int aProcessor = 0;
    private boolean aLeave = false;
    private boolean aReefPickup = false;
    private boolean aCoralPickup = false;
    //Teleop Page
    private int tL1 = 0;
    private int tL2 = 0;
    private int tL3 = 0;
    private int tL4 = 0;
    private int tNet = 0;
    private int tProcessor = 0;
    private boolean tDied;
    private boolean tReefPickup = false;
    private boolean tCoralPickup = false;
    //Endgame Page
    private int eClimb = 0;
    private String eNote;

    public MatchData() {
        scoutName = "NO NAME PROVIDED";
        eventName = "NO EVENT PROVIDED";
        teamNumber = "0000";
        eNote = "NO NOTES PROVIDED";
    }

    public String makeCSVString() {
        String[] matchData = {
                "MELEW",
                String.valueOf(matchNumber),
                teamNumber,
                "Auto",
                String.valueOf(aL4),
                String.valueOf(aL3),
                String.valueOf(aL2),
                String.valueOf(aL1),
                String.valueOf(aNet),
                String.valueOf(aProcessor),
                String.valueOf(aReefPickup),
                String.valueOf(aLeave),
                String.valueOf(aCoralPickup),
                "Tele",
                String.valueOf(tL4),
                String.valueOf(tL3),
                String.valueOf(tL2),
                String.valueOf(tL1),
                String.valueOf(tNet),
                String.valueOf(tProcessor),
                String.valueOf(tReefPickup),
                String.valueOf(tDied),
                String.valueOf(tCoralPickup),
                String.valueOf(startingPosition),
                String.valueOf(eClimb),
                eNote,
                scoutName,
                getTimestamp()
        };

        return String.join("@#@", matchData);

    }



    public String getCSVFileName() {
        return eventName + matchNumber + teamNumber + ".csv";
    }

    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss.SSS", Locale.getDefault());
        return sdf.format(new Date());
    }

    public String getScoutName() {
        return scoutName;
    }

    public void setScoutName(String scoutName) {
        this.scoutName = scoutName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public int getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(int startingPosition) {
        this.startingPosition = startingPosition;
    }

    public boolean isBlueAlliance() {
        return isBlueAlliance;
    }

    public void setBlueAlliance(boolean blueAlliance) {
        isBlueAlliance = blueAlliance;
    }

    public int getaL1() {
        return aL1;
    }

    public void setaL1(int aL1) {
        this.aL1 = Math.max(0, aL1);
    }

    public int getaL2() {
        return aL2;
    }

    public void setaL2(int aL2) {
        this.aL2 = Math.max(0, aL2);
    }

    public int getaL3() {
        return aL3;
    }

    public void setaL3(int aL3) {
        this.aL3 = Math.max(0, aL3);
    }

    public int getaL4() {
        return aL4;
    }

    public void setaL4(int aL4) {
        this.aL4 = Math.max(0, aL4);
    }

    public int getaNet() {
        return aNet;
    }

    public void setaNet(int aNet) {
        this.aNet = Math.max(0, aNet);
    }

    public int getaProcessor() {
        return aProcessor;
    }

    public void setaProcessor(int aProcessor) {
        this.aProcessor = Math.max(0, aProcessor);
    }

    public boolean isaLeave() {
        return aLeave;
    }

    public void setaLeave(boolean aLeave) {
        this.aLeave = aLeave;
    }

    public boolean isaReefPickup() {
        return aReefPickup;
    }

    public void setaReefPickup(boolean aReefPickup) {
        this.aReefPickup = aReefPickup;
    }

    public boolean isaCoralPickup() {
        return aCoralPickup;
    }

    public void setaCoralPickup(boolean aCoralPickup) {
        this.aCoralPickup = aCoralPickup;
    }

    public int gettL1() {
        return tL1;
    }

    public void settL1(int tL1) {
        this.tL1 = Math.max(0, tL1);
    }

    public int gettL2() {
        return tL2;
    }

    public void settL2(int tL2) {
        this.tL2 = Math.max(0, tL2);
    }

    public int gettL3() {
        return tL3;
    }

    public void settL3(int tL3) {
        this.tL3 = Math.max(0, tL3);
    }

    public int gettL4() {
        return tL4;
    }

    public void settL4(int tL4) {
        this.tL4 = Math.max(0, tL4);
    }

    public int gettNet() {
        return tNet;
    }

    public void settNet(int tNet) {
        this.tNet = Math.max(0, tNet);
    }

    public int gettProcessor() {
        return tProcessor;
    }

    public void settProcessor(int tProcessor) {
        this.tProcessor = Math.max(0, tProcessor);
    }

    public boolean istReefPickup() {
        return tReefPickup;
    }

    public void settReefPickup(boolean tReefPickup) {
        this.tReefPickup = tReefPickup;
    }

    public boolean istDied() {
        return tDied;
    }

    public void settDied(boolean tCanLeave) {
        this.tDied = tCanLeave;
    }

    public boolean istCoralPickup() {
        return tCoralPickup;
    }

    public void settCoralPickup(boolean tCoralPickup) {
        this.tCoralPickup = tCoralPickup;
    }

    public int geteClimb() {
        return eClimb;
    }

    public void seteClimb(int eClimb) {
        this.eClimb = eClimb;
    }

    public String geteNote() {
        return eNote;
    }

    public void seteNote(String eNote) {
        this.eNote = eNote;
    }
}

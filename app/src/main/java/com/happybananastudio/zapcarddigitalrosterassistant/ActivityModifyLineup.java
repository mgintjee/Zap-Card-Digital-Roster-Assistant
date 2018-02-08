package com.happybananastudio.zapcarddigitalrosterassistant;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by mgint on 1/9/2018.
 */

public class ActivityModifyLineup extends AppCompatActivity {


    private Context thisContext = this;
    private final String helpType = "modifyLineup";
    private boolean q3Rule = true;

    private ArrayList<String> possibleFormations;
    private ArrayList<ArrayList<String>> formationDetails;
    private ArrayList<Integer> quarterFormations;
    private int whichQuarter = 0, quarterRule = 0;

    // Final Variables to help readability
    private final int RULE_NO_Q = 0;
    private final int RULE_Q2 = 2;
    private final int RULE_Q3 = 3;
    private final int DISPLAY_LINEUP = 3;
    private final int AVAILABLE_PLAYERS = 2;
    private final int ATTENDANCE = 1;
    private final int FORMATION = 0;

    private String teamName;
    private String teamDivision;
    private ArrayList<String> rosterInfo;
    private ArrayList<ArrayList<String>> allPlayers, allGoalies, allDefenders, allMidfielders, allForwards, attendance, lineup;

    // Widgets
    private ImageButton
            imageButtonCreate, imageButtonDelete,
            imageButtonQ1, imageButtonQ2, imageButtonQ3, imageButtonQ4,
            imageButtonReturn,  imageButtonQuarterRule, imageButtonHelp,imageButtonAttendance, imageButtonLineupCard;
    private Spinner spinnerFormation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_modify_lineup);
        hideBars();
        getIntentInfo();

        initializeWidgets();
        fillSpinners();
        updateField();

        setWidgetListeners();
    }

    private void initializeWidgets(){
        initializeImageButtons();
        spinnerFormation = findViewById(R.id.Lineup_spinnerFormation);
        initializeTextViews();
    }
    private void setWidgetListeners(){
        setImageButtonListeners();
        setSpinnerListeners();
        setButtonFieldPlayersListeners();
    }
    private void initializeImageButtons() {
        // Header Image Buttons
        imageButtonCreate = findViewById(R.id.Lineup_imageButtonCreate);
        imageButtonDelete = findViewById(R.id.Lineup_imageButtonDelete);

        imageButtonQ1 = findViewById(R.id.Lineup_imageButtonQ1);
        imageButtonQ2 = findViewById(R.id.Lineup_imageButtonQ2);
        imageButtonQ3 = findViewById(R.id.Lineup_imageButtonQ3);
        imageButtonQ4 = findViewById(R.id.Lineup_imageButtonQ4);

        // Footer Image Buttons
        imageButtonReturn = findViewById(R.id.Lineup_imageButtonReturn);
        imageButtonQuarterRule = findViewById(R.id.Lineup_imageButtonQuarterRule);
        imageButtonHelp = findViewById(R.id.Lineup_imageButtonHelp);
        imageButtonAttendance = findViewById(R.id.Lineup_imageButtonAttendance);
        imageButtonLineupCard = findViewById(R.id.Lineup_imageButtonLineupCard);
    }
    private void initializeTextViews(){
        TextView tViewTeamName = findViewById(R.id.Lineup_tVTeamName);
        TextView tViewTeamDivision = findViewById(R.id.Lineup_tVTeamDivision);
        String nameTemp = teamName;
        String divTemp = teamDivision;
        tViewTeamName.setText(nameTemp);
        tViewTeamDivision.setText(divTemp);

    }
    private void fillArrays(){
        fillQuarterFormations();
        fillFormationArray();
    }
    private void fillSpinners(){
        fillFormationSpinner();
    }
    private void hideBars(){

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
    private void setImageButtonListeners(){
        setImageButtonQ1Listener();
        setImageButtonQ2Listener();
        setImageButtonQ3Listener();
        setImageButtonQ4Listener();
        setImageButtonReturnListener();
        setImageButtonQuarterRule();
        setImageButtonHelpListener();
        setImageButtonAttendanceListener();
        setImageButtonLineupCardListener();
        setImageButtonCreateListener();
        setImageButtonDeleteListener();
    }
    private void setSpinnerListeners(){
        setSpinnerFormationListener();
        //setSpinnerFieldListeners(whichQuarter);
    }
    private void setImageButtonQ1Listener(){
        imageButtonQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( whichQuarter != 0)
                {
                    whichQuarter = 0;

                    imageButtonQ1.setImageResource(R.drawable.q1_light);
                    imageButtonQ2.setImageResource(R.drawable.q2_dark);
                    imageButtonQ3.setImageResource(R.drawable.q3_dark);
                    imageButtonQ4.setImageResource(R.drawable.q4_dark);

                    refillPlayers(whichQuarter);
                    updateAvailablePlayers(whichQuarter);
                    setButtonFieldPlayersListeners();
                    updateField();
                }
            }
        });
    }
    private void setImageButtonQ2Listener(){
        imageButtonQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( whichQuarter != 1)
                {
                    whichQuarter = 1;

                    imageButtonQ1.setImageResource(R.drawable.q1_dark);
                    imageButtonQ2.setImageResource(R.drawable.q2_light);
                    imageButtonQ3.setImageResource(R.drawable.q3_dark);
                    imageButtonQ4.setImageResource(R.drawable.q4_dark);

                    refillPlayers(whichQuarter);
                    updateAvailablePlayers(whichQuarter);
                    setButtonFieldPlayersListeners();
                    updateField();
                }
            }
        });
    }
    private void setImageButtonQ3Listener(){
        imageButtonQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( whichQuarter != 2)
                {
                    whichQuarter = 2;

                    imageButtonQ1.setImageResource(R.drawable.q1_dark);
                    imageButtonQ2.setImageResource(R.drawable.q2_dark);
                    imageButtonQ3.setImageResource(R.drawable.q3_light);
                    imageButtonQ4.setImageResource(R.drawable.q4_dark);

                    refillPlayers(whichQuarter);
                    updateAvailablePlayers(whichQuarter);
                    setButtonFieldPlayersListeners();
                    updateField();
                }
            }
        });
    }
    private void setImageButtonQ4Listener(){
        imageButtonQ4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( whichQuarter != 3)
                {
                    whichQuarter = 3;

                    imageButtonQ1.setImageResource(R.drawable.q1_dark);
                    imageButtonQ2.setImageResource(R.drawable.q2_dark);
                    imageButtonQ3.setImageResource(R.drawable.q3_dark);
                    imageButtonQ4.setImageResource(R.drawable.q4_light);

                    refillPlayers(whichQuarter);
                    updateAvailablePlayers(whichQuarter);
                    setButtonFieldPlayersListeners();
                    updateField();
                }
            }
        });
    }
    private void setImageButtonReturnListener() {
        imageButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmCancel();
            }
        });
    }
    private void setImageButtonQuarterRule(){
        imageButtonQuarterRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( quarterRule == RULE_Q3 ){
                    quarterRule = RULE_NO_Q;
                    imageButtonQuarterRule.setImageResource(R.drawable.rule_no_q);
                }
                else if( quarterRule == RULE_NO_Q){
                    quarterRule = RULE_Q2;
                    imageButtonQuarterRule.setImageResource(R.drawable.rule_q2);
                }
                else{
                    quarterRule = RULE_Q3;
                    imageButtonQuarterRule.setImageResource(R.drawable.rule_q3);
                }
            }
        });
    }
    private void setImageButtonHelpListener() {
        imageButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(thisContext, ActivityHelp.class);
                intent.putExtra("type", helpType);
                startActivity(intent);
            }
        });
    }
    private void setImageButtonAttendanceListener(){
        imageButtonAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringOfPlayers = new StringBuilder("");
                Intent intent = new Intent(thisContext, ActivityModifyAttendance.class);

                intent.putStringArrayListExtra("rosterInfo", rosterInfo);
                intent.putStringArrayListExtra("q1Attendance", attendance.get(0));
                intent.putStringArrayListExtra("q2Attendance", attendance.get(1));
                intent.putStringArrayListExtra("q3Attendance", attendance.get(2));
                intent.putStringArrayListExtra("q4Attendance", attendance.get(3));


                startActivityForResult(intent, ATTENDANCE);
            }
        });
    }
    private void setImageButtonLineupCardListener(){
        imageButtonLineupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisContext, ActivityDisplayLineup.class);

                intent.putExtra("rosterInfo", rosterInfo);
                intent.putExtra("qRule", Integer.toString(quarterRule));
                intent.putExtra("validLineup", lineupValidity());

                intent.putExtra("q1Absent", attendance.get(0));
                intent.putExtra("q2Absent", attendance.get(1));
                intent.putExtra("q3Absent", attendance.get(2));
                intent.putExtra("q4Absent", attendance.get(3));

                intent.putExtra("q1Lineup", lineup.get(0));
                intent.putExtra("q2Lineup", lineup.get(1));
                intent.putExtra("q3Lineup", lineup.get(2));
                intent.putExtra("q4Lineup", lineup.get(3));

                intent.putExtra("teamName", teamName);
                intent.putExtra("teamDivision", teamDivision);

                startActivityForResult(intent, DISPLAY_LINEUP);
            }
        });
    }
    private void setImageButtonCreateListener(){
        imageButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisContext, ActivityModifyFormation.class);
                intent.putExtra("teamDivision",teamDivision);
                intent.putExtra("teamName",teamName);
                intent.putExtra("formationName", "");

                startActivityForResult(intent, FORMATION);
            }
        });
    }
    private void setImageButtonDeleteListener(){
        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formDir = getFilesDir() + File.separator + "formations" + File.separator + teamDivision;
                String fileName = spinnerFormation.getSelectedItem().toString();
                int formPos = spinnerFormation.getSelectedItemPosition();
                if( formPos < 3 ){
                    dialogDeleteError();
                }
                else{
                    File formFile = new File(formDir + File.separator + fileName);
                    possibleFormations.remove(possibleFormations.get(formPos));
                    formationDetails.remove(formationDetails.get(formPos));
                    formFile.delete();
                    updateFormations();
                    fillFormationSpinner();
                }
            }
        });

    }
    private void setSpinnerFormationListener(){
        spinnerFormation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quarterFormations.set(whichQuarter, position);

                refillPlayers(whichQuarter);
                updateAvailablePlayers(whichQuarter);
                setButtonFieldPlayersListeners();
                updateField();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    private void setButtonFieldPlayersListeners(){
        String pos, role, rolePos, buttonName;
        ArrayList<String > whichPlayers = new ArrayList<>();
        Button localButton;
        ArrayList<String> formDetails;
        formDetails = formationDetails.get(quarterFormations.get(whichQuarter));
        for( int p = 0; p < formDetails.size(); ++p ){
            rolePos = formDetails.get(p);
            pos = rolePos.substring(1);
            role = rolePos.substring(0,1);
            buttonName = "b" + pos;

            int buttonID = getStringIdentifier(buttonName);

            switch( role ){
                case "G":
                    whichPlayers = allGoalies.get(whichQuarter);
                    break;
                case "D":
                    whichPlayers = allDefenders.get(whichQuarter);
                    break;
                case "M":
                    whichPlayers = allMidfielders.get(whichQuarter);
                    break;
                case "F":
                    whichPlayers = allForwards.get(whichQuarter);
                    break;
                default:
                    break;
            }

            localButton = findViewById(buttonID);

            final String finalRole = role;
            final String finalPos = pos;
            final int finalP = p;
            final ArrayList<String> finalWhichPlayers = whichPlayers;
            localButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(thisContext, ActivityAvailablePlayers.class);

                    intent.putExtra("role", finalRole);
                    intent.putExtra("pos", finalPos);
                    intent.putExtra("playerInd", Integer.toString(finalP));
                    intent.putStringArrayListExtra("recPlayers", finalWhichPlayers);
                    intent.putStringArrayListExtra("allPlayers", allPlayers.get(whichQuarter));
                    intent.putStringArrayListExtra("rosterInfo", rosterInfo);
                    startActivityForResult(intent, AVAILABLE_PLAYERS);
                }
            });
        }
    }
    private void fillQuarterFormations(){

        quarterFormations = new ArrayList<>();
        quarterFormations.add(0);
        quarterFormations.add(0);
        quarterFormations.add(0);
        quarterFormations.add(0);
    }
    private void fillFormationSpinner() {
        ArrayAdapter<String> spinnerAdapter;
        spinnerAdapter = new ArrayAdapter<>(thisContext, R.layout.not_field_spinner_item, possibleFormations);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFormation.setAdapter(spinnerAdapter);
    }
    private void updateAvailablePlayers(int q){
        String thisPlayerNumber, thatPlayerNumber, thatPlayer, absentPlayer;
        refillPlayers(q);

        for (int p = 0; p < lineup.get(q).size(); ++p) {
            thisPlayerNumber = lineup.get(q).get(p).split(":")[0];
            Log.d("PRE", Integer.toString(p) + ") This player number:" + thisPlayerNumber);
            if (!thisPlayerNumber.equals("Empty")) {
                for (int i = 0; i < allPlayers.get(q).size(); ++i) {
                    thatPlayer = allPlayers.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    Log.d("A", "This player number:" + thisPlayerNumber + " that player:" + thatPlayer);
                    if (thisPlayerNumber.equals(thatPlayerNumber)) {
                        Log.d("A", "DELETED " + thisPlayerNumber);
                        allPlayers.get(q).remove(thatPlayer);
                        break;
                    }
                }
                for (int i = 0; i < allGoalies.get(q).size(); ++i) {
                    thatPlayer = allGoalies.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    Log.d("G", "This player number:" + thisPlayerNumber + " that player:" + thatPlayer);
                    if (thisPlayerNumber.equals(thatPlayerNumber)) {
                        Log.d("G", "DELETED " + thisPlayerNumber);
                        allGoalies.get(q).remove(thatPlayer);
                        break;
                    }
                }
                for (int i = 0; i < allDefenders.get(q).size(); ++i) {
                    thatPlayer = allDefenders.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    Log.d("D", "This player number:" + thisPlayerNumber + " that player:" + thatPlayer);
                    if (thisPlayerNumber.equals(thatPlayerNumber)) {
                        Log.d("D", "DELETED " + thisPlayerNumber);
                        allDefenders.get(q).remove(thatPlayer);
                        break;
                    }
                }
                for (int i = 0; i < allMidfielders.get(q).size(); ++i) {
                    thatPlayer = allMidfielders.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    Log.d("M", "This player number:" + thisPlayerNumber + " that player:" + thatPlayerNumber);
                    if (thisPlayerNumber.equals(thatPlayerNumber)) {
                        Log.d("M", "DELETED " + thisPlayerNumber);
                        allMidfielders.get(q).remove(thatPlayer);
                        break;
                    }
                }
                for (int i = 0; i < allForwards.get(q).size(); ++i) {
                    thatPlayer = allForwards.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    Log.d("F", "This player number:" + thisPlayerNumber + " that player:" + thatPlayer);
                    if (thisPlayerNumber.equals(thatPlayerNumber)) {
                        Log.d("F", "DELETED " + thisPlayerNumber);
                        allForwards.get(q).remove(thatPlayer);
                        break;
                    }
                }
            }
        }

        for (int p = 0; p < attendance.get(q).size(); ++p) {
            absentPlayer = attendance.get(q).get(p);
            if (!absentPlayer.equals("Empty")) {

                for (int i = 0; i < allPlayers.get(q).size(); ++i) {
                    thatPlayer = allPlayers.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    if (absentPlayer.equals(thatPlayerNumber)) {
                        allPlayers.get(q).remove(thatPlayer);
                        break;
                    }
                }
                for (int i = 0; i < allGoalies.get(q).size(); ++i) {
                    thatPlayer = allGoalies.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    if (absentPlayer.equals(thatPlayerNumber)) {
                        allGoalies.get(q).remove(thatPlayer);
                        break;
                    }
                }
                for (int i = 0; i < allDefenders.get(q).size(); ++i) {
                    thatPlayer = allDefenders.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    if (absentPlayer.equals(thatPlayerNumber)) {
                        allDefenders.get(q).remove(thatPlayer);
                        break;
                    }
                }
                for (int i = 0; i < allMidfielders.get(q).size(); ++i) {
                    thatPlayer = allMidfielders.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    if (absentPlayer.equals(thatPlayerNumber)) {
                        allMidfielders.get(q).remove(thatPlayer);
                        break;
                    }
                }
                for (int i = 0; i < allForwards.get(q).size(); ++i) {
                    thatPlayer = allForwards.get(q).get(i);
                    thatPlayerNumber = thatPlayer.split(":")[0];
                    if (absentPlayer.equals(thatPlayerNumber)) {
                        allForwards.get(q).remove(thatPlayer);
                        break;
                    }
                }
            }
        }
    }
    private void refillPlayers( int q ){
        refillAllPlayers(q);
        refillAllGoalies(q);
        refillAllDefenders(q);
        refillAllMidfielders(q);
        refillAllForwards(q);
    }
    private void refillAllPlayers(int q){
        String player;
        for( int i = 0; i < allPlayers.get(4).size(); ++i){
            player = allPlayers.get(4).get(i);
            if( !allPlayers.get(q).contains(player)){
                allPlayers.get(q).add(player);
            }
        }
        Collections.sort(allPlayers.get(q), new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
    }
    private void refillAllGoalies(int q){
        String player;
        for( int i = 0; i < allGoalies.get(4).size(); ++i){
            player = allGoalies.get(4).get(i);
            if( !allGoalies.get(q).contains(player)){
                allGoalies.get(q).add(player);
            }
        }
        Collections.sort(allGoalies.get(q), new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
    }
    private void refillAllDefenders(int q){
        String player;
        for( int i = 0; i < allDefenders.get(4).size(); ++i){
            player = allDefenders.get(4).get(i);
            if( !allDefenders.get(q).contains(player)){
                allDefenders.get(q).add(player);
            }
        }
        Collections.sort(allDefenders.get(q), new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
    }
    private void refillAllMidfielders(int q){
        String player;
        for( int i = 0; i < allMidfielders.get(4).size(); ++i){
            player = allMidfielders.get(4).get(i);
            if( !allMidfielders.get(q).contains(player)){
                allMidfielders.get(q).add(player);
            }
        }
        Collections.sort(allMidfielders.get(q), new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
    }
    private void refillAllForwards(int q){
        String player;
        for( int i = 0; i < allForwards.get(4).size(); ++i){
            player = allForwards.get(4).get(i);
            if( !allForwards.get(q).contains(player)){
                allForwards.get(q).add(player);
            }
        }
        Collections.sort(allForwards.get(q), new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
    }
    private void fillFormationArray() {
        possibleFormations = new ArrayList<>();
        formationDetails = new ArrayList<>();
        ArrayList<ArrayList<String>> defaultFormations = new ArrayList<>();
        ArrayList<String> defaultFormationNames = new ArrayList<>();

        switch (teamDivision){
            case "U10":
                defaultFormations.add(F2F3F1());
                defaultFormationNames.add("F2-F3-F1 (D)");

                defaultFormations.add(D4F1F1());
                defaultFormationNames.add("D4-F1-F1 (D)");

                defaultFormations.add(F3F2F1());
                defaultFormationNames.add("F3-F2-F1 (D)");
                break;

            case "U12":
                defaultFormations.add(F3F2F3());
                defaultFormationNames.add("F3-F2-F3 (D)");

                defaultFormations.add(F2D4F2());
                defaultFormationNames.add("F2-D4-F2 (D)");

                defaultFormations.add(F2F3F3());
                defaultFormationNames.add("F2-F3-F3 (D)");
                break;

            case "U14":
            case "U16":
            case "U19":
                defaultFormations.add(F4F4F2());
                defaultFormationNames.add("F4-F4-F2 (D)");

                defaultFormations.add(F4F3F3());
                defaultFormationNames.add("F4-F3-F3 (D)");

                defaultFormations.add(D4F4F2());
                defaultFormationNames.add("D4-F4-F2 (D)");
                break;

            default:
                break;
        }

        for( int i = 0; i < defaultFormations.size(); i++ ){
            formationDetails.add(defaultFormations.get(i));
            possibleFormations.add(defaultFormationNames.get(i));
        }
    }
    // Default U10 Formations! :)
    private ArrayList<String> F2F3F1() {
        ArrayList<String> F2F3F1 = new ArrayList<>();

        F2F3F1.add("F02");
        F2F3F1.add("M10");
        F2F3F1.add("M12");
        F2F3F1.add("M14");
        F2F3F1.add("D31");
        F2F3F1.add("D33");
        F2F3F1.add("G50");

        return F2F3F1;
    }
    private ArrayList<String> D4F1F1() {
        ArrayList<String> D4F1F1 = new ArrayList<>();

        D4F1F1.add("F02");
        D4F1F1.add("M12");
        D4F1F1.add("D30");
        D4F1F1.add("D32");
        D4F1F1.add("D34");
        D4F1F1.add("D42");
        D4F1F1.add("G50");

        return D4F1F1;
    }
    private ArrayList<String> F3F2F1() {
        ArrayList<String> F3F2F1 = new ArrayList<>();

        F3F2F1.add("F02");
        F3F2F1.add("M11");
        F3F2F1.add("M13");
        F3F2F1.add("D30");
        F3F2F1.add("D32");
        F3F2F1.add("D34");
        F3F2F1.add("G50");

        return F3F2F1;
    }
    // Default U12 Formations! :)
    private ArrayList<String> F3F2F3() {
        ArrayList<String> F3F2F3 = new ArrayList<>();

        F3F2F3.add("F00");
        F3F2F3.add("F02");
        F3F2F3.add("F04");
        F3F2F3.add("M11");
        F3F2F3.add("M13");
        F3F2F3.add("D30");
        F3F2F3.add("D32");
        F3F2F3.add("D34");
        F3F2F3.add("G50");

        return F3F2F3;
    }
    private ArrayList<String> F2D4F2() {
        ArrayList<String> F2D4F2 = new ArrayList<>();

        F2D4F2.add("F01");
        F2D4F2.add("F03");
        F2D4F2.add("M10");
        F2D4F2.add("M12");
        F2D4F2.add("M14");
        F2D4F2.add("M22");
        F2D4F2.add("D31");
        F2D4F2.add("D33");
        F2D4F2.add("G50");

        return F2D4F2;
    }
    private ArrayList<String> F2F3F3() {
        ArrayList<String> F2F3F3 = new ArrayList<>();

        F2F3F3.add("F00");
        F2F3F3.add("F02");
        F2F3F3.add("F04");
        F2F3F3.add("M11");
        F2F3F3.add("M12");
        F2F3F3.add("M13");
        F2F3F3.add("D31");
        F2F3F3.add("D33");
        F2F3F3.add("G50");

        return F2F3F3;
    }
    // Default U14/U16/U19 Formations! :)
    private ArrayList<String> F4F4F2() {
        ArrayList<String> F4F4F2 = new ArrayList<>();

        F4F4F2.add("F01");
        F4F4F2.add("F03");
        F4F4F2.add("M10");
        F4F4F2.add("M11");
        F4F4F2.add("M13");
        F4F4F2.add("M14");
        F4F4F2.add("D30");
        F4F4F2.add("D31");
        F4F4F2.add("D33");
        F4F4F2.add("D34");
        F4F4F2.add("G50");

        return F4F4F2;
    }
    private ArrayList<String> F4F3F3() {
        ArrayList<String> F4F3F3 = new ArrayList<>();

        F4F3F3.add("F00");
        F4F3F3.add("F02");
        F4F3F3.add("F04");
        F4F3F3.add("M10");
        F4F3F3.add("M12");
        F4F3F3.add("M14");
        F4F3F3.add("D30");
        F4F3F3.add("D31");
        F4F3F3.add("D33");
        F4F3F3.add("D34");
        F4F3F3.add("G50");

        return F4F3F3;
    }
    private ArrayList<String> D4F4F2() {
        ArrayList<String> F4T5F1 = new ArrayList<>();

        F4T5F1.add("F01");
        F4T5F1.add("F03");
        F4T5F1.add("M10");
        F4T5F1.add("M14");
        F4T5F1.add("M11");
        F4T5F1.add("M13");
        F4T5F1.add("D30");
        F4T5F1.add("D32");
        F4T5F1.add("D34");
        F4T5F1.add("D42");
        F4T5F1.add("G50");

        return F4T5F1;
    }
    private void updateField(){

        int quarterFormation = quarterFormations.get(whichQuarter);

        spinnerFormation.setSelection(quarterFormation);

        updateFieldVisibility();
        updateFieldPlayers();
    }
    private void updateFieldVisibility(){
        ArrayList<String> quarterFormationDetails;
        String pos;
        int quarterFormation, size;

        quarterFormation = quarterFormations.get(whichQuarter);
        quarterFormationDetails = formationDetails.get(quarterFormation);
        size = quarterFormationDetails.size();

        for( int i = 0; i < 5; ++i )
        {
            for( int j = 0; j < 5; ++j )
            {
                pos = Integer.toString(i) + Integer.toString(j);
                hidePositionFromString(pos);
            }
        }

        for( int i = 0; i < size; ++i ){
            pos = quarterFormationDetails.get(i).substring(1);
            showPositionFromString(pos);
        }
    }
    private void updateFieldPlayers(){
        ArrayList<String> quarterFormationDetails;
        String pos, buttonName, playerNumber, buttonText;
        int buttonID;
        Button button;
        int quarterFormation, size;

        quarterFormation = quarterFormations.get(whichQuarter);
        quarterFormationDetails = formationDetails.get(quarterFormation);
        size = quarterFormationDetails.size();
        for( int p = 0; p < size; ++p ){
            playerNumber = lineup.get(whichQuarter).get(p);
            pos = quarterFormationDetails.get(p).substring(1);
            buttonName = "b" + pos;
            buttonID = getStringIdentifier(buttonName);
            button = findViewById(buttonID);
            buttonText = playerNumber + " " + getPlayerName(playerNumber);
            button.setText( buttonText );
        }
    }
    private void showPositionFromString( String pos ){
        Button button;

        String buttonName = "b" + pos;
        int buttonID = getStringIdentifier(buttonName);

        button = findViewById(buttonID);
        button.setVisibility(View.VISIBLE);
    }
    private void hidePositionFromString( String pos ){
        Button button;

        String buttonName = "b" + pos;
        int buttonID = getStringIdentifier(buttonName);

        button = findViewById(buttonID);
        button.setVisibility(View.INVISIBLE);
    }
    public int getStringIdentifier(String pString){
        return thisContext.getResources().getIdentifier(pString, "id", thisContext.getPackageName());
    }
    private void getIntentInfo() {
        String[] teamInfo;
        FileInputStream fIS;
        BufferedReader reader;

        String teamDir = getIntent().getStringExtra("teamName");
        String rosterFileString = getFilesDir() + File.separator + "teams" + File.separator + teamDir + File.separator + "roster";

        if ( teamDir != null && !teamDir.equals("") ) {
            teamInfo = teamDir.split("_");
            teamName = teamInfo[0];
            teamDivision = teamInfo[1];
            fillArrays();
            File rosterFile = new File(rosterFileString);
            setUpArrayLists();
            if( rosterFile.exists() )
            {
                try {
                    fIS = new FileInputStream(rosterFileString);
                    reader = new BufferedReader( new InputStreamReader(fIS));
                    String line = reader.readLine();
                    String[] playerInfo;
                    String playerName, playerNumber, playerPosition, playerIdentifier;
                    char G, D, M, F;

                    while( line != null )
                    {
                        playerInfo = line.split(";");
                        playerName = playerInfo[0];
                        playerNumber = playerInfo[1];
                        playerPosition = playerInfo[2];

                        G = playerPosition.charAt(0);
                        D = playerPosition.charAt(1);
                        M = playerPosition.charAt(2);
                        F = playerPosition.charAt(3);

                        playerIdentifier = "#" + playerNumber;
                        rosterInfo.add(playerIdentifier + ":" + playerName);
                        for( int i = 0; i < 5; ++i ) {
                            if (G != '0') {
                                allGoalies.get(i).add(playerIdentifier + ":" + G);
                            }

                            if (D != '0') {
                                allDefenders.get(i).add(playerIdentifier + ":"+ D);
                            }

                            if (M != '0') {
                                allMidfielders.get(i).add(playerIdentifier + ":"+ M);
                            }

                            if (F != '0') {
                                allForwards.get(i).add(playerIdentifier + ":"+ F);
                            }

                            allPlayers.get(i).add(playerIdentifier  + ":" + G  + D  + M + F);
                        }

                        line = reader.readLine();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        updateFormations();
    }
    public File[] GetFiles() {
        File f = new File(getFilesDir() + File.separator + "formations" + File.separator + teamDivision);
        f.mkdirs();
        return f.listFiles();
    }
    private  void updateFormations(){
        FileInputStream fIS;
        BufferedReader reader;
        File[] myFiles;
        ArrayList<String> myFileNames;
        String formName, formDir = getFilesDir() + File.separator + "formations" + File.separator + teamDivision;

        myFiles = GetFiles();
        myFileNames = getFileNames(myFiles);

        if( myFileNames != null ) {
            for (int i = 0; i < myFileNames.size(); ++i) {
                try {
                    formName = myFileNames.get(i);
                    fIS = new FileInputStream(formDir + File.separator + formName);
                    reader = new BufferedReader(new InputStreamReader(fIS));
                    String line = reader.readLine();
                    ArrayList<String> positions = new ArrayList<>();
                    String position;

                    while (line != null) {
                        position = line;
                        positions.add(position);
                        line = reader.readLine();
                    }
                    if (!possibleFormations.contains(formName) && !formationDetails.contains(positions)) {
                        possibleFormations.add(formName);
                        formationDetails.add(positions);
                    }
                    else{
                        dialogFormationError();
                        File f = new File(formDir + File.separator + formName);
                        f.delete();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public ArrayList<String> getFileNames(File[] file){
        ArrayList<String> arrayFiles = new ArrayList<>();
        if (file != null && file.length == 0)
            return null;
        else {
            for (File aFile : file != null ? file : new File[0]) arrayFiles.add(aFile.getName());
        }

        return arrayFiles;
    }
    private void setUpArrayLists(){
        rosterInfo = new ArrayList<>();
        allPlayers = new ArrayList<>();
        allGoalies = new ArrayList<>();
        allDefenders = new ArrayList<>();
        allMidfielders = new ArrayList<>();
        allForwards = new ArrayList<>();
        lineup = new ArrayList<>();
        attendance = new ArrayList<>();


        for( int i = 0; i < 5; ++i ){
            allPlayers.add(new ArrayList<String>());
            allGoalies.add(new ArrayList<String>());
            allDefenders.add(new ArrayList<String>());
            allMidfielders.add(new ArrayList<String>());
            allForwards.add(new ArrayList<String>());
        }

        for( int i = 0; i < 4; ++i ){
            allPlayers.get(i).add("Empty");
            allGoalies.get(i).add("Empty");
            allDefenders.get(i).add("Empty");
            allMidfielders.get(i).add("Empty");
            allForwards.get(i).add("Empty");
            lineup.add(new ArrayList<String>());
            attendance.add( new ArrayList<String>());
        }

        for( int i = 0; i < 4; ++i ) {
            for( int j = 0; j < formationDetails.get(0).size(); ++j){
                lineup.get(i).add("Empty");
            }
        }
    }
    private void toastThis(String message){
        Toast.makeText(thisContext,
                message,
                Toast.LENGTH_SHORT).show();
    }
    private void dialogConfirmCancel() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        builder.setTitle("Are you sure you want to RETURN:")
                    .setMessage("You will return to the SELECT TEAM activity.\nYour progress here won't be saved until that is implemented.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            hideBars();
                        }
                    })
                    .show();
    }
    private void dialogDeleteError() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        builder.setTitle("Delete Formation Error")
                .setMessage("Unable to delete a default formation")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hideBars();
                    }
                })
                .show();
    }
    private void dialogFormationError() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        builder.setTitle("Create Formation Error")
                .setMessage("Unable to add an already existing formation\n>Names must be unique\n>Formations must be unique")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hideBars();
                    }
                })
                .show();
    }
    private int quartersPlayed( String playerNumber ){
        int count = 0;

        for( int i = 0; i < 4; ++i ){
            if( lineup.get(i).contains("#" + playerNumber)){
                count++;
            }
        }

        return count;
    }
    private int quartersAbsent( String playerNumber ){
        int count = 0;

        for( int i = 0; i < 4; ++i ){
            if( attendance.get(i).contains("#" + playerNumber)){
                count++;
            }
        }

        return count;
    }
    private String getPlayerName( String playerNumber ){
        String thatPlayer, thatPlayerNumber, thatPlayerName, name = "";

        for (int i = 0; i < rosterInfo.size(); ++i) {
            thatPlayer = rosterInfo.get(i);
            thatPlayerNumber = thatPlayer.split(":")[0];
            thatPlayerName = thatPlayer.split(":")[1];

            if (playerNumber.equals(thatPlayerNumber)) {
                name = thatPlayerName;
                break;
            }
        }

        return name;
    }
    @NonNull
    private String lineupValidity(){
        boolean valid = true;
        StringBuilder validity = new StringBuilder("");
        int errorCount = 0, quartersReq, missingPlayers, qPlayed, qSat, qAbsent;

        for( int i = 0; i < 4; ++i ){
            missingPlayers = 0;
            if( lineup.get(i).contains("Empty")) {
                valid = false;
                for (int p = 0; p < formationDetails.get(0).size(); ++p) {
                    if (lineup.get(i).get(p).equals("Empty")) {
                        missingPlayers++;
                    }
                }
                validity.append(Integer.toString(errorCount + 1)).append(") Quarter ").append(Integer.toString(i + 1)).append(" is missing ").append(Integer.toString(missingPlayers)).append(" players\n");
                errorCount++;
            }
        }

        if(quarterRule != RULE_NO_Q){
            for( int i = 0; i < rosterInfo.size(); ++i ){
                if( quarterRule == RULE_Q2 ){
                    quartersReq = 2;
                }
                else{
                    quartersReq = 3;
                }
                String pNumber = rosterInfo.get(i).split(":")[0];
                pNumber = pNumber.substring(1);
                qAbsent = quartersAbsent(pNumber);
                qPlayed = quartersPlayed(pNumber);

                if( qPlayed + qAbsent < quartersReq ){
                    valid = false;
                    validity.append(Integer.toString(errorCount + 1)).append(") Player #").append(pNumber).append(" requires ").append(Integer.toString(quartersReq - (qPlayed+ qAbsent))).append(" more quarters\n");
                    errorCount++;
                }
            }
        }

        return Boolean.toString(valid) + ":" + validity.toString();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideBars();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        hideBars();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case FORMATION:
                    updateFormations();
                    fillFormationSpinner();
                    break;
                case ATTENDANCE:
                    attendance.clear();
                    ArrayList<String> q1, q2, q3, q4;
                    String thatPlayer, thisPlayer, thisPlayerNumber, thatPlayerNumber;
                    q1 = data.getStringArrayListExtra("q1Attendance");
                    q2 = data.getStringArrayListExtra("q2Attendance");
                    q3 = data.getStringArrayListExtra("q3Attendance");
                    q4 = data.getStringArrayListExtra("q4Attendance");

                    attendance.add(q1);
                    attendance.add(q2);
                    attendance.add(q3);
                    attendance.add(q4);

                    for( int q = 0; q < 4; ++q ){
                        for( int ap = 0; ap < attendance.get(q).size(); ++ap ){
                            thisPlayerNumber = attendance.get(q).get(ap).split(":")[0];

                            for( int lp = 0; lp < lineup.get(q).size(); ++lp ){
                                thatPlayer = lineup.get(q).get(lp);
                                thatPlayerNumber = thatPlayer.split(":")[0];

                                if (thisPlayerNumber.equals(thatPlayerNumber)) {
                                    lineup.get(q).set(lp, "Empty");
                                }
                            }
                        }
                    }

                    updateAvailablePlayers(whichQuarter);
                    updateFieldPlayers();
                    break;

                case AVAILABLE_PLAYERS:
                    Button button;
                    String selectedPlayer = data.getStringExtra("selectedPlayer");
                    String pos =  data.getStringExtra("pos");
                    String playerInd =  data.getStringExtra("playerInd");
                    String buttonName = "b" + pos;
                    int buttonID = getStringIdentifier(buttonName);

                    button = findViewById(buttonID);
                    button.setText(selectedPlayer);
                    lineup.get(whichQuarter).set(Integer.valueOf(playerInd), selectedPlayer);
                    updateAvailablePlayers(whichQuarter);
                    updateFieldPlayers();
                    break;
            }
        }
    }
}

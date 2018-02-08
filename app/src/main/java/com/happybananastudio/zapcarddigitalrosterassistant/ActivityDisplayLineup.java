package com.happybananastudio.zapcarddigitalrosterassistant;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mgint on 1/25/2018.
 */

public class ActivityDisplayLineup extends Activity{
    Context thisContext = this;
    LinearLayout lL_Main;
    String teamName, teamDivision, lineupValidity, lineupValidityReason, quarterRule;
    ArrayList<String>
            q1Absent, q2Absent, q3Absent, q4Absent,
            q1Lineup, q2Lineup, q3Lineup, q4Lineup,
            rosterInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_display_lineup);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFinishOnTouchOutside(false);
        hideBars();
        getIntentInfo();
        setUpLayouts();
    }

    private void setUpLayouts(){
        setUpMinimizeButton();
        setUpTextViews();

        lL_Main = findViewById(R.id.Lineup_Main);

        for( int i = 0; i < rosterInfo.size(); ++i ){
            createNewEntry(rosterInfo.get(i));
        }
    }
    private void setUpTextViews(){
        String valid = "", ruling;
        TextView tVTeamName = findViewById(R.id.dLineup_tVTeamName);
        TextView tVTeamDivision = findViewById(R.id.dLineup_tVTeamDivision);
        TextView tVValidity = findViewById(R.id.dLineup_tVValid);

        tVTeamName.setText(teamName);
        tVTeamDivision.setText(teamDivision);
        switch (lineupValidity){
            case "true":
                valid = "Valid Lineup for Quarter Rule " + quarterRule;
                tVValidity.setText(valid);
                tVValidity.setBackgroundColor(Color.GREEN);
                break;
            case "false":
                if( quarterRule.equals("0")) {
                    ruling = "No Quarter Rule";
                }
                else{
                    ruling = "Quarter Rule " + quarterRule;
                }

                valid = "Invalid Lineup for " + ruling + "\nClick me for why";
                tVValidity.setText(valid);
                tVValidity.setBackgroundColor(Color.RED);
                tVValidity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogValidityError();
                    }
                });
                break;
            default:
                break;
        }
    }
    private void getIntentInfo(){
        String[] valid;
        q1Absent = new ArrayList<>();
        q2Absent = new ArrayList<>();
        q3Absent = new ArrayList<>();
        q4Absent = new ArrayList<>();
        rosterInfo = new ArrayList<>();

        teamName = getIntent().getStringExtra("teamName");
        teamDivision = getIntent().getStringExtra("teamDivision");
        valid = getIntent().getStringExtra("validLineup").split(":");
        lineupValidity = valid[0];

        if( valid.length > 1 ) {
            lineupValidityReason = valid[1];
        }

        quarterRule = getIntent().getStringExtra("qRule");

        rosterInfo = getIntent().getStringArrayListExtra("rosterInfo");

        q1Absent = getIntent().getStringArrayListExtra("q1Absent");
        q2Absent = getIntent().getStringArrayListExtra("q2Absent");
        q3Absent = getIntent().getStringArrayListExtra("q3Absent");
        q4Absent = getIntent().getStringArrayListExtra("q4Absent");

        q1Lineup = getIntent().getStringArrayListExtra("q1Lineup");
        q2Lineup = getIntent().getStringArrayListExtra("q2Lineup");
        q3Lineup = getIntent().getStringArrayListExtra("q3Lineup");
        q4Lineup = getIntent().getStringArrayListExtra("q4Lineup");
    }
    private void setUpMinimizeButton(){
        Button bMinimize = findViewById(R.id.buttonMinimize);
        bMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();

                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
    private void createNewEntry( String playerInfo ){
        String pNumber = playerInfo.split(":")[0];
        String pName = playerInfo.split(":")[1];
        LinearLayout total, lSub, rSub;
        TextView playerNumber, playerName, q1, q2, q3, q4;

        total = new LinearLayout(thisContext);
        lSub = new LinearLayout(thisContext);
        rSub = new LinearLayout(thisContext);

        playerNumber = new TextView(thisContext);
        playerName = new TextView(thisContext);
        q1 = new TextView(thisContext);
        q2 = new TextView(thisContext);
        q3 = new TextView(thisContext);
        q4 = new TextView(thisContext);

        handleTextView_Dimensions(playerNumber, playerName, q1, q2, q3, q4);
        handleTextView_Text(playerNumber, playerName, q1, q2, q3, q4, pNumber + ":" + pName);
        handleTextView_TextAlignment(playerNumber, playerName, q1, q2, q3, q4);
        handleTextView_Background(playerNumber, playerName, q1, q2, q3, q4);
/*
        quarterListener(q1, playerNumber, q1Absence);
        quarterListener(q2, playerNumber, q2Absence);
        quarterListener(q3, playerNumber, q3Absence);
        quarterListener(q4, playerNumber, q4Absence);
        playerListener(playerNameAndNumber, q1, q2, q3, q4, playerNumber);
*/
        lSub.addView(playerNumber);
        lSub.addView(playerName);

        rSub.addView(q1);
        rSub.addView(q2);
        rSub.addView(q3);
        rSub.addView(q4);

        lSub.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        rSub.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        total.addView(lSub);
        total.addView(rSub);

        lL_Main.addView(total);
    }
    private void handleTextView_Text( TextView pNum, TextView pName, TextView q1, TextView q2, TextView q3, TextView q4, String playerNN ){

        String num = playerNN.split(":")[0];
        String name = playerNN.split(":")[1];

        pNum.setTextSize(26);
        pName.setTextSize(26);
        q1.setTextSize(26);
        q2.setTextSize(26);
        q3.setTextSize(26);
        q4.setTextSize(26);

        pNum.setText(num);
        pName.setText(name);
        handleCardMarking(q1, num, q1Lineup, q1Absent);
        handleCardMarking(q2, num, q2Lineup, q2Absent);
        handleCardMarking(q3, num, q3Lineup, q3Absent);
        handleCardMarking(q4, num, q4Lineup, q4Absent);
        /*
        if( q1List.contains(num)) {
            q1Absence.add(num);
            q1.setText("A");
        }
        else{
            q1.setText("");
        }
        if( q2List.contains(num)) {
            q2Absence.add(num);
            q2.setText("A");
        }
        else{
            q2.setText("");
        }
        if( q3List.contains(num)) {
            q3Absence.add(num);
            q3.setText("A");
        }
        else{
            q3.setText("");
        }
        if( q4List.contains(num)) {
            q4Absence.add(num);
            q4.setText("A");
        }
        else{
            q4.setText("");
        }*/

        pNum.setTextColor(Color.BLACK);
        pName.setTextColor(Color.BLACK);
        q1.setTextColor(Color.BLACK);
        q2.setTextColor(Color.BLACK);
        q3.setTextColor(Color.BLACK);
        q4.setTextColor(Color.BLACK);
    }
    private void handleTextView_Background( TextView pNum, TextView pName, TextView q1, TextView q2, TextView q3, TextView q4 ){
        pNum.setBackgroundResource(R.drawable.my_border_white);
        pName.setBackgroundResource(R.drawable.my_border_white);
        q1.setBackgroundResource(R.drawable.my_border_white);
        q2.setBackgroundResource(R.drawable.my_border_white);
        q3.setBackgroundResource(R.drawable.my_border_white);
        q4.setBackgroundResource(R.drawable.my_border_white);
    }
    private void handleTextView_TextAlignment( TextView pNum, TextView pName, TextView q1, TextView q2, TextView q3, TextView q4 ){
        pNum.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        pName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        q1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        q2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        q3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        q4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }
    private void handleTextView_Dimensions( TextView pNum, TextView pName, TextView q1, TextView q2, TextView q3, TextView q4 ){
        pNum.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
        pName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        q1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        q2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        q3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        q4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
    }
    private void handleCardMarking( TextView Q, String num, ArrayList<String> lineup, ArrayList<String> absent){
        int numOfPlayers = lineup.size();

        if( lineup.contains(num)){
            if( lineup.get(numOfPlayers - 1 ).equals(num)){
                Q.setText("G");
            }
            else{
                Q.setText(" ");
            }
        }
        else if( absent.contains(num)){
            Q.setText("A");
        }
        else{
            Q.setText("X");
        }
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
    private void dialogValidityError() {
        String ruling;
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }

        if( quarterRule.equals("0")) {
            ruling = "No Quarter Rule";
        }
        else{
            ruling = "Quarter Rule " + quarterRule;
        }
        builder.setTitle("Why the Lineup is Invalid for " + ruling)
                .setMessage(lineupValidityReason)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hideBars();
                    }
                })
                .show();
    }
    private void toastThis(String message){
        Toast.makeText(thisContext,
                message,
                Toast.LENGTH_SHORT).show();
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
}

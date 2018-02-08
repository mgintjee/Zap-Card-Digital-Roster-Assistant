package com.happybananastudio.zapcarddigitalrosterassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mgint on 1/19/2018.
 */

public class ActivityModifyAttendance extends Activity{
    Context thisContext = this;
    ArrayList<String> q1Absence, q2Absence, q3Absence, q4Absence, rosterInfo;
    ArrayList<String> q1List, q2List, q3List, q4List;
    LinearLayout lL_Main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_modify_attendance);getWindow();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFinishOnTouchOutside(false);
        hideBars();

        getIntentInfo();
        setUpLayout();
    }

    private void getIntentInfo(){
        String players;
        String[] playerTokens;
        q1Absence = new ArrayList<>();
        q2Absence = new ArrayList<>();
        q3Absence = new ArrayList<>();
        q4Absence = new ArrayList<>();
        rosterInfo = new ArrayList<>();

        rosterInfo = getIntent().getStringArrayListExtra("rosterInfo");
        q1List = getIntent().getStringArrayListExtra("q1Attendance");
        q2List = getIntent().getStringArrayListExtra("q2Attendance");
        q3List = getIntent().getStringArrayListExtra("q3Attendance");
        q4List = getIntent().getStringArrayListExtra("q4Attendance");
    }
    private void setUpLayout( ){
        lL_Main = findViewById(R.id.Attendance_Main);
        setUpMinimizeButton();
        for( int i = 0; i < rosterInfo.size(); ++i ){
            createNewEntry(rosterInfo.get(i));
        }
    }
    private void setUpMinimizeButton(){
        Button bMinimize = findViewById(R.id.buttonMinimize);
        bMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();

                data.putStringArrayListExtra("q1Attendance", q1Absence);
                data.putStringArrayListExtra("q2Attendance", q2Absence);
                data.putStringArrayListExtra("q3Attendance", q3Absence);
                data.putStringArrayListExtra("q4Attendance", q4Absence);

                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
    private void createNewEntry( String playerInfo ){
        String playerNumber = playerInfo.split(":")[0];
        String playerName = playerInfo.split(":")[1];
        LinearLayout total, sub;
        TextView playerNameAndNumber, q1, q2, q3, q4;

        total = new LinearLayout(thisContext);
        sub = new LinearLayout(thisContext);

        playerNameAndNumber = new TextView(thisContext);
        q1 = new TextView(thisContext);
        q2 = new TextView(thisContext);
        q3 = new TextView(thisContext);
        q4 = new TextView(thisContext);

        handleTextView_Dimensions(playerNameAndNumber, q1, q2, q3, q4);
        handleTextView_Text(playerNameAndNumber, q1, q2, q3, q4, playerInfo);
        handleTextView_TextAlignment(playerNameAndNumber, q1, q2, q3, q4);
        handleTextView_Background(playerNameAndNumber, q1, q2, q3, q4);

        quarterListener(q1, playerNumber, q1Absence);
        quarterListener(q2, playerNumber, q2Absence);
        quarterListener(q3, playerNumber, q3Absence);
        quarterListener(q4, playerNumber, q4Absence);
        playerListener(playerNameAndNumber, q1, q2, q3, q4, playerNumber);

        sub.addView(q1);
        sub.addView(q2);
        sub.addView(q3);
        sub.addView(q4);
        sub.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        total.addView(playerNameAndNumber);
        total.addView(sub);

        lL_Main.addView(total);
    }
    private void handleTextView_Text( TextView pNN, TextView q1, TextView q2, TextView q3, TextView q4, String playerNN ){
        String num = playerNN.split(":")[0];
        String name = playerNN.split(":")[1];
        String playerNameNumber = num + " " + name;
        pNN.setTextSize(26);
        q1.setTextSize(26);
        q2.setTextSize(26);
        q3.setTextSize(26);
        q4.setTextSize(26);

        pNN.setText(playerNameNumber);
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
        }

        pNN.setTextColor(Color.BLACK);
        q1.setTextColor(Color.BLACK);
        q2.setTextColor(Color.BLACK);
        q3.setTextColor(Color.BLACK);
        q4.setTextColor(Color.BLACK);
    }
    private void handleTextView_Background( TextView pNN, TextView q1, TextView q2, TextView q3, TextView q4 ){
        pNN.setBackgroundResource(R.drawable.my_border_white);
        q1.setBackgroundResource(R.drawable.my_border_white);
        q2.setBackgroundResource(R.drawable.my_border_white);
        q3.setBackgroundResource(R.drawable.my_border_white);
        q4.setBackgroundResource(R.drawable.my_border_white);
    }
    private void handleTextView_TextAlignment( TextView pNN, TextView q1, TextView q2, TextView q3, TextView q4 ){
        pNN.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        q1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        q2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        q3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        q4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }
    private void handleTextView_Dimensions( TextView pNN, TextView q1, TextView q2, TextView q3, TextView q4 ){
        pNN.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        q1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        q2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        q3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        q4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pNN.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            q1.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            q2.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            q3.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            q4.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
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
    private void quarterListener(final TextView quarter, final String playerNumber, final ArrayList<String> quarterAbsence){
        quarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( quarterAbsence.contains(playerNumber)){
                    quarterAbsence.remove(playerNumber);
                    quarter.setText("");
                }
                else{
                    quarterAbsence.add(playerNumber);
                    quarter.setText("A");
                }
            }
        });
    }
    private void playerListener(final TextView playerInfo, final TextView q1,  final TextView q2,  final TextView q3,  final TextView q4,  final String playerNumber){
        playerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean q1Player = q1Absence.contains(playerNumber);
                boolean q2Player = q2Absence.contains(playerNumber);
                boolean q3Player = q3Absence.contains(playerNumber);
                boolean q4Player = q4Absence.contains(playerNumber);

                if(  q1Player && q2Player && q3Player && q4Player ){
                    q1Absence.remove(playerNumber);
                    q2Absence.remove(playerNumber);
                    q3Absence.remove(playerNumber);
                    q4Absence.remove(playerNumber);
                    q1.setText("");
                    q2.setText("");
                    q3.setText("");
                    q4.setText("");
                }
                else if( q1Player || q2Player || q3Player || q4Player ){
                    if( !q1Player){
                        q1Absence.add(playerNumber);
                        q1.setText("A");
                    }
                    if( !q2Player){
                        q2Absence.add(playerNumber);
                        q2.setText("A");
                    }
                    if( !q3Player){
                        q3Absence.add(playerNumber);
                        q3.setText("A");
                    }
                    if( !q4Player){
                        q4Absence.add(playerNumber);
                        q4.setText("A");
                    }
                }
                else{
                    q1Absence.add(playerNumber);
                    q2Absence.add(playerNumber);
                    q3Absence.add(playerNumber);
                    q4Absence.add(playerNumber);
                    q1.setText("A");
                    q2.setText("A");
                    q3.setText("A");
                    q4.setText("A");
                }
            }
        });
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
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    @Override
    public void onResume() {
        super.onResume();
        hideBars();
    }
}

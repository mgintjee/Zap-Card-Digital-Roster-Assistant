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
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by mgint on 1/19/2018.
 */

public class ActivityAvailablePlayers extends Activity{

    Context thisContext = this;
    String selectedPlayer;
    LinearLayout lLRec, lLAll;
    Button bCancel;
    String role, pos, playerInd;
    TextView tVHeader;
    ArrayList<String> allPlayers, recPlayers, rosterInfo;
    private final int RECOMMEND = 0;
    private final int ALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_available_players);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFinishOnTouchOutside(false);
        hideBars();
        getIntentInfo();
        initializeWidgets();
        setUpLayouts();
        setUpListeners();
    }

    private void getIntentInfo(){
        rosterInfo = getIntent().getStringArrayListExtra("rosterInfo");
        allPlayers = getIntent().getStringArrayListExtra("allPlayers");
        recPlayers = getIntent().getStringArrayListExtra("recPlayers");
        role = getIntent().getStringExtra("role");
        pos = getIntent().getStringExtra("pos");
        playerInd = getIntent().getStringExtra("playerInd");
        sortForRole();
    }
    private void sortForRole(){

        Collections.sort(recPlayers, new Comparator<String>() {
            public int compare(String o1, String o2) {
                int o1Rating, o2Rating;
                if( o1.equals("Empty")){
                    o1Rating = 10;
                }
                else{
                    o1Rating = Integer.parseInt(o1.split(":")[1]);

                }
                if( o2.equals("Empty")){
                    o2Rating = 10;
                }
                else{
                    o2Rating = Integer.parseInt(o2.split(":")[1]);
                }
                return -1 * (o1Rating - o2Rating );
            }
        });
/*
        switch (role){
            case "G":
                Collections.sort(allPlayers, new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int o1Rating, o2Rating;
                        if( o1.equals("Empty")){
                            o1Rating = 10;
                        }
                        else{
                            o1Rating = Integer.parseInt(o1.split(":")[1].substring(0,1));
                        }
                        if( o2.equals("Empty")){
                            o2Rating = 10;
                        }
                        else{
                            o2Rating = Integer.parseInt(o2.split(":")[1].substring(0,1));
                        }
                        return -1 * (o1Rating - o2Rating );
                    }
                });
                break;
            case "D":
                Collections.sort(allPlayers, new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int o1Rating, o2Rating;
                        if( o1.equals("Empty")){
                            o1Rating = 10;
                        }
                        else{
                            o1Rating = Integer.parseInt(o1.split(":")[1].substring(1,2));
                        }
                        if( o2.equals("Empty")){
                            o2Rating = 10;
                        }
                        else{
                            o2Rating = Integer.parseInt(o2.split(":")[1].substring(1,2));
                        }
                        return -1 * (o1Rating - o2Rating );
                    }
                });
                break;
            case "M":
                Collections.sort(allPlayers, new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int o1Rating, o2Rating;
                        if( o1.equals("Empty")){
                            o1Rating = 10;
                        }
                        else{
                            o1Rating = Integer.parseInt(o1.split(":")[1].substring(2,3));
                        }
                        if( o2.equals("Empty")){
                            o2Rating = 10;
                        }
                        else{
                            o2Rating = Integer.parseInt(o2.split(":")[1].substring(2,3));
                        }
                        return -1 * (o1Rating - o2Rating );
                    }
                });
                break;
            case "F":
                Collections.sort(allPlayers, new Comparator<String>() {
                    public int compare(String o1, String o2) {
                        int o1Rating, o2Rating;
                        if( o1.equals("Empty")){
                            o1Rating = 10;
                        }
                        else{
                            o1Rating = Integer.parseInt(o1.split(":")[1].substring(3,4));
                        }
                        if( o2.equals("Empty")){
                            o2Rating = 10;
                        }
                        else{
                            o2Rating = Integer.parseInt(o2.split(":")[1].substring(3,4));
                        }
                        return -1 * (o1Rating - o2Rating );
                    }
                });
                break;
            default:
                break;
        }
        */
    }
    private void setUpLayouts(){
        for( int i = 0; i < recPlayers.size(); ++i ){
            createNewEntry(recPlayers.get(i), lLRec, RECOMMEND );
        }
        for( int i = 0; i < allPlayers.size(); ++i ){
            createNewEntry(allPlayers.get(i), lLAll, ALL);
        }
    }
    private void createNewEntry( String playerInfo, LinearLayout lL, int type ){
        LinearLayout total, lSub, rSub;
        TextView plNum, plName;

        total = new LinearLayout(thisContext);
        lSub = new LinearLayout(thisContext);
        rSub = new LinearLayout(thisContext);
        plNum = new TextView(thisContext);
        plName = new TextView(thisContext);

        handleLeftTextViews( playerInfo, plNum, plName, lSub );
        handleRightTextViews( playerInfo, rSub, type);

        lSub.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        rSub.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

        total.addView(lSub);
        total.addView(rSub);
        total.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

        lL.addView(total);
    }
    private void handleLeftTextViews(String playerInfo, TextView plNum, TextView plName, LinearLayout lSub ){
        String playerNumber = (playerInfo.equals("Empty")?"Empty":playerInfo.split(":")[0]);
        plNum.setText((playerInfo.equals("Empty")?"-":playerInfo.split(":")[0]));
        plName.setText((playerInfo.equals("Empty")?playerInfo:getPlayerName(playerNumber)));

        plNum.setTextSize(26);
        plNum.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        plNum.setTextColor(Color.BLACK);
        plNum.setBackgroundResource(R.drawable.my_border_white);
        plNum.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            plNum.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }

        plName.setTextSize(26);
        plName.setTextColor(Color.BLACK);
        plName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        plName.setBackgroundResource(R.drawable.my_border_white);
        plName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            plName.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }

        lSub.addView(plNum);
        lSub.addView(plName);

        setUpPlayerListener(plNum, playerNumber);
        setUpPlayerListener(plName, playerNumber);
    }
    private void handleRightTextViews( String playerInfo, LinearLayout rSub, int type ){
        String playerNumber = (playerInfo.equals("Empty")?"Empty":playerInfo.split(":")[0]);
        ArrayList<TextView> arrayOfTVs = new ArrayList<>();
        // Recommended Players
        if( type == RECOMMEND ){
            TextView plRating = new TextView(thisContext);
            plRating.setText((playerInfo.equals("Empty")?"-":playerInfo.split(":")[1]));
            plRating.setTextSize(26);
            plRating.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            plRating.setTextColor(Color.BLACK);
            plRating.setBackgroundResource(R.drawable.my_border_white);
            plRating.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            arrayOfTVs.add(plRating);
        }
        // All Players
        else{
            String playerRating = (playerInfo.equals("Empty")?"-":playerInfo.split(":")[1]);
            String roleRating = "";
            for( int r = 0; r < 4; ++r ){
                roleRating = (playerRating.equals("-")?playerRating:playerRating.substring(r, r+1));
                TextView plRating = new TextView(thisContext);
                plRating.setText(roleRating);
                plRating.setTextSize(26);
                plRating.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                plRating.setTextColor(Color.BLACK);
                plRating.setBackgroundResource(R.drawable.my_border_white);
                plRating.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                arrayOfTVs.add(plRating);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    plRating.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                }
            }
        }

        for( int i = 0; i < arrayOfTVs.size(); ++i ){
            rSub.addView(arrayOfTVs.get(i));
            setUpPlayerListener(arrayOfTVs.get(i), playerNumber);
        }
    }
    private void setUpPlayerListener(TextView tV, final String playerNumber){
        tV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                selectedPlayer = playerNumber;
                data.putExtra("selectedPlayer", selectedPlayer);
                data.putExtra("pos", pos);
                data.putExtra("playerInd", playerInd);
                setResult(RESULT_OK, data);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
    private void initializeWidgets(){
        initializeLinearLayouts();
        initializeTextViews();
        initializeButtons();
    }
    private void setUpListeners(){
        setButtonListeners();
    }
    private void setButtonListeners(){
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();

                setResult(RESULT_CANCELED, data);
                finish();
            }
        });
    }
    private void initializeLinearLayouts(){
        lLRec = findViewById(R.id.APlayers_lLRec);
        lLAll = findViewById(R.id.APlayers_lLAll);
    }
    private void initializeButtons(){
        bCancel = findViewById(R.id.APlayers_bCancel);
    }
    private void initializeTextViews(){
        tVHeader = findViewById(R.id.APlayers_tVHeader);
        StringBuilder string = new StringBuilder("Select A Player For");

        switch( role ){
            case "G":
                string.append(" Goalie");
                break;
            case "D":
                string.append(" Defender");
                break;
            case "M":
                string.append(" Midfielder");
                break;
            case "F":
                string.append(" Forward");
                break;
            default:
                break;
        }

        tVHeader.setText(string.toString());
    }
    private String getPlayerName( String playerInfo ){
        String thatPlayer, thatPlayerNumber, thatPlayerName, thisPlayerNumber, name = "";

        thisPlayerNumber = playerInfo.split(" ")[0];

        for (int i = 0; i < rosterInfo.size(); ++i) {
            thatPlayer = rosterInfo.get(i);
            thatPlayerNumber = thatPlayer.split(":")[0];
            thatPlayerName = thatPlayer.split(":")[1];

            if (thisPlayerNumber.equals(thatPlayerNumber)) {
                name = thatPlayerName;
                break;
            }
        }

        return name;
    }
    private void toastThis(String message){
        Toast.makeText(thisContext,
                message,
                Toast.LENGTH_SHORT).show();
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

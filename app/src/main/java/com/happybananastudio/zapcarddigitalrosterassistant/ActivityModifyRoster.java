package com.happybananastudio.zapcarddigitalrosterassistant;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mgint on 1/2/2018.
 */

public class ActivityModifyRoster extends AppCompatActivity{

    private final String helpType = "modifyRoster";

    private final int CANCEL = 0;
    private final int SAVE = 1;
    private int TYPE_OF_MODIFY = 0;

    Context thisContext = this;

    private int numberOfPlayers, minimumPlayers;
    private String teamName;
    private String teamDivision;
    private ArrayList<PlayerInfo> playerList;
    private ArrayList<String> possibleTeamDivision;


    private ListViewPlayerList lViewPlayerListAdapter;

    // Widgets
    private EditText editTextTeamName;
    private Spinner spinnerTeamDivision;
    private ImageButton iButtonAddPlayer, iButtonEditPlayer, iButtonDeletePlayer, iButtonHelp, iButtonCancel, iButtonSave;
    private ListView lViewPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_modify_roster);
        hideBars();

        fillTeamDivisionArray();

        initializeWidgets();
        setListeners();
        getIntentInfo();
        updatePlayerListView(null);
    }

    private void getIntentInfo() {
        String[] teamInfo;
        FileInputStream fIS;
        BufferedReader reader;
        playerList = new ArrayList<>();
        String teamDir = getIntent().getStringExtra("teamName");
        String rosterFileString  = getFilesDir() + File.separator + "teams" + File.separator + teamDir + File.separator + "roster";

        if ( teamDir != null && !teamDir.equals("") ) {
            teamInfo = teamDir.split("_");
            teamName = teamInfo[0];
            teamDivision = teamInfo[1];
            updateSpinnerTeamDivision();

            editTextTeamName.setText(teamName);
            File rosterFile = new File(rosterFileString);

            if( rosterFile.exists() )
            {
                try {
                    fIS = new FileInputStream(rosterFileString);
                    reader = new BufferedReader( new InputStreamReader(fIS));
                    String line = reader.readLine();
                    PlayerInfo player;
                    String[] playerInfo;
                    String playerName, playerNumber, playerPosition;

                    while( line != null )
                    {
                        player = new PlayerInfo();
                        playerInfo = line.split(";");
                        playerName = playerInfo[0];
                        playerNumber = playerInfo[1];
                        playerPosition = playerInfo[2];

                        player.setName(playerName);
                        player.setNumber(playerNumber);
                        player.setPosition(playerPosition);

                        playerList.add(player);

                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void initializeWidgets() {
        initializeEditTexts();
        initializeImageButtons();
        initializeSpinners();
        initializeListViews();

        fillTeamDivisionSpinner();
    }
    private void setListeners() {
        setImageButtonListeners();
        setSpinnerListeners();
        setListViewSelectListener();
        setEditTextListeners();
    }
    private void initializeEditTexts() {
        editTextTeamName = findViewById(R.id.Roster_editTextTeamName);
    }
    private void initializeImageButtons() {
        iButtonHelp = findViewById(R.id.Roster_imageButtonHelp);
        iButtonCancel = findViewById(R.id.Roster_imageButtonCancel);
        iButtonSave = findViewById(R.id.Roster_imageButtonSave);
        iButtonDeletePlayer = findViewById(R.id.Roster_imageButtonDeletePlayer);
        iButtonAddPlayer = findViewById(R.id.Roster_imageButtonAddPlayer);
        iButtonEditPlayer = findViewById(R.id.Roster_imageButtonEditPlayer);
    }
    private void initializeSpinners() {
        spinnerTeamDivision = findViewById(R.id.Roster_spinnerDivision);
    }
    private void initializeListViews() {
        lViewPlayerList = findViewById(R.id.Roster_listViewPlayerList);
    }
    private void setImageButtonListeners() {
        setImageButtonDeletePlayerListener();
        setImageButtonAddPlayerListener();
        setImageButtonEditPlayerListener();
        setImageButtonHelpListener();
        setImageButtonCancelListener();
        setImageButtonSaveListener();
    }
    private void setSpinnerListeners() {
        setSpinnerTeamDivisionListener();
    }
    private void setEditTextListeners() {
        setEditTextTeamNameListener();
    }
    private void setImageButtonDeletePlayerListener() {
        iButtonDeletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = lViewPlayerListAdapter.getSelectedPlayerPosition();
                if (pos > -1) {
                    lViewPlayerListAdapter.remove(lViewPlayerListAdapter.getSelectedPlayer());
                    updatePlayerListView(null);
                } else {
                    dialogPlayerError("Delete");
                }
            }
        });
    }
    private void setImageButtonAddPlayerListener() {
        iButtonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogModifyPlayer(0);
            }
        });
    }
    private void setImageButtonEditPlayerListener() {
        iButtonEditPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogModifyPlayer(1);
            }
        });
    }
    private void setImageButtonHelpListener() {
        iButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(thisContext, ActivityHelp.class);
                intent.putExtra("type", helpType);
                startActivity(intent);
            }
        });
    }
    private void setImageButtonCancelListener() {
        iButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmCancel("CANCEL", CANCEL);
            }
        });
    }
    private void setImageButtonSaveListener() {
        iButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmCancel("SAVE", SAVE);
            }
        });
    }
    private void setEditTextTeamNameListener() {
        editTextTeamName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                teamName = charSequence.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                hideBars();
            }
        });

        editTextTeamName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideBars();
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
    }
    private void setSpinnerTeamDivisionListener() {
        spinnerTeamDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                teamDivision = possibleTeamDivision.get(i);
                hideBars();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                hideBars();
            }
        });

        spinnerTeamDivision.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideBars();
            }
        });
    }
    private void setListViewSelectListener() {
        lViewPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lViewPlayerListAdapter.setSelectedIndex(i);
            }
        });

    }
    private void updateSpinnerTeamDivision() {
        switch ( teamDivision )
        {
            case "U10":
                spinnerTeamDivision.setSelection(0);
                break;
            case "U12":
                spinnerTeamDivision.setSelection(1);
                break;
            case "U14":
                spinnerTeamDivision.setSelection(2);
                break;
            case "U16":
                spinnerTeamDivision.setSelection(3);
                break;
            case "U19":
                spinnerTeamDivision.setSelection(4);
                break;
            default:
                break;
        }
    }
    private void updatePlayerListView(PlayerInfo newPlayer) {
        if (newPlayer != null) {
            if (TYPE_OF_MODIFY == 1) {
                int selectedPosition = lViewPlayerListAdapter.getSelectedPlayerPosition();
                playerList.set(selectedPosition, newPlayer);
            } else {
                playerList.add(newPlayer);
            }
        }

        lViewPlayerListAdapter = new ListViewPlayerList(thisContext, 0, playerList);
        lViewPlayerList.setAdapter(lViewPlayerListAdapter);
        playerList = lViewPlayerListAdapter.getPlayerList();
        numberOfPlayers = playerList.size();
    }
    private void fillTeamDivisionSpinner() {
        ArrayAdapter<String> spinnerAdapter;
        spinnerAdapter = new ArrayAdapter<>(thisContext, R.layout.not_field_spinner_item, possibleTeamDivision);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeamDivision.setAdapter(spinnerAdapter);
        spinnerTeamDivision.setPrompt("Pick a Division");
    }
    private void fillTeamDivisionArray() {
        possibleTeamDivision = new ArrayList<>();

        possibleTeamDivision.add(formatYears(10, getBirthYear(8), getBirthYear(10)));
        possibleTeamDivision.add(formatYears(12, getBirthYear(10), getBirthYear(12)));
        possibleTeamDivision.add(formatYears(14, getBirthYear(12), getBirthYear(14)));
        possibleTeamDivision.add(formatYears(16, getBirthYear(14), getBirthYear(16)));
        possibleTeamDivision.add(formatYears(19, getBirthYear(16), getBirthYear(19)));
    }
    private void dialogModifyPlayer(final int type) {

        String playerInfo;
        TYPE_OF_MODIFY = type;
        int MODIFY_PLAYER_RESULT = 0;
        if (type == 0) {
            playerInfo = "";

            Intent intent = new Intent(this, ActivityModifyPlayer.class);
            intent.putExtra("playerInfo", playerInfo);
            this.startActivityForResult(intent, MODIFY_PLAYER_RESULT);
        } else {
            if (lViewPlayerListAdapter.getSelectedPlayerPosition() > -1) {
                PlayerInfo oldPlayer = lViewPlayerListAdapter.getSelectedPlayer();
                playerInfo = oldPlayer.getName() + ";" + oldPlayer.getNumber() + ";" + oldPlayer.getPosition();

                Intent intent = new Intent(this, ActivityModifyPlayer.class);
                intent.putExtra("playerInfo", playerInfo);
                this.startActivityForResult(intent, MODIFY_PLAYER_RESULT);
            } else {
                dialogPlayerError("Edit");
            }
        }

    }
    private void dialogConfirmCancel(String title, final int type) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        if ( ( validTeamName() && validTeamCount() && uniquePlayerNumbers() ) || type == CANCEL) {
            builder.setTitle("Are you sure you want to " + title + " :")
                    .setMessage("Team Name: " + teamName + "\nTeam Division: " + teamDivision + "\nNumber Of Players: " + numberOfPlayers)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (type) {
                                case CANCEL:
                                    handleCancelType();
                                    break;

                                case SAVE:
                                    handleSaveType();
                                    break;

                                default:
                                    break;

                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
        else
        {
            dialogSaveError();
        }
    }
    private void handleCancelType(){
        setResult(RESULT_CANCELED);
        hideBars();
        finish();
    }
    private void handleSaveType(){
        Collections.sort(playerList);
        String teamDir = getFilesDir() + File.separator + "teams" + File.separator + teamName + "_" + formatDivision();
        String teamRosterFile = teamDir + File.separator + "roster";
        String rosterString = formatRoster();
        FileOutputStream outputStream;

        try{
            if( ! new File(teamDir).exists() ) {
                File directory = new File(teamDir);
                directory.mkdirs();
            }

            outputStream = new FileOutputStream(new File( teamRosterFile ) , false  );

            outputStream.write(rosterString.getBytes());
            outputStream.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        updateLineupFile();
        setResult(RESULT_OK);
        hideBars();

        finish();
    }
    private void updateLineupFile(){
        String teamDir = getIntent().getStringExtra("teamName");
        String lineupFileString = getFilesDir() + File.separator + "teams" + File.separator + teamDir + File.separator + "lineup";

        File lineupFile = new File(lineupFileString);

        if( lineupFile.exists() ){
            handleLineupFileExists(lineupFileString);
        }
        else{
            handleLineupFileAbsent(lineupFile);
        }
    }
    private void handleLineupFileExists( String lineupFileString ){
        FileInputStream fIS;
        FileOutputStream fOS;
        BufferedReader reader;
        String line, playerNum;
        try {
            fIS = new FileInputStream(lineupFileString);
            reader = new BufferedReader(new InputStreamReader(fIS));
            ArrayList<String> playerNumbers = new ArrayList<>();
            StringBuilder lineupString = new StringBuilder("");

            for( int i = 0; i < numberOfPlayers; ++i )
            {
                playerNumbers.add(playerList.get(i).getNumber());
            }

            for( int q = 0; q < 4; ++q ){
                for( int p = 0; p < numberOfPlayers; ++p ) {
                    line = reader.readLine();
                    playerNum = line.replace("#", "");
                    if( playerNumbers.contains(playerNum) || playerNum.equals("Empty")){
                        lineupString.append("#" + playerNum);
                    }
                    else{
                        lineupString.append("Empty");
                    }
                    lineupString.append("\n");
                }
            }

            fOS = new FileOutputStream(new File(lineupFileString), false  );

            fOS.write(lineupString.toString().getBytes());

            fOS.close();
            fIS.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void handleLineupFileAbsent(File lineupFile){
        StringBuilder defaultLineup = new StringBuilder("");
        FileOutputStream fOS;

        for( int i = 0; i < numberOfPlayers * 4; ++i ){
            defaultLineup.append("Empty").append("\n");
        }
        try{
            fOS = new FileOutputStream(lineupFile, false  );

            fOS.write(defaultLineup.toString().getBytes());
            fOS.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void dialogPlayerError(String action) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        builder.setTitle(action + " Error")
                .setMessage("No player selected to " + action.toLowerCase())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hideBars();
                    }
                })
                .show();
    }
    private void dialogSaveError() {
        AlertDialog.Builder builder;
        String error = "";

        if( !validTeamCount() )
        {
            error = error + "\nNumber of Players is Insufficient\n\tHave: " + Integer.toString(numberOfPlayers) + "\n\tNeed: " + Integer.toString(minimumPlayers);
        }
        if( !validTeamName() )
        {
            error = error + "\nTeam Name cannot be Blank or Contain Special Characters";
        }
        if( !uniquePlayerNumbers() )
        {
            error = error + "\nPlayers must have unique numbers";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        builder.setTitle("Save Error")
                .setMessage( error )
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hideBars();
                    }
                })
                .show();
    }
    private boolean uniquePlayerNumbers(){
        boolean flag = true;
        ArrayList<String> uniqueNumbers = new ArrayList<>();
        String num;
        PlayerInfo player;

        for( int i = 0; i < numberOfPlayers; ++i )
        {
            player = playerList.get(i);
            num = player.getNumber();

            if( uniqueNumbers.contains(num))
            {
                flag = false;
                break;
            }
            else
            {
                uniqueNumbers.add(num);
            }
        }

        return flag;
    }
    private boolean validTeamName() {
        boolean flag = false;
        String regex = "^[a-zA-Z0-9]+( +[a-zA-Z0-9]+)*$";
        Pattern p = Pattern.compile(regex);

        if (teamName != null) {

            Matcher m = p.matcher(teamName);
            if (m.find()) {
                flag = true;
            }
        }

        return flag;
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
    private boolean validTeamCount() {
        boolean flag = true;

        switch( formatDivision() )
        {
            case "U10":
                if( numberOfPlayers < 7 )
                {
                    flag = false;
                }
                break;

            case "U12":
                if( numberOfPlayers < 9 )
                {
                    flag = false;
                }
                break;

            case "U14":
            case "U16":
            case "U19":
                if( numberOfPlayers < 11 )
                {
                    flag = false;
                }
                break;
        }

        return flag;
    }
    private int getBirthYear(int age) {
        Calendar calendar;
        int birthYear, currentYear;

        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);

        birthYear = currentYear - age;

        return birthYear;
    }
    private String formatYears(int age, int younger, int older) {
        return "U-" + Integer.toString(age) + ": " + Integer.toString(older) + " - " + Integer.toString(younger);
    }
    private String formatDivision() {
        String format;
        format = Character.toString(teamDivision.charAt(0)) + Character.toString(teamDivision.charAt(2)) + Character.toString(teamDivision.charAt(3));

        switch( format )
        {
            case "U10":
                minimumPlayers = 7;
                break;

            case "U12":
                minimumPlayers = 9;
                break;

            case "U14":
            case "U16":
            case "U19":
                minimumPlayers = 11;
                break;
        }

        return format;
    }
    private String formatRoster() {
        StringBuilder format = new StringBuilder("");

        for( int i = 0; i < numberOfPlayers; ++i )
        {
            if( i == numberOfPlayers - 1)
            {
                format.append(playerList.get(i).toString());
            }
            else {
                format.append(playerList.get(i).toString()).append("\n");
            }
        }

        return format.toString();
    }
    private void hideKeyboard( View view ){
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String number = data.getStringExtra("number");
            String position = data.getStringExtra("position");
            PlayerInfo player = new PlayerInfo();

            player.setName(name);
            player.setNumber(number);
            player.setPosition(position);

            updatePlayerListView(player);
        }
        hideBars();
    }
}

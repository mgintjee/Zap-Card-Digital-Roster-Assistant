package com.happybananastudio.zapcarddigitalrosterassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mgint on 1/3/2018.
 */

public class ActivityModifyPlayer extends Activity {

    private Context thisContext = this;
    private TextView dialogTitle, dialogError;
    private EditText editTextPlayerName, editTextPlayerNumber;
    private CheckBox checkBoxGoalie, checkBoxDefender, checkBoxMidfielder, checkBoxForward;
    private Button buttonSave, buttonCancel;
    private NumberPicker numberPickerGoalie, numberPickerDefender, numberPickerMidfielder, numberPickerForward;
    private String playerInfo, playerName, playerNumber, playerPosition, goalie, defender, midfielder, forward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_modify_player);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFinishOnTouchOutside(false);
        hideBars();

        initializeStrings();
        initializeWidgets();
        setWidgetListeners();

        Intent intent = getIntent();
        playerInfo = intent.getStringExtra("playerInfo");

        String title;
        if( playerInfo.equals(""))
        {
            title = "Create New Player";
        }
        else
        {
            title = "Modify Old Player";
            derivePlayerInfo();
            applyPlayerInfo();
            applyPlayerPosition();
        }

        dialogTitle.setText(title);
    }

    private void initializeWidgets() {
        initializeEditTexts();
        initializeCheckBoxes();
        initializeButtons();
        initializeTextViews();
        initializeNumberPickers();
    }
    private void setWidgetListeners() {
        setCheckBoxListeners();
        setButtonListeners();
        setEditTextListeners();
        setNumberPickerListeners();
    }
    private void setEditTextListeners() {
        setEditTextPlayerNameListener();
        setEditTextPlayerNumberListener();
    }
    private void setCheckBoxListeners() {
        setCheckBoxGoalieListener();
        setCheckBoxDefenderListener();
        setCheckBoxMidfielderListener();
        setCheckBoxForwardListener();
    }
    private void setButtonListeners() {
        setButtonCancelListener();
        setButtonSaveListener();
    }
    private void setNumberPickerListeners(){
        setNumberPickerGoalieListener();
        setNumberPickerDefenderListener();
        setNumberPickerMidfielderListener();
        setNumberPickerForwardListener();
    }
    private void setEditTextPlayerNameListener() {
        editTextPlayerName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                playerName = charSequence.toString();
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable){
                hideBars();}
        });

        editTextPlayerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                    hideBars();
                }
            }
        });
    }
    private void setEditTextPlayerNumberListener() {
        editTextPlayerNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                playerNumber = charSequence.toString();
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable){
                hideBars();}
        });

        editTextPlayerNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                    hideBars();
                }
            }
        });
    }
    private void setCheckBoxGoalieListener() {
        checkBoxGoalie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxGoalie.isChecked())
                {
                    numberPickerGoalie.setVisibility(View.VISIBLE);
                    goalie = "1";
                }
                else
                {
                    numberPickerGoalie.setVisibility(View.INVISIBLE);
                    goalie = "0";
                }
            }
        });
    }
    private void setCheckBoxDefenderListener() {
        checkBoxDefender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxDefender.isChecked())
                {
                    numberPickerDefender.setVisibility(View.VISIBLE);
                    defender = "1";
                }
                else
                {
                    numberPickerDefender.setVisibility(View.INVISIBLE);
                    defender = "0";
                }
            }
        });
    }
    private void setCheckBoxMidfielderListener() {
        checkBoxMidfielder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxMidfielder.isChecked())
                {
                    numberPickerMidfielder.setVisibility(View.VISIBLE);
                    midfielder = "1";
                }
                else
                {
                    numberPickerMidfielder.setVisibility(View.INVISIBLE);
                    midfielder = "0";
                }
            }
        });
    }
    private void setCheckBoxForwardListener() {
        checkBoxForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxForward.isChecked())
                {
                    numberPickerForward.setVisibility(View.VISIBLE);
                    forward = "1";
                }
                else
                {
                    numberPickerForward.setVisibility(View.INVISIBLE);
                    forward = "0";
                }
            }
        });
    }
    private void setButtonCancelListener() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult( RESULT_CANCELED );
                finish();
            }
        });
    }
    private void setButtonSaveListener() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean error = false;
                StringBuilder errorMessage = new StringBuilder("");
                String errorMessageString = "";

                if( !validPlayerName() )
                {
                    error = true;
                    errorMessage.append("\nName: Can't be blank or contain special chars");
                }
                if( playerNumber.equals("-") || playerNumber.equals("") )
                {
                    error = true;
                    errorMessage.append("\nNumber: Can't be blank");
                }
                if( !validPlayerPosition() )
                {
                    error = true;
                    errorMessage.append("\nPosition: Can't be blank");
                }
                errorMessageString = errorMessage.toString();
                if( !error )
                {
                    Intent data = new Intent();

                    data.putExtra("name", playerName);
                    data.putExtra("number", playerNumber);
                    data.putExtra("position", playerPosition);

                    setResult(RESULT_OK, data);

                    finish();
                }
                else
                {
                    dialogPlayerError(errorMessageString);
                }
            }
        });
    }
    private void setNumberPickerGoalieListener(){
        numberPickerGoalie.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                goalie = Integer.toString(newVal);
            }
        });
    }
    private void setNumberPickerDefenderListener(){
        numberPickerDefender.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                defender = Integer.toString(newVal);
            }
        });
    }
    private void setNumberPickerMidfielderListener(){
        numberPickerMidfielder.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                midfielder = Integer.toString(newVal);
            }
        });
    }
    private void setNumberPickerForwardListener(){
        numberPickerForward.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                forward = Integer.toString(newVal);
            }
        });
    }
    private void initializeEditTexts() {
        editTextPlayerName = findViewById(R.id.Player_editTextPlayerName);
        editTextPlayerNumber = findViewById(R.id.Player_editTextPlayerNumber);
    }
    private void initializeCheckBoxes() {
        checkBoxGoalie = findViewById(R.id.Player_checkBoxGoalie);
        checkBoxDefender = findViewById(R.id.Player_checkBoxDefender);
        checkBoxMidfielder = findViewById(R.id.Player_checkBoxMidfielder);
        checkBoxForward = findViewById(R.id.Player_checkBoxForward);
    }
    private void initializeButtons() {
        buttonCancel = findViewById(R.id.Player_buttonCancel);
        buttonSave = findViewById(R.id.Player_buttonSave);
    }
    private void initializeTextViews(){
        dialogTitle = findViewById(R.id.Player_textViewDialogTitle);
    }
    private void initializeNumberPickers(){
        numberPickerGoalie = findViewById(R.id.Player_numberPickerGoalie);
        numberPickerDefender = findViewById(R.id.Player_numberPickerDefender);
        numberPickerMidfielder = findViewById(R.id.Player_numberPickerMidfielder);
        numberPickerForward = findViewById(R.id.Player_numberPickerForward);

        setNumberPickerTextColor( numberPickerGoalie, Color.BLACK);
        setNumberPickerTextColor( numberPickerDefender, Color.BLACK);
        setNumberPickerTextColor( numberPickerMidfielder, Color.BLACK);
        setNumberPickerTextColor( numberPickerForward, Color.BLACK);

        fillNumberPicker(numberPickerGoalie);
        fillNumberPicker(numberPickerDefender);
        fillNumberPicker(numberPickerMidfielder);
        fillNumberPicker(numberPickerForward);
    }
    private void fillNumberPicker( NumberPicker numberPicker ){
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(9);
        numberPicker.setDisplayedValues(values);
    }
    private void derivePlayerInfo() {
        String[] splitPlayerInfo = playerInfo.split(";");

        playerName = splitPlayerInfo[0];
        playerNumber = splitPlayerInfo[1];
        playerPosition = splitPlayerInfo[2];

    }
    private void applyPlayerInfo() {
        editTextPlayerName.setText(playerName);
        editTextPlayerNumber.setText(playerNumber);
    }
    private void applyPlayerPosition(){
        char G, D, M, F;
        G = playerPosition.charAt(0);
        D = playerPosition.charAt(1);
        M = playerPosition.charAt(2);
        F = playerPosition.charAt(3);

        if( G != '0' )
        {
            goalie = Character.toString(G);
            checkBoxGoalie.toggle();
            numberPickerGoalie.setVisibility(View.VISIBLE);
            numberPickerGoalie.setValue(Character.getNumericValue(G));
        }
        if( D != '0' )
        {
            defender = Character.toString(D);
            checkBoxDefender.toggle();
            numberPickerDefender.setVisibility(View.VISIBLE);
            numberPickerDefender.setValue(Character.getNumericValue(D));
        }
        if( M != '0' )
        {
            midfielder = Character.toString(M);
            checkBoxMidfielder.toggle();
            numberPickerMidfielder.setVisibility(View.VISIBLE);
            numberPickerMidfielder.setValue(Character.getNumericValue(M));
        }
        if( F != '0' )
        {
            forward = Character.toString(F);
            checkBoxForward.toggle();
            numberPickerForward.setVisibility(View.VISIBLE);
            numberPickerForward.setValue(Character.getNumericValue(F));
        }
    }
    private boolean validPlayerName() {
        boolean flag = false;
        String regex = "^[a-zA-Z]+ ?[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(playerName);

        if( m.find() )
        {
            flag = true;
        }

        return flag;
    }
    private boolean validPlayerPosition() {
        boolean flag = false;

        if( !goalie.equals("0" ) || !defender.equals("0" ) || !midfielder.equals("0" ) || !forward.equals("0") )
        {
            flag = true;
            playerPosition = goalie + defender + midfielder + forward;
        }

        return flag;
    }
    private void initializeStrings() {
        playerName = "-";
        playerNumber = "-";
        goalie = "0";
        defender = "0";
        midfielder = "0";
        forward = "0";
    }
    private void hideKeyboard( View view ){
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public void setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText) try {
                Field selectorWheelPaintField = numberPicker.getClass()
                        .getDeclaredField("mSelectorWheelPaint");
                selectorWheelPaintField.setAccessible(true);
                ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                ((EditText) child).setTextColor(color);
                numberPicker.invalidate();
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {
            }
        }
    }
    private void hideBars(){

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
    private void dialogPlayerError(String error) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        builder.setTitle("Player Error")
                .setMessage(error)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hideBars();
                    }
                })
                .show();
    }
    @Override
    public void onResume() {
        super.onResume();
        hideBars();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideBars();
        }
    }
    private void toastThis(String message){
        Toast.makeText(thisContext,
                message,
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

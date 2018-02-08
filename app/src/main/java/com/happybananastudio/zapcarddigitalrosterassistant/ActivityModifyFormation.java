package com.happybananastudio.zapcarddigitalrosterassistant;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mgint on 1/8/2018.
 */

public class ActivityModifyFormation extends AppCompatActivity {

    private final int CANCEL = 0;
    private final int SAVE = 1;
    private int TYPE_OF_MODIFY = 0;

    private Context thisContext = this;
    private TextView textViewHeader, textViewBody;
    private String formationName, helpHeader, helpBody, teamDivision = null;
    private final String helpType = "modifyFormation";

    // Global widgets because they encourage laziness
    private EditText editTextFormationName;
    private Spinner spinnerQuickAdd;
    private TextView textViewPlayerCount, textViewPlayerRequired;

    // Footer Image Buttons
    private ImageButton imageButtonCancel, imageButtonHelp, imageButtonSave;

    // Player Positions
    private Spinner
            spinnerPlayer00, spinnerPlayer01, spinnerPlayer02, spinnerPlayer03, spinnerPlayer04,
            spinnerPlayer10, spinnerPlayer11, spinnerPlayer12, spinnerPlayer13, spinnerPlayer14,
            spinnerPlayer20, spinnerPlayer21, spinnerPlayer22, spinnerPlayer23, spinnerPlayer24,
            spinnerPlayer30, spinnerPlayer31, spinnerPlayer32, spinnerPlayer33, spinnerPlayer34,
            spinnerPlayer40, spinnerPlayer41, spinnerPlayer42, spinnerPlayer43, spinnerPlayer44,
            spinnerPlayer50;
    private ArrayList<String> playerPositions;
    private int maxPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_modify_formation);
        hideBars();

        getIntentInfo();

        initializeWidgets();
        updatePlayerCountTextView();
        fillSpinners();
        setWidgetListeners();
    }

    private void initializeWidgets(){
        initializeTextViews();
        initializeImageButtons();
        initializeSpinners();
    }

    private void setWidgetListeners(){
        setImageButtonListeners();
        setSpinnerListeners();
        setEditTextFormationNameListener();
    }

    private void fillSpinners() {
        fillQuickAddSpinner();
        fillFieldSpinners();
    }

    private void setSpinnerListeners(){
        setSpinnerQuickAddListener();
        setFieldSpinnerListeners();
    }

    private void initializeTextViews(){
        textViewPlayerCount = findViewById(R.id.Formation_textViewPlayerCount);
        textViewPlayerRequired = findViewById(R.id.Formation_textViewPlayerRequired);
        editTextFormationName = findViewById(R.id.Formation_editTextFormationName);
    }

    private void initializeImageButtons(){
        imageButtonCancel = findViewById(R.id.Formation_imageButtonCancel);
        imageButtonHelp = findViewById(R.id.Formation_imageButtonHelp);
        imageButtonSave = findViewById(R.id.Formation_imageButtonSave);
    }

    private void initializeSpinners(){
        initializeHeaderSpinners();
        initializeFieldSpinners();
    }

    private void initializeHeaderSpinners(){
        spinnerQuickAdd = findViewById(R.id.Formation_spinnerQuickAdd);
    }

    private void initializeFieldSpinners() {
        spinnerPlayer00 = findViewById(R.id.Formation_spinnerPlayer00);
        spinnerPlayer01 = findViewById(R.id.Formation_spinnerPlayer01);
        spinnerPlayer02 = findViewById(R.id.Formation_spinnerPlayer02);
        spinnerPlayer03 = findViewById(R.id.Formation_spinnerPlayer03);
        spinnerPlayer04 = findViewById(R.id.Formation_spinnerPlayer04);
        spinnerPlayer10 = findViewById(R.id.Formation_spinnerPlayer10);
        spinnerPlayer11 = findViewById(R.id.Formation_spinnerPlayer11);
        spinnerPlayer12 = findViewById(R.id.Formation_spinnerPlayer12);
        spinnerPlayer13 = findViewById(R.id.Formation_spinnerPlayer13);
        spinnerPlayer14 = findViewById(R.id.Formation_spinnerPlayer14);
        spinnerPlayer20 = findViewById(R.id.Formation_spinnerPlayer20);
        spinnerPlayer21 = findViewById(R.id.Formation_spinnerPlayer21);
        spinnerPlayer22 = findViewById(R.id.Formation_spinnerPlayer22);
        spinnerPlayer23 = findViewById(R.id.Formation_spinnerPlayer23);
        spinnerPlayer24 = findViewById(R.id.Formation_spinnerPlayer24);
        spinnerPlayer30 = findViewById(R.id.Formation_spinnerPlayer30);
        spinnerPlayer31 = findViewById(R.id.Formation_spinnerPlayer31);
        spinnerPlayer32 = findViewById(R.id.Formation_spinnerPlayer32);
        spinnerPlayer33 = findViewById(R.id.Formation_spinnerPlayer33);
        spinnerPlayer34 = findViewById(R.id.Formation_spinnerPlayer34);
        spinnerPlayer40 = findViewById(R.id.Formation_spinnerPlayer40);
        spinnerPlayer41 = findViewById(R.id.Formation_spinnerPlayer41);
        spinnerPlayer42 = findViewById(R.id.Formation_spinnerPlayer42);
        spinnerPlayer43 = findViewById(R.id.Formation_spinnerPlayer43);
        spinnerPlayer44 = findViewById(R.id.Formation_spinnerPlayer44);
        spinnerPlayer50 = findViewById(R.id.Formation_spinnerPlayer50);
    }

    private void setFieldSpinnerListeners() {
        // Row 1
        setSpinnerPlayer00Listener();
        setSpinnerPlayer01Listener();
        setSpinnerPlayer02Listener();
        setSpinnerPlayer03Listener();
        setSpinnerPlayer04Listener();
        // Row 2
        setSpinnerPlayer10Listener();
        setSpinnerPlayer11Listener();
        setSpinnerPlayer12Listener();
        setSpinnerPlayer13Listener();
        setSpinnerPlayer14Listener();
        // Row 2
        setSpinnerPlayer20Listener();
        setSpinnerPlayer21Listener();
        setSpinnerPlayer22Listener();
        setSpinnerPlayer23Listener();
        setSpinnerPlayer24Listener();
        // Row 2
        setSpinnerPlayer30Listener();
        setSpinnerPlayer31Listener();
        setSpinnerPlayer32Listener();
        setSpinnerPlayer33Listener();
        setSpinnerPlayer34Listener();
        // Row 2
        setSpinnerPlayer40Listener();
        setSpinnerPlayer41Listener();
        setSpinnerPlayer42Listener();
        setSpinnerPlayer43Listener();
        setSpinnerPlayer44Listener();
    }
    private void setImageButtonListeners(){
        setImageButtonSaveListener();
        setImageButtonCancelListener();
        setImageButtonHelpListener();
    }
    private void setImageButtonCancelListener(){
        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmCancel("CANCEL", CANCEL);
            }
        });
    }
    private void setImageButtonSaveListener(){
        imageButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirmCancel("SAVE", SAVE);
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
    private void setEditTextFormationNameListener() {
        editTextFormationName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                formationName = charSequence.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                hideBars();
            }
        });

        editTextFormationName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                    hideBars();
                }
            }
        });
    }
    private void setSpinnerQuickAddListener(){
        spinnerQuickAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> rolePositions = new ArrayList<>();
                String role, pos;
                switch (position) {
                            case 0:
                                break;
                            // Forward Roles
                            case 1: // Flat 3
                                rolePositions.add("F00");
                                rolePositions.add("F02");
                                rolePositions.add("F04");
                                break;

                            case 2: // Flat 2
                                rolePositions.add("F01");
                                rolePositions.add("F03");
                                break;

                            case 3: // Flat 1
                                rolePositions.add("F02");
                                break;
                            // Midfield Roles
                            case 4: // Pentagon 5
                                rolePositions.add("M10");
                                rolePositions.add("M12");
                                rolePositions.add("M14");
                                rolePositions.add("M21");
                                rolePositions.add("M23");
                                break;
                            case 5: // Flat 4
                                rolePositions.add("M20");
                                rolePositions.add("M21");
                                rolePositions.add("M23");
                                rolePositions.add("M24");
                                break;
                            case 6: // Flat 3
                                rolePositions.add("M21");
                                rolePositions.add("M22");
                                rolePositions.add("M23");
                                break;
                            case 7: // Triangle(UP) 3
                                rolePositions.add("M12");
                                rolePositions.add("M21");
                                rolePositions.add("M23");
                                break;
                            case 8: // Triangle(DOWN) 3
                                rolePositions.add("M11");
                                rolePositions.add("M13");
                                rolePositions.add("M22");
                                break;

                            // Defense Roles
                            case 9: // Flat 5
                                rolePositions.add("D30");
                                rolePositions.add("D31");
                                rolePositions.add("D32");
                                rolePositions.add("D33");
                                rolePositions.add("D34");
                                break;
                            case 10: // Flat 4
                                rolePositions.add("D30");
                                rolePositions.add("D31");
                                rolePositions.add("D33");
                                rolePositions.add("D34");
                                break;
                            case 11: // Diamond 4
                                rolePositions.add("D30");
                                rolePositions.add("D32");
                                rolePositions.add("D34");
                                rolePositions.add("D42");
                                break;
                            case 12: // Flat 3
                                rolePositions.add("D31");
                                rolePositions.add("D32");
                                rolePositions.add("D33");
                                break;

                            default:
                                break;
                }
                if( rolePositions.size() + playerPositions.size() <= maxPlayers ) {

                    for (int i = 0; i < rolePositions.size(); ++i) {
                        role = rolePositions.get(i).substring(0, 1);
                        pos = rolePositions.get(i).substring(1);

                        removePositionFromList(pos);
                        playerPositions.add(role + pos);

                        int localSpinnerID = getStringIdentifier("Formation_spinnerPlayer" + pos);

                        Spinner localSpinner = findViewById(localSpinnerID);

                        localSpinner.setSelection( getPositionFromRole(role) );
                        localSpinner.setBackground(getResources().getDrawable(R.drawable.field_player));
                    }
                }
                else{
                    dialogQuickAddError(rolePositions.size());
                }

                updatePlayerCountTextView();
                spinnerQuickAdd.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    // Row 1
    private void setSpinnerPlayer00Listener(){
        spinnerPlayer00.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "00";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer00.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer00.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer01Listener(){
        spinnerPlayer01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "01";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer01.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer01.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer02Listener(){
        spinnerPlayer02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "02";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer02.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer02.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer03Listener(){
        spinnerPlayer03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "03";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer03.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer03.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer04Listener(){
        spinnerPlayer04.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "04";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer04.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer04.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    // Row 2
    private void setSpinnerPlayer10Listener(){
        spinnerPlayer10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "10";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer10.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer10.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer11Listener(){
        spinnerPlayer11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "11";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer11.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer11.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer12Listener(){
        spinnerPlayer12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "12";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer12.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer12.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer13Listener(){
        spinnerPlayer13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "13";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);
                if( position == 0){
                    spinnerPlayer13.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer13.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer14Listener(){
        spinnerPlayer14.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "14";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer14.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer14.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    // Row 3
    private void setSpinnerPlayer20Listener(){
        spinnerPlayer20.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "20";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer20.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer20.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer21Listener(){
        spinnerPlayer21.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "21";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer21.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer21.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer22Listener(){
        spinnerPlayer22.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "22";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer22.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer22.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer23Listener(){
        spinnerPlayer23.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "23";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer23.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer23.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer24Listener(){
        spinnerPlayer24.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "24";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer24.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer24.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    // Row 4
    private void setSpinnerPlayer30Listener(){
        spinnerPlayer30.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "30";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer30.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer30.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer31Listener(){
        spinnerPlayer31.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "31";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer31.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer31.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer32Listener(){
        spinnerPlayer32.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "32";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer32.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer32.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer33Listener(){
        spinnerPlayer33.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "33";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer33.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer33.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer34Listener(){
        spinnerPlayer34.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "34";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer34.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer34.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    // Row 5
    private void setSpinnerPlayer40Listener(){
        spinnerPlayer40.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "40";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer40.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer40.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer41Listener(){
        spinnerPlayer41.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "41";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer41.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer41.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer42Listener(){
        spinnerPlayer42.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "42";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer42.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer42.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer43Listener(){
        spinnerPlayer43.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "43";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer43.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer43.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPlayer44Listener(){
        spinnerPlayer44.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = "44";
                String role = getRoleFromPosition(position);
                String posRole = role + pos;

                removePositionFromList(pos);

                if( position == 0){
                    spinnerPlayer44.setBackgroundResource(R.drawable.field_empty);
                    showBlankPositions();
                }
                else{
                    if (fieldNotFull()) {
                        playerPositions.add(posRole);
                        spinnerPlayer44.setBackgroundResource(R.drawable.field_player);
                        if (!fieldNotFull()) {
                            hideBlankPositions();
                        }
                    }

                }
                updatePlayerCountTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private String getRoleFromPosition(int position){
        String pos;
        switch (position){
            case 1:
                pos = "D";
                break;
            case 2:
                pos = "M";
                break;
            case 3:
                pos = "F";
                break;
            default:
                pos = "";
                break;
        }

        return pos;
    }
    private int getPositionFromRole( String role ){
        int pos;

        switch( role ){
            case "D":
                pos = 1;
                break;
            case "M":
                pos = 2;
                break;
            case "F":
                pos = 3;
                break;
            default:
                pos = 0;
                break;
        }

        return pos;
    }
    private void updatePlayerCountTextView(){
        String playerCount = "Number of Players: ";
        String playerRequired = " Required: ";
        String countTemp = playerCount + Integer.toString(playerPositions.size());
        String reqTemp = playerRequired + Integer.toString(maxPlayers);
        textViewPlayerCount.setText(countTemp);
        textViewPlayerRequired.setText(reqTemp);
    }
    private void getIntentInfo() {
        String[] teamInfo;
        FileInputStream fIS;
        BufferedReader reader;
        String teamName, formationName, formationFileString, teamDir;
        playerPositions = new ArrayList<>();
        playerPositions.add("G50");

        teamName = getIntent().getStringExtra("teamName");
        teamDivision = getIntent().getStringExtra("teamDivision");
        formationName = getIntent().getStringExtra("formationName");

        teamDir = teamName + "_" + teamDivision;

        handleDivision( teamDivision );

        formationFileString  = getFilesDir() + File.separator + "formations" + File.separator + teamDivision + File.separator + File.separator + formationName;

        if ( teamDir != null && !teamDir.equals("") ) {
        }
    }
    private void removePositionFromList( String pos ){
        playerPositions.remove("D" + pos);
        playerPositions.remove("M" + pos);
        playerPositions.remove("F" + pos);
    }
    private void handleDivision( String division) {
        switch( division)
        {
            case "U10":
                maxPlayers = 7;
                break;

            case "U12":
                maxPlayers = 9;
                break;

            case "U14":
            case "U16":
            case "U19":
                maxPlayers = 11;
                break;
        }
    }
    private void fillQuickAddSpinner(){
        ArrayAdapter<String> spinnerAdapter;
        ArrayList<String> quickAddComponents = new ArrayList<>();

        // Let the user go back if accident
        quickAddComponents.add("Nothing");

        // Forward Roles
        quickAddComponents.add("Forward: Flat 3");
        quickAddComponents.add("Forward: Flat 2");
        quickAddComponents.add("Forward: Flat 1");

        // Midfielder Roles
        quickAddComponents.add("Midfield: Pentagon 5");
        quickAddComponents.add("Midfield: Flat 4");
        quickAddComponents.add("Midfield: Flat 3");
        quickAddComponents.add("Midfield: Triangle(UP) 3");
        quickAddComponents.add("Midfield: Triangle(DOWN) 3");

        // Defender Roles
        quickAddComponents.add("Defense: Flat 5");
        quickAddComponents.add("Defense: Flat 4");
        quickAddComponents.add("Defense: Diamond 4");
        quickAddComponents.add("Defense: Flat 3");

        spinnerAdapter = new ArrayAdapter<>(thisContext, R.layout.not_field_spinner_item, quickAddComponents);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerQuickAdd.setAdapter(spinnerAdapter);
        spinnerQuickAdd.setPrompt("Quick Add a Common Formation Component");
    }
    private void fillFieldSpinners() {
        ArrayAdapter<String> spinnerAdapter;
        ArrayList<String> positions = new ArrayList<>();
        positions.add("Empty");
        positions.add("Def.");
        positions.add("Mid.");
        positions.add("For.");

        spinnerAdapter = new ArrayAdapter<>(thisContext, R.layout.spinner_item, positions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Row 1
        spinnerPlayer00.setAdapter(spinnerAdapter);
        spinnerPlayer01.setAdapter(spinnerAdapter);
        spinnerPlayer02.setAdapter(spinnerAdapter);
        spinnerPlayer03.setAdapter(spinnerAdapter);
        spinnerPlayer04.setAdapter(spinnerAdapter);
        spinnerPlayer00.setPrompt("Choose Role of this Position:");
        spinnerPlayer01.setPrompt("Choose Role of this Position:");
        spinnerPlayer02.setPrompt("Choose Role of this Position:");
        spinnerPlayer03.setPrompt("Choose Role of this Position:");
        spinnerPlayer04.setPrompt("Choose Role of this Position:");
        // Row 2
        spinnerPlayer10.setAdapter(spinnerAdapter);
        spinnerPlayer11.setAdapter(spinnerAdapter);
        spinnerPlayer12.setAdapter(spinnerAdapter);
        spinnerPlayer13.setAdapter(spinnerAdapter);
        spinnerPlayer14.setAdapter(spinnerAdapter);
        spinnerPlayer10.setPrompt("Choose Role of this Position:");
        spinnerPlayer11.setPrompt("Choose Role of this Position:");
        spinnerPlayer12.setPrompt("Choose Role of this Position:");
        spinnerPlayer13.setPrompt("Choose Role of this Position:");
        spinnerPlayer14.setPrompt("Choose Role of this Position:");
        // Row 3
        spinnerPlayer20.setAdapter(spinnerAdapter);
        spinnerPlayer21.setAdapter(spinnerAdapter);
        spinnerPlayer22.setAdapter(spinnerAdapter);
        spinnerPlayer23.setAdapter(spinnerAdapter);
        spinnerPlayer24.setAdapter(spinnerAdapter);
        spinnerPlayer20.setPrompt("Choose Role of this Position:");
        spinnerPlayer21.setPrompt("Choose Role of this Position:");
        spinnerPlayer22.setPrompt("Choose Role of this Position:");
        spinnerPlayer23.setPrompt("Choose Role of this Position:");
        spinnerPlayer24.setPrompt("Choose Role of this Position:");
        // Row 4
        spinnerPlayer30.setAdapter(spinnerAdapter);
        spinnerPlayer31.setAdapter(spinnerAdapter);
        spinnerPlayer32.setAdapter(spinnerAdapter);
        spinnerPlayer33.setAdapter(spinnerAdapter);
        spinnerPlayer34.setAdapter(spinnerAdapter);
        spinnerPlayer30.setPrompt("Choose Role of this Position:");
        spinnerPlayer31.setPrompt("Choose Role of this Position:");
        spinnerPlayer32.setPrompt("Choose Role of this Position:");
        spinnerPlayer33.setPrompt("Choose Role of this Position:");
        spinnerPlayer34.setPrompt("Choose Role of this Position:");
        // Row 5
        spinnerPlayer40.setAdapter(spinnerAdapter);
        spinnerPlayer41.setAdapter(spinnerAdapter);
        spinnerPlayer42.setAdapter(spinnerAdapter);
        spinnerPlayer43.setAdapter(spinnerAdapter);
        spinnerPlayer44.setAdapter(spinnerAdapter);
        spinnerPlayer40.setPrompt("Choose Role of this Position:");
        spinnerPlayer41.setPrompt("Choose Role of this Position:");
        spinnerPlayer42.setPrompt("Choose Role of this Position:");
        spinnerPlayer43.setPrompt("Choose Role of this Position:");
        spinnerPlayer44.setPrompt("Choose Role of this Position:");
        // Row Goalie! :)
        positions = new ArrayList<>();
        positions.add("Goalie");
        spinnerAdapter = new ArrayAdapter<>(thisContext, R.layout.spinner_item, positions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayer50.setAdapter(spinnerAdapter);

    }
    private void showBlankPositions(){
        String pos;

        for( int i = 0; i < 5; ++i )
        {
            for( int j = 0; j < 5; ++j )
            {
                pos = Integer.toString(i) + Integer.toString(j);
                showPositionFromString(pos);
            }
        }
    }
    private void hideBlankPositions(){
        String pos;

        for( int i = 0; i < 5; ++i )
        {
            for( int j = 0; j < 5; ++j )
            {
                pos = Integer.toString(i) + Integer.toString(j);
                hidePositionFromString(pos);
            }
        }

        for( int i = 0; i < playerPositions.size(); ++i ){
            pos = playerPositions.get(i).substring(1);
            showPositionFromString(pos);
        }
    }
    private void showPositionFromString( String pos ){
        Spinner spinner;

        String spinnerName = "Formation_spinnerPlayer" + pos;
        int spinnerID = getStringIdentifier(spinnerName);

        spinner = findViewById(spinnerID);
        spinner.setVisibility(View.VISIBLE);
    }
    private void hidePositionFromString( String pos ){
        Spinner spinner;

        String spinnerName = "Formation_spinnerPlayer" + pos;
        int spinnerID = getStringIdentifier(spinnerName);

        spinner = findViewById(spinnerID);
        spinner.setVisibility(View.INVISIBLE);
    }
    public int getStringIdentifier(String pString){
        return thisContext.getResources().getIdentifier(pString, "id", thisContext.getPackageName());
    }
    private boolean fieldNotFull() {
        boolean flag = true;

        if( playerPositions.size() >= maxPlayers )
        {
            flag = false;
        }
        return flag;
    }
    private void dialogConfirmCancel(String title, final int type) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        if ( ( playerPositions.size() == maxPlayers && validTeamName() ) || type == CANCEL) {
            builder.setTitle("Are you sure you want to " + title + " :")
                    .setMessage("Formation Name: " + formationName)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            hideBars();
                            switch (type) {
                                case CANCEL:
                                    setResult(RESULT_CANCELED);
                                    finish();
                                    break;

                                case SAVE:
                                    String formDir = getFilesDir() + File.separator + "formations" + File.separator + teamDivision;
                                    String formFile = formDir + File.separator + formationName;
                                    String formationString = formatFormation();
                                    FileOutputStream outputStream;

                                    try{
                                        if( ! new File(formDir).exists() ) {
                                            File directory = new File(formDir);
                                            directory.mkdirs();
                                        }

                                        outputStream = new FileOutputStream(new File( formFile ) , false  );
                                        outputStream.write(formationString.getBytes());
                                        outputStream.close();

                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                    setResult(RESULT_OK);

                                    finish();

                                    break;

                                default:
                                    break;

                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            hideBars();
                        }
                    })
                    .show();
        }
        else
        {
            dialogSaveError();
        }
    }
    private void dialogSaveError() {
        AlertDialog.Builder builder;
        String error = "";

        if( fieldNotFull() )
        {
            error = "Number of Players is Insufficient\n\tHave: " + Integer.toString(playerPositions.size()) + "\n\tNeed: " + Integer.toString(maxPlayers);
        }
        else
        {
            error = "Formation Name cannot be Blank or Contain Special Characters";
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
    private void dialogQuickAddError(int number) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        builder.setTitle("Quick Add Error")
                .setMessage("Attempted to add: " + Integer.toString(number) + "\nAlready have: " + Integer.toString(playerPositions.size()))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hideBars();
                    }
                })
                .show();
    }
    private void hideBars(){

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
    private boolean validTeamName() {
        boolean flag = false;
        String regex = "^[a-zA-Z0-9-]+( +[a-zA-Z0-9-]+)*$";
        Pattern p = Pattern.compile(regex);

        if( formationName != null ) {
            Matcher m = p.matcher(formationName);
            if (m.find()) {
                flag = true;
            }
        }

        return flag;
    }
    private void hideKeyboard( View view ){
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @NonNull
    private String formatFormation(){
        StringBuilder temp = new StringBuilder();

        for( int i = 0 ; i < playerPositions.size(); ++i ){
            temp.append(playerPositions.get(i)).append("\n");
        }

        return temp.toString();
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
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

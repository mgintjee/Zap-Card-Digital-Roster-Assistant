package com.happybananastudio.zapcarddigitalrosterassistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by mgint on 1/2/2018.
 */

public class ActivityHelp extends AppCompatActivity {

    private TextView textViewHeader, textViewBody;
    private String helpHeader, helpBody, aboutHeader, aboutBody;
    private ImageButton imageButtonReturn, imageButtonHelp, imageButtonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_help);
        hideBars();
        initializeWidgets();
        getHelpType();
        setListeners();
    }

    private void initializeWidgets() {
        initializeTextViews();
        initializeImageButtons();
    }
    private void setListeners() {
        setImageViewListeners();
    }
    private void setImageViewListeners() {
        setImageButtonReturnListener();
        setImageButtonHelpListener();
        setImageButtonAboutListener();
    }
    private void setImageButtonReturnListener() {
        imageButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setImageButtonHelpListener() {
        imageButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewHeader.setText(helpHeader);
                textViewBody.setText(helpBody);
            }
        });
    }
    private void setImageButtonAboutListener() {
        imageButtonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewHeader.setText(aboutHeader);
                textViewBody.setText(aboutBody);
            }
        });
    }
    private void initializeTextViews() {
        initializeTextViewHeader();
        initializeTextViewBody();
    }
    private void initializeImageButtons() {
        initializeImageButtonReturn();
        initializeImageButtonHelp();
        initializeImageButtonAbout();
    }
    private void initializeTextViewHeader() {
        textViewHeader = findViewById(R.id.Help_textViewHeader);
    }
    private void initializeTextViewBody() {
        textViewBody = findViewById(R.id.Help_textViewBody);

    }
    private void initializeImageButtonReturn() {
        imageButtonReturn = findViewById(R.id.Help_imageButtonReturn);
    }
    private void initializeImageButtonHelp() {
        imageButtonHelp = findViewById(R.id.Help_imageButtonHelp);
    }
    private void initializeImageButtonAbout() {
        imageButtonAbout = findViewById(R.id.Help_imageButtonAbout);
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
    private void getHelpType() {
        String type;

        type = getIntent().getStringExtra("type");

        switch( type )
        {
            case "selectTeam":
                helpHeader = getString(R.string.helpSelectTeamHeader);
                helpBody = getString(R.string.helpSelectTeamBody);
                break;

            case "modifyRoster":
                helpHeader = getString(R.string.helpModifyRosterHeader);
                helpBody = getString(R.string.helpModifyRosterBody);
                break;

            case "modifyLineup":
                helpHeader = getString(R.string.helpModifyLineupHeader);
                helpBody = getString(R.string.helpModifyLineupBody);
                break;

            case "modifyFormation":
                helpHeader = getString(R.string.helpModifyFormationHeader);
                helpBody = getString(R.string.helpModifyFormationBody);
                break;

            default:
                break;
        }
        aboutHeader = getString(R.string.helpAboutHeader);
        aboutBody = getString(R.string.helpAboutBody);

        textViewHeader.setText(helpHeader);
        textViewBody.setText(helpBody);
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

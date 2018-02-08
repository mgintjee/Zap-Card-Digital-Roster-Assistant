package com.happybananastudio.zapcarddigitalrosterassistant;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ActivitySelectTeam extends AppCompatActivity {

    Context thisContext = this;
    private final String helpType = "selectTeam";

    private final int DELETE = 0;
    private final int EDIT = 1;
    private final int SELECT = 2;

    public static int activityCreateEditResult  = 1;

    private ImageButton iButtonDelete, iButtonCreate, iButtonHelp, iButtonEdit, iButtonSelect;


    private ListView lViewTeamList;
    private ListViewTeamList lViewTeamListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_select_team);

        if( ! new File(getFilesDir()+File.separator+"teams").exists()) {
            File directory = new File(getFilesDir() + File.separator + "teams");
            directory.mkdirs();
        }

        hideBars();

        initializeWidgets();
        updateTeamListView();
        setImageButtonListeners();
        setListViewSelectListener();
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
    private void initializeWidgets() {
        initializeImageButtons();
        initializeListViews();
    }
    private void initializeImageButtons() {
        iButtonDelete = findViewById(R.id.Team_imageButtonDelete);
        iButtonCreate = findViewById(R.id.Team_imageButtonCreate);
        iButtonHelp = findViewById(R.id.Team_imageButtonHelp);
        iButtonEdit = findViewById(R.id.Team_imageButtonEdit);
        iButtonSelect = findViewById(R.id.Team_imageButtonSelect);
    }
    private void initializeListViews() {
        lViewTeamList = findViewById(R.id.Team_listViewTeamList);
    }
    private void setImageButtonListeners() {
        setImageButtonDeleteListener();
        setImageButtonCreateListener();
        setImageButtonHelpListener();
        setImageButtonEditListener();
        setImageButtonSelectListener();
    }
    private void setImageButtonDeleteListener() {
        final String selectedAction = "Delete";

        iButtonDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if( lViewTeamListAdapter.getSelectedTeamPosition() > -1) {
                    dialogConfirmCancel(selectedAction, lViewTeamListAdapter.getSelectedTeam(), DELETE);
                }
                else
                {
                    dialogError(selectedAction);
                }
            }
        });
    }
    private void setImageButtonCreateListener() {

        iButtonCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisContext, ActivityModifyRoster.class);
                intent.putExtra("folderName", "");
                startActivityForResult(intent, activityCreateEditResult);
            }
        });
    }
    private void setImageButtonHelpListener() {

        iButtonHelp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(thisContext, ActivityHelp.class);
                intent.putExtra("type", helpType);
                startActivity(intent);
            }
        });
    }
    private void setImageButtonEditListener() {
        final String selectedAction = "Edit";

        iButtonEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if( lViewTeamListAdapter.getSelectedTeamPosition() > -1) {
                    dialogConfirmCancel(selectedAction, lViewTeamListAdapter.getSelectedTeam(), EDIT);
                }
                else
                {
                    dialogError(selectedAction);
                }
            }
        });
    }
    private void setImageButtonSelectListener() {
        final String selectedAction = "Select";

        iButtonSelect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if( lViewTeamListAdapter.getSelectedTeamPosition() > -1) {
                    dialogConfirmCancel(selectedAction, lViewTeamListAdapter.getSelectedTeam(), SELECT);
                }
                else
                {
                    dialogError(selectedAction);
                }
            }
        });
    }
    private void setListViewSelectListener() {
        lViewTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lViewTeamListAdapter.setSelectedIndex(i);
            }
        });

    }
    private void updateTeamListView() {
        File[] myFiles;
        ArrayList<String> myFileNames;
        String teamName, teamDivision, fileName;
        TeamInfo teamInfo;
        String[] stringTeamInfo;

        ArrayList<TeamInfo> teamList = new ArrayList<>();
        myFiles = GetFiles();
        myFileNames = getFileNames(myFiles);

        if( myFileNames!= null ) {
            for (int i = 0; i < myFileNames.size(); ++i) {
                fileName = myFileNames.get(i);
                stringTeamInfo = fileName.split("_");
                teamName = stringTeamInfo[0];
                teamDivision = stringTeamInfo[1];
                teamInfo = new TeamInfo();
                teamInfo.setName(teamName);
                teamInfo.setDivision(teamDivision);

                teamList.add(teamInfo);
            }
        }
        lViewTeamListAdapter = new ListViewTeamList(thisContext, 0, teamList);
        lViewTeamList.setAdapter(lViewTeamListAdapter);
    }
    private void dialogConfirmCancel(String title, TeamInfo team, final int type) {
        AlertDialog.Builder builder;
        String name = team.getName();
        String division = team.getDivision();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        builder.setTitle("Are you sure you want to " + title.toUpperCase() + ":")
                .setMessage("Name: " + name + "\nDivision: " + division )
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        String teamName, fileName;
                        TeamInfo deleteTeam;
                        File file;
                        hideBars();

                        switch (type)
                        {
                            case DELETE:
                                toastThis("Deleted " + formatTeamInfo(lViewTeamListAdapter.getSelectedTeam()));

                                deleteTeam = lViewTeamListAdapter.getSelectedTeam();
                                fileName = deleteTeam.getName() + "_" + deleteTeam.getDivision();

                                file = new File(getFilesDir() + File.separator +"teams" + File.separator + fileName);
                                Log.d( "DEBUG", getFilesDir() + File.separator +"teams" + File.separator + fileName );

                                if( file.exists() )
                                {
                                    deleteDir( file );
                                }

                                lViewTeamListAdapter.remove(deleteTeam);
                                updateTeamListView();

                                break;

                            case EDIT:
                                toastThis("Editing " + formatTeamInfo(lViewTeamListAdapter.getSelectedTeam()));

                                intent = new Intent(thisContext, ActivityModifyRoster.class);
                                teamName = formatTeamInfo(lViewTeamListAdapter.getSelectedTeam());
                                intent.putExtra("teamName",teamName);
                                startActivityForResult(intent, activityCreateEditResult);
                                break;

                            case SELECT:
                                toastThis("Selecting " + formatTeamInfo(lViewTeamListAdapter.getSelectedTeam()));

                                intent = new Intent(thisContext, ActivityModifyLineup.class);
                                teamName = formatTeamInfo(lViewTeamListAdapter.getSelectedTeam());
                                intent.putExtra("teamName",teamName);
                                startActivityForResult(intent, activityCreateEditResult);
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
    private void dialogError(String action) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(thisContext, R.style.AlertDialogStyle);
        } else {
            builder = new AlertDialog.Builder(thisContext);
        }
        builder.setTitle(action + " Error")
                .setMessage("No team selected to " + action.toLowerCase())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        hideBars();
                    }
                })
                .show();
    }
    private String formatTeamInfo(TeamInfo tInfo) {
        String name = tInfo.getName();
        String division = tInfo.getDivision();

        return name + "_" + division;
    }
    public File[] GetFiles() {
        File f = new File(getFilesDir()+File.separator + "teams");
        f.mkdirs();
        return f.listFiles();
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
    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            updateTeamListView();
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
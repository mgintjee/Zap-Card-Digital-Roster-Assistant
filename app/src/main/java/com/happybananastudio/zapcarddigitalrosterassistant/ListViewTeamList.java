package com.happybananastudio.zapcarddigitalrosterassistant;

/**
 * Created by mgint on 1/2/2018.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewTeamList extends ArrayAdapter<TeamInfo> {

    private Context thisContext;

    private ArrayList<TeamInfo> teamList;
    private int selectedTeamPosition = -1;

    ListViewTeamList(Context context, int resource, ArrayList<TeamInfo> newTeamList) {

        super(context, resource, newTeamList);

        thisContext = context;
        teamList = newTeamList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return teamList.size();
    }

    @Override
    public TeamInfo getItem(int position) {
        return teamList.get(position);
    }

    TeamInfo getSelectedTeam() {
        return teamList.get(selectedTeamPosition);
    }
    int getSelectedTeamPosition() {
        return selectedTeamPosition;
    }

    void setSelectedIndex(int ind) {
        selectedTeamPosition = ind;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView tVDivision, tVName;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        String teamName;
        String teamDivision;
        View result;
        TeamInfo localTeamInfo = teamList.get(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.team_info_row, parent, false);

            viewHolder.tVDivision = convertView.findViewById(R.id.TeamInfoRow_tVDivision);
            viewHolder.tVName = convertView.findViewById(R.id.TeamInfoRow_tVName);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        teamName = localTeamInfo.getName();
        teamDivision = localTeamInfo.getDivision();

        viewHolder.tVName.setText(teamName);
        viewHolder.tVDivision.setText(teamDivision);

        if (position != selectedTeamPosition) {
            if (position % 2 == 0) {
                viewHolder.tVName.setBackgroundResource(R.drawable.my_border_blue);
                viewHolder.tVDivision.setBackgroundResource(R.drawable.my_border_blue);

            } else {
                viewHolder.tVName.setBackgroundResource(R.drawable.my_border_white);
                viewHolder.tVDivision.setBackgroundResource(R.drawable.my_border_white);
            }
        } else {
            viewHolder.tVName.setBackgroundResource(R.drawable.my_border_yellow);
            viewHolder.tVDivision.setBackgroundResource(R.drawable.my_border_yellow);
        }

        return result;
    }
}

package com.happybananastudio.zapcarddigitalrosterassistant;

/**
 * Created by mgint on 1/4/2018.
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewPlayerList extends ArrayAdapter<PlayerInfo> {

    private Context thisContext;

    private ArrayList<PlayerInfo> playerList;
    private int selectedPlayerPosition = -1;

    ListViewPlayerList(Context context, int resource, ArrayList<PlayerInfo> newPlayerList) {

        super(context, resource, newPlayerList);

        thisContext = context;
        playerList = newPlayerList;
    }
    // Setters
    void setSelectedIndex(int ind) {
        selectedPlayerPosition = ind;
        notifyDataSetChanged();
    }

    // Getters
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getCount() {
        return playerList.size();
    }
    @Override
    public PlayerInfo getItem(int position) { return playerList.get(position); }

    PlayerInfo getSelectedPlayer() {
        return playerList.get(selectedPlayerPosition);
    }
    int getSelectedPlayerPosition() {
        return selectedPlayerPosition;
    }
    ArrayList<PlayerInfo> getPlayerList(){
        return playerList;
    }

    private class ViewHolder {
        private TextView tVName, tVNumber, tVGoalie, tVDefender, tVMidfielder, tVForward;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        String playerName;
        String playerNumber;
        String playerPosition;

        View result;
        PlayerInfo localPlayerInfo = playerList.get(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.player_info_row, parent, false);

            viewHolder.tVNumber = convertView.findViewById(R.id.PlayerInfoRow_tVNumber);
            viewHolder.tVName = convertView.findViewById(R.id.PlayerInfoRow_textViewPlayerInfo);
            viewHolder.tVGoalie = convertView.findViewById(R.id.PlayerInfoRow_tVGoalie);
            viewHolder.tVDefender = convertView.findViewById(R.id.PlayerInfoRow_tVDefender);
            viewHolder.tVMidfielder = convertView.findViewById(R.id.PlayerInfoRow_tVMidfielder);
            viewHolder.tVForward = convertView.findViewById(R.id.PlayerInfoRow_tVForward);

            result = convertView;

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        playerName = localPlayerInfo.getName();
        playerNumber = "#" + localPlayerInfo.getNumber();
        playerPosition = localPlayerInfo.getPosition();

        viewHolder.tVNumber.setText(playerNumber);
        viewHolder.tVName.setText(playerName);
        viewHolder.tVGoalie.setText(Character.toString(playerPosition.charAt(0)));
        viewHolder.tVDefender.setText(Character.toString(playerPosition.charAt(1)));
        viewHolder.tVMidfielder.setText(Character.toString(playerPosition.charAt(2)));
        viewHolder.tVForward.setText(Character.toString(playerPosition.charAt(3)));

        if (position != selectedPlayerPosition) {
            if (position % 2 == 0) {
                viewHolder.tVNumber.setBackgroundResource(R.drawable.my_border_blue);
                viewHolder.tVName.setBackgroundResource(R.drawable.my_border_blue);
                viewHolder.tVGoalie.setBackgroundResource(R.drawable.my_border_blue);
                viewHolder.tVDefender.setBackgroundResource(R.drawable.my_border_blue);
                viewHolder.tVMidfielder.setBackgroundResource(R.drawable.my_border_blue);
                viewHolder.tVForward.setBackgroundResource(R.drawable.my_border_blue);

            } else {
                viewHolder.tVNumber.setBackgroundResource(R.drawable.my_border_white);
                viewHolder.tVName.setBackgroundResource(R.drawable.my_border_white);
                viewHolder.tVGoalie.setBackgroundResource(R.drawable.my_border_white);
                viewHolder.tVDefender.setBackgroundResource(R.drawable.my_border_white);
                viewHolder.tVMidfielder.setBackgroundResource(R.drawable.my_border_white);
                viewHolder.tVForward.setBackgroundResource(R.drawable.my_border_white);
            }
        } else {
            viewHolder.tVNumber.setBackgroundResource(R.drawable.my_border_yellow);
            viewHolder.tVName.setBackgroundResource(R.drawable.my_border_yellow);
            viewHolder.tVGoalie.setBackgroundResource(R.drawable.my_border_yellow);
            viewHolder.tVDefender.setBackgroundResource(R.drawable.my_border_yellow);
            viewHolder.tVMidfielder.setBackgroundResource(R.drawable.my_border_yellow);
            viewHolder.tVForward.setBackgroundResource(R.drawable.my_border_yellow);
        }

        return result;
    }

    private String formatPlayerPosition( String position ) {
        StringBuilder format = new StringBuilder();
        int pos = 0;
        String[] superFormat;
        ArrayList<String> positions = new ArrayList<>();

        positions.add("Goalie");
        positions.add("Defender");
        positions.add("Midfielder");
        positions.add("Forward");

        for( int i = 0; i < position.length(); ++i )
        {
            if( position.charAt(i) != '0' )
            {
                format.append(" ").append(positions.get(i)).append(":").append(position.charAt(i));
            }
        }
        superFormat = format.toString().split(" ");
        format = new StringBuilder();

        for( int i = 1; i < superFormat.length - 1; ++i )
        {
            format.append(superFormat[i]).append(", ");
            pos = pos + 1;
        }

        format.append(superFormat[pos + 1]);

        return format.toString();
    }
}
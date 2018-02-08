package com.happybananastudio.zapcarddigitalrosterassistant;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by mgint on 1/2/2018.
 */

public class PlayerInfo implements Comparable<PlayerInfo>{

    private String playerName;
    private String playerNumber;
    private String playerPosition;

    PlayerInfo() {
        super();
    }

    // Setters
    public void setName(String name) {this.playerName = name;}
    void setNumber(String number) {this.playerNumber = number;}
    void setPosition(String position) {this.playerPosition = position;}

    // Getters
    String getPosition()
    {
        return this.playerPosition;
    }
    String getNumber()
    {
        return this.playerNumber;
    }
    public String getName()
    {
        return this.playerName;
    }
    public String toString() {return this.playerName + ";" + this.playerNumber + ";" + this.playerPosition;}

    @Override
    public int compareTo(@NonNull PlayerInfo o) {
        int other, mine;
        other = Integer.valueOf( o.getNumber() );
        mine = Integer.valueOf( this.getNumber() );
        return mine - other;
    }
}

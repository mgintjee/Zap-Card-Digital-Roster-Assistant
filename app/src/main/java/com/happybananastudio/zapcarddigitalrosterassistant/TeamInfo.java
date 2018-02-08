package com.happybananastudio.zapcarddigitalrosterassistant;

/**
 * Created by mgint on 1/2/2018.
 */

public class TeamInfo {
    private String teamName;
    private String teamDivision;

    TeamInfo() {
        super();
    }

    // Setters
    public void setName( String name)
    {
        this.teamName = name;
    }
    void setDivision(String division)
    {
        this.teamDivision = division;
    }

    // Getters
    String getDivision() {return this.teamDivision;}
    public String getName()
    {
        return this.teamName;
    }
}
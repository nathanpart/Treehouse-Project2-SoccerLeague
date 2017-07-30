package com.teamtreehouse.view;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ReportView {
    protected String mHeading;
    protected Console mConsole;
    protected Teams mTeams;
    protected Players mAvailablePlayers;

    public ReportView(Console console, String heading, Teams teams, Players players){
        mConsole = console;
        mHeading = heading;
        mTeams = teams;
        mAvailablePlayers = players;
    }

    public void displayHeading() {
        mConsole.clearAndPrint(mHeading);
    }


    public void render() {
        displayHeading();
        displayBody();
        displayFooting();
    }

    public void displayBody() {
        System.out.println("Override Me");
    }

    public void displayFooting() {
        mConsole.waitForReturn();
    }

}

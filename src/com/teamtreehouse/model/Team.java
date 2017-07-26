package com.teamtreehouse.model;

import java.util.Set;
import java.util.TreeSet;

public class Team {

    //Default string reports team with stats
    @Override
    public String toString() {
        return mTeamName + '(' +
                "Coach: " + mCoach +
                ", Average Height: " + getAverageHeight() + "\"" +
                ", Experience: " + getExperienceLevel() + "%)";
    }

    private String mTeamName;
    private String mCoach;
    private Set<Player> mPlayers;

    public Team(String teamName, String coach) {
        mTeamName = teamName;
        mCoach = coach;
        mPlayers = new TreeSet<Player>();
    }

    public void addPlayer(Player player) {
        mPlayers.add(player);
    }

    public Set<Player> getPlayers() {
        return mPlayers;
    }

    public String getName() {
        return mTeamName;
    }

    public String getCoash() {
        return mCoach;
    }

    //Returns team's average player height
    public int getAverageHeight() {
        int sumHeights = 0;

        //If no players in team report 0 and avoid division by 0
        if (mPlayers.size() == 0) {
            return 0;
        }

        for (Player player : mPlayers) {
            sumHeights += player.getHeightInInches();
        }

        return sumHeights / mPlayers.size();
    }


    //Return percentage of team that has previous experence
    public double getExperienceLevel() {
        int experienceCount = 0;

        //If no players in team report no experience.
        if (mPlayers.size() == 0) {
            return 0;
        }

        for (Player player : mPlayers) {
            if (player.isPreviousExperience()) {
                experienceCount += 1;
            }
        }
        return ((double) experienceCount / (double) mPlayers.size()) * 100;
    }

    public void removePlayer(Player player) {
        if (mPlayers.contains(player)) {
            mPlayers.remove(player);
        }
    }
}

package com.teamtreehouse.model;

import java.util.OptionalDouble;

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
    private Players mPlayers;

    public Team(String teamName, String coach) {
        mTeamName = teamName;
        mCoach = coach;
        mPlayers = new Players();
    }

    public void addPlayer(Player player) {
        mPlayers.addPlayer(player);
    }

    public Players getTeamPlayers() {
        return mPlayers;
    }

    public String getName() {
        return mTeamName;
    }

    public String getCoach() {
        return mCoach;
    }

    //Returns team's average player height
    public int getAverageHeight() {
        OptionalDouble average = mPlayers.getPlayersList().stream()
                .mapToInt(Player::getHeightInInches)
                .average();
        return average.isPresent() ? (int) Math.round(average.getAsDouble()) : 0;
    }

    //Return percentage of team that has previous experence
    public double getExperienceLevel() {
        //If no players in team, report no experience.
        if (mPlayers.getPlayerCount() == 0) {
            return 0;
        }

        return ((double) mPlayers.getPlayersWithExperience().size() / (double) mPlayers.getPlayerCount()) * 100;
    }

    public void removePlayer(Player player) {
        if (mPlayers.hasPlayer(player)) {
            mPlayers.removePlayer(player);
        }
    }


    public String getTeamNameWithStats() {
        return String.format("%s coached by %s - Average Height: %d\", Average Experience Level: %2.1f%%",
                getName(),
                getCoach(),
                getAverageHeight(),
                getExperienceLevel()
        );
    }

    public String getTeamNameWithCoach() {
        return String.format("%s coached by %s", getName(), getCoach());
    }

    public int getPlayerCount() {
        return mPlayers.getPlayerCount();
    }
}

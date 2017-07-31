package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Teams {
    private List<Team> mTeams;

    public enum SortOptions {NAME, HEIGHT, EXPERIENCE}

    public Teams() {
        mTeams = new ArrayList<>();
    }

    public List<Team> getTeamsListSorted(SortOptions sortType, boolean isAscending) {
        List<Team> teams;

        switch (sortType) {
            case NAME:
                teams =  mTeams.stream()
                        .sorted(Comparator.comparing(Team::getName))
                        .collect(Collectors.toList());
                break;
            case HEIGHT:
                teams =  mTeams.stream()
                        .sorted(Comparator.comparing(Team::getAverageHeight))
                        .collect(Collectors.toList());
                break;
            case EXPERIENCE:
                teams =  mTeams.stream()
                        .sorted(Comparator.comparing(Team::getExperienceLevel))
                        .collect(Collectors.toList());
                break;
            default:
                //Make compiler happy that it knows for sure of an initialized teams
                teams = new ArrayList<>(mTeams);
        }
        if (!isAscending) {
            Collections.reverse(teams);
        }
        return teams;
    }

    public void createTeam(String name, String coach) {
        Team team = new Team(name, coach);
        mTeams.add(team);

    }

    public int getNumberOfTeams() {
        return mTeams.size();
    }

    public boolean isEmpty() {
        return mTeams.isEmpty();
    }

}

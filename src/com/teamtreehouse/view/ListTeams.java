package com.teamtreehouse.view;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListTeams extends ReportView {
    private ListMenu<Team> mTeamMenu;
    private List<Team> mTeamList;


    public ListTeams(Console console, Teams teams, Players players) {
        super(console, "Team Reports By Height", teams, players);

        mTeamList = null;
        List<String> mGlobalOptions = new ArrayList<>();
        mGlobalOptions.add("Exit to main menu");

        mTeamMenu = new ListMenu<>(console,
                mTeamList,
                "Select a team to view",
                mGlobalOptions,
                Team::getTeamNameWithCoach,
                this);
    }

    @Override
    public void displayBody() {
        int selection;
        mTeamList = mTeams.getTeamsListSorted(Teams.SortOptions.NAME, true);

        selection = mTeamMenu.getSelection(mTeamList);
        if (selection < 0) {
            return;
        }

        Team team = mTeamList.get(selection);

        StringBuilder teamReport = new StringBuilder(String.format("Report for team %s coached by %s%n",
                team.getName(),
                team.getCoach()));
        teamReport.append(String.format("%nList of player by height:%n"));
        team.getTeamPlayers().getPlayersList().stream()
                .sorted(Comparator.comparingInt(Player::getHeightInInches))
                .map(Player::getFullNameStats)
                .forEach(s ->teamReport.append(String.format("  %s%n", s)));
        teamReport.append(String.format("%nThe average experience level for this team is: %2.1f%%",
                team.getExperienceLevel()));

        mConsole.printParagraph(String.valueOf(teamReport));
    }
}

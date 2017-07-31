package com.teamtreehouse.view;

import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Roster extends ReportView {
    private ListMenu<Team> mRosterMenu;
    private List<Team> mTeamList;

    public Roster(Console console, Teams teams, Players player) {
        super(console, "Team Rosters", teams, player);
        mTeamList = null;
        List<String> allOption = new ArrayList<>(Collections.singletonList("All Teams"));
        mRosterMenu = new ListMenu<>(console,
                mTeamList,
                "Roster for which team",
                allOption,
                Team::getTeamNameWithStats,
                this);
    }

    @Override
    public void displayBody() {
        //Get a current list of teams sorted by Team Name for the menu
        mTeamList = mTeams.getTeamsListSorted(Teams.SortOptions.NAME, true);
        int selection = mRosterMenu.getSelection(mTeamList);

        displayHeading();
        if (selection < 0) {
            mTeamList.forEach(this::displayTeamRoster);
        } else {
            displayTeamRoster(mTeamList.get(selection));
        }
    }

    private void displayTeamRoster(Team team) {
        StringBuilder rosterParagraph = new StringBuilder(String.format("%s%n", team.getTeamNameWithStats()));
        rosterParagraph.append("   Players with experience:%n");
        team.getTeamPlayers()
                .getPlayersWithExperience()
                .forEach(player -> rosterParagraph.append(String.format("    %s%n", player.getFullNameHeight())));
        rosterParagraph.append("  Players without experience:%n");
        team.getTeamPlayers()
                .getPlayersWithoutExperience()
                .forEach(player -> rosterParagraph.append(String.format("    %s%n", player.getFullNameHeight())));
        rosterParagraph.append(String.format("Number of player: %d%n", team.getTeamPlayers().getPlayersList().size()));
        mConsole.printParagraph(String.valueOf(rosterParagraph));
    }


}

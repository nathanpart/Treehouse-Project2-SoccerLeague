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
        mTeamList = teams.getTeamsListSorted(Teams.SortOptions.NAME, true);
        List<String> allOption = new ArrayList<String>(Collections.singletonList("All Teams"));
        mRosterMenu = new ListMenu<>(console,
                mTeamList,
                "Roster for which team",
                allOption,
                Team::getTeamNameWithStats,
                this);
    }

    @Override
    public void displayBody() {
        int selection = mRosterMenu.getSelection();

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
        team.getPlayers()
                .getPlayersWithExperience()
                .forEach(player -> rosterParagraph.append(String.format("    %s%n", player.getFullNameHeight())));
        rosterParagraph.append("  Players without experience:%n");
        team.getPlayers()
                .getPlayersWithoutExperience()
                .forEach(player -> rosterParagraph.append(String.format("    %s%n", player.getFullNameHeight())));
        rosterParagraph.append(String.format("Number of player: %d%n", team.getPlayers().getPlayers().size()));
        mConsole.printParagraph(String.valueOf(rosterParagraph));
    }


}

package com.teamtreehouse.view;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.util.List;

public class BalanceReport extends ReportView{
    public BalanceReport(Console console, Teams teams, Players players) {
        super(console, "League Balance Report", teams, players);
    }

    @Override
    public void displayBody() {
//      String col1          = "         1         2         3         4         5         6         7        8";
//      String col2          = "1234567890123456789012345678901234567890123456789012345678901234567890234567890";
        String tableHeading1 = "Team            Total       Non Avg Avg    per heights";
        String tableHeading2 = "Name            Players Exp Exp Exp Height 30 33 36 39 42 45 48 51 54 57 60";

        StringBuilder text = new StringBuilder();
        text.append(String.format("Player counts in the per heights table are grouped in 3\" increments%n"));
        text.append(String.format("starting at 30\" (2.5 feet).%n"));
        text.append(String.format("%n"));
        text.append(String.format("%s%n%s%n",
                tableHeading1,
                tableHeading2));

        List<Team> teams = mTeams.getTeamsListSorted(Teams.SortOptions.NAME, true);
        for (Team team: teams) {
            int heightCounts[] = {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
            int lowBound[]     = {30, 33, 36, 39, 42, 45, 48, 51, 54, 57, 60};
            int highBound[]    = {33, 36, 39, 42, 45, 48, 51, 54, 57, 60, 100};
            int expCount = 0;
            int inExpCount = 0;
            List<Player> players = team.getTeamPlayers().getPlayersList();

            for (Player player: players) {
                int height = player.getHeightInInches();
                for (int i=0; i<heightCounts.length; i++) {
                    if (height >= lowBound[i] && height < highBound[i]) {
                        heightCounts[i]++;
                    }
                }
                if (player.isPreviousExperience()) {
                    expCount++;
                } else {
                    inExpCount++;
                }
            }
            text.append(String.format("%-15s %-2d      %-2d  %-2d  %-2d%% %-2d\"    ",
                    team.getName(),
                    players.size(),
                    expCount,
                    inExpCount,
                    new Double(team.getExperienceLevel()).intValue(),
                    team.getAverageHeight()));

            for (int height : heightCounts) {
                text.append(String.format("%-2d ", height));
            }
            text.append(String.format("%n"));
        }
        mConsole.printParagraph(String.valueOf(text));
    }
}

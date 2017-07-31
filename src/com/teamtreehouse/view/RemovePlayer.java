package com.teamtreehouse.view;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.util.ArrayList;
import java.util.List;

public class RemovePlayer extends ReportView{
    private ListMenu<Player> mPlayerMenu;
    private ListMenu<Team> mTeamMenu;
    private List<Player> mPlayerList;
    private List<Team> mTeamList;

    public RemovePlayer(Console console, Teams teams, Players players) {
        super(console, "Remove Player From A Team", teams, players);

        //Creat menu lists

        //Create global menu options
        List<String> playerGlobalOptions = new ArrayList<>();
        List<String> teamGlobalOptions = new ArrayList<>();

        playerGlobalOptions.add("Exit back to main menu");
        playerGlobalOptions.add("Select a different team");

        teamGlobalOptions.add("Exit back to main menu");

        mPlayerMenu = new ListMenu<>(console,
                mPlayerList,
                "",                            //We adjust heading dynamically later
                playerGlobalOptions,
                Player::getFullNameStats,
                this);

        mTeamMenu = new ListMenu<>(console,
                mTeamList,
                "Select a team to remove player from",
                teamGlobalOptions,
                Team::getTeamNameWithStats,
                this);
    }


    @Override
    public void displayBody() {
        boolean isSelecting = true;
        boolean isTeamMenu = true;
        int selection;
        Player player = null;
        Team team = null;

        do {
            if (isTeamMenu) {
                mTeamList = mTeams.getTeamsListSorted(Teams.SortOptions.NAME, true);
                selection = mTeamMenu.getSelection(mTeamList);
            } else {
                mPlayerList = mAvailablePlayers.getPlayersList();
                selection = mPlayerMenu.getSelection(mPlayerList);
            }

            //Handle the return to main menu global option
            if (selection == -1) {
                return;
            }

            //Select another player global option
            if (selection == -2) {
                isTeamMenu = true;
                continue;
            }

            //We now have an actual selection from one of the lists
            if (isTeamMenu) {
                team = mTeamList.get(selection);
                mPlayerMenu.setHeading(String.format("Select a player to remove from team %s", team.getName()));
                isTeamMenu = false;
            } else {
                player = mPlayerList.get(selection);
                isSelecting = false;
            }

        } while(isSelecting);
        team.removePlayer(player);
        mAvailablePlayers.addPlayer(player);

        mConsole.printParagraph(String.format("Removed player %s from team %s coached by %s.%n",
                player.getFullName(),
                team.getName(),
                team.getCoach()));

    }

}


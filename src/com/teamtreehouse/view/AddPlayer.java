package com.teamtreehouse.view;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddPlayer extends ReportView{
    private ListMenu<Player> mPlayerMenu;
    private ListMenu<Team> mTeamMenu;
    private List<Player> mPlayerList;
    private List<Team> mTeamList;

    public AddPlayer(Console console, Teams teams, Players players) {
        super(console, "Add A Player", teams, players);

        //We pass the menus an updated lists when we get a selection
        mPlayerList = null;
        mTeamList = null;

        //Create global menu options
        List<String> playerGlobalOptions = new ArrayList<>();
        List<String> teamGlobalOptions = new ArrayList<>();

        playerGlobalOptions.add("Exit back to main menu");

        teamGlobalOptions.add("Exit back to main menu");
        teamGlobalOptions.add("Select a different player");

        mPlayerMenu = new ListMenu<>(console,
                mPlayerList,
                "Select an available player",
                playerGlobalOptions,
                Player::getFullNameStats,
                this);

        mTeamMenu = new ListMenu<>(console,
                mTeamList,
                "",            //We adjust heading dynamically later
                teamGlobalOptions,
                Team::getTeamNameWithStats,
                this);
    }

    @Override
    public void displayBody() {
        boolean isSelecting = true;
        boolean isPlayerMenu = true;
        int selection;
        Player player = null;
        Team team = null;

        do {
            if (isPlayerMenu) {
                mPlayerList = mAvailablePlayers.getPlayersList();
                selection = mPlayerMenu.getSelection(mPlayerList);
            } else {
                // Get a list of teams sorted, but also apply the max 11 player restraint by removing
                //  any teams with 11 players.  Hmm a job for filter
                mTeamList = mTeams.getTeamsListSorted(Teams.SortOptions.NAME, true).stream()
                        .filter(t -> t.getPlayerCount() < 11)
                        .collect(Collectors.toList());

                selection = mTeamMenu.getSelection(mTeamList);
            }

            //Handle the return to main menu global option
            if (selection == -1) {
                return;
            }

            //Select another player global option
            if (selection == -2) {
                isPlayerMenu = true;
                continue;
            }

            //We now have an actual selection from one of the lists
            if (isPlayerMenu) {
                player = mPlayerList.get(selection);
                mTeamMenu.setHeading(String.format("Select a team for player %s", player.getFullNameStats()));
                isPlayerMenu = false;
            } else {
                team = mTeamList.get(selection);
                isSelecting = false;
            }

        } while(isSelecting);
        team.addPlayer(player);
        mAvailablePlayers.removePlayer(player);

        mConsole.printParagraph(String.format("Add player %s to team %s coached by %s.%n",
                player.getFullName(),
                team.getName(),
                team.getCoach()));

    }


}

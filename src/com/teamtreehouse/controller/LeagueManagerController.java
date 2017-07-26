package com.teamtreehouse.controller;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.view.Console;
import com.teamtreehouse.view.ListMenu;
import com.teamtreehouse.view.Menu;
import com.teamtreehouse.view.MenuItem;

import java.util.*;

public class LeagueManagerController {
    private Set<Player> mPlayers;
    private List<Team> mTeams;

    // Console stuff
    private Console mConsole;

    // Main Menu
    enum MenuOptions {CREATE_TEAM, QUIT, REMOVE_PLAYER, ADD_PLAYER, LIST_TEAMS}
    private Menu<MenuOptions> mMainMenu;

    enum States {SELECT_PLAYER, SELECT_TEAM, ABORT, DONE}
    enum PlayerOps {ADD_PLAYER, REMOVE_PLAYER}

    private void createMainMenu() {
        List<MenuItem<MenuOptions>> itemList = new ArrayList<>();
        itemList.add(new MenuItem<>(MenuOptions.CREATE_TEAM, "Create a Team", "create", "create"));
        itemList.add(new MenuItem<>(MenuOptions.ADD_PLAYER, "Add player to a team", "add", "add"));
        itemList.add(new MenuItem<>(MenuOptions.REMOVE_PLAYER, "Remove a player from a team", "remove", "remove"));
        itemList.add(new MenuItem<>(MenuOptions.LIST_TEAMS, "List teams", "list", "list"));
        itemList.add(new MenuItem<>(MenuOptions.QUIT, "Exit Program", "quit", "quit"));


        mMainMenu = new Menu<>(mConsole, "League Manager Menu", itemList);
    }

    public LeagueManagerController(Player[] players) {
        mPlayers = new TreeSet<>(Arrays.asList(players));
        mTeams = new ArrayList<>();
        mConsole = new Console();
        createMainMenu();
    }

    public void run() {
        boolean runFlag = true;
        while (runFlag) {
            MenuOptions option = mMainMenu.getSelction();
            switch (option) {
                case QUIT:
                    runFlag = false;
                    break;
                case CREATE_TEAM:
                    doCreateTeam();
                    break;
                case ADD_PLAYER:
                    doAddPlayer();
                    break;
                case REMOVE_PLAYER:
                    doRemovePlayer();
                    break;
                case LIST_TEAMS:
                    doListTeams();
                default:
                    break;
            }
        }
    }

    private void doListTeams() {

        mConsole.clearAndPrint("List of Teams sorted by Average Height");
        Collections.sort(mTeams, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                return o2.getAverageHeight() - o1.getAverageHeight();
            }
        });
        for (Team team : mTeams) {
            StringBuilder teamParagraph = new StringBuilder(team.toString());
            teamParagraph.append(String.format("%n"));
            for (Player player : team.getPlayers()) {
                teamParagraph.append(String.format("  %s%n", player.toString()));
            }
            mConsole.printParagraph(String.valueOf(teamParagraph));
        }
        mConsole.waitForReturn();
    }

    private void doRemovePlayer() {
        addRemovePlayer(PlayerOps.REMOVE_PLAYER);
    }


    private void doAddPlayer() {
        addRemovePlayer(PlayerOps.ADD_PLAYER);
    }

    private void addRemovePlayer(PlayerOps operation) {
        States currentState;

        String opTitle;

        String playerBackOption = "";
        String playerHeading = "";

        String teamHeading = "";
        String teamBackOption = "";


        if (operation == PlayerOps.ADD_PLAYER) {
            playerBackOption = "Return to Main Menu";
            playerHeading = "Select An Avialable Player";
            currentState = States.SELECT_PLAYER;
            opTitle = "Add";
        } else {
            teamBackOption = "Return to Main Menu";
            teamHeading = "Select A Team To Remove Player From";
            currentState = States.SELECT_TEAM;
            opTitle = "Remove";
        }

        Player player = null;
        Team team = null;

        do {
            mConsole.clearAndPrint(String.format("%s Player To A Team", opTitle));
            mConsole.verticleSpace(1);

            switch (currentState) {
                case SELECT_PLAYER:
                    player = selectAPlayer(mPlayers, playerHeading, playerBackOption);
                    if (player == null) {
                        if (operation == PlayerOps.ADD_PLAYER) {
                            currentState = States.ABORT;
                        } else {
                            currentState = States.SELECT_TEAM;
                        }
                    } else {
                        if (operation == PlayerOps.ADD_PLAYER) {
                            currentState = States.SELECT_TEAM;
                            teamHeading = String.format("Select a team for %s", player.toString());
                            teamBackOption = "Select a different player";
                        }  else {
                            currentState = States.DONE;
                        }
                    }
                    break;
                case SELECT_TEAM:
                    team = selectATeam(mTeams, teamHeading, teamBackOption);
                    if (team == null) {
                        if (operation == PlayerOps.REMOVE_PLAYER) {
                            currentState = States.ABORT;
                        } else {
                            currentState = States.SELECT_PLAYER;
                        }
                    } else {
                        if (operation == PlayerOps.REMOVE_PLAYER) {
                            currentState = States.SELECT_PLAYER;
                            teamHeading = String.format("Select a player for %s", team.toString());
                            teamBackOption = "Select a different team";
                        } else {
                            currentState = States.DONE;
                        }
                    }
                    break;
            }
        } while (currentState != States.DONE && currentState != States.ABORT);
        if (currentState == States.ABORT) {
            return;
        }
        if (operation == PlayerOps.ADD_PLAYER) {
            team.addPlayer(player);
            mPlayers.remove(player);
        } else {
            mPlayers.add(player);
            if (team != null) {
                team.removePlayer(player);
            }
        }
    }

    private List<String> getTeamList() {
        List<String> teamList = new ArrayList<>();
        for (Team team : mTeams) {
            teamList.add(team.toString());
        }
        return teamList;
    }

    private Player selectAPlayer(Set<Player> playerSet, String heading, String backOption) {
        ListMenu<Player> playerMenu = new ListMenu<>(mConsole, playerSet, heading, backOption);
        Player [] players = (Player[]) playerSet.toArray();

        int selection = playerMenu.getSelection();
        if (selection == 0) {
            return null;
        } else {
            return players[selection - 1];
        }
    }

    private Team selectATeam(List<Team> teams, String heading, String backOption) {
        ListMenu<Team> teamMenu = new ListMenu<>(mConsole, teams, heading, backOption);

        int selection = teamMenu.getSelection();
        if (selection == 0) {
            return null;
        } else {
            return teams.get(selection - 1);
        }
    }

    private List<String> getAvailblePlayers(Set<Player> players) {
        List<String> playerList = new ArrayList<>();
        for (Player player : players) {
            playerList.add(player.toString());
        }
        return playerList;
    }

    private void doCreateTeam() {
        mConsole.clearAndPrint("Create New Team");
        mConsole.verticleSpace(1);
        String name = mConsole.getPromptLine("Enter team name");
        String coach = mConsole.getPromptLine(String.format("Enter coach's name for team %s", name));
        mTeams.add(new Team(name, coach));
        mConsole.printParagraph(String.format("Team %s with coach %s was created.", name, coach));
        mConsole.waitForReturn();
    }
}

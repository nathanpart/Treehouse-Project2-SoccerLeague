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
    enum MenuOptions {CREATE_TEAM, QUIT, ADD_PLAYER}
    private Menu<MenuOptions> mMainMenu;

    enum States {SELECT_PLAYER, SELECT_TEAM, ABORT, DONE}

    private void createMainMenu() {
        List<MenuItem<MenuOptions>> itemList = new ArrayList<>();
        itemList.add(new MenuItem<>(MenuOptions.CREATE_TEAM, "Create a Team", "create", "create"));
        itemList.add(new MenuItem<>(MenuOptions.ADD_PLAYER, "Add player to a team", "add", "add"));
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
                default:
                    break;
            }
        }
    }

    private void doAddPlayer() {
        States currentState = States.SELECT_PLAYER;
        ListMenu playerMenu = new ListMenu(mConsole, getAvailblePlayers(), "Select A Player", "Back to Main Menu");
        ListMenu teamMenu = new ListMenu(mConsole, getTeamList(), "", "Select another player");

        int selection;
        Player player = null;
        Team team = null;

        do {
            mConsole.clearAndPrint("Add Player To A Team");
            mConsole.verticleSpace(1);

            switch (currentState) {
                case SELECT_PLAYER:
                    selection = playerMenu.GetSelection();
                    if (selection == 0) {
                        currentState = States.ABORT;
                    } else {
                        player = mPlayers.toArray(new Player[0])[selection - 1];
                        currentState = States.SELECT_TEAM;
                        teamMenu.setHeading(String.format("Select a team for %s", player.toString()));
                    }
                    break;
                case SELECT_TEAM:
                    selection = teamMenu.GetSelection();
                    if (selection == 0) {
                        currentState = States.SELECT_PLAYER;
                    } else {
                        team = mTeams.toArray(new Team[0])[selection - 1];
                        currentState = States.DONE;
                    }
                    break;
            }
        } while (currentState != States.DONE && currentState != States.ABORT);
        if (currentState == States.ABORT) {
            return;
        }
        team.addPlayer(player);
    }

    private List<String> getTeamList() {
        List<String> teamList = new ArrayList<>();
        for (Team team : mTeams) {
            teamList.add(team.toString());
        }
        return teamList;
    }

    private List<String> getAvailblePlayers() {
        List<String> playerList = new ArrayList<>();
        for (Player player : mPlayers) {
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

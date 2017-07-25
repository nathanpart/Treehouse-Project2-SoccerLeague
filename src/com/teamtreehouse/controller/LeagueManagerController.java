package com.teamtreehouse.controller;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.view.Console;
import com.teamtreehouse.view.Menu;
import com.teamtreehouse.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeagueManagerController {
    private List<Player> mPlayers;
    private List<Team> mTeams;

    // Console stuff
    private Console mConsole;

    // Main Menu
    enum MenuOptions {CREATE_TEAM, QUIT, ADD_PLAYER};
    private Menu<MenuOptions> mMainMenu;

    private void createMainMenu() {
        List<MenuItem<MenuOptions>> itemList = new ArrayList<MenuItem<MenuOptions>>();
        itemList.add(new MenuItem<MenuOptions>(MenuOptions.CREATE_TEAM, "Create a Team","create","create" ));
        itemList.add(new MenuItem<MenuOptions>(MenuOptions.QUIT, "Exit Program", "quit", "quit"));

        mMainMenu = new Menu<MenuOptions>(mConsole, "League Manager Menu", itemList);
    }

    public LeagueManagerController(Player[] players) {
        mPlayers = new ArrayList<Player>(Arrays.asList(players));
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
                default:
                    break;
            }
        }
    }

    private void doCreateTeam() {
        mConsole.clearAndPrint("Create New Team%n");
        mConsole.verticleSpace(1);
        String name = mConsole.getPromptLine("Enter team name");
        String coach = mConsole.getPromptLine(String.format("Enter coach's name for team %s", name));
        mTeams.add(new Team(name, coach));
        mConsole.printParagraph(String.format("Team %s with coach %s was created.", name, coach));
        mConsole.waitForReturn();
    }
}

package com.teamtreehouse.controller;

import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Teams;
import com.teamtreehouse.view.*;

import java.util.*;

public class LeagueManagerController {
    private Teams mTeams;
    private Players mAvailablePlayers;

    // Console stuff
    private Console mConsole;

    // Main Menu
    enum MenuOptions {CREATE_TEAM, ADD_PLAYER, REMOVE_PLAYER, LIST_TEAMS, BALANCE_REPORT, ROSTER_LIST, QUIT}
    private Menu<MenuOptions> mMainMenu;

    private void createMainMenu() {
        List<MenuItem<MenuOptions>> itemList = new ArrayList<>();
        itemList.add(new MenuItem<>(MenuOptions.CREATE_TEAM, "Create a Team", "create", "create"));
        itemList.add(new MenuItem<>(MenuOptions.ADD_PLAYER, "Add player to a team", "add", "add"));
        itemList.add(new MenuItem<>(MenuOptions.REMOVE_PLAYER, "Remove a player from a team", "remove", "remove"));
        itemList.add(new MenuItem<>(MenuOptions.LIST_TEAMS, "List teams", "list", "list"));
        itemList.add(new MenuItem<>(MenuOptions.QUIT, "Exit Program", "quit", "quit"));
        itemList.add(new MenuItem<>(MenuOptions.BALANCE_REPORT, "Balance report", "balance", "ballance"));
        itemList.add(new MenuItem<>(MenuOptions.ROSTER_LIST, "Team Rosters", "roster", "roster"));


        mMainMenu = new Menu<>(mConsole, "League Manager Menu", itemList, MenuOptions.class);
    }

    public LeagueManagerController() {
        mAvailablePlayers = new Players();
        mAvailablePlayers.reset();

        mTeams = new Teams();
        mConsole = new Console();



        createMainMenu();
    }

    public void run() {
        boolean runFlag = true;
        while (runFlag) {

            //Check and adjust which menu items are enabled or disabled
            checkMenuState();
            checkCreateTeamMenuState();

            //Get a menu selection
            MenuOptions option = mMainMenu.getSelction();

            //Dispatch to appropriate view class
            switch (option) {
                case QUIT:
                    runFlag = false;
                    break;
                case CREATE_TEAM:
                    new CreateTeam(mConsole, mTeams, mAvailablePlayers).render();
                    break;
                case ADD_PLAYER:
                    new AddPlayer(mConsole, mTeams, mAvailablePlayers).render();
                    break;
                case REMOVE_PLAYER:
                    new RemovePlayer(mConsole, mTeams, mAvailablePlayers).render();
                    break;
                case LIST_TEAMS:
                    new ListTeams(mConsole, mTeams, mAvailablePlayers).render();
                    break;
                case ROSTER_LIST:
                    new Roster(mConsole, mTeams, mAvailablePlayers).render();
                    break;
                case BALANCE_REPORT:
                    new BalanceReport(mConsole, mTeams, mAvailablePlayers).render();
                    break;
                default:
                    break;
            }
        }
    }

    //With no teams created - Most menu options make no sense so disable them
    private void checkMenuState() {
        if (mTeams.isEmpty()) {
            mMainMenu.disableMenuItem(MenuOptions.ADD_PLAYER);
            mMainMenu.disableMenuItem(MenuOptions.REMOVE_PLAYER);
            mMainMenu.disableMenuItem(MenuOptions.LIST_TEAMS);
            mMainMenu.disableMenuItem(MenuOptions.ROSTER_LIST);
            mMainMenu.disableMenuItem(MenuOptions.BALANCE_REPORT);
        } else {
            mMainMenu.enableMenuItem(MenuOptions.ADD_PLAYER);
            mMainMenu.enableMenuItem(MenuOptions.REMOVE_PLAYER);
            mMainMenu.enableMenuItem(MenuOptions.LIST_TEAMS);
            mMainMenu.enableMenuItem(MenuOptions.ROSTER_LIST);
            mMainMenu.enableMenuItem(MenuOptions.BALANCE_REPORT);
        }
    }

    //This handles the criteria of not add more teams than there are available players
    //  if this is the case we just disable the "Create Team" menu option"
    private void checkCreateTeamMenuState() {
        if (mTeams.getNumberOfTeams() >= mAvailablePlayers.getPlayerCount()) {
            mMainMenu.disableMenuItem(MenuOptions.CREATE_TEAM);
        } else {
            mMainMenu.enableMenuItem(MenuOptions.CREATE_TEAM);
        }
    }


}

package com.teamtreehouse.controller;

import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;
import com.teamtreehouse.view.AddPlayer;
import com.teamtreehouse.view.BalanceReport;
import com.teamtreehouse.view.Console;
import com.teamtreehouse.view.CreateTeam;
import com.teamtreehouse.view.ListTeams;
import com.teamtreehouse.view.Menu;
import com.teamtreehouse.view.MenuItem;
import com.teamtreehouse.view.RemovePlayer;
import com.teamtreehouse.view.Roster;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        itemList.add(new MenuItem<>(MenuOptions.CREATE_TEAM, "Create a Team", "(cr)|(create)", "(cr)eate"));
        itemList.add(new MenuItem<>(MenuOptions.ADD_PLAYER, "Add a player to a team", "(a)|(add)", "(a)dd"));
        itemList.add(new MenuItem<>(MenuOptions.REMOVE_PLAYER, "Remove a player from a team", "(re)|(remove)", "(re)move"));
        itemList.add(new MenuItem<>(MenuOptions.LIST_TEAMS, "List Reports", "(r)|(report)", "(r)report"));
        itemList.add(new MenuItem<>(MenuOptions.QUIT, "Exit Program", "(q)|(quit)", "(q)uit"));
        itemList.add(new MenuItem<>(MenuOptions.BALANCE_REPORT, "Balance report", "(b)|(balance)", "(b)alance"));
        itemList.add(new MenuItem<>(MenuOptions.ROSTER_LIST, "Team Rosters", "(ro)|(roster)", "(ro)ster"));


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
            StringBuilder menuHeading = new StringBuilder();

            //Menu screan headings
            menuHeading.append(String.format("Soccer League Manager%n%n"));

            menuHeading.append(String.format("The soccer league has a total of %d players.%n",
                    Players.getLeaguePlayerCount()));
            menuHeading.append(String.format("There are currently %d available players waiting to be assigned a team.%n",
                    mAvailablePlayers.getPlayerCount()));
            menuHeading.append(String.format("There are currently %d teams.%n",
                    mTeams.getNumberOfTeams()));
            menuHeading.append(String.format("%n"));

            menuHeading.append(String.format("Main Menu%n%n"));

            //Check and adjust which menu items are enabled or disabled
            boolean mainDisabled = checkMenuState();
            boolean teamDisabled = checkCreateTeamMenuState();
            boolean playerDisabled = checkAddPlayerMenuState();
            boolean removeDisabled = checkRevovePlayerMenuState();

            //Print any disabled menu option notes
            boolean prevNote = false;
            if (mainDisabled || teamDisabled || playerDisabled || removeDisabled) {
                menuHeading.append(String.format("Not all options are shown because:%n"));
            }
            if (mainDisabled) {
                menuHeading.append(String.format("  Most menu entries are unavailable because no teams exist."));
                prevNote = true;
            }
            if (teamDisabled) {
                if (prevNote) {
                    menuHeading.append(String.format("%n"));
                }
                menuHeading.append(String.format("  'Create a Team' is unavailable due to not enough available players."));
                prevNote = true;
            }
            if (playerDisabled && !mainDisabled) {
                if (prevNote) {
                    menuHeading.append(String.format("%n"));
                }
                menuHeading.append(String.format("  'Add a player' is unavailable because either:%n"));
                menuHeading.append(String.format("     No available payers to add to a team.%n"));
                menuHeading.append(String.format("     All teams currently have the maximum number of players."));
                prevNote = true;
            }
            if (removeDisabled && !mainDisabled) {
                if (prevNote) {
                    menuHeading.append(String.format("%n"));
                }
                menuHeading.append(String.format("  'Remove a player' is unavailable because no teams have any players."));
            }

            //Get a menu selection
            mMainMenu.setMenuTitle(String.valueOf(menuHeading));
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
    private Boolean checkMenuState() {
        if (mTeams.isEmpty()) {
            mMainMenu.disableMenuItem(MenuOptions.ADD_PLAYER);
            mMainMenu.disableMenuItem(MenuOptions.REMOVE_PLAYER);
            mMainMenu.disableMenuItem(MenuOptions.LIST_TEAMS);
            mMainMenu.disableMenuItem(MenuOptions.ROSTER_LIST);
            mMainMenu.disableMenuItem(MenuOptions.BALANCE_REPORT);
            return true;
        } else {
            mMainMenu.enableMenuItem(MenuOptions.ADD_PLAYER);
            mMainMenu.enableMenuItem(MenuOptions.REMOVE_PLAYER);
            mMainMenu.enableMenuItem(MenuOptions.LIST_TEAMS);
            mMainMenu.enableMenuItem(MenuOptions.ROSTER_LIST);
            mMainMenu.enableMenuItem(MenuOptions.BALANCE_REPORT);
            return false;
        }
    }

    //This handles the criteria of not add more teams than there are available players
    //  if this is the case we just disable the "Create Team" menu option"
    private Boolean checkCreateTeamMenuState() {
        if (mTeams.getNumberOfTeams() >= mAvailablePlayers.getPlayerCount()) {
            mMainMenu.disableMenuItem(MenuOptions.CREATE_TEAM);
            return true;
        } else {
            mMainMenu.enableMenuItem(MenuOptions.CREATE_TEAM);
            return false;
        }
    }


    //If all available teams have 11 players we need to disable the Add player menu
    //   as the select team menu will have no entries available.
    private Boolean checkAddPlayerMenuState() {
        if (mTeams.getTeamsListSorted(Teams.SortOptions.NAME, true).stream()
                .filter(t -> t.getPlayerCount() < 11)
                .count() == 0 || mAvailablePlayers.getPlayerCount() == 0) {
            mMainMenu.disableMenuItem(MenuOptions.ADD_PLAYER);
            return true;
        } else {
            mMainMenu.enableMenuItem(MenuOptions.ADD_PLAYER);
            return false;
        }
    }

    //If all teams have no players it is impossible to remove a player
    private Boolean checkRevovePlayerMenuState() {
        if (mTeams.getTeamsListSorted(Teams.SortOptions.NAME, true).stream()
                .filter(t -> t.getPlayerCount() != 0)
                .count() == 0) {
            mMainMenu.disableMenuItem(MenuOptions.REMOVE_PLAYER);
            return true;
        } else {
            mMainMenu.enableMenuItem(MenuOptions.REMOVE_PLAYER);
            return false;
        }
    }

}

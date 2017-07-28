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
    enum MenuOptions {CREATE_TEAM, ADD_PLAYER, REMOVE_PLAYER, LIST_TEAMS, BALANCE_REPORT, ROSTER_LIST, QUIT}
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
        itemList.add(new MenuItem<>(MenuOptions.BALANCE_REPORT, "Balance report", "balance", "ballance"));
        itemList.add(new MenuItem<>(MenuOptions.ROSTER_LIST, "Team Rosters", "roster", "roster"));


        mMainMenu = new Menu<>(mConsole, "League Manager Menu", itemList, MenuOptions.class);
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
            handleTeamMenuState();
            handlePlayerMenuState();
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
                    break;
                case ROSTER_LIST:
                    doListRoster();
                    break;
                case BALANCE_REPORT:
                    doBalanceReport();
                    break;
                default:
                    break;
            }
        }
    }

    private void handleTeamMenuState() {
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

    private void handlePlayerMenuState() {
        if (mPlayers.isEmpty()) {
            mMainMenu.disableMenuItem(MenuOptions.CREATE_TEAM);
        } else {
            mMainMenu.enableMenuItem(MenuOptions.CREATE_TEAM);
        }
    }



//    private boolean teamExistCheck() {
//        if (mTeams.size() > 0) {
//            return true;
//        } else {
//            mConsole.clearAndPrint("" +
//                    "");
//        }
//    }

    private void doListRoster() {
        mConsole.clearAndPrint("List Team Rosters");

        Team team = selectATeam(mTeams, "Roster List For Which Team?", "Every Team");
        if (team == null) {
            for (Team t : mTeams) {
                printTeamRoster(t);
            }
        } else {
            printTeamRoster(team);
        }
        mConsole.waitForReturn();
    }

    private void printTeamRoster(Team team) {
        StringBuilder teamRoster = new StringBuilder(String.format("%s   Coach: %s    Average Height: %d%n",
                team.getName(), team.getCoash(), team.getAverageHeight()));
        StringBuilder experiencedPlayers;
        experiencedPlayers = new StringBuilder(String.format("  Players with prior experience:%n"));
        StringBuilder nonExperiencedPlayers;
        nonExperiencedPlayers = new StringBuilder(String.format("  Players without prior experience:%n"));
        for (Player player : team.getPlayers()) {
            if (player.isPreviousExperience()) {
                experiencedPlayers.append(String.format("    %-30s Height %d\"%n", player.getFullName(), player.getHeightInInches()));
            } else {
                nonExperiencedPlayers.append(String.format("    %-30s Height %d\"%n", player.getFullName(), player.getHeightInInches()));
            }
        }
        teamRoster.append(experiencedPlayers);
        teamRoster.append(nonExperiencedPlayers);
        teamRoster.append(String.format("Percentage of players with prior experience is %3.1f%n", team.getExperienceLevel()));
        mConsole.printParagraph(String.valueOf(teamRoster));
    }

    private void doBalanceReport() {
        mTeams.sort((o1, o2) ->  (int) (Math.round(o2.getExperienceLevel() - o1.getExperienceLevel())));

        StringBuilder teamList = new StringBuilder();
        teamList.append(String.format("Percentage of team's players with prior experence:%n"));

        double averageExperienceLevel = 0;
        for (Team team : mTeams) {
            averageExperienceLevel += team.getExperienceLevel();
            teamList.append(String.format("  %s - %3.1f%n", team.getName(), team.getExperienceLevel()));
        }
        averageExperienceLevel /= mTeams.size();
        mConsole.clearAndPrint("Teams Balance Report For Experience");
        mConsole.printParagraph(String.format("Average team percentage of players with prior experience: %3.1f%%.%n",
                averageExperienceLevel));
        mConsole.printParagraph(String.valueOf(teamList));
        mConsole.waitForReturn();
    }

    private void doListTeams() {

        mConsole.clearAndPrint("List of Teams sorted by Average Height");
        mTeams.sort((o1, o2) -> o2.getAverageHeight() - o1.getAverageHeight());
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

        Set<Player> players = mPlayers;


        if (operation == PlayerOps.ADD_PLAYER) {
            playerBackOption = "Return to Main Menu";
            playerHeading = "Select an available:";
            currentState = States.SELECT_PLAYER;
            opTitle = "Add A Player To A Team";
        } else {
            teamBackOption = "Return to Main Menu";
            teamHeading = "Select the team to remove player from:";
            currentState = States.SELECT_TEAM;
            opTitle = "Remove A Player From A Team";
        }

        Player player = null;
        Team team = null;

        do {
            mConsole.clearAndPrint(opTitle);
            mConsole.verticleSpace(1);

            switch (currentState) {
                case SELECT_PLAYER:
                    player = selectAPlayer(players, playerHeading, playerBackOption);
                    if (player == null) {
                        if (operation == PlayerOps.ADD_PLAYER) {
                            currentState = States.ABORT;
                        } else {
                            currentState = States.SELECT_TEAM;
                        }
                    } else {
                        if (operation == PlayerOps.ADD_PLAYER) {
                            currentState = States.SELECT_TEAM;
                            teamHeading = String.format("Select a team for %s:", player.toString());
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
                            players = team.getPlayers();
                            playerHeading = String.format("Select a player for %s:", team.toString());
                            playerBackOption = "Select a different team";
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
        Player [] players = playerSet.toArray(new Player[0]);

        int selection = playerMenu.getSelection();
        if (selection == 0) {
            return null;
        } else {
            return players[selection - 1];
        }
    }

    private Team selectATeam(List<Team> teams, String heading, String backOption) {
        teams.sort(Comparator.comparing(Team::getName));
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

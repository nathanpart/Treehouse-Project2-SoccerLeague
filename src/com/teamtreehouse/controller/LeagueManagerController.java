package com.teamtreehouse.controller;

import com.teamtreehouse.view.Console;
import com.teamtreehouse.view.Menu;
import com.teamtreehouse.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class LeagueManagerController {
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

    public LeagueManagerController() {
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
                default:
                    break;
            }
        }
    }
}

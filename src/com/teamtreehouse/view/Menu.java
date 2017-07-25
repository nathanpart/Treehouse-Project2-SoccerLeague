package com.teamtreehouse.view;

import java.util.List;
import java.util.Map;

/**
 * Created by nathan on 7/11/17.
 */
public class Menu <E extends Enum>{
    private List<MenuItem<E>> mMenuList;
    private String mMenuTitle;
    private Console mConsole;

    public Menu(Console console, String menuTitle, List<MenuItem<E>> menu) {
        mMenuList = menu;
        mMenuTitle = menuTitle;
        mConsole = console;
    }

    public E getSelction() {
        mConsole.clear();
        while (true) {
            System.out.println(mMenuTitle);
            System.out.println();
            for (MenuItem menuItem : mMenuList) {
                System.out.printf("%s - %s%n", menuItem.getSelectHint(), menuItem.getDisplayText());
            }
            String selection = mConsole.getPromptLine("Enter Selection", true);
            for (MenuItem<E> menuItem : mMenuList) {
                if (menuItem.isSelected(selection)) {
                    return menuItem.getMenuItem();
                }
            }
            mConsole.clear();
            System.out.printf("Sorry, %s was an invalid selection. Please try again.", selection);
        }
    }


}

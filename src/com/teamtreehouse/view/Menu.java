package com.teamtreehouse.view;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nathan on 7/11/17.
 */
public class Menu <E extends Enum<E>>{
    private Map<E,MenuItem<E>> mMenuList;
    private String mMenuTitle;
    private Console mConsole;

    public Menu(Console console, String menuTitle, List<MenuItem<E>> menu, Class<E> enumClass) {
        mMenuList = new EnumMap<>(enumClass);
        for (MenuItem<E> menuItem: menu) {
            E itemKey = menuItem.getMenuItem();
            mMenuList.put(itemKey, menuItem);
        }
        mMenuTitle = menuTitle;
        mConsole = console;
    }

    public E getSelction() {
        mConsole.clear();
        while (true) {
            System.out.println(mMenuTitle);
            System.out.println();
            for (E key : mMenuList.keySet()) {
                if (isEnabled(key) == false) {
                    continue;
                }
                String selectHint = mMenuList.get(key).getSelectHint();
                String displayText = mMenuList.get(key).getDisplayText();
                System.out.printf("%-10s - %s%n", selectHint, displayText);
            }
            String selection = mConsole.getPromptLine("Enter Selection", true);
            for (E key : mMenuList.keySet()) {
                if (isEnabled(key) == false) {
                    continue;
                }
                if (mMenuList.get(key).isSelected(selection)) {
                    return key;
                }
            }
            mConsole.clear();
            System.out.printf("Sorry, %s was an invalid selection. Please try again.", selection);
        }
    }


    public void disableMenuItem(E item) {
        mMenuList.get(item).disableItem();
    }

    public void enableMenuItem(E item) {
        mMenuList.get(item).enableItem();
    }

    public boolean isEnabled(E item) {
        return mMenuList.get(item).isEnabled();
    }


}

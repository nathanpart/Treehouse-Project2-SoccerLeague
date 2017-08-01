package com.teamtreehouse.view;

import java.util.*;

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
        boolean badSelection = false;
        String selection = "";
        OptionalInt hintLen = mMenuList.values().stream()
                .mapToInt(item -> item.getSelectHint().length())
                .max();
        String itemFmt = String.format("%%-%ds - %%s%%n", (hintLen.isPresent() ? hintLen.getAsInt() : 0));
        while (true) {
            mConsole.clear();
            System.out.println(mMenuTitle);
            if (badSelection) {
                System.out.printf("%n'%s' was an invalid selection.%n", selection);
                badSelection = false;
            }
            System.out.println();
            for (E key : mMenuList.keySet()) {
                if (isEnabled(key) == false) {
                    continue;
                }
                String selectHint = mMenuList.get(key).getSelectHint();
                String displayText = mMenuList.get(key).getDisplayText();
                System.out.printf(itemFmt, selectHint, displayText);
            }
            selection = mConsole.getPromptLine("Enter Selection by type letters in () or the whole key without ()", true);
            for (E key : mMenuList.keySet()) {
                if (isEnabled(key) == false) {
                    continue;
                }
                if (mMenuList.get(key).isSelected(selection)) {
                    return key;
                }
            }
            badSelection = true;
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

    public void setMenuTitle(String title) {
        mMenuTitle = title;
    }
}

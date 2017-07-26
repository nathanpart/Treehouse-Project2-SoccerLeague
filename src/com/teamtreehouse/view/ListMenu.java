package com.teamtreehouse.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListMenu<E> {
    private String mHeading;
    private String mBackOption;
    private Console mConsole;
    private List<String> mMenuItems;

    public ListMenu(Console console, Collection<E> items, String heading, String backOption) {
        mConsole = console;
        mHeading = heading;
        mBackOption = backOption;
        mMenuItems = new ArrayList<>();
        for (E item: items) {
            mMenuItems.add(item.toString());
        }
    }

    public int getSelection() {
        int selection = 0;
        int lowEnd = 0;
        do {
            System.out.println(mHeading);
            if (!mBackOption.equals("")) {
                System.out.printf("  0 - %s%n", mBackOption);
            } else {
                lowEnd = 1;
            }
            for (int i=1; i <= mMenuItems.size(); i++) {
                System.out.printf("  %d - %s%n", i, mMenuItems.get(i - 1));
            }

            try {
                selection = Integer.parseInt(mConsole.getPromptLine("Eneter Selection"));
            } catch (NumberFormatException nfe) {
                System.out.println();
                System.out.printf("Invlid Input. Please enter an integer between %d and %d.%n", lowEnd, mMenuItems.size());
                continue;
            }
            if (selection < lowEnd || selection > mMenuItems.size()) {
                System.out.println();
                System.out.printf("Invalid Input. Please enter an integer between %d and %d.%n", lowEnd, mMenuItems.size());
            }
        } while (selection < lowEnd || selection > mMenuItems.size());
        return selection;
    }

    public void setHeading(String s){
        mHeading = s;
    }
}

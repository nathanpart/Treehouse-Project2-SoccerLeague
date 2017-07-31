package com.teamtreehouse.view;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ListMenu<E> {
    private Console mConsole;
    private List<E> mMenuItems;
    private String mHeading;
    private List<String> mGlobalOptions;
    private Function<E, String> mGetItemString;
    private int mPages;
    private boolean mIsPaged;
    private ReportView mParent;

    // Various Menu state variables used during selection
    private int mStart;
    private int mFirstGlobalItem;
    private int mFirstListItem;
    private int mPreviousPageItem;
    private int mEnd;
    private int mNextPageItem;


    public ListMenu(Console console, List<E> items, String heading, List<String> globalOptions,
                    Function<E, String> getItemText, ReportView parent) {
        mConsole = console;
        mHeading = heading;
        mGlobalOptions = globalOptions;
        mMenuItems = items;
        mGetItemString = getItemText;
        mParent = parent;
    }


    //Figure out how many pages need for list selection menu with 10 items per page
    private void computePaging() {
        mPages = (new Double(Math.ceil(mMenuItems.size() / 10.0))).intValue();
        mIsPaged = mPages != 1;
    }

    // Display and get menu selection from updated list
    public int getSelection(List<E> items) {
        mMenuItems = items;
        return getSelection();
    }


    //Display and get menu selection from updated list and menu heading
    public int getSelection(List<E> items, String heading) {
        mMenuItems = items;
        mHeading = heading;
        return getSelection();
    }

    // Display and get the menus selection
    public int getSelection() {
        int currentPage = 1;
        int selection = 0;
        boolean menuFlag = true;

        //Compute the paging for the menu we are showing
        computePaging();

        //Loop until we have an item selected from the menu send back
        do {
            mParent.displayHeading();
            mConsole.printParagraph(generateMenuPage(currentPage));

            try {
                selection = Integer.parseInt(mConsole.getPromptLine("Enter Selection:"));
            } catch (NumberFormatException e) {
                //User didn't enter a number - redisplay the menu and continue waiting
                continue;
            }

            // Range check the result
            if (selection < 1 || selection > mEnd) {
                continue;
            }

            // Handle change page selections
            if (selection == mNextPageItem) {
                currentPage += 1;
            } else if (selection == mPreviousPageItem) {
                currentPage -= 1;
            } else {
                menuFlag = false;
            }
        } while (menuFlag);

        //Convert the selection number into a list index value for global or list items
        if (selection < mFirstListItem) {
            return -((selection - mFirstGlobalItem) + 1); //Global indexes are negative
        } else {
            return (selection - mFirstListItem) + mStart;
        }
    }

    private String generateMenuPage(int page) {
        // Buffer to generate menu lines into
        StringBuilder menuPage = new StringBuilder();

        // Funtional stuff for inserting menu item lines into menu
        int itemNumber[] = {1};  //Menu line number in a array for functional closure
        Consumer<String> appendItem = item -> {
            menuPage.append(String.format("  %d - %s%n", itemNumber[0], item));
            itemNumber[0]++;
        };

        //Display page navigation heading if needed
        if (mIsPaged) {
            menuPage.append(String.format("Page %d of %d%n", page, mPages));
        }

        //Menu heading line
        menuPage.append(String.format("%s%n", mHeading));

        //If paged and not display the last menu page, Insert the "Next Page" option
        mNextPageItem = 0;
        if (mIsPaged && page != mPages) {
            appendItem.accept("Next Page");
            menuPage.append(String.format("%n"));
            mNextPageItem = 1;
        }

        //List out the global options - if we have them
        mFirstGlobalItem = itemNumber[0];
        if (mGlobalOptions != null && mGlobalOptions.size() > 0) {
            mGlobalOptions.forEach(appendItem);
            menuPage.append(String.format("%n"));
        }


        //Now set some state variables of where list items begin in both Menu Numbers and List index
        mFirstListItem = itemNumber[0];
        mStart = (page - 1) * 10;

        // We do 10 items per page, but we need to make sure we don't go past end of mMenuItems
        int stop = ((mStart + 10) < mMenuItems.size()) ? (mStart + 10) : mMenuItems.size();

        // Put a page worth of list items into menu
        IntStream.range(mStart, stop)
                .mapToObj(mMenuItems::get)
                .map(mGetItemString)
                .forEach(appendItem);

        // Record where the end of the list items are

        // If we have muliple pages and not on first page add the "Previous Page" option
        if (mIsPaged && page != 1) {
            mPreviousPageItem = itemNumber[0];     //Record item number for Previous Page
            menuPage.append(String.format("%n"));
            appendItem.accept("Previous Page");
        }

        //Record end of menu
        mEnd = itemNumber[0];

        return String.valueOf(menuPage);
    }

    public void setHeading(String s){
        mHeading = s;
    }
}

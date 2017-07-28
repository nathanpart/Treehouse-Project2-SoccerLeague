package com.teamtreehouse.view;

import java.util.regex.Pattern;

/**
 * Created by nathan on 7/11/17.
 */
public class MenuItem < E extends Enum> {
    private E mMenuItem;
    private String mDisplay;
    private Pattern mSelector;
    private String mSelectText;
    private boolean mEnabledFlag;

    public MenuItem(E item, String displayStr, String selectPattern, String selectText) {
        mMenuItem = item;
        mDisplay = displayStr;
        mSelector = Pattern.compile(selectPattern);
        mSelectText = selectText;
        mEnabledFlag = true;
    }

    public String getDisplayText() {
        return mDisplay;
    }

    public String getSelectHint() {
        return mSelectText;
    }

    public boolean isSelected(String selection) {
        return mSelector.matcher(selection).matches();
    }

    public E getMenuItem() {
        return mMenuItem;
    }

    public void disableItem() {
        mEnabledFlag = false;
    }

    public void enableItem() {
        mEnabledFlag = true;
    }

    public boolean isEnabled() {
        return mEnabledFlag;
    }
}

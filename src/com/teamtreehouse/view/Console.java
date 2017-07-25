package com.teamtreehouse.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nathan on 7/9/17.
 */
public class Console {

    private BufferedReader mReader;

    public Console() {
        mReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void clear() {
        ProcessBuilder clearCmd;
        Runtime rt = Runtime.getRuntime();
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                clearCmd = new ProcessBuilder("clr");

            } else {
                clearCmd = new ProcessBuilder("clear");
            }
            clearCmd.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            clearCmd.start();
        } catch (IOException e) {
            //Not able to call clear command
            e.printStackTrace();
        }
    }

    public String getPromptLine(String prompt) {
        return getPromptLine(prompt, false);
    }

    public String getPromptLine(String prompt, boolean forceLower) {
        String line = "";

        System.out.println();
        System.out.printf("%s:  ", prompt);
        try {
            line = mReader.readLine().trim();
            if (forceLower) {
                line = line.toLowerCase();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return line;
    }


    public void verticleSpace(int lines) {
        for (int i=0; i<lines; i++) {
            System.out.println();
        }
    }

    public void clearAndPrint(String s) {
        clear();
        System.out.println(s);
    }

    public void printParagraph(String s) {
        System.out.println();
        System.out.println(s);
    }

    public void waitForReturn() {
        System.out.println();
        getPromptLine("Press 'Enter' to continue");
    }
}

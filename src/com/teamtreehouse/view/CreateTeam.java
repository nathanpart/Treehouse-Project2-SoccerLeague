package com.teamtreehouse.view;

import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Teams;

public class CreateTeam extends ReportView{


    public CreateTeam(Console console, Teams teams, Players players) {
        super(console, "Create A New Team", teams, players);
    }

    @Override
    public void displayBody() {
        String teamName = mConsole.getPromptLine("Enter new Team's name");
        String coachName = mConsole.getPromptLine(String.format("Enter name of coach for %s", teamName));

        mTeams.createTeam(teamName, coachName);
        mConsole.printParagraph(String.format("Team %s with coach %s has been created.%n", teamName, coachName));
    }
}

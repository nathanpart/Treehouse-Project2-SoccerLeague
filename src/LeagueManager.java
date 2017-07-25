import com.teamtreehouse.controller.LeagueManagerController;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.view.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeagueManager {

    private static LeagueManagerController mController;

    public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    // Your code here!
    mController = new LeagueManagerController(players);
    mController.run();
  }

}

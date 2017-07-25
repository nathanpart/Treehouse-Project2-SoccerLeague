import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.view.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeagueManager {

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    // Your code here!
    System.out.printf("OS: %s", System.getProperty("os.name"));
    System.out.printf("OS Arch: %s", System.getProperty("os.arch"));
    Console console = new Console();
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    try {
      String s = r.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    console.clear();
    System.out.println("This should be in cleared screen");

  }

}

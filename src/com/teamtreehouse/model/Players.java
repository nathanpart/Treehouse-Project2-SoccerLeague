package com.teamtreehouse.model;

import java.util.*;
import java.util.stream.Collectors;

public class Players {
    private Set<Player> mPlayers;
    private static int maxFullNameWidth;
    private static int leaguePlayerCount;

    public Players() {
        mPlayers = new TreeSet<Player>();
    }

    //Fill players set with provided list of players below that was provided to project by instructor
    public void reset() {
        Player[] players = load();
        mPlayers.clear();
        Collections.addAll(mPlayers, players);

        //Compute the width of full name to help with setting field width in text formatting
        OptionalInt maxWdith = getPlayersList().stream()
                .map(Player::getFullName)
                .mapToInt(String::length)
                .max();
        maxFullNameWidth = maxWdith.isPresent() ? maxWdith.getAsInt() : 0;
        leaguePlayerCount = mPlayers.size();
    }

    public static Player[] load() {
        return new Player[]{
                new Player("Joe", "Smith", 42, true),
                new Player("Jill", "Tanner", 36, true),
                new Player("Bill", "Bon", 43, true),
                new Player("Eva", "Gordon", 45, false),
                new Player("Matt", "Gill", 40, false),
                new Player("Kimmy", "Stein", 41, false),
                new Player("Sammy", "Adams", 45, false),
                new Player("Karl", "Saygan", 42, true),
                new Player("Suzane", "Greenberg", 44, true),
                new Player("Sal", "Dali", 41, false),
                new Player("Joe", "Kavalier", 39, false),
                new Player("Ben", "Finkelstein", 44, false),
                new Player("Diego", "Soto", 41, true),
                new Player("Chloe", "Alaska", 47, false),
                new Player("Arfalseld", "Willis", 43, false),
                new Player("Phillip", "Helm", 44, true),
                new Player("Les", "Clay", 42, true),
                new Player("Herschel", "Krustofski", 45, true),
                new Player("Andrew", "Chalklerz", 42, true),
                new Player("Pasan", "Membrane", 36, true),
                new Player("Kenny", "Lovins", 35, true),
                new Player("Alena", "Sketchings", 45, false),
                new Player("Carling", "Seacharpet", 40, false),
                new Player("Joseph", "Freely", 41, false),
                new Player("Gabe", "Listmaker", 45, false),
                new Player("Jeremy", "Smith", 42, true),
                new Player("Ben", "Droid", 44, true),
                new Player("James", "Dothnette", 41, false),
                new Player("Nick", "Grande", 39, false),
                new Player("Will", "Guyam", 44, false),
                new Player("Jason", "Seaver", 41, true),
                new Player("Johnny", "Thunder", 47, false),
                new Player("Ryan", "Creedson", 43, false)
        };
    }


    public void addPlayer(Player player) {
        mPlayers.add(player);
    }

    public boolean hasPlayer(Player player) {
        return mPlayers.contains(player);
    }

    public void removePlayer(Player player) {
        mPlayers.remove(player);
    }


    public List<Player> getPlayersList() {
        return new ArrayList<>(mPlayers);
    }

    public List<Player> getPlayersWithExperience() {
        return mPlayers.stream()
                .filter(Player::isPreviousExperience)
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersWithoutExperience() {
        return mPlayers.stream()
                .filter(player -> !player.isPreviousExperience())
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByHeight() {
        return mPlayers.stream()
                .sorted(Comparator.comparingInt(Player::getHeightInInches))
                .collect(Collectors.toList());
    }

    public int getPlayerCount() {
        return mPlayers.size();
    }

    public static int getMaxFullNameWidth() {
        return maxFullNameWidth;
    }

    public static int getLeaguePlayerCount() {
        return leaguePlayerCount;
    }

}

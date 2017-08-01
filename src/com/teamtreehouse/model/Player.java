package com.teamtreehouse.model;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private int heightInInches;
    private boolean previousExperience;

    public Player(String firstName, String lastName, int heightInInches, boolean previousExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.heightInInches = heightInInches;
        this.previousExperience = previousExperience;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public boolean isPreviousExperience() {
        return previousExperience;
    }

    @Override
    public int compareTo(Player other) {
        // We always want to sort by last name then first name
        int lname = lastName.compareTo(other.lastName);
        if (lname != 0) {
            return lname;
        } else {
            return firstName.compareTo(other.firstName);
        }
    }

    @Override
    public String toString() {
        return getLastName() + ", "
                + getFirstName()
                + " (Height: " + getHeightInInches()
                + "\" Prior Experience: " + (isPreviousExperience() ? "yes" : "no")
                + ")";


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        return heightInInches == player.heightInInches &&
                previousExperience == player.previousExperience &&
                firstName.equals(player.firstName) &&
                lastName.equals(player.lastName);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + heightInInches;
        result = 31 * result + (previousExperience ? 1 : 0);
        return result;
    }

    public String getFullName() {
        return String.format("%s, %s", lastName, firstName);
    }

    public String getFullNameHeight() {
        String fmt = String.format("%%-%ds Height: %%2d\"", Players.getMaxFullNameWidth());
        return String.format(fmt, getFullName(), getHeightInInches());
    }

    public String getFullNameStats() {
        String fmt = String.format("%%-%ds Height: %%2d\" %%s", Players.getMaxFullNameWidth());

        return String.format(fmt,
                getFullName(),
                getHeightInInches(),
                (isPreviousExperience() ? "experience" : ""));
    }
}

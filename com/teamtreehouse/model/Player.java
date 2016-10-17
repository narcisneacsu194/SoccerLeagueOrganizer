package com.teamtreehouse.model;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable{
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private int heightInInches;
    private boolean previousExperience;

    public Player(String firstName, String lastName, int heightInInches, boolean previousExperience){
        this.firstName = firstName;
        this.lastName = lastName;
        this.heightInInches = heightInInches;
        this.previousExperience = previousExperience;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public int getHeightInInches(){
        return heightInInches;
    }

    public boolean isPreviousExperience(){
        return previousExperience;
    }

    //This method makes a list of players be sorted firstly by last name.
    //If two last names are equal, the comparison of the first names comes next.
    @Override
    public int compareTo(Player other){

        if(this.equals(other)){
            return 0;
        }

        if(!lastName.equals(other.lastName)){
            return lastName.compareTo(other.lastName);
        }else{
            return firstName.compareTo(other.firstName);
        }
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Player)) return false;

        Player player = (Player) o;

        if(heightInInches != player.heightInInches) return false;
        if(previousExperience != player.previousExperience) return false;
        if(!firstName.equals(player.firstName)) return false;
        return lastName.equals(player.lastName);
    }

    @Override
    public int hashCode(){
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + heightInInches;
        result = 31 * result + (previousExperience ? 1 : 0);
        return result;
    }
}

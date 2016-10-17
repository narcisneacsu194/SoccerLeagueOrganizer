package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;

public class Team implements Comparable<Team>, Serializable{
    private String mTeamName;
    private String mCoachName;
    private Set<Player> mPlayers;
    private Map<Integer, Set<Player>> mPlayersByHeight;
    private Map<Integer, Set<Player>> mPlayersByExperience;
    private int mPercentageOfExperiencedPlayers;

    public Team(String teamName, String coachName){
        mTeamName = teamName;
        mCoachName = coachName;
        mPlayers = new TreeSet<>();

        mPlayersByHeight = new HashMap<>();
        mPlayersByHeight.put(1, new HashSet<>());
        mPlayersByHeight.put(2, new HashSet<>());
        mPlayersByHeight.put(3, new HashSet<>());

        mPlayersByExperience = new HashMap<>();
        mPlayersByExperience.put(1, new HashSet<>());
        mPlayersByExperience.put(2, new HashSet<>());

        mPercentageOfExperiencedPlayers = 0;
    }


    public String getTeamName(){
        return mTeamName;
    }

    public String getCoachName(){
        return mCoachName;
    }

    public Set<Player> getPlayers(){
        return mPlayers;
    }

    //Add a player to the set, after that, categorize the player by height, and by experience.
    public void addPlayer(Player player){
        mPlayers.add(player);

        if(player.getHeightInInches() >= 35 && player.getHeightInInches() <= 40){
            mPlayersByHeight.get(1).add(player);
        }else if(player.getHeightInInches() >= 41 && player.getHeightInInches() <= 46){
            mPlayersByHeight.get(2).add(player);
        }else{
            mPlayersByHeight.get(3).add(player);
        }

        if(player.isPreviousExperience()){
            mPlayersByExperience.get(1).add(player);
        }else{
            mPlayersByExperience.get(2).add(player);
        }
    }

    public void removePlayer(int playerIndex) {
        List<Player> playerList = new ArrayList<>(mPlayers);
        Player player = playerList.get(playerIndex);
        System.out.printf("%nPlayer %s %s has been removed from team %s.%n",
                player.getFirstName(),
                player.getLastName(),
                mTeamName);
        mPlayers.remove(player);
    }


    public boolean isTeamEmpty(){
        return mPlayers.size() == 0;
    }

    public Map<Integer, Set<Player>> getPlayersByHeight(){
        return mPlayersByHeight;
    }

    public Map<Integer, Set<Player>> getPlayersByExperience(){
        return mPlayersByExperience;
    }

    public int getPercentageOfExperiencedPlayers(){
        calculatePercentageOfExperiencedPlayers();
        return mPercentageOfExperiencedPlayers;
    }

    //This method makes the Team objects be sorted alphabetically by team name.
    @Override
    public int compareTo(Team other){
        if(this.equals(other)){
            return 0;
        }

        return mTeamName.compareTo(other.mTeamName);
    }

    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }

        if(!(other instanceof Team)){
            return false;
        }

        Team otherTeam = (Team) other;

        if(!mTeamName.equals(otherTeam.mTeamName)){
            return false;
        }

        if(!mPlayers.equals(otherTeam.mPlayers)){
            return false;
        }

        return true;

    }

    private void calculatePercentageOfExperiencedPlayers(){
        int total = mPlayers.size();
        int numberOfExperiencedPlayers = 0;

        for(Player player : mPlayers){
            if(player.isPreviousExperience()){
                numberOfExperiencedPlayers++;
            }
        }
        System.out.printf("%n%d  %d%n",
                total,
                numberOfExperiencedPlayers);

        float percentage = (float)numberOfExperiencedPlayers / total;
        mPercentageOfExperiencedPlayers = (int)(percentage * 100);

        System.out.printf("%n%f  %d%n",
                percentage,
                mPercentageOfExperiencedPlayers);
    }

}

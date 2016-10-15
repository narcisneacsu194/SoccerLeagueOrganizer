package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Team implements Comparable<Team>, Serializable{
  private String mTeamName;
  private List<Player> mPlayers;
  private int mNoExperiencedPlayers;
  private int mNoInexperiencedPlayers;
  
  public Team(String teamName){
    mTeamName = teamName;
    mPlayers = new ArrayList<>();
  }
  
  public String getTeamName(){
    return mTeamName;
  }
  
  public List<Player> getPlayers(){
    return mPlayers;
  }
  
  public void addPlayer(Player player){
    mPlayers.add(player);
  }
  
  public void removePlayer(int playerIndex){
    Player player = mPlayers.get(playerIndex);
    System.out.println("\nPlayer " +
                       player.getFirstName() +
                       " " + player.getLastName() +
                       " has been removed from team " + 
                      getTeamName() + ".");
    mPlayers.remove(playerIndex);
  }
  
  public int getNumberOfPlayers(){
    return mPlayers.size();
  }
  
  public boolean isTeamEmpty(){
    return mPlayers.size() == 0;
  }
  
  public int getNoOfExperiencedPlayers(){
    mNoExperiencedPlayers = 0;
    for(Player player : mPlayers){
      if(player.isPreviousExperience()){
        mNoExperiencedPlayers++;
      }
    }
    
    return mNoExperiencedPlayers;
  }
  
  public int getNoOfInexperiencedPlayers(){
    mNoInexperiencedPlayers = 0;
    for(Player player : mPlayers){
      if(!player.isPreviousExperience()){
        mNoInexperiencedPlayers++;
      }
    }
    
    return mNoInexperiencedPlayers;
  }
  
  //This method makes a list of teams be sorted by team name.
  @Override
  public int compareTo(Team other){
    if(mTeamName.compareToIgnoreCase(other.mTeamName) > 0){
      return 1;
    }else if(mTeamName.compareToIgnoreCase(other.mTeamName) < 0){
      return -1;
    }
    
    return 0;
  }
}
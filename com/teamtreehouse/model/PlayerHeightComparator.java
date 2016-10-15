package com.teamtreehouse.model;
import java.util.Comparator;
//Created this class to sort a list of players in descending order by height in inches.
public class PlayerHeightComparator implements Comparator<Player>{
  
  @Override
  public int compare(Player player1, Player player2){
    if(player1.getHeightInInches() > player2.getHeightInInches()){
          return -1;
        }else if(player1.getHeightInInches() < player2.getHeightInInches()){
          return 1;
        }else{
          return 0;
        }
  }
  
}
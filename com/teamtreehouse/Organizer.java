package com.teamtreehouse;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.PlayerHeightComparator;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

public class Organizer{
  
  private List<Team> mTeamList;
  private Player[] mPlayers;
  private int mChoice;
  private int mCounter;
  private Console mConsole;
  
  public Organizer(){
    mTeamList = new ArrayList<>();
    mPlayers = Players.load();
    mChoice = 0;
    mCounter = 1;
    mConsole = System.console();
  }
  
  public List<Team> getTeams(){
    return mTeamList;
  }
  
  //This method starts the application.
  public void run(){
   
   while(true){
   printMenu();
   mChoice = Integer.parseInt(mConsole.readLine("%nChoice:  "));
     
   switch(mChoice){
    case 1:
     createTeam();
     break;
    case 2:
     addPlayer();
     break;
    case 3:
     removePlayer();
     break;
    case 4:
     leagueBalanceReport();
     break;
    case 5:
     heightReport();
     break;
    case 6:
     mConsole.printf("%nExiting Organizer mode... Entering Coach mode.%n");
     break;
    default:
     mConsole.printf("%nYou entered an invalid option. Try again.%n");
     break;
    }
     
    if(mChoice == 6){
      break;
    }
   }
   
  }
  
  //This method prints a menu with options to choose from.
  private void printMenu(){
   mConsole.printf("%nORGANIZER%n");
   mConsole.printf("==================%n");
   mConsole.printf("What do you want to do:%n%n");
   mConsole.printf("1. Create team %n");
   mConsole.printf("2. Add player to a team%n");
   mConsole.printf("3. Remove a player from a team%n"); 
   mConsole.printf("4. League balance report%n");
   mConsole.printf("5. Height Report%n");
   mConsole.printf("6. Quit%n");
  }
  
  //This method prompts you for a team name and uses it to create a new team.
  private void createTeam(){
    Team team = new Team(
      mConsole.readLine("%nEnter a name for your new team: "));
    mTeamList.add(team);
    mConsole.printf("%nCreated team with the name \"%s\".%n", 
                    team.getTeamName());
  }
  
  //This method lists players for you to choose. 
  //After you choose a player, you are prompted with a list of teams in which you want to add the selected player.
  private void addPlayer(){
    
    if(mTeamList.isEmpty()){
      mConsole.printf("%nSorry, there are no teams available.%n");
      return;
    }
    
    Arrays.sort(mPlayers);
 
    mCounter = 1;
    mConsole.printf("%nAvailable players:%n");
    for(Player player : mPlayers){
      mConsole.printf("%d. First Name: %s, Last Name: %s, Inches: %d, Experience: ",
                       mCounter,
                       player.getFirstName(),
                       player.getLastName(),
                       player.getHeightInInches());
        if(player.isPreviousExperience()){
          mConsole.printf("yes%n");
        }
        else{
          mConsole.printf("no%n");
        }
      mCounter++;
      
    }
    mCounter = 1;
    
    mChoice = Integer.parseInt(mConsole.readLine("%nSelect a player to add to a team:  "));
    mChoice--;
    Player player = mPlayers[mChoice];
    
    Collections.sort(mTeamList);
    
    mConsole.printf("%nAvailable teams:%n");
    for(Team team : mTeamList){
      mConsole.printf("%d. %s%n",
                      mCounter,
                      team.getTeamName());
      mCounter++;
    }
    
    mChoice = Integer.parseInt(mConsole.readLine("%nSelect the team where you want to add your player:  "));
    mChoice--;
    mTeamList.get(mChoice).addPlayer(player);
    mConsole.printf("%nPlayer %s %s has been added to the team %s.%n",
                   player.getFirstName(),
                   player.getLastName(),
                   mTeamList.get(mChoice).getTeamName());
  }
  
  //This method lists available teams. 
  //After you choose a team, you must also choose a player you wish to remove from the team.
  private void removePlayer(){
    
    if(mTeamList.isEmpty()){
      mConsole.printf("%nSorry, there are no teams available.%n");
      return;
    }
    
    Collections.sort(mTeamList);
    
    mCounter = 1;
    mConsole.printf("%nAvailable teams: %n");
    for(Team team : mTeamList){
     mConsole.printf("%d. %s%n",
                     mCounter,
                     team.getTeamName()); 
     mCounter++;
    }
    
    mChoice = Integer.parseInt(mConsole.readLine("%nChoose a team:  "));
    mChoice--;
    Team team = mTeamList.get(mChoice);
    
    if(team.isTeamEmpty()){
      mConsole.printf("%nSorry, there are no players for team %s.%n",
                     team.getTeamName());
      return;
    }
    
      Collections.sort(team.getPlayers());
      mCounter = 1;
      mConsole.printf("%nAvailable players:%n");
      for(Player player : team.getPlayers()){
        mConsole.printf("%d. First Name: %s, Last Name: %s, Inches: %d, Experience: ",
                       mCounter,
                       player.getFirstName(),
                       player.getLastName(),
                       player.getHeightInInches());
        if(player.isPreviousExperience()){
          mConsole.printf("yes%n");
        }
        else{
          mConsole.printf("no%n");
        }
        mCounter++;
      }
    
    
    mChoice = Integer.parseInt(mConsole.readLine("%nChoose a player you want to remove from team %s: ",
                                               team.getTeamName()));
    team.removePlayer(--mChoice);
  }
  
  //This method lists number of experienced and inexperienced players from each available team.
  private void leagueBalanceReport(){
    mConsole.printf("%nLeague Balance Report: %n");
    if(mTeamList.isEmpty()){
      mConsole.printf("%nSorry, there are no teams available.%n");
      return;
    }
    
      mCounter = 1;
      for(Team team : mTeamList){
      mConsole.printf("%d. %s --> Exp players: %d, Inexp players: %d %n", 
                    mCounter,
                    team.getTeamName(),
                    team.getNoOfExperiencedPlayers(),
                    team.getNoOfInexperiencedPlayers());
      mCounter++;
    } 
  }
  
  //This method let's you choose from the available team.
  //After that, it lists all the players available in descending order by height in inches.
  private void heightReport(){
    
    if(mTeamList.isEmpty()){
      mConsole.printf("%nSorry, there are no teams available.%n");
      return;
    }
    
    mCounter = 1;
    mConsole.printf("%nAvailable teams:%n");
    for(Team team : mTeamList){
      mConsole.printf("%d. %s%n", 
                    mCounter,
                    team.getTeamName());
      mCounter++;
    }
    mCounter = 1;
    
    mChoice = Integer.parseInt(mConsole.readLine("%nChoose a team: "));
    Team team = mTeamList.get(--mChoice);
    
    if(team.getPlayers().isEmpty()){
      mConsole.printf("%nSorry, there are no players available for team %s. %n",
                     team.getTeamName());
      return;
    }
    
    team.getPlayers().sort(new PlayerHeightComparator());
    
    for(Player player : team.getPlayers()){
      mConsole.printf("%d. %s %s Height: %d inches %n",
                      mCounter,
                      player.getFirstName(),
                      player.getLastName(),
                      player.getHeightInInches());
      mCounter++;
    }
  }
  
}
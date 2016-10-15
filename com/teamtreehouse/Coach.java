package com.teamtreehouse;

import java.io.Console;
import java.util.List;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

public class Coach{
  private Team mTeam;
  private Organizer mOrganizer;
  private Console mConsole;
  private int mChoice;
  private int mCounter;
  
  public Coach(Organizer organizer){
    mTeam = null;
    mOrganizer = organizer;
    mConsole = System.console();
    mChoice = 0;
    mCounter = 1;
  }
  
  public void run(){
    while(true){
      printMenu();
      mChoice = Integer.parseInt(mConsole.readLine("%nChoice:  "));
      
      switch(mChoice){
        case 1:
          chooseTeam();
          break;
        case 2:
          displayTeamRoster();
          break;
        case 3:
          mConsole.printf("Exiting Coach mode...");
          break;
        default:
          mConsole.printf("%nYou entered an invalid option. Try again.%n");
          break;
      }
      
      if(mChoice == 3){
        break;
      }
    }
  }
  
  private void printMenu(){
    mConsole.printf("%nCOACH%n");
    mConsole.printf("======%n%n");
    mConsole.printf("1. Choose a team%n");
    mConsole.printf("2. Display team roster%n");
    mConsole.printf("3. Quit%n");
  }
  
  private void chooseTeam(){
    List<Team> teamList = mOrganizer.getTeams();
    
    if(teamList.isEmpty()){
      mConsole.printf("%nSorry, there are no teams available.%n");
      return;
    }
    
    mCounter = 1;
    mConsole.printf("%nAvailable teams:%n");
    for(Team team : teamList){
      mConsole.printf("%d. %s%n", 
                      mCounter,
                      team.getTeamName());
      mCounter++;
    }
    
    mChoice = Integer.parseInt(mConsole.readLine("%nChoose a team: "));
    mTeam = teamList.get(--mChoice);
    
    mConsole.printf("%nYou have chosen to be the coach of %s team.%n",
                    mTeam.getTeamName());
  }
  
  private void displayTeamRoster(){
    if(mTeam == null){
      mConsole.printf("%nSorry, you haven't yet chosen a team to coach.%n");
      return;
    }
    
    List<Player> playerList = mTeam.getPlayers();
    if(playerList.isEmpty()){
      mConsole.printf("%nSorry, there are no players available for team %s%n.", 
                      mTeam.getTeamName());
    }
    
    mCounter = 1;
    mConsole.printf("%nTeam roster:%n");
    for(Player player : playerList){
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
  }
 
}
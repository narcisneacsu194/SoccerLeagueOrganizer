package com.teamtreehouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.PlayerHeightComparator;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

public class Organizer {
    private List<Team> mTeamList;
    private Player[] mPlayers;
    private int mChoice;
    private int mCounter;
    private BufferedReader mConsole;

    public Organizer(){
        mTeamList = new ArrayList<>();
        mPlayers = Players.load();
        mChoice = 0;
        mCounter = 1;
        mConsole = new BufferedReader(new InputStreamReader(System.in));
    }

    public List<Team> getTeams(){
        return mTeamList;
    }

    //This method starts the application.
    public void run() throws IOException{
        while(true){
            printMenu();
            System.out.printf("%nChoice:  ");
            mChoice = Integer.parseInt(mConsole.readLine());

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
                    System.out.printf("%nExiting Organizer mode... Entering Coach mode.%n");
                    break;
                default:
                    System.out.printf("%nYou entered an invalid option. Try again.%n");
                    break;
            }

            if(mChoice == 6){
                break;
            }
        }
    }

    //This method prints a menu with options to choose from.
    private void printMenu(){

        System.out.printf("%nORGANIZER%n");
        System.out.printf("=================%n");
        System.out.printf("What do you want to do:%n%n");
        System.out.printf("1. Create team %n");
        System.out.printf("2. Add player to a team%n");
        System.out.printf("3. Remove a player from a team%n");
        System.out.printf("4. League balance report%n");
        System.out.printf("5. Height Report%n");
        System.out.printf("6. Quit%n");
    }

    //This method prompts you for a team name and uses it to create a new one.
    private void createTeam() throws IOException{
        System.out.printf("%nEnter a name for your new team:  ");
        Team team = new Team(
                mConsole.readLine()
        );
        mTeamList.add(team);
        System.out.printf("%nCreated team with the name \"%s\".%n",
                team.getTeamName());
    }

    //This method lists players for you to choose.
    //After you choose a player, you are prompted with a list of teams in which you want to add the selected player.
    private void addPlayer() throws IOException{
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        Arrays.sort(mPlayers);

        mCounter = 1;
        System.out.printf("%nAvailable players:%n");
        for(Player player : mPlayers){
            System.out.printf("%d. First Name: %s, Last Name: %s, Inches: %d, Experience: ",
                    mCounter,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getHeightInInches());
            if(player.isPreviousExperience()){
                System.out.printf("yes%n");
            }else{
                System.out.printf("no%n");
            }

            mCounter++;
        }
        mCounter = 1;

        System.out.printf("%nSelect a player to add to a team:  ");
        mChoice = Integer.parseInt(mConsole.readLine());
        mChoice--;
        Player player = mPlayers[mChoice];

        Collections.sort(mTeamList);

        System.out.printf("%nAvailable teams:%n");
        for(Team team : mTeamList){
            System.out.printf("%d. %s%n",
                    mCounter,
                    team.getTeamName());

            mCounter++;
        }

        System.out.printf("%nSelect the team where you want to add your player:  ");
        mChoice = Integer.parseInt(mConsole.readLine());
        mChoice--;
        mTeamList.get(mChoice).addPlayer(player);
        System.out.printf("%nPlayer %s %s has been added to the team %s.%n",
                player.getFirstName(),
                player.getLastName(),
                mTeamList.get(mChoice).getTeamName());

    }

    //This method lists avaiable teams.
    //After you choose a team, you must also choose a player you wish to remove from the team.
    private void removePlayer() throws IOException{
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        Collections.sort(mTeamList);

        mCounter = 1;
        System.out.printf("%nAvailable teams: %n");
        for(Team team : mTeamList){
            System.out.printf("%d. %s%n",
                    mCounter,
                    team.getTeamName());

            mCounter++;
        }

        System.out.printf("%nChoose a team:  ");
        mChoice = Integer.parseInt(mConsole.readLine());
        mChoice--;
        Team team = mTeamList.get(mChoice);

        if(team.isTeamEmpty()){
            System.out.printf("%nSorry, there are no players for team %s.%n",
                    team.getTeamName());

            return;
        }

        Collections.sort(team.getPlayers());
        mCounter = 1;
        System.out.printf("%nAvailable players:%n");
        for(Player player : team.getPlayers()){
            System.out.printf("%d. First Name: %s, Last Name: %s, Inches: %d, Experience: ",
                    mCounter,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getHeightInInches());
            if(player.isPreviousExperience()){
                System.out.printf("yes%n");
            }else{
                System.out.printf("no%n");
            }

            mCounter++;
        }

        System.out.printf("%nChoose a player you want to remove from ream %s: ",
                team.getTeamName());
        mChoice = Integer.parseInt(mConsole.readLine());

        team.removePlayer(--mChoice);
    }

    //This method lists the number of experienced and inexperienced players from each available team.
    private void leagueBalanceReport(){
        System.out.printf("%nLeague Balance Report: %n");
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        mCounter = 1;
        for(Team team : mTeamList){
            System.out.printf("%d. %s --> Exp players: %d, Inexp players: %d %n",
                    mCounter,
                    team.getTeamName(),
                    team.getNoOfExperiencedPlayers(),
                    team.getNoOfInexperiencedPlayers());

            mCounter++;
        }
    }

    //This method lets you choose from the available teams.
    //After that, it lists all the players available in descending order by height in inches.
    private void heightReport() throws IOException{
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        mCounter = 1;
        System.out.printf("%nAvailable teams:%n");
        for(Team team : mTeamList){
            System.out.printf("%d. %s%n",
                    mCounter,
                    team.getTeamName());

            mCounter++;
        }
        mCounter = 1;

        System.out.printf("%nChoose a team:  ");
        mChoice = Integer.parseInt(mConsole.readLine());
        Team team = mTeamList.get(--mChoice);

        if(team.getPlayers().isEmpty()){
            System.out.printf("%nSorry, there are no players available for team %s. %n",
                    team.getTeamName());

            return;
        }

        team.getPlayers().sort(new PlayerHeightComparator());

        for(Player player : team.getPlayers()){
            System.out.printf("%d. %s %s Height: %d inches %n",
                    mCounter,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getHeightInInches());

            mCounter++;
        }
    }
}

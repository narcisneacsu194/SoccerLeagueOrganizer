package com.teamtreehouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

public class Prompter {
    private List<Team> mTeamList;
    private List<Player> mPlayers;
    private int mChoice;
    private int mCounter;
    private BufferedReader mConsole;

    public Prompter(){
        mTeamList = new ArrayList<>();
        mPlayers = new LinkedList<>(Arrays.asList(Players.load()));
        Collections.sort(mPlayers);
        mChoice = 0;
        mCounter = 1;
        mConsole = new BufferedReader(new InputStreamReader(System.in));
    }

    //This method starts the application.
    public void run() throws IOException, IllegalArgumentException{
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
                    displayRoster();
                    break;
                case 7:
                    System.out.printf("%nExiting Soccer League Prompter app...%n");
                    break;
                default:
                    System.out.printf("%nYou entered an invalid option (must be a value between 1 and 6, respectively). Please try again.%n");
                    break;
            }

            if(mChoice == 7){
                break;
            }
        }
    }

    //This method prints a menu with options to choose from.
    private void printMenu(){

        System.out.printf("%nORGANIZER%n");
        System.out.printf("=================%n");
        System.out.printf("What do you want to do:%n%n");
        System.out.printf("1. Create team and assign a coach%n");
        System.out.printf("2. Add player to a team%n");
        System.out.printf("3. Remove a player from a team%n");
        System.out.printf("4. League balance report%n");
        System.out.printf("5. Height Report%n");
        System.out.printf("6. Display team roster%n");
        System.out.printf("7. Quit%n");
    }

    //This method prompts you for a team name and uses it to create a new one.
    private void createTeam() throws IOException{

        if(mTeamList.size() == mPlayers.size()){
            System.out.printf("%nYou already have %d teams created. You can't have more teams than players.%n",
                    mTeamList.size());
            return;
        }

        System.out.printf("%nEnter a name for your team:  ");
        String teamName = mConsole.readLine();
        System.out.printf("%nEnter a name for your coach:  ");
        String coachName = mConsole.readLine();
        Team team = new Team(teamName, coachName);
        mTeamList.add(team);
        Collections.sort(mTeamList);
        System.out.printf("%nCreated team with the name \"%s\". The coach of this team will be %s.%n",
                team.getTeamName(),
                team.getCoachName());
    }

    //This method lists players for you to choose.
    //After you choose a player, you are prompted with a list of teams in which you want to add the selected player.
    private void addPlayer() throws IOException, IllegalArgumentException{
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

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
        Player player = mPlayers.get(mChoice);
        mPlayers.remove(mChoice);

        System.out.printf("%nAvailable teams:%n");
        for(Team team : mTeamList){
            System.out.printf("%d. %s, Coach: %s%n",
                    mCounter,
                    team.getTeamName(),
                    team.getCoachName());

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
    private void removePlayer() throws IOException, IllegalArgumentException{
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        mCounter = 1;
        System.out.printf("%nAvailable teams: %n");
        for(Team team : mTeamList){
            System.out.printf("%d. %s, Coach: %s%n",
                    mCounter,
                    team.getTeamName(),
                    team.getCoachName());

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

        mCounter = 1;
        System.out.printf("%nAvailable players for team %s:%n",
                    team.getTeamName());
        System.out.printf("Coach: %s%n%n",
                team.getCoachName());
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
        mChoice--;

        List<Player> playerList = new ArrayList<>(team.getPlayers());
        Player player = playerList.get(mChoice);
        mPlayers.add(player);
        Collections.sort(mPlayers);

        team.removePlayer(mChoice);
    }

    //This method lists the number of experienced and inexperienced players from each available team.
    private void leagueBalanceReport() throws IOException, IllegalArgumentException{
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        mCounter = 1;
        System.out.printf("%nAvailable teams:%n");
        for(Team team : mTeamList){
            System.out.printf("%d. %s, Coach: %s%n",
                    mCounter,
                    team.getTeamName(),
                    team.getCoachName());

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

        Map<Integer, Set<Player>> playersByExperience = team.getPlayersByExperience();
        Set<Player> playerSet;

        System.out.printf("%nLeague balance report for team %s:%n",
                team.getTeamName());
        for(Integer index : playersByExperience.keySet()){
            int counter = 1;
            if(index == 1){
                playerSet = playersByExperience.get(index);
                System.out.printf("%nExperienced players: %n");
                for(Player player : playerSet){
                    System.out.printf("%d. %s %s%n",
                            counter,
                            player.getFirstName(),
                            player.getLastName());
                    counter++;
                }
            }else{
                playerSet = playersByExperience.get(index);
                System.out.printf("%nInexperienced players: %n");
                for(Player player : playerSet){
                    System.out.printf("%d. %s %s%n",
                            counter,
                            player.getFirstName(),
                            player.getLastName());
                    counter++;
                }
            }
        }

        System.out.printf("%nPercentage of experienced players: %d%n",
                team.getPercentageOfExperiencedPlayers());

    }

    //This method lets you choose from the available teams.
    //After that, it lists all the players available in descending order by height in inches.
    private void heightReport() throws IOException, IllegalArgumentException{
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        mCounter = 1;
        System.out.printf("%nAvailable teams:%n");
        for(Team team : mTeamList){
            System.out.printf("%d. %s, Coach: %s%n",
                    mCounter,
                    team.getTeamName(),
                    team.getCoachName());

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

        Map<Integer, Set<Player>> playersByHeight = team.getPlayersByHeight();
        Set<Player> playerList;
        System.out.printf("%nHeight report for team %s: %n",
                team.getTeamName());
        for(Integer index : playersByHeight.keySet()){
            int counter = 1;
            if(index == 1){
                playerList = playersByHeight.get(1);
                System.out.printf("%n35-40 inches:%n");
                for(Player player : playerList){
                    System.out.printf("%d. %s %s --> %d inches.%n",
                            counter,
                            player.getFirstName(),
                            player.getLastName(),
                            player.getHeightInInches());
                    counter++;
                }
                System.out.printf("%nThere is/are %d player/players with a height between 35 and 40 inches.%n",
                        playerList.size());
            }else if(index == 2){
                playerList = playersByHeight.get(2);
                System.out.printf("%n41-46 inches:%n");
                for(Player player : playerList){
                    System.out.printf("%d. %s %s --> %d inches.%n",
                            counter,
                            player.getFirstName(),
                            player.getLastName(),
                            player.getHeightInInches());
                    counter++;
                }
                System.out.printf("%nThere is/are %d player/players with a height between 41 and 46 inches.%n",
                        playerList.size());
            }else{
                playerList = playersByHeight.get(3);
                System.out.printf("%n47-50 inches:%n");
                for(Player player : playerList){
                    System.out.printf("%d. %s %s --> %d inches.%n",
                            counter,
                            player.getFirstName(),
                            player.getLastName(),
                            player.getHeightInInches());
                    counter++;
                }
                System.out.printf("%nThere is/are %d player/players with a height between 47 and 50 inches.%n",
                        playerList.size());
            }
        }

    }

    private void displayRoster() throws IOException, IllegalArgumentException{
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        mCounter = 1;
        System.out.printf("%nAvailable teams:%n");
        for(Team team : mTeamList){
            System.out.printf("%d. %s, Coach: %s%n",
                    mCounter,
                    team.getTeamName(),
                    team.getCoachName());

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

        System.out.printf("%nAvailable players for team %s:%n",
                team.getTeamName());
        System.out.printf("Coach: %s%n%n",
                team.getCoachName());
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
    }
}

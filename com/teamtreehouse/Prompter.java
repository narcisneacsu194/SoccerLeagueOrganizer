package com.teamtreehouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    public Prompter(){
        mTeamList = new ArrayList<>();
        mPlayers = new LinkedList<>(Arrays.asList(Players.load()));
        Collections.sort(mPlayers);
    }

    //This method prompts the user to select an option, from the ones available.
    private int makeChoice(String message, int top){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice;
        System.out.printf("%n%s", message);
        while(true){
            try{
                    choice = Integer.parseInt(bufferedReader.readLine());
                    if(choice >= 1 && choice <= top){
                        break;
                    }else{
                        if(top != 1){
                            System.out.printf("%nThe option you provided is invalid. ");
                            System.out.printf("You must enter a number between 1 and %d, respectively.%n%nChoice:  ",
                                    top);
                        }else{
                            System.out.printf("%nThe option you provided is invalid. ");
                            System.out.printf("Your only option in this case is to choose number 1.%n%nChoice:  ");
                        }
                    }
            }catch(IOException ioe){
                System.out.printf("%nSomething went wrong with the input stream.%n");
                ioe.printStackTrace();
                System.exit(0);
            }catch(IllegalArgumentException iae){
                System.out.printf("%nYou must only enter numerical values.%n%nChoice:  ");
            }
        }

        return choice;
    }

    //This method starts the application.
    public void run(){
        int choice;
        while(true){
            printMenu();
            choice = makeChoice("Choice:  ", 7);

            switch(choice){
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
                    System.out.printf("%nExiting Soccer League Organizer app...%n");
                    break;
            }

            if(choice == 7){
                break;
            }
        }
    }

    //This method prints a menu with options to choose from.
    private void printMenu(){

        System.out.printf("%nSoccer League Organizer%n");
        System.out.printf("================================%n");
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
    private void createTeam(){

        if(mTeamList.size() == mPlayers.size()){
            System.out.printf("%nYou already have %d teams created. You can't have more teams than players.%n",
                    mTeamList.size());
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String teamName = "";String coachName = "";

        try{
            System.out.printf("%nEnter a name for your team:  ");
            teamName = bufferedReader.readLine();
            System.out.printf("%nEnter a name for your coach:  ");
            coachName = bufferedReader.readLine();
        }catch(IOException ioe){
            System.out.printf("%nSomething went wrong with the input stream.%n");
            ioe.printStackTrace();
        }

        Team team = new Team(teamName, coachName);
        mTeamList.add(team);
        Collections.sort(mTeamList);
        System.out.printf("%nCreated team with the name \"%s\". The coach of this team will be %s.%n",
                team.getTeamName(),
                team.getCoachName());
    }

    private void printPlayers(Collection<Player> playerCollection){
        int counter = 1;
        for(Player player : playerCollection){
            System.out.printf("%d. %s %s, Height: %d inches, Experience: ",
                    counter,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getHeightInInches());
            if(player.isPreviousExperience()){
                System.out.printf("yes%n");
            }else{
                System.out.printf("no%n");
            }

            counter++;
        }
    }

    private void printTeams(){
        int counter = 1;
        System.out.printf("%nAvailable teams:%n");
        for(Team team : mTeamList){
            System.out.printf("%d. %s, Coach: %s%n",
                    counter,
                    team.getTeamName(),
                    team.getCoachName());

            counter++;
        }
    }

    //This method lists players for you to choose.
    //After you choose a player, you are prompted with a list of teams in which you want to add the selected player.
    private void addPlayer(){
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        int choice;
        System.out.printf("%nAvailable players:%n");
        printPlayers(mPlayers);

        choice = makeChoice("Select a player to add to a team:  ", mPlayers.size());
        choice--;
        Player player = mPlayers.get(choice);
        mPlayers.remove(choice);

        printTeams();

        choice = makeChoice("Select the team where you want to add your player:  ", mTeamList.size());
        choice--;
        mTeamList.get(choice).addPlayer(player);
        System.out.printf("%nPlayer %s %s has been added to the team %s.%n",
                player.getFirstName(),
                player.getLastName(),
                mTeamList.get(choice).getTeamName());

    }

    //This method lists avaiable teams.
    //After you choose a team, you must also choose a player you wish to remove from the team.
    private void removePlayer(){
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        int choice;

        printTeams();

        choice = makeChoice("Choose a team:  ", mTeamList.size());
        Team team = mTeamList.get(--choice);

        if(team.isTeamEmpty()){
            System.out.printf("%nSorry, there are no players for team %s.%n",
                    team.getTeamName());

            return;
        }

        System.out.printf("%nAvailable players for team %s:%n",
                    team.getTeamName());
        System.out.printf("Coach: %s%n%n",
                team.getCoachName());
        printPlayers(team.getPlayers());

        choice = makeChoice("Choose a player you want to remove:  ", team.getPlayers().size());
        choice--;

        List<Player> playerList = new ArrayList<>(team.getPlayers());
        Player player = playerList.get(choice);
        mPlayers.add(player);
        Collections.sort(mPlayers);
        team.removePlayer(choice);
    }

    private void printExperience(Set<Player> playerSet){
        int counter = 1;
        for(Player player : playerSet){
            System.out.printf("%d. %s %s%n",
                    counter,
                    player.getFirstName(),
                    player.getLastName());
            counter++;
        }
    }

    //This method lists the number of experienced and inexperienced players from each available team.
    private void leagueBalanceReport(){
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        int choice;
        printTeams();

        choice = makeChoice("Choose a team:  ", mTeamList.size());
        Team team = mTeamList.get(--choice);

        if(team.getPlayers().isEmpty()){
            System.out.printf("%nSorry, there are no players available for team %s. %n",
                    team.getTeamName());

            return;
        }

        System.out.printf("%nLeague balance report for team %s:%n",
                team.getTeamName());
        Map<Integer, Set<Player>> playersByExperience = team.getPlayersByExperience();
        Set<Player> playerSet = playersByExperience.get(1);
        System.out.printf("%nExperienced players: %n");
        printExperience(playerSet);
        playerSet = playersByExperience.get(2);
        System.out.printf("%nInexperienced players: %n");
        printExperience(playerSet);

        System.out.printf("%nApproximate percentage of experienced players: %d%n",
                team.getPercentageOfExperiencedPlayers());

    }

    private void printHeight(Set<Player> playerSet, int leftLimit, int rightLimit){
        int counter = 1;
        System.out.printf("%n%d-%d inches:%n",
                leftLimit,
                rightLimit);

        for(Player player : playerSet){
            System.out.printf("%d. %s %s --> %d inches.%n",
                    counter,
                    player.getFirstName(),
                    player.getLastName(),
                    player.getHeightInInches());
            counter++;
        }

        System.out.printf("%nThere is/are %d player/players with a height between %d and %d inches.%n",
                playerSet.size(),
                leftLimit,
                rightLimit);
    }

    //This method lets you choose from the available teams.
    //After that, it lists all the players available in descending order by height in inches.
    private void heightReport(){
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        int choice;
        printTeams();

        choice = makeChoice("Choose a team:  ", mTeamList.size());
        Team team = mTeamList.get(--choice);

        if(team.getPlayers().isEmpty()){
            System.out.printf("%nSorry, there are no players available for team %s. %n",
                    team.getTeamName());

            return;
        }

        System.out.printf("%nHeight report for team %s: %n",
                team.getTeamName());
        Map<Integer, Set<Player>> playersByHeight = team.getPlayersByHeight();

        Set<Player> playerSet = playersByHeight.get(1);
        printHeight(playerSet, 35, 40);

        playerSet = playersByHeight.get(2);
        printHeight(playerSet, 41, 46);

        playerSet = playersByHeight.get(3);
        printHeight(playerSet, 47, 50);

    }

    //This method just lists the available players of a chosen team.
    private void displayRoster(){
        if(mTeamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        int choice;
        printTeams();

        choice = makeChoice("Choose a team:  ", mTeamList.size());
        Team team = mTeamList.get(--choice);

        if(team.getPlayers().isEmpty()){
            System.out.printf("%nSorry, there are no players available for team %s. %n",
                    team.getTeamName());

            return;
        }

        System.out.printf("%nAvailable players for team %s:%n",
                team.getTeamName());
        System.out.printf("Coach: %s%n%n",
                team.getCoachName());
        printPlayers(team.getPlayers());

    }
}

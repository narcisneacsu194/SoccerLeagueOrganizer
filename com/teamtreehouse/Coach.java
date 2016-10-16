package com.teamtreehouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Team;

public class Coach {
    private Team mTeam;
    private Organizer mOrganizer;
    private BufferedReader mConsole;
    private int mChoice;
    private int mCounter;

    public Coach(Organizer organizer){
        mTeam = null;
        mOrganizer = organizer;
        mConsole =
                new BufferedReader(new InputStreamReader(System.in));
        mChoice = 0;
        mCounter = 1;
    }

    public void run() throws IOException{
        while(true){
            printMenu();
            System.out.printf("%nChoice:  ");
            mChoice = Integer.parseInt(mConsole.readLine());

            switch(mChoice){
                case 1:
                    chooseTeam();
                    break;
                case 2:
                    displayTeamRoster();
                    break;
                case 3:
                    System.out.printf("%nExiting Coach mode...%n");
                    System.out.printf("%nExiting Soccer League Organizer app...%n");
                    break;
                default:
                    System.out.printf("%nYou entered an invalid numerical option. Please try again.%n");
                    break;
            }

            if(mChoice == 3){
                break;
            }
        }
    }

    private void printMenu(){
        System.out.printf("%nCOACH%n");
        System.out.printf("=================%n");
        System.out.printf("What do you want to do:%n%n");
        System.out.printf("1. Choose a team%n");
        System.out.printf("2. Display team roster%n");
        System.out.printf("3. Quit%n");
    }

    private void chooseTeam() throws IOException{
        List<Team> teamList = mOrganizer.getTeams();

        if(teamList.isEmpty()){
            System.out.printf("%nSorry, there are no teams available.%n");
            return;
        }

        mCounter = 1;
        System.out.printf("%nAvailable teams:%n");
        for(Team team : teamList){
            System.out.printf("%d. %s%n",
                    mCounter,
                    team.getTeamName());

            mCounter++;
        }

        System.out.printf("%nChoose a team:  ");
        mChoice = Integer.parseInt(mConsole.readLine());
        mTeam = teamList.get(--mChoice);

        System.out.printf("%nYou have chosen to be the coach of %s team.%n",
                mTeam.getTeamName());
    }

    private void displayTeamRoster(){
        if(mTeam == null){
            System.out.printf("%nSorry, you haven't yet chosen a team to coach.%n");
            return;
        }

        List<Player> playerList = mTeam.getPlayers();
        if(playerList.isEmpty()){
            System.out.printf("%nSorry, there are no players available for team %s.%n",
                    mTeam.getTeamName());
            return;
        }

        mCounter = 1;
        System.out.printf("%nTeam roster: %n");
        for(Player player : playerList){
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

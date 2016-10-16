import java.io.IOException;

import com.teamtreehouse.Coach;
import com.teamtreehouse.Organizer;

public class LeagueManager {
    public static void main(String[] args){
        Organizer organizer = new Organizer();
        try{
            organizer.run();
        }catch(IOException ioe){
            System.out.printf("%nYour input is invalid.%nYou must enter one of the numerical values displayed on the screen.");
            ioe.printStackTrace();
        }

        Coach coach = new Coach(organizer);
        try{
            coach.run();
        }catch(IOException ioe){
            System.out.printf("%nYour input is invalid.%nYou must enter one of the numerical values displayed on the screen.");
            ioe.printStackTrace();
        }
    }
}

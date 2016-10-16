import java.io.IOException;

import com.teamtreehouse.Coach;
import com.teamtreehouse.Organizer;

public class LeagueManager {
    public static void main(String[] args){
        Organizer organizer = new Organizer();
        try{
            organizer.run();
        }catch(IOException ioe){
            System.out.printf("%nSomething went wrong with the input stream.%n");
            ioe.printStackTrace();
            return;
        }catch(IllegalArgumentException iae){
            System.err.printf("%nYou must only enter numerical values between 1 and 6, respectively.%n");
            System.out.printf("%nExiting Organizer mode...%n");
            System.out.printf("%nExiting Soccer League Organizer app...%n");
            return;
        }

        Coach coach = new Coach(organizer);
        try{
            coach.run();
        }catch(IOException ioe){
            System.out.printf("%nSomething went wrong with the input stream.%n");
            ioe.printStackTrace();
        }catch(IllegalArgumentException iae){
            System.err.printf("%nYou must only enter numerical values between 1 and 3, respectively.%n");
            System.out.printf("%nExiting Coach mode...%n");
            System.out.printf("%nExiting Soccer League Organizer app...%n");
        }
    }
}

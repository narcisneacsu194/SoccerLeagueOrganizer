import java.io.IOException;

import com.teamtreehouse.Prompter;

public class LeagueManager {
    public static void main(String[] args){
        Prompter prompter = new Prompter();
        try{
            prompter.run();
        }catch(IOException ioe){
            System.out.printf("%nSomething went wrong with the input stream.%n");
            ioe.printStackTrace();
        }catch(IllegalArgumentException iae){
            System.err.printf("%nYou must only enter numerical values between 1 and 6, respectively.%n");
            System.out.printf("%nExiting Soccer League Prompter app...%n");
        }
    }

}

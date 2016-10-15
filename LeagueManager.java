import com.teamtreehouse.Coach;
import com.teamtreehouse.Organizer;
public class LeagueManager {

  public static void main(String[] args) {
    Organizer organizer = new Organizer();
    organizer.run();
    Coach coach = new Coach(organizer);
    coach.run();
  }

}

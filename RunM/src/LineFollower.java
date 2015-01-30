import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;


public class LineFollower {

	private DifferentialPilot pilot;
	private int direction;
	private int bestValue;
	
	
	public LineFollower(DifferentialPilot p) {
		pilot = p;
	}
}

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;


public class SimpleThread extends FunctionThread {

	public SimpleThread(DataExchange d, DifferentialPilot p) {
		super(d, p);
		getDE().setDoBarcodeScan(true);
		LCD.drawString("SIMPLE start", 0, 1);
	}

	@Override
	public void run() {
		while(isRunning()) {
			LCD.refresh();
			LCD.drawString("SIMPLE RUNNING", 0, 1);
			if (getDE().getChallengeEnded()) {
				getDE().setDoBarcodeScan(false);
				
				LCD.drawString("TASK1 ENDED! " , 0, 1);
				
				Motor.C.rotate(-90);
				Motor.C.resetTachoCount();
				getDE().setDoBarcodeScan(false);
				setRunning(false);
				
				getPilot().travel(DATA.travelDist*4);
				
				getDE().setChallengeEnded(false);
				DATA.startNext();
				
			//	return; 
			}
			getPilot().travel(DATA.travelDist);
			
		}
	}

}

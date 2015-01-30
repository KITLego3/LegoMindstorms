import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;


public class ScanningThread extends FunctionThread {
	
	private double angle;
	private int value;
	
	public ScanningThread(DataExchange d, DifferentialPilot p ) {
		super(d, p);
		Motor.C.resetTachoCount();
	}
	

	public void run() {
		while (isRunning()) {
			if(getDE().getDoBarcodeScan()) { continue; }
			
			if(DATA.ls.readValue() > DATA.silverTres) {
				value = DATA.ls.readValue();
				angle = Motor.C.getTachoCount();
				LCD.drawString(Double.toString(angle), 0, 1);
				LCD.drawInt(value, 0, 2);
				//pilot.steer(Sensors.ARC, angle);
				if (Math.abs(angle) > 15) {getPilot().rotate(angle/2);}
				getPilot().travel(DATA.travelDist);
				getDE().reset();
			}
		}
	}

}

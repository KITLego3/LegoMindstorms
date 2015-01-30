import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;


public class RotateScanThread extends FunctionThread {
	
	private int toBarcode = 0;
	
	public RotateScanThread(DataExchange d, DifferentialPilot p ) {
		super(d,p);
	}
	
	private void scanFromTo(int angle) {
		Motor.C.rotateTo(-angle); 
		Motor.C.rotateTo(angle);
	}

	public void run() {
		
		while (isRunning()) {
			if (getDE().getDoBarcodeScan()) { continue;}
			
			if (getDE().getHistory()<DATA.historyStep*2) {
				Motor.C.rotateTo(0);
				getDE().inc();
			} /*else if (DE.getHistory() < DATA.historyStep*2){
				Motor.C.rotateTo(-15); 
				Motor.C.rotateTo(15);
				DE.inc();
			} */ else if (getDE().getHistory() < DATA.historyStep*3){
				scanFromTo(30); getDE().inc();
			} else if (getDE().getHistory() < DATA.historyStep*4){
				scanFromTo(60); getDE().inc();
			} else if (getDE().getHistory() < DATA.historyStep*5){
				scanFromTo(80); getDE().inc();
			} else {
				getPilot().travel(-DATA.travelDist);
				//getPilot().rotate(10); // change position a bit
				LCD.drawString("GOBACK!!!", 0, 1);
				getDE().setHistory(DATA.historyStep*4);
				toBarcode++;
				if(toBarcode > 2) {
					//READ BARCODE
					LCD.drawString("NOW SCAN FOR BARCODE!", 0, 1);
					Motor.C.rotateTo(0);
					//DE.setReadBarcode(true);
					getDE().setDoBarcodeScan(true);
					getPilot().travel(550);
				}
			}
			
		}
	}
	
}

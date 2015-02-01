import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class BridgeThread extends Thread {
	
	NXTRegulatedMotor linkerMotor; 
	NXTRegulatedMotor rechterMotor; 
	TouchSensor linkerTaster;
	TouchSensor rechterTaster;
	UltrasonicSensor ultraSchall;
	int NormGeschwindigkeitRechts;
	int NormGeschwindigkeitLinks;
	
	public BridgeThread(){
		NormGeschwindigkeitRechts = 595;
		NormGeschwindigkeitLinks = 610;
		linkerMotor = new NXTRegulatedMotor(MotorPort.A); 
		rechterMotor = new NXTRegulatedMotor(MotorPort.B);
		linkerTaster = new TouchSensor(SensorPort.S2);
		rechterTaster = new TouchSensor(SensorPort.S4);
		ultraSchall = new UltrasonicSensor(SensorPort.S1);
		ultraSchall.continuous();		
		linkerMotor.setSpeed(NormGeschwindigkeitLinks);
		rechterMotor.setSpeed(NormGeschwindigkeitRechts);
		linkerMotor.forward();
		rechterMotor.forward();
	}
	
	public void run(){
		
		while(true){
					
			LCD.drawString("US: "+ultraSchall.getDistance()+" ", 0, 1);
			
			if(ultraSchall.getDistance() < 240){
			
				if(ultraSchall.getDistance() > 40){	
					linksKurve();
				}
				if(ultraSchall.getDistance() < 40){	
					rechtsKurve();
				}
			}
		}
	}

	private void rechtsKurve() {
		
		LCD.drawString("rechtsKurve", 0, 5);
				
		linkerMotor.setSpeed(500);
		rechterMotor.setSpeed(400);
		linkerMotor.forward();
		rechterMotor.forward();
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void linksKurve() {
		
		LCD.drawString("linksKurve", 0, 5);
				
		linkerMotor.setSpeed(400);
		rechterMotor.setSpeed(500);
		linkerMotor.forward();
		rechterMotor.forward();
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

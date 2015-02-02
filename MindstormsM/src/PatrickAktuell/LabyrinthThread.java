package PatrickAktuell;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class LabyrinthThread extends Thread {
	
	int rückfahrt;
	TouchSensor linkerTaster;
	TouchSensor rechterTaster;
	UltrasonicSensor ultraSchall;
	private boolean naheWand;
	DifferentialPilot pilot;
	
	public LabyrinthThread(){
		pilot = new DifferentialPilot(34, 135, Motor.A, Motor.B);
		rückfahrt = -40;
		linkerTaster = new TouchSensor(SensorPort.S2);
		rechterTaster = new TouchSensor(SensorPort.S4);
		ultraSchall = new UltrasonicSensor(SensorPort.S1);
		ultraSchall.continuous();
		naheWand = false;
	}
	
	public void run(){
		
		LCD.clear();
		pilot.setTravelSpeed(800);
		pilot.forward();
		
		while(true){
					
			LCD.drawString("US: "+ultraSchall.getDistance()+"   ", 0, 0);
			
			//Fall1
			if(linkerTaster.isPressed() || rechterTaster.isPressed()){
				LCD.drawString("Taster gemerkt", 0, 1);
				
				//Bug in leJOS?
				Motor.A.stop();
				Motor.B.stop();
				pilot.stop();
				Motor.A.stop();
				Motor.B.stop();
				
				entferneVonWand();
				dreheInPosition();
				naheWand = true;
			}
			
			//Fall2
			if(naheWand && !linkerTaster.isPressed() && !rechterTaster.isPressed()){	
				folgeWand();
			}
			
			//Fall3
			if((linkerTaster.isPressed() && rechterTaster.isPressed()) && (ultraSchall.getDistance() < 12)){
				naheWand = false;
				entferneSchrägVonWand();	
			}
		}
	}

	private void entferneSchrägVonWand() {
		
		LCD.drawString("entferneSchrägVonWand", 0, 5);
		
		pilot.travel(rückfahrt*3, false);
		
		pilot.rotate(70, false);
		
		pilot.forward();
	}

	private void folgeWand() {
		
		LCD.drawString("folgeWand", 0, 2);
				
		if((ultraSchall.getDistance() > 11)&&(ultraSchall.getDistance() < 16)){	
			Motor.A.setSpeed(550);
			Motor.B.setSpeed(400);
			Motor.A.forward();
			Motor.B.forward();
		}
		
		else if((ultraSchall.getDistance() > 8)&&(ultraSchall.getDistance() < 12)){	
			Motor.A.setSpeed(700);
			Motor.B.setSpeed(700);
			Motor.A.forward();
			Motor.B.forward();
		}
			
		else if(ultraSchall.getDistance() < 9){
			Motor.A.setSpeed(400);
			Motor.B.setSpeed(550);
			Motor.A.forward();
			Motor.B.forward();		
			}
		
		else if(ultraSchall.getDistance() > 15){
			Motor.A.setSpeed(700);
			Motor.B.setSpeed(200);
			Motor.A.forward();
			Motor.B.forward();		
			}
	}

	private void dreheInPosition() {
		
		LCD.drawString("dreheInPosition", 0, 2);
		
		pilot.rotate(550, true);
		
		while(true){
			
			if((ultraSchall.getDistance() < 12)){	
				pilot.stop();
				break;
			}
			if(!pilot.isMoving()){
				pilot.stop();
				break;
			}
		}
	}

	private void entferneVonWand() {
				
		LCD.drawString("entferneVonWand", 0, 2);
		
		pilot.stop();
		pilot.travel(rückfahrt, false);
		
	}
}

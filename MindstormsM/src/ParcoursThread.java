import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class ParcoursThread extends Thread {
	
	int rückfahrt;
	int bodenEntfernungIST;
	int bodenEntfernungMAX;
	NXTRegulatedMotor linkerMotor; 
	NXTRegulatedMotor rechterMotor; 
	TouchSensor linkerTaster;
	TouchSensor rechterTaster;
	UltrasonicSensor ultraSchall;
	int NormGeschwindigkeitRechts;
	int NormGeschwindigkeitLinks;
	private boolean naheWand;
	private int bodenEntfernungMIN;
	private int time;
	
	public ParcoursThread(){
		time = 0;
		rückfahrt = 200;
		bodenEntfernungMAX = 20;
		bodenEntfernungMIN = 16;
		NormGeschwindigkeitRechts = 595;
		NormGeschwindigkeitLinks = 600;
		linkerMotor = new NXTRegulatedMotor(MotorPort.A); 
		rechterMotor = new NXTRegulatedMotor(MotorPort.B);
		linkerTaster = new TouchSensor(SensorPort.S2);
		rechterTaster = new TouchSensor(SensorPort.S4);
		ultraSchall = new UltrasonicSensor(SensorPort.S1);
		ultraSchall.continuous();
		bodenEntfernungIST = ultraSchall.getDistance();
		linkerMotor.setSpeed(NormGeschwindigkeitLinks);
		rechterMotor.setSpeed(NormGeschwindigkeitRechts);
		naheWand = false;
		linkerMotor.forward();
		rechterMotor.forward();
	}
	
	public void run(){
		
		while(true){
					
			LCD.drawString("US: "+ultraSchall.getDistance()+" ", 0, 3);
			
			//Fall1
			if(linkerTaster.isPressed() || rechterTaster.isPressed()){
				linkerMotor.stop();
				rechterMotor.stop();
				entferneVonWand();
				dreheInPosition();
				naheWand = true;
			}
			
			//Fall2
			if(naheWand){	
				folgeWand();
			}
			
			//Fall3
			if((linkerTaster.isPressed() && rechterTaster.isPressed()) && (ultraSchall.getDistance() < bodenEntfernungMAX)){
				entferneSchrägVonWand();
				fahreVorwärts();
				naheWand = false;
			}
		}
	}

	private void fahreVorwärts() {
		
		LCD.drawString("fahreVorwärts", 0, 5);

		
		linkerMotor.setSpeed(NormGeschwindigkeitLinks);
		rechterMotor.setSpeed(NormGeschwindigkeitRechts);
		linkerMotor.forward();
		rechterMotor.forward();
	}

	private void entferneSchrägVonWand() {
		
		LCD.drawString("entferneSchrägVonWand", 0, 5);

		
		linkerMotor.setSpeed(50);
		rechterMotor.setSpeed(300);
		linkerMotor.backward();
		rechterMotor.backward();
		
		try {
			Thread.sleep(rückfahrt*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		linkerMotor.setSpeed(300);
		rechterMotor.setSpeed(50);
		linkerMotor.backward();
		rechterMotor.backward();
		
		try {
			Thread.sleep(rückfahrt*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		linkerMotor.stop();
		rechterMotor.stop();
		
	}

	private void folgeWand() {
		
		LCD.drawString("folgeWand", 0, 5);

		
			if((ultraSchall.getDistance() < bodenEntfernungMAX) && (ultraSchall.getDistance() > bodenEntfernungMIN)){
				
				linkerMotor.setSpeed(600);
				rechterMotor.setSpeed(500);
				linkerMotor.forward();
				rechterMotor.forward();
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				
			if(ultraSchall.getDistance() > bodenEntfernungMAX){
				
					linkerMotor.setSpeed(600);
					rechterMotor.setSpeed(150);
					linkerMotor.forward();
					rechterMotor.forward();
					
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			
			if(ultraSchall.getDistance() < bodenEntfernungMIN){

				
				LCD.drawString("zuklein", 0, 5);

				linkerMotor.setSpeed(400);
				rechterMotor.setSpeed(650);
				linkerMotor.forward();
				rechterMotor.forward();
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

	private void dreheInPosition() {
		
		LCD.drawString("dreheInPosition", 0, 5);

		
		linkerMotor.setSpeed(300);
		rechterMotor.setSpeed(300);
		
		linkerMotor.backward();
		rechterMotor.forward();
		
		while(true){

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			if((ultraSchall.getDistance() < bodenEntfernungMAX)){	
				
				try {
					Thread.sleep(400);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				linkerMotor.forward();
				rechterMotor.forward();
				
				break;
			}
		}
	}

	private void entferneVonWand() {
		
		LCD.drawString("entferneVonWand", 0, 5);
		
		linkerMotor.setSpeed(NormGeschwindigkeitLinks);
		rechterMotor.setSpeed(NormGeschwindigkeitRechts);
		linkerMotor.backward();
		rechterMotor.backward();
		
		try {
			Thread.sleep(rückfahrt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		linkerMotor.stop();
		rechterMotor.stop();
		
	}
}

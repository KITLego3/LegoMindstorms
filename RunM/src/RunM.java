import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.*;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.robotics.navigation.DifferentialPilot;

public class RunM {

	private static boolean running = true;
	public static DataExchange DE;
	private static DifferentialPilot pilot;
	private static RotateScanThread rotator;
	private static ScanningThread scanner;

	public static void init() {
		DE = new DataExchange();
		pilot = new DifferentialPilot(DATA.wheelD, DATA.trackWidth, Motor.A, Motor.B);
		pilot.setRotateSpeed(DATA.rotateSpeed);
		pilot.setTravelSpeed(DATA.travelSpeed);
		rotator = new RotateScanThread(DE, pilot);
		scanner = new ScanningThread(DE, pilot);
		
		DATA.ls.setFloodlight(true);
		
		LCD.clear();
		
	}
	
	public static void dealWithTurnTable() {
		TurntableController ttc = new TurntableController();
		while (!ttc.connectionToTurntableSuccessful()) {
			;
		}
		
		ttc.turnClockwise(90);

		// Wenn ihr fertig seid und raus fahrt
		ttc.disconnectFromTurntable();

		System.out.println("Turntable turned");
		Button.waitForAnyPress();
		LCD.clear();
	
	}
	
	
	public static void dodaturntable() {
		RemoteDevice btrd = Bluetooth.getKnownDevice("TurnTable");
        if (btrd != null) {
            BTConnection connection = Bluetooth.connect(btrd);
            try {
                DataOutputStream dataOutputStream =	connection.openDataOutputStream();
                DataInputStream dataInputStream = connection.openDataInputStream();

                if (dataInputStream.readInt() == DATA.LETSGO) {
                    System.out.println("Got letsgo command");
                    pilot.travel(100);

                    dataOutputStream.writeInt(DATA.TURN);
                    dataOutputStream.flush();
                    System.out.println("Sent turn command");
                    if (dataInputStream.readInt() == DATA.DONE) {
                        System.out.println("Got done command");
                        pilot.travel(-100);
                        dataOutputStream.writeInt(DATA.CYA);
                        dataOutputStream.flush();
                    }
                }
            } catch (IOException e) {
                System.out.println("IOExc");
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
	}
	
	public static void dodalift() {	}
	
	
	
	public static void main(String[] args) {
		
		init();
		
		LCD.drawString("Press any key " , 0, 0);

		Button.waitForAnyPress();
		
		LCD.clear();
		
		rotator.start();
		scanner.start();
		
		BarcodeReader b = new BarcodeReader(DE);
		SensorPort.S3.addSensorPortListener(b);
		
	//	pilot.travel(500);
		
		while (running) {	
			if (Button.ESCAPE.isDown()) { running = false; } 
			if (DATA.ts.isPressed()) {running = false; }
			if (DE.getChallengeEnded()) {running = false; LCD.drawString("CHALLANGE ENDED!" , 0, 1);} 
		}
		
		Motor.C.rotateTo(0);
		
		System.exit(0);
	}

}

import lejos.nxt.*;


public class DATA {
	
	//Sensors
	public static final UltrasonicSensor ss = new UltrasonicSensor(SensorPort.S1);
	public static final LightSensor ls = new LightSensor(SensorPort.S3);
	public static final TouchSensor ts = new TouchSensor(SensorPort.S2);
	
	//pilot
	public static final int wheelD = 34;
	public static final int trackWidth = 135;
	public static final int rotateSpeed = 100;
	public static final int travelSpeed = 100;
	
	//LineFollowing
	public static final int silverTres = 40;
	public static final int ARC = 100;
	public static final int historyStep = 1;
	public static final int travelDist = 30;
	
	//Barcode Reader
	public static final int barcodeRead = 8*historyStep;
	public static final int distToBarcode = 350;
	public static final int barcodeTres = 30;
	public static final int switchesTres = 6;
	
	//TurnTable
	public static final String TURNTABLE_BRICK_NAME = "TurnTable";
	public static final int COMMAND_CLOSE_CONNECTION = 1337;
	public static final int LETSGO = 1;
	public static final int TURN = 2;
	public static final int DONE = 3;
	public static final int CYA = 4;
	
}

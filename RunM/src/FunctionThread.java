import lejos.robotics.navigation.DifferentialPilot;


public abstract class FunctionThread extends Thread {

	private DataExchange DE;
	private DifferentialPilot pilot;
	private boolean running = true;
	
	
	public FunctionThread(DataExchange d, DifferentialPilot p) { DE = d; pilot = p; }
	
	public abstract void run();

	public DataExchange getDE() { return DE; }
	public boolean isRunning() { return running; }
	public void setRunning(boolean running) { this.running = running; }
	public DifferentialPilot getPilot() { return pilot; }
	public void setPilot(DifferentialPilot pilot) { this.pilot = pilot;}
	
}

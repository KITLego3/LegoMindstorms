import java.util.ArrayList;
import java.util.List;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;


public class Task { 

	private List<FunctionThread> threads = new ArrayList<FunctionThread>();;

	public void add(FunctionThread t) { threads.add(t); }
	public void start() { for (FunctionThread t: threads) { t.start(); } }
	public void stop() { for(FunctionThread t: threads) {t.setRunning(false); } }
}

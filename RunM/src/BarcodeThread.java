
public class BarcodeThread extends Thread { 
	
	DataExchange DE;
	
	public BarcodeThread(DataExchange d) {
		DE = d;
	}
	
	public void run() {
		DATA.ls.getLightValue();
	}

}

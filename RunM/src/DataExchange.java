
public class DataExchange {

	private int history;
	private boolean readBarcode = false;
	private boolean challengeEnded = false;
	private boolean barcodeDetected = false;
	private boolean doBarcodeScan = false;
	
	
	public void reset() { history = 0;; }
	public void inc () { history++; }
	public int getHistory() { return history; }
	public void setHistory(int history) { this.history = history; }
	public boolean getReadBarcode() { return readBarcode; }
	public void setReadBarcode(boolean readBarcode) { this.readBarcode = readBarcode; }
	public boolean getChallengeEnded() { return challengeEnded; }
	public void setChallengeEnded(boolean challengeEnded) { this.challengeEnded = challengeEnded;}
	public boolean getBarcodeDetected() { return barcodeDetected; }
	public void setBarcodeDetected(boolean barcodeDetected) { this.barcodeDetected = barcodeDetected; }
	public boolean getDoBarcodeScan() { return doBarcodeScan; }
	public void setDoBarcodeScan(boolean doBarcodeScan) { this.doBarcodeScan = doBarcodeScan; }
	
}

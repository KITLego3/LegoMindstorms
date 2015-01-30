import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class TurntableController {
	
	private RemoteDevice remoteDevice;
	private BTConnection connection;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	
	
	public boolean connectionToTurntableSuccessful() {
		String turntableBrickName = DATA.TURNTABLE_BRICK_NAME;
		
		remoteDevice = Bluetooth.getKnownDevice(turntableBrickName);
		if (remoteDevice == null) {
			System.out.println("Device not found.");
			return false;
		}		

		connection = Bluetooth.connect(remoteDevice);
		if (connection == null) {
			System.out.println("Connection failed.");
			return false;
		}

		System.out.println("Connected.");

		inputStream = connection.openDataInputStream();
		outputStream = connection.openDataOutputStream();

		return (inputStream != null && outputStream != null);
	}
	
	
	public boolean disconnectFromTurntable() {
		boolean successful = sendCommand(DATA.COMMAND_CLOSE_CONNECTION);
		if (successful)
			connection.close();
		return successful;
	}


	public boolean turnClockwise(int angle) {
		return sendCommand(angle);
	}
	
	private boolean sendCommand(int command) {
		try {
			outputStream.writeInt(command);
			outputStream.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}

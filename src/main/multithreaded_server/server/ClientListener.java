package multithreaded_server.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ClientListener
 * <p>
 * This class is responsible for receiving data from a client.
 * 
 * @since 0.2.0
 * @version 0.2.0
 * @author Peter Voigt
 *
 */
class ClientListener implements Runnable {

	private Socket client;
	private int clientID;
	private boolean isActive = true;
	private BasicServer server;

	public ClientListener(Socket client, int clientID, BasicServer server) {
		this.client = client;
		this.clientID = clientID;
		this.server = server;
	}

	public void setActive(boolean b) {
		isActive = b;
	}

	public void setClientId(int clientID) {
		this.clientID = clientID;
	}

	@Override
	public void run() {
		while (server.isRunning == true && isActive == true) {
			try {
				DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
				int dataLength = dataInputStream.readInt();
				byte[] data = new byte[dataLength];
				dataInputStream.readFully(data);
				if (dataLength > 0) {
					server.clientMessage(clientID, server.clients.get(clientID).packetHandler.decodePacket(data));
				}
			} catch (IOException e) {
				server.removeClient(clientID);
				e.printStackTrace();
			}
		}
	}

}

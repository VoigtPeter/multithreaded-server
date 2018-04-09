package multithreaded_server.server;

import java.net.Socket;

import multithreaded_server.packet_handler.PacketHandler;

/**
 * ClientObject
 * <p>
 * This class represents a client object.
 * 
 * @since 0.1.0
 * @version 0.2.0
 * @author Peter Voigt
 *
 */
class ClientObject {

	private Socket client;
	private int clientID;
	private ClientListener clientListener;
	private boolean finished = false;
	public PacketHandler packetHandler;

	/**
	 * 
	 * @param client
	 * @param clientID
	 * @param server
	 */
	public ClientObject(Socket client, int clientID, BasicServer server) {
		this.client = client;
		this.clientID = clientID;
		packetHandler = new PacketHandler();
		clientListener = new ClientListener(this.client, this.clientID, server);
		Thread t = new Thread(clientListener);
		t.start();
	}

	public void stopClient() {
		clientListener.setActive(false);
	}

	/**
	 * 
	 * @return The client socket
	 */
	public Socket getClient() {
		return client;
	}

	/**
	 * 
	 * @return The client ID
	 */
	public int getClientID() {
		return clientID;
	}

	/**
	 * 
	 * @param clientID
	 */
	public void setClientID(int clientID) {
		this.clientID = clientID;
		clientListener.setClientId(clientID);
	}

	/**
	 * 
	 * @param finished
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * 
	 * @return The finish state of the client
	 */
	public boolean isFinished() {
		return finished;
	}

}

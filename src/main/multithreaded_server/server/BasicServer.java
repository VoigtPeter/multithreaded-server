package multithreaded_server.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import multithreaded_server.packet_handler.PacketElement;


/**
 * BasicServer
 * <p>
 * This class implements a server object.
 * 
 * @since 0.1.0
 * @version 0.3.1
 * @author Peter Voigt
 *
 */
public abstract class BasicServer {

	public boolean isRunning = false;
	public List<ClientObject> clients;
	public int maxClients = 5;
	public int port = 7070;
	private ClientConnectionListener clientConnectionListener;

	/**
	 * 
	 * @param clientID
	 * @param elements
	 */
	public abstract void messageFromClient(int clientID, PacketElement[] elements);

	/**
	 * 
	 * @param clientID
	 */
	public abstract void clientConnected(int clientID);

	/**
	 * This method starts the server
	 * 
	 * @param port
	 *            The server port
	 */
	public void startServer(int port) {
		this.port = port;
		isRunning = true;
		clients = new ArrayList<ClientObject>();
		clientConnectionListener = new ClientConnectionListener(this);
		Thread clientConnectionListenerThread = new Thread(clientConnectionListener);
		clientConnectionListenerThread.start();
	}

	/**
	 * This method stops the server
	 */
	public void stopServer() {
		if (isRunning == true) {
			clientConnectionListener.stopClientConnectionListener();
			if(clientConnectionListener.serverSocket != null) {
				try {
					clientConnectionListener.serverSocket.close();
					clientConnectionListener.serverSocket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			clientConnectionListener = null;
			isRunning = false;
		}
	}

	/**
	 * This method sends a packet to a specific client
	 * 
	 * @param clientID
	 *            The client ID
	 * @param elements
	 *            The elements
	 */
	public void sendToClient(int clientID, PacketElement[] elements) {
		if (clientID < clients.size()) {
			DataOutputStream dataOutputStream;
			try {
				dataOutputStream = new DataOutputStream(clients.get(clientID).getClient().getOutputStream());
				for (int i = 0; i < elements.length; i++) {
					clients.get(clientID).packetHandler.addElement(elements[i]);
				}
				byte[] data = clients.get(clientID).packetHandler.createPacket();
				dataOutputStream.writeInt(data.length);
				dataOutputStream.write(data);
				dataOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void newClient(int clientID) {
		PacketElement[] e = { new PacketElement(("[set_id]=" + clientID).getBytes(), PacketElement.SERVER_MESSAGE) };
		sendToClient(clientID, e);
		
		clientConnected(clientID);
	}

	/**
	 * This method sends a packet to all connected clients
	 * 
	 * @param elements
	 *            The elements
	 */
	public void sendToAllClients(PacketElement[] elements) {
		for (int i = 0; i < clients.size(); i++) {
			sendToClient(i, elements);
		}
	}

	/**
	 * This method receives packets from the clients connected to the server
	 * 
	 * @param clientID
	 *            The client ID
	 * @param elements
	 *            The packets
	 */
	protected void clientMessage(int clientID, PacketElement[] elements) {
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].getType() == PacketElement.SERVER_MESSAGE) {
				if (new String(elements[i].getData()).equals("[disconnect]")) {
					removeClient(clientID);
				} else if (new String(elements[i].getData()).equals("[client_id]")) {
					PacketElement[] e = { new PacketElement(("[set_id]=" + clientID).getBytes(), PacketElement.SERVER_MESSAGE) };
					sendToClient(clientID, e);
				}
			}
		}
		messageFromClient(clientID, elements);
		System.out.println("we got some shit!! ayy");
	}

	/**
	 * This method removes a client with a given ID from the server
	 * 
	 * @param clientID
	 *            The client ID
	 */
	public void removeClient(int clientID) {
		clients.get(clientID).stopClient();
		clients.remove(clientID);
		for (int i = clientID; i < clients.size(); i++) {
			clients.get(i).setClientID(clients.get(i).getClientID() - 1);
			PacketElement[] element = { new PacketElement(("[set_id]=" + clients.get(i).getClientID()).getBytes(), PacketElement.SERVER_MESSAGE) };
			sendToClient(clients.get(i).getClientID(), element);
		}
	}

	/**
	 * This method sets the maximum amount of clients that can be connected to the server at the same time
	 * 
	 * @param maxClients
	 */
	public void setMaxClients(int maxClients) {
		this.maxClients = maxClients;
	}

}

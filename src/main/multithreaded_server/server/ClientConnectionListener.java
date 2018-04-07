package multithreaded_server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ClientConnectionListener
 * <p>
 * This class accepts clients.
 * 
 * @since 0.1.0
 * @version 0.1.0
 * @author Peter Voigt
 *
 */
class ClientConnectionListener implements Runnable {

	private BasicServer server;
	private boolean isRunning = true;
	private ServerSocket serverSocket;

	public ClientConnectionListener(BasicServer server) {
		this.server = server;
	}

	public void stopClientConnectionListener() {
		isRunning = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverSocket = null;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(this.server.port);
			while (this.server.isRunning == true && this.isRunning == true) {
				if (this.server.clients.size() < this.server.maxClients) {
					Socket client = serverSocket.accept();
					this.server.clients.add(new ClientObject(client, this.server.clients.size(), this.server));
					this.server.newClient(this.server.clients.size() - 1);
				}
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

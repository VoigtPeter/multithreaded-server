package multithreaded_server.client;

import java.io.DataInputStream;
import java.io.IOException;

import multithreaded_server.packet_handler.PacketElement;

/**
 * ServerListener
 * <p>
 * This class is responsible for receiving data from the server.
 * 
 * @since 0.2.0
 * @version 0.2.0
 * @author Peter Voigt
 *
 */
class ServerListener implements Runnable {

	private boolean isActive = true;
	private BasicClient client;

	public ServerListener(BasicClient client) {
		this.client = client;
	}

	public void stopServerListener() {
		isActive = false;
	}

	@Override
	public void run() {
		while (client.isActive == true && isActive == true) {
			try {
				DataInputStream dataInputStream = new DataInputStream(client.getClientSocket().getInputStream());
				byte[] data = new byte[dataInputStream.readInt()];
				dataInputStream.readFully(data);
				PacketElement[] elements = client.packetHandler.decodePacket(data);
				for (int i = 0; i < elements.length; i++) {
					if (elements[i].getType() == PacketElement.SERVER_MESSAGE) {
						String s = new String(elements[i].getData());
						if (s.contains("[set_id]")) {
							client.currentID = Integer.parseInt(s.split("=")[1]);
						}
					}
				}
				client.messageFromServer(elements);
			} catch (IOException e) {
				client.stopClient();
				e.printStackTrace();
			}
		}
	}

}

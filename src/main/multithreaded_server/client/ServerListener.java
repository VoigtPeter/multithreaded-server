package multithreaded_server.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import multithreaded_server.packet_handler.PacketElement;

/**
 * ServerListener
 * <p>
 * This class is responsible for receiving data from the server.
 * 
 * @since 0.1.0
 * @version 0.3.2
 * @author Peter Voigt
 *
 */
class ServerListener implements Runnable {

	private boolean isActive = true;
	private BasicClient client;
	private DataInputStream dataInputStream;

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
				dataInputStream = new DataInputStream(client.getClientSocket().getInputStream());
				byte[] data = new byte[dataInputStream.readInt()];
				dataInputStream.readFully(data);
				PacketElement[] elements = client.packetHandler.decodePacket(data);
				List<PacketElement> output_elements = new ArrayList<PacketElement>();
				for (int i = 0; i < elements.length; i++) {
					if (elements[i].getType() == PacketElement.SERVER_MESSAGE) {
						String s = new String(elements[i].getData());
						if (s.contains("[set_id]")) {
							client.currentID = Integer.parseInt(s.split("=")[1]);
						}
					} else {
						output_elements.add(elements[i]);
					}
				}
				PacketElement[] return_elements = new PacketElement[output_elements.size()];
				for (int i = 0; i < output_elements.size(); i++) {
					return_elements[i] = output_elements.get(i);
				}
				client.messageFromServer(return_elements);
			} catch (IOException e) {
				if(client.isActive == true) {
					client.stopClient();
				}
				//e.printStackTrace();
			}
		}
	}

}

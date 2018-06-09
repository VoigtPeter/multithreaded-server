package multithreaded_server.test_objects;

import java.util.ArrayList;
import java.util.List;

import multithreaded_server.client.BasicClient;
import multithreaded_server.packet_handler.PacketElement;

/**
 * TestClient
 * 
 * @since 0.3.0
 * @version 0.3.1
 * @author Peter Voigt
 *
 */
public class TestClient extends BasicClient {

	public List<PacketElement[]> elementHistoryStack;
	
	public TestClient() {
		elementHistoryStack = new ArrayList<PacketElement[]>();
	}
	
	@Override
	public void messageFromServer(PacketElement[] elements) {
		System.out.println("a client got some fucking thing mate");
		elementHistoryStack.add(elements);
	}

	@Override
	public void disconnectedFromServer() {
		
	}

	@Override
	public void unableToConnect() {
		
	}

}

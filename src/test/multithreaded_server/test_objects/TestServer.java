package multithreaded_server.test_objects;

import java.util.ArrayList;
import java.util.List;

import multithreaded_server.packet_handler.PacketElement;
import multithreaded_server.server.BasicServer;

/**
 * TestServer
 * 
 * @since 0.3.0
 * @version 0.3.2
 * @author Peter Voigt
 *
 */
public class TestServer extends BasicServer {

	public List<PacketElement[]> elementHistoryStack;
	public List<Integer> clientHistoryStack;

	public TestServer() {
		elementHistoryStack = new ArrayList<PacketElement[]>();
		clientHistoryStack = new ArrayList<Integer>();
	}

	@Override
	public void messageFromClient(int clientID, PacketElement[] elements) {
		elementHistoryStack.add(elements);
		clientHistoryStack.add(clientID);
	}

	@Override
	public void clientConnected(int clientID) {

	}

}

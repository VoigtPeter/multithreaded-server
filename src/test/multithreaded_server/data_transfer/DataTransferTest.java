package multithreaded_server.data_transfer;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import multithreaded_server.packet_handler.PacketElement;
import multithreaded_server.test_objects.TestClient;
import multithreaded_server.test_objects.TestServer;

public class DataTransferTest {
	
	@Test
	public void testClientServerTransfer() {
		System.out.println("--- testClientServerTransfer() ---");
		
		byte[] testData = getRandomData(100000);
		
		TestServer server = new TestServer();
		server.startServer(62140);
		
		TestClient client = new TestClient();
		client.connectToServer(server.port, "localhost");
		
		client.sendToServer(new PacketElement[] {new PacketElement(testData, PacketElement.OTHER)});
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(server.elementHistoryStack.size() > 0) {
			byte[] result = server.elementHistoryStack.get(0)[0].getData();
			Assert.assertArrayEquals("Random generated test data does not match received data!", testData, result);
		} else {
			client.stopClient();
			server.stopServer();
			client = null;
			server = null;
			Assert.fail("No data received!");
		}
		
		testData = null;
		
		client.stopClient();
		server.stopServer();
		client = null;
		server = null;
	}
	
	@Test
	public void testServerClientTransfer() {
		System.out.println("--- testServerClientTransfer() ---");
		
		byte[] testData = getRandomData(100000);
		
		TestServer server = new TestServer();
		server.startServer(62145);
		
		TestClient client = new TestClient();
		client.connectToServer(server.port, "localhost");
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(server.clients.size() > 0) {
			server.sendToClient(0, new PacketElement[] {new PacketElement(testData, PacketElement.OTHER)});
		} else {
			client.stopClient();
			server.stopServer();
			client = null;
			server = null;
			Assert.fail("No client available!");
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(client.elementHistoryStack.size() > 0) {
			byte[] result = client.elementHistoryStack.get(client.elementHistoryStack.size()-1)[0].getData();
			Assert.assertArrayEquals("Random generated test data does not match received data!", testData, result);
		} else {
			client.stopClient();
			server.stopServer();
			client = null;
			server = null;
			Assert.fail("No data received!");
		}
		
		testData = null;
		
		client.stopClient();
		server.stopServer();
		client = null;
		server = null;
	}
	
	@Test
	public void testDataTransfer() {
		System.out.println("--- testDataTransfer() ---");
		
		TestServer server = new TestServer();
		server.startServer(62160);
		server.setMaxClients(10);
		
		TestClient[] client = new TestClient[10];
		for(int i=0; i<client.length; i++) {
			client[i] = new TestClient();
			client[i].connectToServer(server.port, "localhost");
		}
		
		for(int i=0; i<50; i++) {
			byte[] testData = getRandomData(100000);
			
			for(int h=0; h<client.length; h++) {
				client[h].sendToServer(new PacketElement[] {new PacketElement(testData, PacketElement.OTHER)});
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(server.elementHistoryStack.size() >= client.length) {
				for(int j=0; j<client.length; j++) {
					byte[] result = server.elementHistoryStack.get(j)[0].getData();
					Assert.assertArrayEquals("Random generated test data does not match received data!", testData, result);
				}
				server.elementHistoryStack.clear();
			} else {
				for(int j=0; j<client.length; j++) {
					client[i].stopClient();
					client[i] = null;
				}
				server.stopServer();
				server = null;
				Assert.fail("Packets lost!");
			}
			
			testData = null;
		}
		
		for(int i=0; i<client.length; i++) {
			client[i].stopClient();
			client[i] = null;
		}
		
		server.stopServer();
		server = null;
	}
	
	public byte[] getRandomData(int maxBytes) {
		byte[] randomData = new byte[(2 + new Random().nextInt(maxBytes - 2))];
		for(int i=0; i<randomData.length; i++) {
			randomData[i] = (byte) (new Random().nextInt(256) - 128);
		}
		return randomData;
	}

}

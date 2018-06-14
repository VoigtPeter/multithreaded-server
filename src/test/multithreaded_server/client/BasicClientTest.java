package multithreaded_server.client;

import org.junit.Assert;
import org.junit.Test;

import multithreaded_server.test_objects.TestClient;
import multithreaded_server.test_objects.TestServer;

/**
 * BasicClientTest
 * 
 * @since 0.3.0
 * @version 0.3.2
 * @author Peter Voigt
 *
 */
public class BasicClientTest {

	@Test
	public void testConnection() {
		TestServer server = new TestServer();
		server.startServer(62150);
		
		TestClient[] client = new TestClient[4];
		for(int i=0; i<client.length; i++) {
			client[i] = new TestClient();
			Assert.assertTrue(client[i].connectToServer(server.port, "localhost"));
		}
		
		for(int i=0; i<client.length; i++) {
			client[i].stopClient();
		}
		
		server.stopServer();
		server = null;
	}
	
	@Test
	public void testIDs() {
		TestServer server = new TestServer();
		server.startServer(62155);
		
		TestClient[] client = new TestClient[4];
		for(int i=0; i<client.length; i++) {
			client[i] = new TestClient();
			Assert.assertTrue(client[i].connectToServer(server.port, "localhost"));
		}
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assert.assertSame(0, client[0].currentID);
		Assert.assertSame(1, client[1].currentID);
		Assert.assertSame(2, client[2].currentID);
		Assert.assertSame(3, client[3].currentID);
		
		client[1].stopClient();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assert.assertSame(0, client[0].currentID);
		Assert.assertSame(1, client[2].currentID);
		Assert.assertSame(2, client[3].currentID);
		
		client[0].stopClient();
		client[2].stopClient();
		client[3].stopClient();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		server.stopServer();
		server = null;
	}
	
	
	
}

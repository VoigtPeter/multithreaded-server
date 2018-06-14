package multithreaded_server.packet_handler_test;

import org.junit.Assert;
import org.junit.Test;

import multithreaded_server.packet_handler.PacketElement;

/**
 * PacketElementTest
 * 
 * @since 0.2.0
 * @version 0.3.2
 * @author Peter Voigt
 *
 */
public class PacketElementTest {
	
	@Test
	public void testPacketData() {
		byte[] testData = "test data".getBytes();
		
		PacketElement packetElement = new PacketElement();
		packetElement.setData(testData);
		Assert.assertArrayEquals(testData, packetElement.getData());
		
		packetElement = new PacketElement(testData, 0);
		Assert.assertArrayEquals(testData, packetElement.getData());
	}
	
	@Test
	public void testPacketType() {
		int testType = PacketElement.OTHER;
		
		PacketElement packetElement = new PacketElement();
		packetElement.setType(testType);
		Assert.assertSame(testType, packetElement.getType());
		
		packetElement = new PacketElement(null, testType);
		Assert.assertSame(testType, packetElement.getType());
	}
	
	@Test
	public void testPacketDescription() {
		String testDescription = "test description";
		
		PacketElement packetElement = new PacketElement();
		packetElement.setDescription(testDescription);
		Assert.assertTrue(testDescription.equals(packetElement.getDescription()));
	}
	
}

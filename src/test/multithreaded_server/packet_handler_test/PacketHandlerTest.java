package multithreaded_server.packet_handler_test;

import org.junit.Assert;
import org.junit.Test;

import multithreaded_server.packet_handler.PacketElement;
import multithreaded_server.packet_handler.PacketHandler;

/**
 * PacketHandlerTest
 * 
 * @since 0.2.0
 * @version 0.3.1
 * @author Peter Voigt
 *
 */
public class PacketHandlerTest {

	@Test
	public void testCreateDecodePacket() {
		PacketElement packetElement1 = new PacketElement("test data".getBytes(), PacketElement.STRING_TEXT);
		PacketElement packetElement2 = new PacketElement(new byte[] {(byte) 1, (byte) 12, (byte) 3}, PacketElement.OTHER);
		PacketElement packetElement3 = new PacketElement("test data 2".getBytes(), PacketElement.SERVER_MESSAGE);
		packetElement1.setDescription("this is a test description");
		packetElement2.setDescription("this is another test description");
		packetElement3.setDescription("this is a different test description");
		
		PacketHandler packetHandler = new PacketHandler();
		packetHandler.addElement(packetElement1);
		packetHandler.addElement(packetElement2);
		packetHandler.addElement(packetElement3);
		
		byte[] packetData = packetHandler.createPacket();
		PacketElement[] packetElements = packetHandler.decodePacket(packetData);
		
		Assert.assertArrayEquals(packetElement1.getData(), packetElements[0].getData());
		Assert.assertArrayEquals(packetElement2.getData(), packetElements[1].getData());
		Assert.assertArrayEquals(packetElement3.getData(), packetElements[2].getData());
		
		Assert.assertSame(packetElement1.getType(), packetElements[0].getType());
		Assert.assertSame(packetElement2.getType(), packetElements[1].getType());
		Assert.assertSame(packetElement3.getType(), packetElements[2].getType());
		
		Assert.assertTrue(packetElement1.getDescription().equals(packetElements[0].getDescription()));
		Assert.assertTrue(packetElement2.getDescription().equals(packetElements[1].getDescription()));
		Assert.assertTrue(packetElement3.getDescription().equals(packetElements[2].getDescription()));
	}
	
}

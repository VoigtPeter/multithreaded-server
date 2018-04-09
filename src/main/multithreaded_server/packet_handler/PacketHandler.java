package multithreaded_server.packet_handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import multithreaded_server.util.*;
 
/**
 * PacketHandler
 * <p>
 * This class implements methods for storing data inside packet objects.
 * 
 * @since 0.2.0
 * @version 0.2.0
 * @author Peter Voigt
 * 
 */
public class PacketHandler {

	private List<PacketElement> elements;
	private int maxSize = 100000;

	private String END_INFORMATION_1 = "[end-information-1]";
	private String START_POS_ELEMENT = "[start-pos-element]";
	private String END_POS_ELEMENT = "[end-pos-element]";
	private String ELEMENT_DATA_TYPE = "[element-data-type]";
	private String ELEMENT_DATA_DESCRIPTION = "[element-data-description]";

	public PacketHandler() {
		elements = new ArrayList<PacketElement>();
	}

	/**
	 * This method creates a packet consisting out of elements
	 * 
	 * @return The packed data
	 */
	public byte[] createPacket() {
		if (elements.size() > 0) {
			List<PacketElement> currentElements = new ArrayList<PacketElement>();
			List<byte[]> data = new ArrayList<byte[]>();
			int currentSize = 0;

			for (int i = 0; i < elements.size(); i++) {
				if (currentSize + elements.get(i).getData().length <= maxSize) {
					currentElements.add(elements.get(i));
				}
			}

			data.add(numberToByteArray(currentElements.size()));

			data.add(BinaryEncoder.encodeString(END_INFORMATION_1));

			int currentPos = 0;
			for (int i = 0; i < currentElements.size(); i++) {
				int startPos = currentPos;
				int endPos = currentPos + currentElements.get(i).getData().length;
				int type = currentElements.get(i).getType();
				String description = currentElements.get(i).getDescription();

				data.add(numberToByteArray(startPos));
				data.add(BinaryEncoder.encodeString(START_POS_ELEMENT));

				data.add(numberToByteArray(endPos));
				data.add(BinaryEncoder.encodeString(END_POS_ELEMENT));

				data.add(numberToByteArray(type));
				data.add(BinaryEncoder.encodeString(ELEMENT_DATA_TYPE));

				data.add(description.getBytes());
				data.add(BinaryEncoder.encodeString(ELEMENT_DATA_DESCRIPTION));

				currentPos = endPos;
			}

			for (int i = 0; i < currentElements.size(); i++) {
				data.add(currentElements.get(i).getData());
			}

			int totalSize = 0;
			for (int i = 0; i < data.size(); i++) {
				totalSize += data.get(i).length;
			}

			byte[] packet = new byte[totalSize + 1];
			int currentPos2 = 0;
			for (int i = 0; i < data.size(); i++) {
				System.arraycopy(data.get(i), 0, packet, currentPos2 + 1, data.get(i).length);
				currentPos2 += data.get(i).length;
			}

			for (int i = 0; i < currentElements.size(); i++) {
				elements.remove(currentElements.get(i));
			}

			//System.out.println("--> Packet succesfully created | Size: " + ((float) totalSize / 1000f) + "KB");

			return packet;
		}
		return null;
	}

	/**
	 * This method unpacks a packet and returns the included elements
	 * 
	 * @param packet
	 *            The packet
	 * @return The unpacked elements
	 */
	public PacketElement[] decodePacket(byte[] packet) {
		int totalElements = 0;
		int currentPos = 0;
		for (int i = 0; i < packet.length; i++) {
			if (BinaryDecoder.decodeString(Arrays.copyOfRange(packet, i, i + END_INFORMATION_1.length())).equals(END_INFORMATION_1)) {
				totalElements = byteArrayToNumber(Arrays.copyOfRange(packet, 0, i));
				currentPos = i + END_INFORMATION_1.length();
				break;
			}
		}
		PacketElement[] elements = new PacketElement[totalElements];
		int[] start = new int[totalElements];
		int[] end = new int[totalElements];
		int[] type = new int[totalElements];
		String[] description = new String[totalElements];

		for (int i = 0; i < totalElements; i++) {
			int startPos = 0;
			int endPos = 1;
			int dataType = 0;
			String dataDescription = "";
			for (int h = currentPos; h < packet.length; h++) {
				if (BinaryDecoder.decodeString(Arrays.copyOfRange(packet, h, h + START_POS_ELEMENT.length())).equals(START_POS_ELEMENT)) {
					startPos = byteArrayToNumber(Arrays.copyOfRange(packet, currentPos, h));
					currentPos = h + START_POS_ELEMENT.length();
					break;
				}
			}
			for (int h = currentPos; h < packet.length; h++) {
				if (BinaryDecoder.decodeString(Arrays.copyOfRange(packet, h, h + END_POS_ELEMENT.length())).equals(END_POS_ELEMENT)) {
					endPos = byteArrayToNumber(Arrays.copyOfRange(packet, currentPos, h));
					currentPos = h + END_POS_ELEMENT.length();
					break;
				}
			}
			for (int h = currentPos; h < packet.length; h++) {
				if (BinaryDecoder.decodeString(Arrays.copyOfRange(packet, h, h + ELEMENT_DATA_TYPE.length())).equals(ELEMENT_DATA_TYPE)) {
					dataType = byteArrayToNumber(Arrays.copyOfRange(packet, currentPos, h));
					currentPos = h + ELEMENT_DATA_TYPE.length();
					break;
				}
			}
			for (int h = currentPos; h < packet.length; h++) {
				if (BinaryDecoder.decodeString(Arrays.copyOfRange(packet, h, h + ELEMENT_DATA_DESCRIPTION.length())).equals(ELEMENT_DATA_DESCRIPTION)) {
					dataDescription = new String(Arrays.copyOfRange(packet, currentPos, h));
					currentPos = h + ELEMENT_DATA_DESCRIPTION.length();
					break;
				}
			}

			start[i] = startPos;
			end[i] = endPos;
			type[i] = dataType;
			description[i] = dataDescription;
		}

		for (int i = 0; i < elements.length; i++) {
			elements[i] = new PacketElement(Arrays.copyOfRange(packet, start[i] + currentPos, end[i] + currentPos),
					type[i]);
			elements[i].setDescription(description[i]);
		}

		return elements;
	}

	/**
	 * This method adds one element to the element list
	 * 
	 * @param element
	 */
	public void addElement(PacketElement element) {
		elements.add(element);
	}

	/**
	 * This method converts a number string into a byte array
	 * 
	 * @param number
	 *            The number as integer
	 * @return The byte array
	 */
	private byte[] numberToByteArray(int number) {
		String s = Integer.toString(number);
		return BinaryEncoder.encodeString(s);
	}

	/**
	 * This method converts a byte array into a number
	 * 
	 * @param bytes
	 *            The number string as byte array
	 * @return The number as integer
	 */
	private int byteArrayToNumber(byte[] bytes) {
		String s = BinaryDecoder.decodeString(bytes);
		return Integer.parseInt(s);
	}

	/**
	 * This method sets the maximum packet size in bytes
	 * 
	 * @param maxSize
	 *            The maximum packet size in bytes
	 */
	public void setMaxPacketSize(int maxSize) {
		this.maxSize = maxSize;
	}

}

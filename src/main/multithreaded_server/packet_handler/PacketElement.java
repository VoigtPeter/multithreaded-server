package multithreaded_server.packet_handler;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * PacketElement
 * <p>
 * This class implements a packet object for storing binary data.
 * 
 * @since 0.1.0
 * @version 0.3.2
 * @author Peter Voigt
 * 
 */
public class PacketElement {

	public static int SERVER_MESSAGE = 0;
	public static int STRING_TEXT = 1;
	public static int PNG_IMAGE = 2;
	public static int JPG_IMAGE = 3;
	public static int BMP_IMAGE = 4;
	public static int OTHER = 5;

	private byte[] data;
	private int type = 0;
	private String description = "no description";

	public PacketElement() {
	}

	public PacketElement(byte[] data, int type) {
		this.data = data;
		this.type = type;
	}

	/**
	 * This method returns the data
	 * 
	 * @return The data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * This method returns the data type
	 * 
	 * @return The data type
	 */
	public int getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * This method sets the data type
	 * 
	 * @param type
	 *            The data type
	 */
	public void setType(int type) {
		this.type = type;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * This method loads image data
	 * 
	 * @param imageFile
	 *            The image path
	 */
	public void loadImageData(File imageFile) {
		String imagePath = imageFile.getPath();
		String imageFormat = "";
		if (imagePath.toUpperCase().endsWith(".PNG")) {
			imageFormat = "PNG";
			type = PNG_IMAGE;
		} else if (imagePath.toUpperCase().endsWith(".JPG")) {
			imageFormat = "JPG";
			type = JPG_IMAGE;
		} else if (imagePath.toUpperCase().endsWith(".BMP")) {
			imageFormat = "BMP";
			type = BMP_IMAGE;
		} else {
			return;
		}

		try {
			BufferedImage image = ImageIO.read(new File(imagePath));
			setImageData(image, imageFormat);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method converts image data into a binary format
	 * 
	 * @param image
	 *            The image
	 * @param imageFormat
	 *            The image format (PNG, JPG, etc.)
	 */
	public void setImageData(BufferedImage image, String imageFormat) {
		try {
			if (imageFormat.equals("PNG")) {
				type = PNG_IMAGE;
			} else if (imageFormat.equals("JPG")) {
				type = JPG_IMAGE;
			} else if (imageFormat.equals("BMP")) {
				type = BMP_IMAGE;
			}
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ImageIO.write(image, imageFormat, byteStream);
			byteStream.flush();
			data = byteStream.toByteArray();
			byteStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

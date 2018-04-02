package multithreaded_server_client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * BinaryDecoder
 * <p>
 * This class implements a binary decoder.
 * 
 * @since 0.0.1
 * @version 0.0.1
 * @author Peter Voigt
 * 
 */
public class BinaryDecoder {

	/**
	 * This method decodes encoded strings
	 * 
	 * @param data
	 *            The encoded string
	 * @return The decoded string
	 */
	public static String decodeString(byte[] data) {
		String output = "";
		for (int i = 0; i < data.length; i++) {
			switch ((int) data[i]) {
				case 0: output += "0"; break;
				case 1: output += "1"; break;
				case 2: output += "2"; break;
				case 3: output += "3"; break;
				case 4: output += "4"; break;
				case 5: output += "5"; break;
				case 6: output += "6"; break;
				case 7: output += "7"; break;
				case 8: output += "8"; break;
				case 9: output += "9"; break;
				
				case 36: output += "a"; break;
				case 37: output += "b"; break;
				case 38: output += "c"; break;
				case 39: output += "d"; break;
				case 40: output += "e"; break;
				case 41: output += "f"; break;
				case 42: output += "g"; break;
				case 43: output += "h"; break;
				case 44: output += "i"; break;
				case 45: output += "j"; break;
				case 46: output += "k"; break;
				case 47: output += "l"; break;
				case 48: output += "m"; break;
				case 49: output += "n"; break;
				case 50: output += "o"; break;
				case 51: output += "p"; break;
				case 52: output += "q"; break;
				case 53: output += "r"; break;
				case 54: output += "s"; break;
				case 55: output += "t"; break;
				case 56: output += "u"; break;
				case 57: output += "v"; break;
				case 58: output += "w"; break;
				case 59: output += "x"; break;
				case 60: output += "y"; break;
				case 61: output += "z"; break;
				
				case 10: output += "A"; break;
				case 11: output += "B"; break;
				case 12: output += "C"; break;
				case 13: output += "D"; break;
				case 14: output += "E"; break;
				case 15: output += "F"; break;
				case 16: output += "G"; break;
				case 17: output += "H"; break;
				case 18: output += "I"; break;
				case 19: output += "J"; break;
				case 20: output += "K"; break;
				case 21: output += "L"; break;
				case 22: output += "M"; break;
				case 23: output += "N"; break;
				case 24: output += "O"; break;
				case 25: output += "P"; break;
				case 26: output += "Q"; break;
				case 27: output += "R"; break;
				case 28: output += "S"; break;
				case 29: output += "T"; break;
				case 30: output += "U"; break;
				case 31: output += "V"; break;
				case 32: output += "W"; break;
				case 33: output += "X"; break;
				case 34: output += "Y"; break;
				case 35: output += "Z"; break;
				
				case 62: output += "."; break;
				case 63: output += "-"; break;
				case 64: output += ":"; break;
				case 65: output += "+"; break;
				case 66: output += "="; break;
				case 67: output += "^"; break;
				case 68: output += "!"; break;
				case 69: output += "/"; break;
				case 70: output += "*"; break;
				case 71: output += "?"; break;
				case 72: output += "&"; break;
				case 73: output += "<"; break;
				case 74: output += ">"; break;
				case 75: output += "("; break;
				case 76: output += ")"; break;
				case 77: output += "["; break;
				case 78: output += "]"; break;
				case 79: output += "{"; break;
				case 80: output += "}"; break;
				case 81: output += "@"; break;
				case 82: output += "%"; break;
				case 83: output += "$"; break;
				case 84: output += "#"; break;
			}
		}
		return output;
	}
	
	/**
	 * This method loads binary data from a file
	 * 
	 * @param file
	 *            The file
	 * @return The binary data as byte array
	 */
	public static byte[] getBinaryDataFromFile(File file) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			byte[] output = new byte[objectInputStream.available()];
			objectInputStream.readFully(output);
			objectInputStream.close();
			
			return output;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

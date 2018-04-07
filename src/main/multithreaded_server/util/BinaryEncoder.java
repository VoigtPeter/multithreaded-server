package multithreaded_server.util;

/**
 * BinaryEncoder
 * <p>
 * This class implements a binary encoder.
 * 
 * @since 0.1.0
 * @version 0.1.0
 * @author Peter Voigt
 * 
 */
public class BinaryEncoder {

	/**
	 * This method encodes strings
	 * 
	 * @param s
	 *            The string
	 * @return The encoded string
	 */
	public static byte[] encodeString(String s) {
		byte[] bytes = new byte[s.length()];
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
				case 'a': bytes[i] = (byte)36; break;
				case 'b': bytes[i] = (byte)37; break;
				case 'c': bytes[i] = (byte)38; break;
				case 'd': bytes[i] = (byte)39; break;
				case 'e': bytes[i] = (byte)40; break;
				case 'f': bytes[i] = (byte)41; break;
				case 'g': bytes[i] = (byte)42; break;
				case 'h': bytes[i] = (byte)43; break;
				case 'i': bytes[i] = (byte)44; break;
				case 'j': bytes[i] = (byte)45; break;
				case 'k': bytes[i] = (byte)46; break;
				case 'l': bytes[i] = (byte)47; break;
				case 'm': bytes[i] = (byte)48; break;
				case 'n': bytes[i] = (byte)49; break;
				case 'o': bytes[i] = (byte)50; break;
				case 'p': bytes[i] = (byte)51; break;
				case 'q': bytes[i] = (byte)52; break;
				case 'r': bytes[i] = (byte)53; break;
				case 's': bytes[i] = (byte)54; break;
				case 't': bytes[i] = (byte)55; break;
				case 'u': bytes[i] = (byte)56; break;
				case 'v': bytes[i] = (byte)57; break;
				case 'w': bytes[i] = (byte)58; break;
				case 'x': bytes[i] = (byte)59; break;
				case 'y': bytes[i] = (byte)60; break;
				case 'z': bytes[i] = (byte)61; break;
				
				case 'A': bytes[i] = (byte)10; break;
				case 'B': bytes[i] = (byte)11; break;
				case 'C': bytes[i] = (byte)12; break;
				case 'D': bytes[i] = (byte)13; break;
				case 'E': bytes[i] = (byte)14; break;
				case 'F': bytes[i] = (byte)15; break;
				case 'G': bytes[i] = (byte)16; break;
				case 'H': bytes[i] = (byte)17; break;
				case 'I': bytes[i] = (byte)18; break;
				case 'J': bytes[i] = (byte)19; break;
				case 'K': bytes[i] = (byte)20; break;
				case 'L': bytes[i] = (byte)21; break;
				case 'M': bytes[i] = (byte)22; break;
				case 'N': bytes[i] = (byte)23; break;
				case 'O': bytes[i] = (byte)24; break;
				case 'P': bytes[i] = (byte)25; break;
				case 'Q': bytes[i] = (byte)26; break;
				case 'R': bytes[i] = (byte)27; break;
				case 'S': bytes[i] = (byte)28; break;
				case 'T': bytes[i] = (byte)29; break;
				case 'U': bytes[i] = (byte)30; break;
				case 'V': bytes[i] = (byte)31; break;
				case 'W': bytes[i] = (byte)32; break;
				case 'X': bytes[i] = (byte)33; break;
				case 'Y': bytes[i] = (byte)34; break;
				case 'Z': bytes[i] = (byte)35; break;
				
				case '0': bytes[i] = (byte)0; break;
				case '9': bytes[i] = (byte)9; break;
				case '8': bytes[i] = (byte)8; break;
				case '7': bytes[i] = (byte)7; break;
				case '6': bytes[i] = (byte)6; break;
				case '5': bytes[i] = (byte)5; break;
				case '4': bytes[i] = (byte)4; break;
				case '3': bytes[i] = (byte)3; break;
				case '2': bytes[i] = (byte)2; break;
				case '1': bytes[i] = (byte)1; break;
				
				case '.': bytes[i] = (byte)62; break;
				case '-': bytes[i] = (byte)63; break;
				case ':': bytes[i] = (byte)64; break;
				case '+': bytes[i] = (byte)65; break;
				case '=': bytes[i] = (byte)66; break;
				case '^': bytes[i] = (byte)67; break;
				case '!': bytes[i] = (byte)68; break;
				case '/': bytes[i] = (byte)69; break;
				case '*': bytes[i] = (byte)70; break;
				case '?': bytes[i] = (byte)71; break;
				case '&': bytes[i] = (byte)72; break;
				case '<': bytes[i] = (byte)73; break;
				case '>': bytes[i] = (byte)74; break;
				case '(': bytes[i] = (byte)75; break;
				case ')': bytes[i] = (byte)76; break;
				case '[': bytes[i] = (byte)77; break;
				case ']': bytes[i] = (byte)78; break;
				case '{': bytes[i] = (byte)79; break;
				case '}': bytes[i] = (byte)80; break;
				case '@': bytes[i] = (byte)81; break;
				case '%': bytes[i] = (byte)82; break;
				case '$': bytes[i] = (byte)83; break;
				case '#': bytes[i] = (byte)84; break;
			}
		}
		return bytes;
	}
	
}

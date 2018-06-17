package multithreaded_server.util_test;

import org.junit.Assert;
import org.junit.Test;

import multithreaded_server.util.BinaryDecoder;
import multithreaded_server.util.BinaryEncoder;

/**
 * BinaryEncoderDecoderTest
 * 
 * @since 0.2.0
 * @version 0.3.2
 * @author Peter Voigt
 *
 */
public class BinaryEncoderDecoderTest {

	@Test
	public void testEncodeDecodeNumbers() {
		String numbers = "1234567890";
		Assert.assertTrue(numbers.equals(BinaryDecoder.decodeString(BinaryEncoder.encodeString(numbers))));
	}

	@Test
	public void testEncodeDecodeLowerCaseLetters() {
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		Assert.assertTrue(
				lowerCaseLetters.equals(BinaryDecoder.decodeString(BinaryEncoder.encodeString(lowerCaseLetters))));
	}

	@Test
	public void testEncodeDecodeUpperCaseLetters() {
		String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Assert.assertTrue(
				upperCaseLetters.equals(BinaryDecoder.decodeString(BinaryEncoder.encodeString(upperCaseLetters))));
	}

	@Test
	public void testEncodeDecodeSymbols() {
		String symbols = ".-:+=^!/*?&<>()[]{}@%$#";
		Assert.assertTrue(symbols.equals(BinaryDecoder.decodeString(BinaryEncoder.encodeString(symbols))));
	}

}

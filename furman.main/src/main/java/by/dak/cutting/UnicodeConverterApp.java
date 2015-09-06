/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting;

/**
 * Class for converting Russian symbols to unicode (utf-8)
 * How to use:
 * Set some string data to convert() function at the main() call and
 * get the result string via console output
 *
 * @author LSD (www.vingrad.ru)
 */
public class UnicodeConverterApp {
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	private static final char ASCII_LOWER = 0x20;
	private static final char ASCII_UPPER = 0x7F;

	public static void main(String[] args) throws Exception {
	}

	public static String convert(String s) {
		int len = s.length();
		StringBuilder buffer = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c >= ASCII_LOWER && c <= ASCII_UPPER) {
				buffer.append(c);
			} else {
				appendEscape(c, buffer);
			}
		}
		return buffer.toString();
	}

	private static void appendEscape(char c, StringBuilder buffer) {
		buffer.append('\\').append('u');
		char d0 = HEX_DIGITS[0x000F & c];
		c >>>= 4;
		char d1 = HEX_DIGITS[0x000F & c];
		c >>>= 4;
		char d2 = HEX_DIGITS[0x000F & c];
		c >>>= 4;
		char d3 = HEX_DIGITS[0x000F & c];
		buffer.append(d3).append(d2).append(d1).append(d0);
	}
}

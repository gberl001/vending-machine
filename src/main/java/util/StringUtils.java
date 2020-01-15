package util;

public class StringUtils {

	/**
	 * Used to left pad a string with spaces
	 * 
	 * @param value
	 * @param num
	 * @return the formatted string
	 */
	public static String lPad(String value, int num) {
		return String.format("%" + num + "s", value);
	}
	
	/**
	 * Used to right pad a string with spaces
	 * 
	 * @param value
	 * @param num
	 * @return the formatted string
	 */
	public static String rPad(String value, int num) {
		return String.format("%-" + num + "s", value);
	}
	
	/**
	 * Use to center pad a string with spaces.  If the number of spaces
	 * is odd, one more space will be placed to the left.
	 * 
	 * @param value
	 * @param num
	 * @return
	 */
	public static String cPad(String value, int num) {
		// Find out the number of spaces to pad
		int spaces = num - value.length();
		
		// Compute half of the spaces, so we can pad
		int halfPad = value.length() + (spaces / 2);
		
		// Right pad with half of the spaces
		String retVal = StringUtils.rPad(value, halfPad);
		
		// Return left pad with the total desired length
		return StringUtils.lPad(retVal, num);
	}
	
	/**
	 * Use to add leading zeroes to a number
	 * 
	 * @param value
	 * @param num
	 * @return the formatted String
	 * String
	 */
	public static String leadingZeroes(int value, int num) {
		return String.format("%0" + num + "d", value);
	}
	
	public static void main(String args[]) {
		System.out.println(leadingZeroes(34, 5));
	}
}

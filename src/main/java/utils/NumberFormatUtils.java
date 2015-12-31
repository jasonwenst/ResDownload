package utils;

import java.text.DecimalFormat;

public class NumberFormatUtils {
	
	public static String formatByPattern(double number, String pattern) {
		DecimalFormat format = new DecimalFormat(pattern);
		return format.format(number);
	}
	
}

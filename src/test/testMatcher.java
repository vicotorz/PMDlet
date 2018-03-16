package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testMatcher {
	public static void main(String[] args) {
		String line = "Hello";
		String word = "hELLo";
		Pattern p = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(line);
		System.out.println(m.find());

	}
}

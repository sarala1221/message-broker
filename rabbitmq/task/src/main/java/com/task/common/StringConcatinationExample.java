package com.task.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StringConcatinationExample {

	public static void main(String as[]) {
		System.out.println("Vijay" + 20 + 20);// string append
		System.out.println(20 + 20 + "Vijay");// binary append
		inputStreamToString();
		inputStreamToStringUsingScanner();
	}

	//Java 8
	public static String inputStreamToString() {

		String s = "Convert this byte array to String Using Java 8!!";
		InputStream is = new ByteArrayInputStream(s.getBytes());
		String text = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining("\n"));
		System.out.println(text);
		return text;
	}
	
	public static String inputStreamToStringUsingScanner() {

		String s = "Convert this byte array to String Using Scanner!!";
	    InputStream inputStream = new ByteArrayInputStream(s.getBytes());

	    String text = null;
	    try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
	        text = scanner.useDelimiter("\\A").next();//\A means that the next() call reads the entire input stream.
	    }
		System.out.println(text);
		return text;
	}
}

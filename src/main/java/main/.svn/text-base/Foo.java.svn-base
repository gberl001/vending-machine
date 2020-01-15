package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Foo {

	private static final String hello = "Hello World";

	public String getMessage() {
		return hello;
		
	}

	public static void main(String[] args) throws IOException {
		System.out.println(new Foo().getMessage());
		System.out.println(new BufferedReader(new InputStreamReader(ClassLoader
				.getSystemClassLoader().getResourceAsStream("foo.properties")))
				.readLine());
	}
}

package com.test.aKafka;

import java.io.*;
import java.time.LocalTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ATest {

	static void dataScrapingDemo() {
		try {
			String URL = "https://blog.softtek.com/en/automation-through-java-jsoup-in-a-spring-boot-project";
			Document doc = Jsoup.connect(URL).get();
			doc.select("h2 > span").forEach(element ->{
				System.out.println(element.text());
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void fileHandlingTest() throws IOException {
		String filePath = "/Users/apple/Desktop/test.txt";
		Console console = System.console();
		System.out.println(console);
		System.out.println("Your name is: " + console.readLine());

	}

	public static void main(String[] args) throws IOException {
		String floatValue = "01.979";
		Double strDouble = Double.parseDouble(floatValue);
		System.out.println(strDouble);
		
		LocalTime openTime = LocalTime.parse("10:00");

		fileHandlingTest();

	}
}

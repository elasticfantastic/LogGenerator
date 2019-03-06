package com.github.elasticfantastic.loggenerator.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;

import com.github.elasticfantastic.loggenerator.LogRow;
import com.github.elasticfantastic.loggenerator.client.http.HttpRequester;
import com.github.elasticfantastic.loggenerator.client.http.HttpUtility;

public class Client {

	private String logFile;
	private String host;
	private int millisToSleep;

	public Client(String logFile, String host, int millisToSleep) {
		this.logFile = logFile;
		this.host = host;
		this.millisToSleep = millisToSleep;
	}

	public void run() throws IOException, InterruptedException {
		int i = 0;
		while (true) {
			String id = "Client1";
			String level = "INFO";
			String message = "";

			String method = "POST";

			// Every 4th request is an order request
			if (i % 4 == 0) {
				this.host = "http://localhost:8080/order";
				method = "POST";
				message = "Sending order request to " + host + ", method: " + method;
			} else {
				this.host = "http://localhost:8080/hello";
				method = "GET";
				message = "Sending request to " + host + ", method: " + method;
			}

			// LogGenerator generator = new LogGenerator();

			// Generate request output
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.logFile, true))) {
				LogRow logRow = new LogRow(id, level, ZonedDateTime.now(), message);
				// LogRow logRow = generator.getLog(ZonedDateTime.now(), zoneId);
				System.out.println(logRow);
				bw.write(logRow + System.getProperty("line.separator"));
			}

			HttpRequester requester = new HttpRequester(this.host, method);
			String responseBody = requester.getResponseBody();
			int responseCode = requester.getResponseCode();

			// Generate response output
			level = HttpUtility.toLogLevel(responseCode);
			message = responseBody;

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.logFile, true))) {
				LogRow logRow = new LogRow(id, level, ZonedDateTime.now(), message);
				// LogRow logRow = generator.getLog(ZonedDateTime.now(), zoneId, inputs);
				System.out.println(logRow);
				bw.write(logRow + System.getProperty("line.separator"));
			}

			Thread.sleep(this.millisToSleep);

			i++;
		}
	}

}

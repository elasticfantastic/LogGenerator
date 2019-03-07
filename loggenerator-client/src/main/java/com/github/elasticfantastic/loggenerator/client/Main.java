package com.github.elasticfantastic.loggenerator.client;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		String logFile = "log_client1.txt";
		String host = "http://localhost:8080/order";
		int millisToSleep = 5087;
		if (args.length >= 1) {
			System.out.println(args[0]);
			logFile = (args[0] != null ? args[0] : logFile);
		}
		if (args.length >= 2) {
			host = (args[1] != null ? args[1] : host);
		}
		if (args.length >= 3) {
			millisToSleep = (args[2] != null ? Integer.valueOf(args[2]) : millisToSleep);
		}

		Client client = new Client(logFile, host, millisToSleep);
		client.run();
	}

}

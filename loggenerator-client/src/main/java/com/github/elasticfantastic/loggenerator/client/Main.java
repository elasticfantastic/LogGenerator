package com.github.elasticfantastic.loggenerator.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.github.elasticfantastic.loggenerator.core.LogGenerator;
import com.github.elasticfantastic.loggenerator.core.LogRow;

public class Main {

	public static void main(String[] args) {
		LogGenerator generator = new LogGenerator();

		String id = "Client1";
		String logFile = "log_client1.txt";
		String host = "http://localhost:8080/order";
		int millisToSleep = 5087;
		if (args.length >= 1) {
			System.out.println(args[0]);
			id = (args[0] != null ? args[0] : id);
		}
		if (args.length >= 2) {
			System.out.println(args[1]);
			logFile = (args[1] != null ? args[1] : logFile);
		}
		if (args.length >= 3) {
			System.out.println(args[2]);
			host = (args[2] != null ? args[2] : host);
		}
		if (args.length >= 4) {
			System.out.println(args[3]);
			millisToSleep = (args[3] != null ? Integer.valueOf(args[3]) : millisToSleep);
		}

		Client client = new Client(id, logFile, host);
		int iteration = 0;
		while (true) {
			try {
				client.run(iteration);
				
				Thread.sleep(millisToSleep);
			} catch (IOException | InterruptedException e) {
				Map<String, Object> inputs = new HashMap<>();
				inputs.put("id", id);
				inputs.put("level", "ERROR");
				inputs.put("message", e.getMessage());

				try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
					LogRow logRow = generator.getLog(ZonedDateTime.now(), inputs);
					System.out.println(logRow);
					bw.write(logRow + System.getProperty("line.separator"));

					Thread.sleep(millisToSleep);
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			iteration++;
		}
	}

}

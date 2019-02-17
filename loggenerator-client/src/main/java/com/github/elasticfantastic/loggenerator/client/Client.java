package com.github.elasticfantastic.loggenerator.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.github.elasticfantastic.loggenerator.LogGenerator;
import com.github.elasticfantastic.loggenerator.LogRow;
import com.github.elasticfantastic.loggenerator.utility.ArrayUtility;

public class Client {

	private static final String LOG_FILE = "log_client1.txt";

	public static void main(String[] args) throws IOException, InterruptedException {
		ZoneId zoneId = ZoneId.of("Europe/Stockholm");
		
		String[] users = { "Anna", "Bob", "Eve", "Charlie", "Victor", "Samantha" };

		String host = "http://localhost:8080/order";
		int millisToSleep = 2011;
		if (args.length != 0) {
			host = (args[0] != null ? args[0] : host);
			millisToSleep = (args[1] != null ? Integer.valueOf(args[1]) : millisToSleep);
		}
		while (true) {
			// Generate request output
			Map<String, Object> inputs = new HashMap<>();
			inputs.put("id", "Client1");
			inputs.put("level", "INFO");
			inputs.put("message", "Sending order request to " + host);

			LogGenerator generator = new LogGenerator();

//            generator.setLevelFrequency("ERROR", 0.05);
//            generator.setLevelFrequency("WARN", 0.10);
//            generator.setLevelFrequency("INFO", 0.30);
//            generator.setLevelFrequency("DEBUG", 0.55);

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
				LogRow logRow = generator.getLog(ZonedDateTime.now(), zoneId, inputs);
				System.out.println(logRow);
				bw.write(logRow + System.getProperty("line.separator"));
			}

			URL url = new URL(host);
			URLConnection conn = url.openConnection();
			HttpURLConnection http = (HttpURLConnection) conn;
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setDoInput(true);

			String urlParameters = "user=" + ArrayUtility.getRandom(users);
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			try (DataOutputStream wr = new DataOutputStream(http.getOutputStream())) {
				wr.write(postData);
			}

			String result = null;
			String level = null;
			try {
				BufferedReader br;
				if (200 <= http.getResponseCode() && http.getResponseCode() <= 299) {
					level = "INFO";
					br = new BufferedReader(new InputStreamReader(http.getInputStream()));
				} else {
					level = "ERROR";
					br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
				}
				StringBuilder builder = new StringBuilder();
				String inputLine = "";
				while ((inputLine = br.readLine()) != null) {
					builder.append(inputLine);
				}
				result = builder.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Generate response output
			inputs.put("level", level);
			inputs.put("message", result);

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
				LogRow logRow = generator.getLog(ZonedDateTime.now(), ZoneId.of("Europe/Stockholm"), inputs);
				System.out.println(logRow);
				bw.write(logRow + System.getProperty("line.separator"));
			}

			Thread.sleep(millisToSleep);
		}
	}

}

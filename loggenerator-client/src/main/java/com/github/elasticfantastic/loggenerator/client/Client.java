package com.github.elasticfantastic.loggenerator.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.github.elasticfantastic.loggenerator.LogGenerator;
import com.github.elasticfantastic.loggenerator.LogRow;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "http://localhost:8080/hello";
        int millisToSleep = 500;
        if (args.length != 0) {
            host = (args[0] != null ? args[0] : host);
            millisToSleep = (args[1] != null ? Integer.valueOf(args[1]) : millisToSleep);
        }
        while (true) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("id", "Client");
            //inputs.put("level", "INFO");
            inputs.put("message", "Sending request to " + host);

            LogGenerator generator = new LogGenerator(LocalDateTime.now());
        	
            generator.setLevelFrequency("ERROR", 0.05);
            generator.setLevelFrequency("WARN", 0.10);
            generator.setLevelFrequency("INFO", 0.30);
            generator.setLevelFrequency("DEBUG", 0.55);
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("log_server.txt", true))) {
            	LogRow logRow = generator.getLog(inputs);
            	
            	System.out.println(logRow);
            	bw.write(logRow + System.getProperty("line.separator"));
            }

            URL url = new URL(host);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            con.getInputStream();

            // System.out.println(content.toString());

            Thread.sleep(millisToSleep);
        }
    }

}

package com.github.elasticfantastic.loggenerator.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.github.elasticfantastic.loggenerator.LogGenerator;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "http://localhost:8080/hello";
        int millisToSleep = 2000;
        if (args.length != 0) {
            host = (args[0] != null ? args[0] : host);
            millisToSleep = (args[1] != null ? Integer.valueOf(args[1]) : millisToSleep);
        }
        while (true) {
            Map<String, Object> inputs = new HashMap<>();
            inputs.put("id", "Client");
            inputs.put("level", "INFO");
            inputs.put("message", "Sending request to " + host);

            LogGenerator generator = new LogGenerator(LocalDateTime.now());
            System.out.println(generator.getLog(inputs));

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

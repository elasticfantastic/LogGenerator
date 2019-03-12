package com.github.elasticfantastic.loggenerator.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;

import com.github.elasticfantastic.loggenerator.client.http.HttpRequester;
import com.github.elasticfantastic.loggenerator.core.LogRow;
import com.github.elasticfantastic.loggenerator.core.utility.http.HttpUtility;

public class Client {

    private String id;
    private String logFile;
    private String host;

    public Client(String id, String logFile, String host) {
        this.id = id;
        this.logFile = logFile;
        this.host = host;
    }

    public void run(int iteration) throws IOException, InterruptedException {
        String level = "INFO";
        String text = "";

        String method = "POST";

        // Every 4th request is an order request
        if (iteration % 4 == 0) {
            this.host = "http://localhost:8080/order";
            method = "POST";
            text = "Sending order request to " + host + ", method: " + method;
        } else {
            this.host = "http://localhost:8080/hello";
            method = "GET";
            text = "Sending request to " + host + ", method: " + method;
        }

        // Generate request output
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.logFile, true))) {
            LogRow logRow = new LogRow(this.id, level, ZonedDateTime.now(), text);
            System.out.println(logRow);
            bw.write(logRow + System.getProperty("line.separator"));
        }

        HttpRequester requester = new HttpRequester(this.host, method);
        String responseBody = requester.getResponseBody();
        int responseCode = requester.getResponseCode();

        // Generate response output
        level = HttpUtility.toLevel(responseCode);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.logFile, true))) {
            LogRow logRow = new LogRow(this.id, level, ZonedDateTime.now(), responseBody);
            System.out.println(logRow);
            bw.write(logRow + System.getProperty("line.separator"));
        }
    }

}

package com.github.elasticfantastic.loggenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2 {

	public static void main(String[] args) throws IOException {
        //LocalDateTime endingDate2 = LocalDateTime.of(2018, 01, 01, 23, 59, 59);
        
    	List<LogRow> logs = new ArrayList<>();
    	
        int iterations = 200000;
        for (int i = 0; i < iterations; i++) {
            Map<String, Object> inputs = new HashMap<>();
        	
        	LocalDateTime beginningDate = LocalDateTime.of(2018, 01, 01, 00, 00, 00);
            LocalDateTime endingDate = LocalDateTime.of(2018, 12, 31, 23, 59, 59);
            
            if (i < 400) {
            	// Generate a bunch of ERROR and WARNING logs on 15 June between 08:00 and 09:00
            	String level = (i % 2 == 0 ? "WARN" : "ERROR");
            	inputs.put("level", level);
            	beginningDate = LocalDateTime.of(2018, 06, 15, 8, 00, 00);
                endingDate = LocalDateTime.of(2018, 06, 15, 8, 59, 59);
            }
            
        	LogGenerator generator = new LogGenerator();
        	
            generator.setIdFrequency("Client", 0.10);
            generator.setIdFrequency("Server", 0.60);
            generator.setIdFrequency("Middleware", 0.30);
        	
            generator.setLevelFrequency("ERROR", 0.05);
            generator.setLevelFrequency("WARN", 0.10);
            generator.setLevelFrequency("INFO", 0.30);
            generator.setLevelFrequency("DEBUG", 0.55);
            
            logs.add(generator.getLog(LocalDateTime.now(), inputs));
        }
        
        Collections.sort(logs);
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", true))) {
	        for (LogRow log : logs) {
	        	bw.write(log.toString() + System.getProperty("line.separator"));
	        }
        }

    }

}

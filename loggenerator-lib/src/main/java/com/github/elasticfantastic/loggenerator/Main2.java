package com.github.elasticfantastic.loggenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2 {

	public static void main(String[] args) throws IOException {
        //LocalDateTime endingDate2 = LocalDateTime.of(2018, 01, 01, 23, 59, 59);
        
    	List<LogRow> logs = new ArrayList<>();
    	
        int iterations = 250000;
        for (int i = 0; i < iterations; i++) {
            Map<String, Object> inputs = new HashMap<>();
        	
        	LocalDateTime beginningDate = LocalDateTime.of(2018, 01, 01, 00, 00, 00);
            //LocalDateTime endingDate = LocalDateTime.of(2018, 12, 31, 23, 59, 59);
        	LocalDateTime endingDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 00, 00));
            
            if (i < 300) {
            	// Generate a bunch of ERROR and WARNING logs on 15 June between 08:00 and 09:00
            	String level = (i % 3 == 0 ? "ERROR" : "WARN");
            	inputs.put("level", level);
            	beginningDate = LocalDateTime.of(2018, 06, 15, 8, 00, 00);
                endingDate = LocalDateTime.of(2018, 06, 15, 8, 59, 59);
            }
            
        	LogGenerator generator = new LogGenerator();
        	
            generator.setIdFrequency("Client1", 0.10);
            generator.setIdFrequency("Server1", 0.15);
            generator.setIdFrequency("Middleware", 0.20);
            generator.setIdFrequency("Server2", 0.30);
            generator.setIdFrequency("Client2", 0.15);
            generator.setIdFrequency("Client3", 0.10);
        	
            generator.setLevelFrequency("ERROR", 0.02);
            generator.setLevelFrequency("WARN", 0.12);
            generator.setLevelFrequency("INFO", 0.31);
            generator.setLevelFrequency("DEBUG", 0.55);
            
            //logs.add(generator.getLog(LocalDateTime.now(), inputs));
            logs.add(generator.getLog(beginningDate, endingDate, inputs));
        }
        
        Collections.sort(logs);
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt"))) {
	        for (LogRow log : logs) {
	        	bw.write(log.toString() + System.getProperty("line.separator"));
	        }
        }

    }

}

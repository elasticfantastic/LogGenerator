package com.github.elasticfantastic.loggenerator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        LogGenerator generator = new LogGenerator();
        
        generator.setIdFrequency("Client", 0.10);
        generator.setIdFrequency("Server", 0.60);
        generator.setIdFrequency("Middleware", 0.30);
        
        generator.setLevelFrequency("ERROR", 0.05);
        generator.setLevelFrequency("WARN", 0.10);
        generator.setLevelFrequency("INFO", 0.30);
        generator.setLevelFrequency("DEBUG", 0.55);
        
        System.out.println(generator.getLevelFrequencies());
        System.out.println(generator.getIdFrequencies());
        
        System.out.println(generator.getRandomLevel("INFO", "ERROR"));
        
        LocalDateTime time1 = LocalDateTime.of(2018, 12, 31, 00, 00, 00);
        LocalDateTime time2 = LocalDateTime.of(2019, 01, 01, 23, 59, 59);
        
        for (int i = 0; i < 10; i++) {
        	System.out.println(generator.getLog(time1, time2, new HashMap<>()));
        }
        
    }

}

package com.github.elasticfantastic.loggenerator;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException {
        LogGenerator generator = new LogGenerator(LocalDateTime.now());
        
        generator.setIdFrequency("Client", 0.10);
        generator.setIdFrequency("Server", 0.60);
        generator.setIdFrequency("Middleware", 0.30);
        
        generator.setLevelFrequency("ERROR", 0.05);
        generator.setLevelFrequency("WARN", 0.10);
        generator.setLevelFrequency("INFO", 0.30);
        generator.setLevelFrequency("DEBUG", 0.55);
        
        System.out.println(generator.getLevelFrequencies());
        System.out.println(generator.getIdFrequencies());
    }

}

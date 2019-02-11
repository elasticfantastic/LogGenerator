package com.github.elasticfantastic.loggenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.time.temporal.ChronoUnit;

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
        
        System.out.println(Arrays.toString(generator.getFrequencies()));
    }

}

package com.github.elasticfantastic.loggenerator;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main2 {

    public static void main(String[] args) throws IOException {
        LocalDateTime beginningDate = LocalDateTime.of(2018, 01, 01, 00, 00, 00);
        LocalDateTime endingDate = LocalDateTime.of(2018, 12, 31, 23, 59, 59);
        
        LocalDateTime endingDate2 = LocalDateTime.of(2018, 01, 01, 23, 59, 59);
        
        LogGenerator generator = new LogGenerator(beginningDate, beginningDate);
        
        for (int i = 0; i < 7; i++) {
            System.out.println(generator.getLogRow());
        }
        
    }
    
}

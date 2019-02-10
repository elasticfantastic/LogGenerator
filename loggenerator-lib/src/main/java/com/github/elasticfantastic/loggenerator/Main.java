package com.github.elasticfantastic.loggenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) throws IOException {
        // String[] ids = { "Client", "Server", "Middleware" };
        // String[] levels = { "ERROR", "INFO", "WARN" };
        //
        // Map<String, String[]> messagesMapping = new HashMap<>();
        // messagesMapping.put("ERROR", new String[] { "error1", "error2", "error3" });
        // messagesMapping.put("INFO", new String[] { "info1", "info2", "info3" });
        // messagesMapping.put("WARN", new String[] { "warn1", "warn2", "warn3" });
        //
        // LocalDateTime beginningDate = LocalDateTime.of(2018, 01, 01, 00, 00, 00);
        // LocalDateTime endingDate = LocalDateTime.of(2018, 12, 31, 23, 59, 59);
        //
        // DateTimeFormatter formatter =
        // DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:MM:ss.SSS'Z'");
        //
        // try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"))) {
        // int iterations = 100000;
        // for (int i = 0; i < iterations; i++) {
        // StringBuilder builder = new StringBuilder();
        //
        // String id = getRandom(ids);
        // String level = getRandom(levels);
        // LocalDateTime date = getRandom(beginningDate, endingDate);
        //
        // String[] messages = messagesMapping.get(level);
        // String msg = getRandom(messages);
        //
        // builder.append(String.format("[%s] [%s] [%s] - %s", id, level,
        // date.format(formatter), msg));
        // writer.write(builder.toString() + "\n");
        // }
        // }
        // }
        //
        // public static String getRandom(String[] arr) {
        // int rnd = new Random().nextInt(arr.length);
        // return arr[rnd];
        // }
        //
        // public static LocalDateTime getRandom(LocalDateTime beginningDate,
        // LocalDateTime endingDate) {
        // Random random = new Random();
        // long daysBetween = ChronoUnit.DAYS.between(beginningDate, endingDate);
        // LocalDateTime randomizedDate =
        // beginningDate.plusDays(random.nextInt((int)daysBetween));
        // LocalTime time = LocalTime.ofNanoOfDay(random.nextInt(86400000) * 1000000L);
        // return LocalDateTime.of(randomizedDate.toLocalDate(), time);
    }

}

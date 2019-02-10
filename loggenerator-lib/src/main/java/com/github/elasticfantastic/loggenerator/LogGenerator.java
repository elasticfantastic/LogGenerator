package com.github.elasticfantastic.loggenerator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LogGenerator {

    // FIX
    private String[] ids = { "Client", "Server", "Middleware" };
    private String[] levels = { "ERROR", "INFO", "WARN" };

    // FIX
    private Map<String, String[]> messagesMapping = new HashMap<>();

    private LocalDateTime beginningDate;
    private LocalDateTime endingDate;

    private DateTimeFormatter formatter;

    /**
     * <p>Create a <code>LogGenerator</code> which generates logs for the specified date.
     * 
     * @param specificDate
     *            the specific date to create logs for
     */
    public LogGenerator(LocalDateTime specificDate) {
        this.beginningDate = specificDate;
        this.endingDate = specificDate;

        this.formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");

        // FIX
        messagesMapping.put("ERROR", new String[] { "error1", "error2", "error3" });
        messagesMapping.put("INFO", new String[] { "info1", "info2", "info3" });
        messagesMapping.put("WARN", new String[] { "warn1", "warn2", "warn3" });
    }

    /**
     * <p>Create a <code>LogGenerator</code> which generates logs between the beginning
     * date and ending date.
     * 
     * @param beginningDate
     *            the lower bound for which dates can be generated
     * @param endingDate
     *            the upper bound for which dates can be generated
     */
    public LogGenerator(LocalDateTime beginningDate, LocalDateTime endingDate) {
        this(null);
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
    }

    public String getLog() {
        return getLog(new HashMap<String, Object>());
    }

    public String getLog(Map<String, Object> inputs) {
        StringBuilder builder = new StringBuilder();

        //String id = getRandom(this.ids);
        Object id = inputs.getOrDefault("id", getRandom(this.ids));
        
        //String level = getRandom(this.levels);
        Object level = inputs.getOrDefault("level", getRandom(this.levels));
        
        LocalDateTime date = getRandom(this.beginningDate, this.endingDate);
        
        String[] messages = this.messagesMapping.get(level);
        //String message = getRandom(messages);
        Object message = inputs.getOrDefault("message", getRandom(messages));

        builder.append(String.format("[%s] [%s] [%s] - %s", id, level, date.format(this.formatter), message));
        return builder.toString();
    }

    private static String getRandom(String[] arr) {
        int rnd = new Random().nextInt(arr.length);
        return arr[rnd];
    }

    private static LocalDateTime getRandom(LocalDateTime beginningDate, LocalDateTime endingDate) {
        Random random = new Random();
        if (beginningDate.equals(endingDate)) {
            return beginningDate;
        } else if (beginningDate.toLocalDate().equals(endingDate.toLocalDate())
                && !beginningDate.toLocalTime().equals(endingDate.toLocalTime())) {
            long nanosBetween = ChronoUnit.NANOS.between(beginningDate, endingDate);
            long nanos = 0 + (long) (random.nextDouble() * (nanosBetween - 0));
            LocalTime time = LocalTime.ofNanoOfDay(nanos);
            return LocalDateTime.of(beginningDate.toLocalDate(), time);
        } else {
            long daysBetween = ChronoUnit.DAYS.between(beginningDate, endingDate);
            LocalDateTime randomizedDate = beginningDate.plusDays(random.nextInt((int) daysBetween));
            LocalTime time = LocalTime.ofNanoOfDay(random.nextInt(86400000) * 1000000L);
            return LocalDateTime.of(randomizedDate.toLocalDate(), time);
        }
    }

}

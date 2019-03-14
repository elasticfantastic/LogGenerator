package com.github.elasticfantastic.loggenerator.core;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.elasticfantastic.loggenerator.core.utility.ArrayUtility;

/**
 * Class for generating log rows, either with default values or supplied values
 * 
 * @author Daniel Nilsson
 */
public class LogGenerator {

    private Map<String, String[]> textsMappings;

    private String[] idFrequencies;
    private String[] levelFrequencies;

    private ObjectMapper mapper;

    /**
     * Default constructor. Creates an instance which can generate logs.
     */
    public LogGenerator() {
        this.textsMappings = new HashMap<>();
        this.textsMappings.put("ERROR",
                new String[] { "Something threw an error", "An error occured", "Fatal crash!" });
        this.textsMappings.put("INFO",
                new String[] { "Functioning normally", "All systems are GO!", "Showing information to users" });
        this.textsMappings.put("WARN", new String[] { "Unidentified source of radiation",
                "Temporarily ignoring fan noise", "System is slowing down" });
        this.textsMappings.put("DEBUG",
                new String[] { "Disk usage is good", "RAM usage is good", "CPU usage is good" });

        this.idFrequencies = new String[100];
        this.levelFrequencies = new String[100];

        this.mapper = new ObjectMapper();
    }

    /**
     * Set the frequency rate for a specified ID.
     * 
     * @param id
     *            the ID
     * @param frequency
     *            the frequency in the range 0.00 to 1.00
     */
    public void setIdFrequency(String id, double frequency) {
        int maxFrequency = (int) Math.rint(frequency * idFrequencies.length);
        int firstEmptyPos = getFirstEmptyPosition(idFrequencies);
        for (int i = firstEmptyPos; i < firstEmptyPos + maxFrequency; i++) {
            idFrequencies[i] = id;
        }
    }

    /**
     * Returns the frequency rates for all the ID's.
     * 
     * @return the frequency rates for ID's
     */
    public Map<String, Double> getIdFrequencies() {
        Set<String> ids = new HashSet<>();
        for (String id : idFrequencies) {
            ids.add(id);
        }
        return getFrequencyMappings(ids, idFrequencies);
    }

    /**
     * Set the frequency rate for a specified level.
     * 
     * @param level
     *            the level
     * @param frequency
     *            the frequency in the range 0.00 to 1.00
     */
    public void setLevelFrequency(String level, double frequency) {
        int maxFrequency = (int) Math.rint(frequency * levelFrequencies.length);
        int firstEmptyPos = getFirstEmptyPosition(levelFrequencies);
        for (int i = firstEmptyPos; i < firstEmptyPos + maxFrequency; i++) {
            levelFrequencies[i] = level;
        }
    }

    /**
     * 
     * Returns the frequency rates for all the levels.
     * 
     * @return the frequency rates for levels
     */
    public Map<String, Double> getLevelFrequencies() {
        Set<String> levels = new HashSet<>();
        for (String level : levelFrequencies) {
            levels.add(level);
        }
        return getFrequencyMappings(levels, levelFrequencies);
    }

    /**
     * Gets the frequency for a number of specified values (<code>keys</code> parameter).
     * 
     * @param keys
     *            the keys
     * @param frequencies
     *            the frequencies (an array consisting of elements which also exists in
     *            the <code>keys</code> parameter)
     * @return the frequency mappings for all the specified <code>keys</code>
     */
    private Map<String, Double> getFrequencyMappings(Set<String> keys, String[] frequencies) {
        Map<String, Double> mappings = new HashMap<>();
        for (String key : keys) {
            for (int i = 0; i < frequencies.length; i++) {
                String frequency = frequencies[i];
                if (Objects.equals(key, frequency)) {
                    if (!mappings.containsKey(key)) {
                        mappings.put(key, 1.0);
                    } else {
                        mappings.put(key, mappings.get(key) + 1.0);
                    }
                }
            }
            mappings.put(key, mappings.get(key) / frequencies.length);
        }
        return mappings;
    }

    private int getFirstEmptyPosition(Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            Object obj = arr[i];
            if (obj == null) {
                return i;
            }
        }
        throw new IllegalArgumentException("Array doesn't contain empty indices");
    }

    public String getRandomId() {
        return getRandom(this.idFrequencies);
    }

    public String getRandomLevel() {
        return getRandom(this.levelFrequencies);
    }

    /**
     * Returns a random level from the specified input by inspecting the frequency array.
     * 
     * @param levels
     *            the levels to choose from
     * @return a random level from the specified input
     */
    public String getRandomLevel(String... levels) {
        int totalFrequency = 0;
        Map<String, Double> frequencies = getLevelFrequencies();
        for (String level : levels) {
            totalFrequency += frequencies.get(level) * 100;
        }
        String[] arr = new String[totalFrequency];
        for (String level : levels) {
            int firstEmpty = getFirstEmptyPosition(arr);
            for (int i = firstEmpty; i < firstEmpty + frequencies.get(level) * 100; i++) {
                arr[i] = level;
            }
        }
        return getRandom(arr);
    }

    /**
     * Returns a log row with random default values.
     * 
     * @return a log row with random default values
     * @throws JsonProcessingException
     *             if the log row couldn't process it's JSON contents
     */
    public LogRow getLog() throws JsonProcessingException {
        return getLog(ZonedDateTime.now(), new HashMap<String, Object>());
    }

    /**
     * Returns a log row with the specified date and inputs.
     * 
     * @param specificDate
     *            the date
     * @param inputs
     *            the inputs
     * @return a log row with random values fetched from the specified inputs
     * @throws JsonProcessingException
     *             if the log row couldn't process it's JSON contents
     */
    public LogRow getLog(ZonedDateTime specificDate, Map<String, Object> inputs) throws JsonProcessingException {
        return getLog(specificDate, specificDate, ZoneId.of("Europe/Stockholm"), inputs);
    }

    /**
     * Returns a log row with the specified date, zone ID and inputs.
     * 
     * @param specificDate
     *            the date
     * @param inputs
     *            the inputs
     * @param zoneId
     *            the zone ID
     * @return a log row with random values fetched from the specified inputs
     * @throws JsonProcessingException
     *             if the log row couldn't process it's JSON contents
     */
    public LogRow getLog(ZonedDateTime specificDate, ZoneId zoneId, Map<String, Object> inputs)
            throws JsonProcessingException {
        return getLog(specificDate, specificDate, zoneId, inputs);
    }

    /**
     * Returns a log row with the specified beginning date, ending date, zone ID and
     * inputs.
     * 
     * @param beginningDate
     *            the beginning date
     * @param endingDate
     *            the ending date
     * @param inputs
     *            the inputs
     * @param zoneId
     *            the zone ID
     * @return a log row with random values fetched from the specified inputs
     * @throws JsonProcessingException
     *             if the log row couldn't process it's JSON contents
     */
    public LogRow getLog(ZonedDateTime beginningDate, ZonedDateTime endingDate, ZoneId zoneId,
            Map<String, Object> inputs) throws JsonProcessingException {
        Object id = inputs.getOrDefault("id", getRandom(this.idFrequencies));
        Object level = inputs.getOrDefault("level", getRandom(this.levelFrequencies));
        ZonedDateTime date = getRandom(beginningDate, endingDate, zoneId);

        String[] texts = this.textsMappings.get(level);
        Object text = inputs.getOrDefault("text", getRandom(texts));

        LogRow logRow = new LogRow(id.toString(), level.toString(), date, text.toString());

        // Add some JSON if it's either a WARN or ERROR level
        if (level.equals("WARN") || level.equals("ERROR")) {
            UUID uuid = UUID.randomUUID();

            ObjectNode rootNode = mapper.createObjectNode();

            rootNode.put("text", text.toString());
            rootNode.put("reference", uuid.toString());

            String json = mapper.writeValueAsString(rootNode);
            logRow.setPayload(json);
        }
        return logRow;
    }

    /**
     * Get a random element from the specified input.
     * 
     * @param arr
     *            the array of elements
     * @return a random element
     */
    private static String getRandom(String[] arr) {
        return ArrayUtility.getRandom(arr);
    }

    /**
     * Returns a random <code>ZonedDateTime</code> between the beginning date (inclusive)
     * and ending date (exclusive)
     * 
     * @param beginningDate
     *            the beginning date
     * @param endingDate
     *            the ending date
     * @param zoneId
     *            the zone ID
     * @return a random <code>ZonedDateTime</code>
     */
    public static ZonedDateTime getRandom(ZonedDateTime beginningDate, ZonedDateTime endingDate, ZoneId zoneId) {
        if (beginningDate.equals(endingDate)) {
            return beginningDate;
        }
        long beginningInstant = beginningDate.toInstant().toEpochMilli();
        long endInstant = endingDate.toInstant().toEpochMilli();
        long randomInstant = ThreadLocalRandom.current().nextLong(beginningInstant, endInstant);

        Instant instant = Instant.ofEpochMilli(randomInstant);

        return ZonedDateTime.ofInstant(instant, zoneId);
    }

}

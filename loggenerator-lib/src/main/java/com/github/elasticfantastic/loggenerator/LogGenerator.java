package com.github.elasticfantastic.loggenerator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.github.elasticfantastic.loggenerator.utility.ArrayUtility;

public class LogGenerator {

	// FIX
	// private String[] ids = { "Client", "Server", "Middleware" };
	// private String[] levels = { "ERROR", "INFO", "WARN", "DEBUG" };

	// FIX
	private Map<String, String[]> messagesMapping = new HashMap<>();

	private String[] idFrequencies;
	private String[] levelFrequencies;

	/**
	 * <p>
	 * Create a <code>LogGenerator</code> which generates logs for.
	 * </p>
	 */
	public LogGenerator() {
		this.idFrequencies = new String[100];
		this.levelFrequencies = new String[100];

		// FIX
		messagesMapping.put("ERROR", new String[] { "error1", "error2", "error3" });
		messagesMapping.put("INFO", new String[] { "info1", "info2", "info3" });
		messagesMapping.put("WARN", new String[] { "warn1", "warn2", "warn3" });
		messagesMapping.put("DEBUG", new String[] { "debug1", "debug2", "debug3" });
	}

	public void setIdFrequency(String id, double frequency) {
		int maxFrequency = (int) Math.rint(frequency * idFrequencies.length);
		int firstEmptyPos = getFirstEmptyPosition(idFrequencies);
		for (int i = firstEmptyPos; i < firstEmptyPos + maxFrequency; i++) {
			idFrequencies[i] = id;
		}
	}

	public Map<String, Double> getIdFrequencies() {
		Set<String> ids = new HashSet<>();
		for (String id : idFrequencies) {
			ids.add(id);
		}
		return getFrequencyMappings(ids, idFrequencies);
	}

	public void setLevelFrequency(String level, double frequency) {
		int maxFrequency = (int) Math.rint(frequency * levelFrequencies.length);
		int firstEmptyPos = getFirstEmptyPosition(levelFrequencies);
		for (int i = firstEmptyPos; i < firstEmptyPos + maxFrequency; i++) {
			levelFrequencies[i] = level;
		}
	}

	public Map<String, Double> getLevelFrequencies() {
		Set<String> levels = new HashSet<>();
		for (String level : levelFrequencies) {
			levels.add(level);
		}
		return getFrequencyMappings(levels, levelFrequencies);
	}

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

	public LogRow getLog() {
		return getLog(ZonedDateTime.now(), new HashMap<String, Object>());
	}

	public LogRow getLog(ZonedDateTime specificDate, Map<String, Object> inputs) {
		return getLog(specificDate, specificDate, ZoneId.of("Europe/Stockholm"), inputs);
	}

	public LogRow getLog(ZonedDateTime specificDate, ZoneId zoneId, Map<String, Object> inputs) {
		return getLog(specificDate, specificDate, zoneId, inputs);
	}

	public LogRow getLog(ZonedDateTime beginningDate, ZonedDateTime endingDate, ZoneId zoneId,
			Map<String, Object> inputs) {
		Object id = inputs.getOrDefault("id", getRandom(this.idFrequencies));
		Object level = inputs.getOrDefault("level", getRandom(this.levelFrequencies));
		ZonedDateTime date = getRandom(beginningDate, endingDate, zoneId);

		String[] messages = this.messagesMapping.get(level);
		Object message = inputs.getOrDefault("message", getRandom(messages));

		return new LogRow(id.toString(), level.toString(), date, message.toString());
	}

	private static String getRandom(String[] arr) {
		return ArrayUtility.getRandom(arr);
	}

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

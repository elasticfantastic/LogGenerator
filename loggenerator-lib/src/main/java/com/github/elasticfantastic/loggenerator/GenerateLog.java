package com.github.elasticfantastic.loggenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateLog {

	public static void main(String[] args) throws IOException {
		// LocalDateTime endingDate2 = LocalDateTime.of(2018, 01, 01, 23, 59, 59);

		List<LogRow> logs = new ArrayList<>();
		
		ZoneId zoneId = ZoneId.of("Europe/Stockholm");

		int iterations = 250000;
		for (int i = 0; i < iterations; i++) {
			LogGenerator generator = new LogGenerator();

			generator.setIdFrequency("Client1", 0.10);
			generator.setIdFrequency("Server1", 0.15);
			generator.setIdFrequency("Middleware", 0.20);
			generator.setIdFrequency("Server2", 0.30);
			generator.setIdFrequency("Client2", 0.15);
			generator.setIdFrequency("Client3", 0.10);
			
			Map<String, Object> inputs = new HashMap<>();

			ZonedDateTime beginningDate = ZonedDateTime.of(2018, 01, 01, 00, 00, 00, 00, zoneId);
			// LocalDateTime endingDate = LocalDateTime.of(2018, 12, 31, 23, 59, 59);
			ZonedDateTime endingDate = ZonedDateTime.of(LocalDateTime.now(), zoneId);
			
			ZonedDateTime generatedDate = LogGenerator.getRandom(beginningDate, endingDate, zoneId);

			ZonedDateTime spikeDateBeginningApril = ZonedDateTime.of(2018, 4, 2, 7, 00, 00, 00, zoneId);
			ZonedDateTime spikeDateEndingJune = ZonedDateTime.of(2018, 6, 14, 2, 34, 54, 999, zoneId);
			
			ZonedDateTime spikeDateBeginningAugust = ZonedDateTime.of(2018, 8, 15, 14, 00, 00, 00, zoneId);
			ZonedDateTime spikeDateEndingAugust = ZonedDateTime.of(2018, 8, 15, 19, 59, 59, 999, zoneId);
			
			ZonedDateTime spikeDateBeginningNovember = ZonedDateTime.of(2018, 11, 3, 12, 00, 00, 00, zoneId);
			ZonedDateTime spikeDateEndingNovember = ZonedDateTime.of(2018, 11, 22, 16, 59, 59, 999, zoneId);
			
			if (generatedDate.isAfter(spikeDateBeginningApril) && generatedDate.isBefore(spikeDateEndingJune)) {
				// April to June has a slight distribution difference
				generator.setLevelFrequency("ERROR", 0.04);
				generator.setLevelFrequency("WARN", 0.16);
				generator.setLevelFrequency("INFO", 0.29);
				generator.setLevelFrequency("DEBUG", 0.51);
			} else if (generatedDate.isAfter(spikeDateBeginningAugust) && generatedDate.isBefore(spikeDateEndingAugust)) {
				// Generate a bunch of ERROR and WARNING logs on 8 August between 14:00 and 20:00
				generator.setLevelFrequency("ERROR", 0.35);
				generator.setLevelFrequency("WARN", 0.30);
				generator.setLevelFrequency("INFO", 0.12);
				generator.setLevelFrequency("DEBUG", 0.23);
			} else if (generatedDate.isAfter(spikeDateBeginningNovember) && generatedDate.isBefore(spikeDateEndingNovember)) {
				// November wasn't a great month
				generator.setLevelFrequency("ERROR", 0.11);
				generator.setLevelFrequency("WARN", 0.18);
				generator.setLevelFrequency("INFO", 0.26);
				generator.setLevelFrequency("DEBUG", 0.45);
			} else {
				generator.setLevelFrequency("ERROR", 0.02);
				generator.setLevelFrequency("WARN", 0.12);
				generator.setLevelFrequency("INFO", 0.31);
				generator.setLevelFrequency("DEBUG", 0.55);
			}

			// logs.add(generator.getLog(LocalDateTime.now(), inputs));
			logs.add(generator.getLog(generatedDate, zoneId, inputs));
		}

		Collections.sort(logs);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt"))) {
			for (LogRow log : logs) {
				bw.write(log.toString() + System.getProperty("line.separator"));
			}
		}

	}

}

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

public class GenerateLog {

	public static void main(String[] args) throws IOException {
		// LocalDateTime endingDate2 = LocalDateTime.of(2018, 01, 01, 23, 59, 59);

		List<LogRow> logs = new ArrayList<>();

		int iterations = 250000;
//		int iterationsTenThousandth = iterations / 1000;
//		int iterationsForSpike = Math.max(50, iterationsTenThousandth);
		for (int i = 0; i < iterations; i++) {
			LogGenerator generator = new LogGenerator();

			generator.setIdFrequency("Client1", 0.10);
			generator.setIdFrequency("Server1", 0.15);
			generator.setIdFrequency("Middleware", 0.20);
			generator.setIdFrequency("Server2", 0.30);
			generator.setIdFrequency("Client2", 0.15);
			generator.setIdFrequency("Client3", 0.10);
			
			Map<String, Object> inputs = new HashMap<>();

			LocalDateTime beginningDate = LocalDateTime.of(2018, 01, 01, 00, 00, 00);
			// LocalDateTime endingDate = LocalDateTime.of(2018, 12, 31, 23, 59, 59);
			LocalDateTime endingDate = LocalDateTime.now();
			
			LocalDateTime generatedDate = LogGenerator.getRandom(beginningDate, endingDate);

			LocalDateTime spikeDateBeginningAugust = LocalDateTime.of(2018, 8, 15, 14, 00, 00);
			LocalDateTime spikeDateEndingAugust = LocalDateTime.of(2018, 8, 15, 19, 59, 59);
			
			LocalDateTime spikeDateBeginningNovember = LocalDateTime.of(2018, 11, 3, 12, 00, 00);
			LocalDateTime spikeDateEndingNovember = LocalDateTime.of(2018, 11, 22, 16, 59, 59);
			
			if (generatedDate.isAfter(spikeDateBeginningAugust) && generatedDate.isBefore(spikeDateEndingAugust)) {
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
			logs.add(generator.getLog(generatedDate, inputs));
		}

		Collections.sort(logs);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt"))) {
			for (LogRow log : logs) {
				bw.write(log.toString() + System.getProperty("line.separator"));
			}
		}

	}

}

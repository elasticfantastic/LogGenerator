package com.github.elasticfantastic.loggenerator.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.elasticfantastic.loggenerator.LogGenerator;
import com.github.elasticfantastic.loggenerator.LogRow;
import com.github.elasticfantastic.loggenerator.database.model.Customer;
import com.github.elasticfantastic.loggenerator.database.model.Order;
import com.github.elasticfantastic.loggenerator.database.model.Product;
import com.github.elasticfantastic.loggenerator.database.service.CustomerService;
import com.github.elasticfantastic.loggenerator.database.service.OrderService;
import com.github.elasticfantastic.loggenerator.database.service.ProductService;
import com.github.elasticfantastic.loggenerator.utility.CollectionUtility;
import com.github.elasticfantastic.loggenerator.utility.DatabaseUtility;
import com.github.elasticfantastic.loggenerator.utility.JsonUtility;
import com.github.elasticfantastic.loggenerator.utility.SqlBuilder;

public class GenerateLog {

	private DatabaseUtility dbUtility;

	public GenerateLog() {
		this.dbUtility = new DatabaseUtility();
	}

	public void run() throws IOException {
		// LocalDateTime endingDate2 = LocalDateTime.of(2018, 01, 01, 23, 59, 59);
		List<LogRow> logs = new ArrayList<>();

		ZoneId zoneId = ZoneId.of("Europe/Stockholm");
		ZonedDateTime beginningDate = ZonedDateTime.of(2018, 01, 01, 00, 00, 00, 00, zoneId);

		int logsPerDay = 300;
		int daysBetweenStartAndEnd = (int) ChronoUnit.DAYS.between(beginningDate, ZonedDateTime.now());
		int totalLogs = logsPerDay * daysBetweenStartAndEnd;

		System.out.println("Generating " + totalLogs + " logs");
		for (int i = 0; i < totalLogs; i++) {
			LogGenerator generator = new LogGenerator();

			generator.setIdFrequency("Client1", 0.11);
			generator.setIdFrequency("Server1", 0.15);
			generator.setIdFrequency("Middleware", 0.17);
			generator.setIdFrequency("Server2", 0.28);
			generator.setIdFrequency("Client2", 0.18);
			generator.setIdFrequency("Client3", 0.11);

			Map<String, Object> inputs = new HashMap<>();

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
				generator.setLevelFrequency("ERROR", 0.02);
				generator.setLevelFrequency("WARN", 0.14);
				generator.setLevelFrequency("INFO", 0.32);
				generator.setLevelFrequency("DEBUG", 0.52);
			} else if (generatedDate.isAfter(spikeDateBeginningAugust)
					&& generatedDate.isBefore(spikeDateEndingAugust)) {
				// Generate a bunch of ERROR and WARNING logs on 8 August between 14:00 and
				// 20:00
				generator.setLevelFrequency("ERROR", 0.25);
				generator.setLevelFrequency("WARN", 0.15);
				generator.setLevelFrequency("INFO", 0.25);
				generator.setLevelFrequency("DEBUG", 0.35);
			} else if (generatedDate.isAfter(spikeDateBeginningNovember)
					&& generatedDate.isBefore(spikeDateEndingNovember)) {
				// November wasn't a great month
				generator.setLevelFrequency("ERROR", 0.05);
				generator.setLevelFrequency("WARN", 0.19);
				generator.setLevelFrequency("INFO", 0.26);
				generator.setLevelFrequency("DEBUG", 0.50);
			} else {
				generator.setLevelFrequency("ERROR", 0.01);
				generator.setLevelFrequency("WARN", 0.10);
				generator.setLevelFrequency("INFO", 0.34);
				generator.setLevelFrequency("DEBUG", 0.55);
			}

			logs.add(generator.getLog(generatedDate, zoneId, inputs));
		}

		Collections.sort(logs);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("orders.sql"))) {
			bw.write("SET IDENTITY_INSERT Orderr ON" + System.getProperty("line.separator"));
			int i = 0;
			for (LogRow log : logs) {
				// Every fourth INFO log is an order
				if (log.getLevel().equals("INFO") && i % 4 == 0) {
					// Do the database stuff
					Order order = dbUtility.placeRandomOrder(log.getDate().toLocalDateTime());
					double orderPrice = order.getTotalPrice();

					// Modify log row contents
					String orderAsJson = JsonUtility.toJson(order);
					String customerName = order.getCustomer().getName();

					log.setMessage("Customer " + customerName + " placed an order worth " + orderPrice);
					log.setPayload(Base64.getEncoder().encodeToString(orderAsJson.getBytes()));

					Collection<String> sqlStatements = SqlBuilder.buildOrder(order);
					for (String statement : sqlStatements) {
						bw.write(statement + System.getProperty("line.separator"));
					}
				} else {
					i++;
				}
			}
			bw.write("SET IDENTITY_INSERT Orderr OFF" + System.getProperty("line.separator"));
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt"))) {
			for (LogRow log : logs) {
				bw.write(log.toString() + System.getProperty("line.separator"));
			}
		}
	}

	public static void main(String[] args) throws IOException {
		GenerateLog g = new GenerateLog();
		g.run();
	}

}

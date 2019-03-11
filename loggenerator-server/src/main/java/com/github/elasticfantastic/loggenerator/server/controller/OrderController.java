package com.github.elasticfantastic.loggenerator.server.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.elasticfantastic.loggenerator.LogGenerator;
import com.github.elasticfantastic.loggenerator.LogRow;
import com.github.elasticfantastic.loggenerator.database.model.Order;
import com.github.elasticfantastic.loggenerator.server.utility.HttpUtility;
import com.github.elasticfantastic.loggenerator.utility.DatabaseUtility;
import com.github.elasticfantastic.loggenerator.utility.JsonUtility;
import com.github.elasticfantastic.loggenerator.utility.ParameterContainer;

@RestController
public class OrderController {

	private DatabaseUtility dbUtility;

	private Encoder encoder;
	private LogGenerator generator;

	public OrderController() {
		this.dbUtility = new DatabaseUtility();

		this.encoder = Base64.getEncoder();
		this.generator = new LogGenerator();

		this.generator.setLevelFrequency("ERROR", 0.01);
		this.generator.setLevelFrequency("WARN", 0.10);
		this.generator.setLevelFrequency("INFO", 0.34);
		this.generator.setLevelFrequency("DEBUG", 0.55);
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ResponseEntity<Object> addOrder(HttpServletRequest request) throws IOException {
		String logFile = ParameterContainer.getParameter("logFile");
		String id = ParameterContainer.getParameter("id");

		// Generate request output
		String messageRequest = "Received order request from " + request.getRemoteAddr() + ":"
				+ request.getRemotePort();
		LogRow logRowRequest = new LogRow(id, "INFO", ZonedDateTime.now(), messageRequest);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			System.out.println(logRowRequest);
			bw.write(logRowRequest + System.getProperty("line.separator"));
		}

		// Generate response output

		// Do the database stuff
		Order order = dbUtility.placeRandomOrder(LocalDateTime.now());
		double orderPrice = order.getTotalPrice();

		// Modify log row contents
		String orderAsJson = JsonUtility.toJson(order);
		String customerName = order.getCustomer().getName();

		String messageResponse = "Customer " + customerName + " placed an order worth " + orderPrice;
		LogRow logRowResponse = new LogRow(id, "INFO", ZonedDateTime.now(), messageResponse);
		logRowResponse.setPayload(this.encoder.encodeToString(orderAsJson.getBytes()));

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			System.out.println(logRowResponse);
			bw.write(logRowResponse + System.getProperty("line.separator"));
			return new ResponseEntity<>("Order for " + customerName + " was placed",
					HttpUtility.getStatus(logRowResponse.getLevel()));
		}
	}

}

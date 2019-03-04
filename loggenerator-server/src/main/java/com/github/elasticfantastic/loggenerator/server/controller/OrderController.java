package com.github.elasticfantastic.loggenerator.server.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.github.elasticfantastic.loggenerator.LogGenerator;
import com.github.elasticfantastic.loggenerator.LogRow;
import com.github.elasticfantastic.loggenerator.database.model.Customer;
import com.github.elasticfantastic.loggenerator.database.model.Order;
import com.github.elasticfantastic.loggenerator.database.model.OrderLine;
import com.github.elasticfantastic.loggenerator.database.model.Product;
import com.github.elasticfantastic.loggenerator.database.service.CustomerService;
import com.github.elasticfantastic.loggenerator.database.service.OrderService;
import com.github.elasticfantastic.loggenerator.database.service.ProductService;
import com.github.elasticfantastic.loggenerator.server.thread.ThreadTest;
import com.github.elasticfantastic.loggenerator.utility.ArrayUtility;
import com.github.elasticfantastic.loggenerator.utility.CollectionUtility;
import com.github.elasticfantastic.loggenerator.utility.DatabaseUtility;
import com.github.elasticfantastic.loggenerator.utility.JsonUtility;
import com.github.elasticfantastic.loggenerator.utility.MessageUtility;
import com.github.elasticfantastic.loggenerator.utility.ParameterContainer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	private DatabaseUtility dbUtility;

	private LogGenerator generator;

	public OrderController() {
		this.dbUtility = new DatabaseUtility();

		this.generator = new LogGenerator();

		this.generator.setLevelFrequency("ERROR", 0.01);
		this.generator.setLevelFrequency("WARN", 0.10);
		this.generator.setLevelFrequency("INFO", 0.34);
		this.generator.setLevelFrequency("DEBUG", 0.55);

		Runnable r = new ThreadTest(generator);
		Thread t = new Thread(r);
		t.start();
	}

	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	public ResponseEntity<Object> order(HttpServletRequest request) throws IOException {
		String logFile = ParameterContainer.getParameter("logFile");

		// Generate request output
		Map<String, Object> inputs = new HashMap<>();
		inputs.put("id", "Server1");
		inputs.put("level", "INFO");
		inputs.put("message", "Received request from " + request.getRemoteAddr() + ":" + request.getRemotePort());

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			LogRow logRow = this.generator.getLog(ZonedDateTime.now(), inputs);
			System.out.println(logRow);
			bw.write(logRow + System.getProperty("line.separator"));
		}

		// Generate response output
		inputs = new HashMap<>();
		inputs.put("id", "Server1");
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			LogRow logRow = this.generator.getLog(ZonedDateTime.now(), inputs);
			System.out.println(logRow);
			bw.write(logRow + System.getProperty("line.separator"));

			return new ResponseEntity<>(logRow.getMessage(), getStatus(logRow.getLevel()));
		}
	}

	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	public ResponseEntity<Object> addOrder(HttpServletRequest request) throws IOException {
		String logFile = ParameterContainer.getParameter("logFile");

		// Generate request output

		String messageRequest = "Received order request from " + request.getRemoteAddr() + ":"
				+ request.getRemotePort();
		LogRow logRowRequest = new LogRow("Server1", "INFO", ZonedDateTime.now(), messageRequest);

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
		LogRow logRowResponse = new LogRow("Server1", "INFO", ZonedDateTime.now(), messageResponse);
		logRowResponse.setPayload(Base64.getEncoder().encodeToString(orderAsJson.getBytes()));

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			System.out.println(logRowResponse);
			bw.write(logRowResponse + System.getProperty("line.separator"));
			return new ResponseEntity<>("Order for " + customerName + " was placed",
					getStatus(logRowResponse.getLevel()));
		}
	}

	public HttpStatus getStatus(String level) {
		switch (level) {
		case "ERROR":
			return HttpStatus.FORBIDDEN;
		default:
			return HttpStatus.OK;
		}
	}

}

package com.github.elasticfantastic.loggenerator.server.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

	private CustomerService customerService;
	private OrderService orderService;
	private ProductService productService;

	private LogGenerator generator;

	public OrderController() {
		this.customerService = new CustomerService();
		this.productService = new ProductService();

		this.generator = new LogGenerator();

		this.generator.setLevelFrequency("ERROR", 0.10);
		this.generator.setLevelFrequency("WARN", 0.10);
		this.generator.setLevelFrequency("INFO", 0.25);
		this.generator.setLevelFrequency("DEBUG", 0.55);

		Runnable r = new ThreadTest(generator);
		Thread t = new Thread(r);
		t.start();
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ResponseEntity<Object> order(@RequestParam("user") String user, HttpServletRequest request)
			throws IOException {
		String logFile = ParameterContainer.getParameter("logFile");

		// Generate request output
		Map<String, Object> inputs = new HashMap<>();
		inputs.put("id", "Server1");
		inputs.put("level", "INFO");
		inputs.put("message", "Received order request from " + request.getRemoteAddr() + ":" + request.getRemotePort());

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			LogRow logRow = this.generator.getLog(ZonedDateTime.now(), inputs);
			System.out.println(logRow);
			bw.write(logRow + System.getProperty("line.separator"));
		}

		// Generate response output
		inputs = new HashMap<>();
		inputs.put("id", "Server1");

		String level = this.generator.getRandomLevel("INFO", "ERROR");
		String message = MessageUtility.getMessage(level, user);

		inputs.put("level", level);
		inputs.put("message", message);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			LogRow logRow = this.generator.getLog(ZonedDateTime.now(), inputs);
			System.out.println(logRow);
			bw.write(logRow + System.getProperty("line.separator"));

			return new ResponseEntity<>(logRow.getMessage(), getStatus(level));
		}
	}

	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	public ResponseEntity<Object> addOrder(@RequestParam("user") String user, HttpServletRequest request)
			throws IOException {
		String logFile = ParameterContainer.getParameter("logFile");

		// Generate request output
		// String ssn = CollectionUtility.getRandom(customerService.findAll());		
		// Customer customer = CollectionUtility.getRandom(customerService.findAll());
		
		String ssn = "16081216-2816";
		Customer customer = customerService.findById(ssn);
		
//		System.out.println(customer.toString());
//		System.out.println(customer.getOrders().size());
//		
//		List<Product> products = new ArrayList<>(productService.findAll());
//		List<Product> randomizedProducts = CollectionUtility.getRandom(products, 4);
//		
//		Order order = new Order(LocalDateTime.now());
//		
//		Random random = new Random();
//		
//		for (Product p : randomizedProducts) {
//			OrderLine orderLine = new OrderLine(order, p, random.nextInt(3) + 1);
//			order.addOrderLine(orderLine);
//		}
//		
//		customer.addOrder(order);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
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

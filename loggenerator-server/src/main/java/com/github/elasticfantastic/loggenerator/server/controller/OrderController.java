package com.github.elasticfantastic.loggenerator.server.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.elasticfantastic.loggenerator.LogGenerator;
import com.github.elasticfantastic.loggenerator.LogRow;
import com.github.elasticfantastic.loggenerator.server.thread.ThreadTest;
import com.github.elasticfantastic.loggenerator.utility.MessageUtility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	private static final String LOG_FILE = "log_server.txt";

	private LogGenerator generator;

	private ThreadTest test;

	public OrderController() {
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
	public ResponseEntity<Object> addOrder(@RequestParam("user") String user, HttpServletRequest request)
			throws IOException {
		// Generate request output
		Map<String, Object> inputs = new HashMap<>();
		inputs.put("id", "Server1");
		inputs.put("level", "INFO");
		inputs.put("message", "Received request from " + request.getRemoteAddr() + ":" + request.getRemotePort());

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
			LogRow logRow = this.generator.getLog(LocalDateTime.now(), inputs);
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

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
			LogRow logRow = this.generator.getLog(LocalDateTime.now(), inputs);
			System.out.println(logRow);
			bw.write(logRow + System.getProperty("line.separator"));

			return new ResponseEntity<>(logRow.getMessage(), getStatus(level));
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

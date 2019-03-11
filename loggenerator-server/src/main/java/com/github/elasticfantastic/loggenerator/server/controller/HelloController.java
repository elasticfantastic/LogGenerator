package com.github.elasticfantastic.loggenerator.server.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.elasticfantastic.loggenerator.LogGenerator;
import com.github.elasticfantastic.loggenerator.LogRow;
import com.github.elasticfantastic.loggenerator.server.utility.HttpUtility;
import com.github.elasticfantastic.loggenerator.utility.ParameterContainer;

@RestController
public class HelloController {

	private LogGenerator generator;

	public HelloController() {
		this.generator = new LogGenerator();
		
		this.generator.setLevelFrequency("ERROR", 0.01);
		this.generator.setLevelFrequency("WARN", 0.10);
		this.generator.setLevelFrequency("INFO", 0.34);
		this.generator.setLevelFrequency("DEBUG", 0.55);
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ResponseEntity<Object> order(HttpServletRequest request) throws IOException {
		String logFile = ParameterContainer.getParameter("logFile");
		String id = ParameterContainer.getParameter("id");

		// Generate request output
		String level = "INFO";
		String message = "Received request from " + request.getRemoteAddr() + ":" + request.getRemotePort();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			LogRow logRow = new LogRow(id, level, ZonedDateTime.now(), message);
			System.out.println(logRow);
			bw.write(logRow + System.getProperty("line.separator"));
		}

		// Generate response output
		Map<String, Object> inputs = new HashMap<>();
		inputs.put("id", id);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			LogRow logRow = this.generator.getLog(ZonedDateTime.now(), inputs);
			System.out.println(logRow);
			bw.write(logRow + System.getProperty("line.separator"));

			return new ResponseEntity<>(logRow.getMessage(), HttpUtility.getStatus(logRow.getLevel()));
		}
	}

}

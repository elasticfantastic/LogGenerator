package com.github.elasticfantastic.loggenerator.server.exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.github.elasticfantastic.loggenerator.core.LogGenerator;
import com.github.elasticfantastic.loggenerator.core.LogRow;
import com.github.elasticfantastic.loggenerator.core.utility.ParameterContainer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler for controllers.
 * 
 * @author Daniel Nilsson
 */
@RestControllerAdvice
public class RestExceptionHandler {

	private LogGenerator generator;

	public RestExceptionHandler() {
		this.generator = new LogGenerator();
	}

	@RequestMapping(produces = "text/plain")
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleException(Throwable t) {
		String logFile = ParameterContainer.getParameter("logFile");

		Map<String, Object> inputs = new HashMap<>();
		inputs.put("id", ParameterContainer.getParameter("id"));
		inputs.put("level", "ERROR");
		inputs.put("text", t.getMessage());

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			LogRow logRow = this.generator.getLog(ZonedDateTime.now(), inputs);
			System.out.println(logRow);
			bw.write(logRow + System.getProperty("line.separator"));
			return new ResponseEntity<String>(logRow.getPayload(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
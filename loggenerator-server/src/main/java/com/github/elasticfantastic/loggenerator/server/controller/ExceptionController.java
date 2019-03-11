package com.github.elasticfantastic.loggenerator.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	public ResponseEntity<Object> exception(HttpServletRequest request) throws Exception {
		throw new Exception("ExceptionController with mapping /exception threw an exception, just as expected");
	}

}

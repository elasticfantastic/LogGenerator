package com.github.elasticfantastic.loggenerator.server.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.elasticfantastic.loggenerator.LogGenerator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private LogGenerator generator;

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public ResponseEntity<Object> hello(HttpServletRequest request) {
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("id", "Server");
        inputs.put("level", "INFO");
        inputs.put("message", "Received request from " + request.getRemoteAddr() + ":" + request.getRemotePort());
        
        this.generator = new LogGenerator(LocalDateTime.now());
        
        System.out.println(this.generator.getLog(inputs));

        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    // @RequestMapping(value = "/hello")
    // public ResponseEntity<Object> getProduct() {
    // return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    // }

}

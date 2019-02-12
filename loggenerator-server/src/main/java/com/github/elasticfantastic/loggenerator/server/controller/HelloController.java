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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private LogGenerator generator;

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public ResponseEntity<Object> hello(HttpServletRequest request) throws IOException {
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("id", "Server");
        //inputs.put("level", "INFO");
        inputs.put("message", "Received request from " + request.getRemoteAddr() + ":" + request.getRemotePort());
        
        this.generator = new LogGenerator(LocalDateTime.now());
        
        this.generator.setLevelFrequency("ERROR", 0.05);
        this.generator.setLevelFrequency("WARN", 0.10);
        this.generator.setLevelFrequency("INFO", 0.30);
        this.generator.setLevelFrequency("DEBUG", 0.55);
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log_client.txt", true))) {
        	LogRow logRow = this.generator.getLog(inputs);
        	
        	System.out.println(logRow);
        	bw.write(logRow + System.getProperty("line.separator"));
        }

        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    // @RequestMapping(value = "/hello")
    // public ResponseEntity<Object> getProduct() {
    // return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    // }

}

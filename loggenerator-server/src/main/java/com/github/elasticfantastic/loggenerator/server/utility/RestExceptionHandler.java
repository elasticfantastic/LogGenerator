package com.github.elasticfantastic.loggenerator.server.utility;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.elasticfantastic.loggenerator.LogGenerator;
import com.github.elasticfantastic.loggenerator.LogRow;
import com.github.elasticfantastic.loggenerator.utility.ParameterContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private LogGenerator generator;
    
    @RequestMapping(produces = "application/json")
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleException(Throwable ex) {
        String logFile = ParameterContainer.getParameter("logFile");
        
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("id", ParameterContainer.getParameter("id"));
        inputs.put("level", "ERROR");
        
        try {
            LogRow logRow = this.generator.getLog(ZonedDateTime.now(), inputs);
            return new ResponseEntity<String>(logRow.getPayload(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
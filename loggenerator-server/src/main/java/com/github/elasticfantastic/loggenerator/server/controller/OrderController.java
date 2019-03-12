package com.github.elasticfantastic.loggenerator.server.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.elasticfantastic.loggenerator.core.LogGenerator;
import com.github.elasticfantastic.loggenerator.core.LogRow;
import com.github.elasticfantastic.loggenerator.core.database.model.Order;
import com.github.elasticfantastic.loggenerator.core.utility.DatabaseUtility;
import com.github.elasticfantastic.loggenerator.core.utility.ParameterContainer;
import com.github.elasticfantastic.loggenerator.core.utility.http.HttpUtility;
import com.github.elasticfantastic.loggenerator.core.utility.json.JsonUtility;

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
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity<Object> addOrder(HttpServletRequest request) throws IOException {
        String logFile = ParameterContainer.getParameter("logFile");
        String id = ParameterContainer.getParameter("id");

        // Generate request output
        String remoteAddress = request.getRemoteAddr();
        int remotePort = request.getRemotePort();
        String textRequest = String.format("Received order request from %s:%d", remoteAddress, remotePort);

        LogRow logRowRequest = new LogRow(id, "INFO", ZonedDateTime.now(), textRequest);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
            System.out.println(logRowRequest);
            bw.write(logRowRequest + System.getProperty("line.separator"));
        }

        // Generate response output

        // Do the database stuff
        Order order = this.dbUtility.placeRandomOrder(LocalDateTime.now());
        double orderPrice = order.getTotalPrice();

        // Modify log row contents
        String orderAsJson = JsonUtility.toJson(order);
        String customerName = order.getCustomer().getName();

        String textResponse = "Customer " + customerName + " placed an order worth " + orderPrice;
        LogRow logRowResponse = new LogRow(id, "INFO", ZonedDateTime.now(), textResponse);
        logRowResponse.setPayload(orderAsJson);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
            System.out.println(logRowResponse);
            bw.write(logRowResponse + System.getProperty("line.separator"));

            HttpStatus status = HttpUtility.toStatus(logRowResponse.getLevel());
            return new ResponseEntity<>("Order for " + customerName + " was placed", status);
        }
    }

}

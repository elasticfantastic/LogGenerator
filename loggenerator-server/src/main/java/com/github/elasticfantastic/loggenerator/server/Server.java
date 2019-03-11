package com.github.elasticfantastic.loggenerator.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.elasticfantastic.loggenerator.utility.ParameterContainer;

@SpringBootApplication
public class Server {

    public static void main(String[] args) {
        String id = "Server1";
        String logFile = "log_server1.txt";
        if (args.length >= 1) {
            System.out.println(args[0]);
            id = (args[0] != null ? args[0] : id);
        }
        if (args.length >= 2) {
            System.out.println(args[1]);
            logFile = (args[1] != null ? args[1] : logFile);
        }
        ParameterContainer.putParameter("id", id);
        ParameterContainer.putParameter("logFile", logFile);
        SpringApplication.run(Server.class, args);
    }

}

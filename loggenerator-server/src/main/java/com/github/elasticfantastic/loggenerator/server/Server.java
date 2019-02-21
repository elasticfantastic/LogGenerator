package com.github.elasticfantastic.loggenerator.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.elasticfantastic.loggenerator.utility.ParameterContainer;

@SpringBootApplication
public class Server {

    public static void main(String[] args) {
    	String logFile = "log_server1.txt";
		if (args.length >= 1) {
			System.out.println(args[0]);
			logFile = (args[0] != null ? args[0] : logFile);
			ParameterContainer.putParameter("logFile", logFile);
		}
        SpringApplication.run(Server.class, args);
    }

}

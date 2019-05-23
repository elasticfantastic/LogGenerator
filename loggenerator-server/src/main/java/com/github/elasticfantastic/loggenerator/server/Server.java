package com.github.elasticfantastic.loggenerator.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.elasticfantastic.loggenerator.core.LogGenerator;
import com.github.elasticfantastic.loggenerator.core.utility.ParameterContainer;
import com.github.elasticfantastic.loggenerator.server.thread.BackgroundJob;

@SpringBootApplication
public class Server {

    public static void main(String[] args) {
        // Code for starting a HTTP server with Spring Boot
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

        // Start second thread on server
        LogGenerator generator = new LogGenerator();

        generator.setLevelFrequency("ERROR", 0.01);
        generator.setLevelFrequency("WARN", 0.10);
        generator.setLevelFrequency("INFO", 0.34);
        generator.setLevelFrequency("DEBUG", 0.55);

        Runnable job = new BackgroundJob(generator);
        Thread thread = new Thread(job);
        thread.start();
    }

}

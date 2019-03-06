package com.github.elasticfantastic.loggenerator.test;

import java.io.IOException;

import com.github.elasticfantastic.loggenerator.database.service.CustomerService;

public class Main {

	public static void main(String[] args) throws IOException {
//        LogGenerator generator = new LogGenerator();
//        
//        generator.setIdFrequency("Client", 0.10);
//        generator.setIdFrequency("Server", 0.60);
//        generator.setIdFrequency("Middleware", 0.30);
//        
//        generator.setLevelFrequency("ERROR", 0.05);
//        generator.setLevelFrequency("WARN", 0.10);
//        generator.setLevelFrequency("INFO", 0.30);
//        generator.setLevelFrequency("DEBUG", 0.55);
//        
//        System.out.println(generator.getLevelFrequencies());
//        System.out.println(generator.getIdFrequencies());
//        
//        System.out.println(generator.getRandomLevel("INFO", "ERROR"));
//        
//        ZonedDateTime time3 = ZonedDateTime.of(2018, 12, 31, 00, 00, 00, 00, ZoneId.systemDefault());
//        ZonedDateTime time4 = ZonedDateTime.of(2018, 01, 01, 00, 00, 00, 00, ZoneId.systemDefault());
//        
//        System.out.println(time3);
//        System.out.println(time4);
//        
//        System.out.println(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(time3));
//        System.out.println(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(time4));
//        
//        for (int i = 0; i < 10; i++) {
//        	System.out.println(generator.getLog(time3, time4, ZoneId.of("Europe/Stockholm"), new HashMap<>()));
//        }

		CustomerService cs = new CustomerService();
//    	Collection<Customer> customers = cs.findAll();
//    	for (Customer c : customers) {
//    		System.out.println(c.getName());
//    	}

		// System.out.println(cs.findById("16081216-2816").getOrder(12).getOrderLines().stream().findFirst().get().getProduct().getName());

		System.out.println(cs.findAllSsns());

	}

}

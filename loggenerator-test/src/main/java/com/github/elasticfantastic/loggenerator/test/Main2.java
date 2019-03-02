package com.github.elasticfantastic.loggenerator.test;

import java.awt.font.GraphicAttribute;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.persistence.internal.oxm.conversion.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.elasticfantastic.loggenerator.database.model.Customer;
import com.github.elasticfantastic.loggenerator.database.model.Order;
import com.github.elasticfantastic.loggenerator.database.model.Product;
import com.github.elasticfantastic.loggenerator.database.service.CustomerService;
import com.github.elasticfantastic.loggenerator.database.service.OrderService;
import com.github.elasticfantastic.loggenerator.database.service.ProductService;
import com.github.elasticfantastic.loggenerator.utility.CollectionUtility;

public class Main2 {

	private CustomerService customerService;
	private OrderService orderService;
	private ProductService productService;

	public static void main(String[] args) throws Exception {
		Main2 r = new Main2();
		r.run();
	}

	public Main2() {
		this.customerService = new CustomerService();
		this.orderService = new OrderService();
		this.productService = new ProductService();
	}

	public void run() throws JsonProcessingException {
		Random random = new Random();

		String ssn = "16081216-2816";
		Customer customer = customerService.findById(ssn);

		System.out.println(customer.toString());

		List<Product> products = new ArrayList<>(productService.findAll());

		System.out.println("---");

		List<Product> randomizedProducts = CollectionUtility.getRandom(products, random.nextInt(4) + 1);

		System.out.println("RP: " + randomizedProducts.size());

		System.out.println("Adding order");

		Order order = new Order(LocalDateTime.now());
		customer.addOrder(order);
		customerService.update(customer);

		for (Product product : randomizedProducts) {
			order.addProduct(product, random.nextInt(5) + 1);
		}

		System.out.println("Updating");

		customerService.update(customer);

		System.out.println("Serializing");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		String s = mapper.writeValueAsString(order);
		System.out.println(s);

		String encoded = new String(Base64.base64Encode(s.getBytes()));
		System.out.println(encoded);
	}

}

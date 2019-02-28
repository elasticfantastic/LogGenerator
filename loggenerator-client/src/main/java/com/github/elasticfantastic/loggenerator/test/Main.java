package com.github.elasticfantastic.loggenerator.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.elasticfantastic.loggenerator.database.model.Customer;
import com.github.elasticfantastic.loggenerator.database.model.Order;
import com.github.elasticfantastic.loggenerator.database.model.OrderLine;
import com.github.elasticfantastic.loggenerator.database.model.Product;
import com.github.elasticfantastic.loggenerator.database.service.CustomerService;
import com.github.elasticfantastic.loggenerator.database.service.OrderService;
import com.github.elasticfantastic.loggenerator.database.service.ProductService;
import com.github.elasticfantastic.loggenerator.utility.CollectionUtility;

public class Main {

	private CustomerService customerService;
	private OrderService orderService;
	private ProductService productService;

	public static void main(String[] args) {
		Main r = new Main();
		r.run();
	}

	public Main() {
		this.customerService = new CustomerService();
		this.orderService = new OrderService();
		this.productService = new ProductService();
	}

	public void run() {
		Random random = new Random();
		
		String ssn = "16081216-2816";
		Customer customer = customerService.findById(ssn);

		System.out.println(customer.toString());
		System.out.println("O: " + customer.getOrders().size());

		List<Product> products = new ArrayList<>(productService.findAll());
		List<Product> randomizedProducts = CollectionUtility.getRandom(products, random.nextInt(4) + 1);

		System.out.println("RP: " + randomizedProducts.size());

		Order order = new Order(LocalDateTime.now());
		customer.addOrder(order);
		customerService.update(customer);
		
		for (Product product : randomizedProducts) {
			order.addProduct(product, random.nextInt(5) + 1);
		}
		
		customerService.update(customer);
		
//		for (Product product : randomizedProducts) {
//			OrderLine orderLine = new OrderLine(order, product, random.nextInt(3) + 1);
//			order.addOrderLine(orderLine);
//			System.out.println("COLS: " + order.getOrderLines().size());
//		}
//		System.out.println("OL: " + order.getOrderLines().size());



		System.out.println("O: " + customer.getOrders().size());

		

		System.out.println("NO: " + customerService.findById(ssn).getOrders().size());
	}

}

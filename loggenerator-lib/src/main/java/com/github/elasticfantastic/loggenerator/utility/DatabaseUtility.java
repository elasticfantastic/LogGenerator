package com.github.elasticfantastic.loggenerator.utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.github.elasticfantastic.loggenerator.database.model.Customer;
import com.github.elasticfantastic.loggenerator.database.model.Order;
import com.github.elasticfantastic.loggenerator.database.model.Product;
import com.github.elasticfantastic.loggenerator.database.service.CustomerService;
import com.github.elasticfantastic.loggenerator.database.service.ProductService;

public class DatabaseUtility {

	private CustomerService customerService;
	private ProductService productService;
	
	public DatabaseUtility() {
		this.customerService = new CustomerService();
		this.productService = new ProductService();
	}
	
	public Order placeRandomOrder(LocalDateTime time) {
		Random random = new Random();
		
		Customer customer = CollectionUtility.getRandom(customerService.findAll());
		
		List<Product> products = new ArrayList<>(productService.findAll());
		List<Product> randomizedProducts = CollectionUtility.getRandom(products, random.nextInt(3) + 1);
		
		Order order = new Order(time);
		customer.addOrder(order);
		customerService.update(customer);

		for (Product product : randomizedProducts) {
			order.addProduct(product, random.nextInt(3) + 1);
		}
		customerService.update(customer);
		
		return order;
	}
	
}

package com.github.elasticfantastic.loggenerator.utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.elasticfantastic.loggenerator.comparator.Ordering;
import com.github.elasticfantastic.loggenerator.comparator.ProductPriceComparator;
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
		
		List<Customer> customers = new ArrayList<>(customerService.findAll());
		Customer randomizedCustomer = CollectionUtility.getRandom(customers);

		List<Product> products = new ArrayList<>(productService.findAll());
		Collections.sort(products, new ProductPriceComparator(Ordering.DESCENDING));

		Map<Product, Integer> loadFactors = new HashMap<>();
		int factor = 1;
		for (Product p : products) {
			loadFactors.put(p, factor);
			factor += 1;
		}
		
		List<Product> randomizedProducts = CollectionUtility.getRandom(products, random.nextInt(3) + 1, loadFactors);

		Order order = new Order(time);
		randomizedCustomer.addOrder(order);
		customerService.update(randomizedCustomer);

		for (Product product : randomizedProducts) {
			order.addProduct(product, random.nextInt(4) + 1);
		}
		customerService.update(randomizedCustomer);

		return order;
	}

}

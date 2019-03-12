package com.github.elasticfantastic.loggenerator.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.elasticfantastic.loggenerator.core.comparator.Ordering;
import com.github.elasticfantastic.loggenerator.core.comparator.ProductPriceComparator;
import com.github.elasticfantastic.loggenerator.core.database.model.Product;
import com.github.elasticfantastic.loggenerator.core.database.service.ProductService;

public class Main {
	
	public static void main(String[] args) throws IOException {
		ProductService productService = new ProductService();
		List<Product> products = new ArrayList<>(productService.findAll());
		
		// Should be unsorted
		for (Product p : products) {
			System.out.println(p.getName() + ": " + p.getPrice());
		}
		
		System.out.println("-----------");
		
		// Should be sorted ascending by price
		Collections.sort(products, new ProductPriceComparator(Ordering.ASCENDING));
		for (Product p : products) {
			System.out.println(p.getName() + ": " + p.getPrice());
		}
		
		System.out.println("-----------");
		
		// Should be sorted descending by price
		Collections.sort(products, new ProductPriceComparator(Ordering.DESCENDING));
		for (Product p : products) {
			System.out.println(p.getName() + ": " + p.getPrice());
		}
		
	}

}

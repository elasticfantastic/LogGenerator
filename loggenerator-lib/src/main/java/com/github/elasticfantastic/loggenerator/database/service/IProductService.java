package com.github.elasticfantastic.loggenerator.database.service;

import java.util.Collection;

import com.github.elasticfantastic.loggenerator.database.model.Customer;
import com.github.elasticfantastic.loggenerator.database.model.Product;

public interface IProductService {

	Product findById(String nbr);
	
	Collection<Product> findAll();
	
	// Product update(Customer customer);
	
}
package com.github.elasticfantastic.loggenerator.core.database.service;

import java.util.Collection;

import com.github.elasticfantastic.loggenerator.core.database.model.Product;

public interface IProductService {

	Product findById(String nbr);

	Collection<Product> findAll();

	// Product update(Customer customer);

}

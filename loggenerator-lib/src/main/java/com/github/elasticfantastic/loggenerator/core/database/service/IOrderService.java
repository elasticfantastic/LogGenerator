package com.github.elasticfantastic.loggenerator.core.database.service;

import java.util.Collection;

import com.github.elasticfantastic.loggenerator.core.database.model.Order;

public interface IOrderService {

	void add(Order order);

	Collection<Order> findAll();

	// Collection<Order> findBySsn(String ssn);

}

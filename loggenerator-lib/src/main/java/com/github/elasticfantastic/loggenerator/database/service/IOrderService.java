package com.github.elasticfantastic.loggenerator.database.service;

import java.util.Collection;

import com.github.elasticfantastic.loggenerator.database.model.Order;

public interface IOrderService {

	Collection<Order> findAll();
	
}

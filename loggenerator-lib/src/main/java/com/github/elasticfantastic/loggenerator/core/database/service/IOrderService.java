package com.github.elasticfantastic.loggenerator.core.database.service;

import java.util.Collection;

import com.github.elasticfantastic.loggenerator.core.database.model.Order;

public interface IOrderService {

    /**
     * Add an order.
     * 
     * @param order
     *            the order to add
     */
    void add(Order order);

    /**
     * Fetch all the orders.
     * 
     * @return all the orders
     */
    Collection<Order> findAll();

}

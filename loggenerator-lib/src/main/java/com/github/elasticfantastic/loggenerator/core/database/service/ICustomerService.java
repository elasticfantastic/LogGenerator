package com.github.elasticfantastic.loggenerator.core.database.service;

import java.util.Collection;

import com.github.elasticfantastic.loggenerator.core.database.model.Customer;

/**
 * Definition for database manipulation methods for customers.
 * 
 * @author Daniel Nilsson
 */
public interface ICustomerService {

    /**
     * Fetch the customer with the specified social security number.
     * 
     * @param ssn
     *            the social security number
     * @return the customer
     */
    Customer findById(String ssn);

    /**
     * Fetch all the customers.
     * 
     * @return all the customers
     */
    Collection<Customer> findAll();

    /**
     * Update a specific customer.
     * 
     * @param customer
     *            the customer to update
     * @return the updated customer
     */
    Customer update(Customer customer);

}

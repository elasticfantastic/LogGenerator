package com.github.elasticfantastic.loggenerator.core.database.service;

import java.util.Collection;

import com.github.elasticfantastic.loggenerator.core.database.model.Customer;

public interface ICustomerService {

	Customer findById(String ssn);

	Collection<Customer> findAll();

	Collection<String> findAllSsns();

	Customer update(Customer customer);

}

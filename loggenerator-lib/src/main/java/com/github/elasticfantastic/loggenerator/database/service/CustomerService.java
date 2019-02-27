package com.github.elasticfantastic.loggenerator.database.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.github.elasticfantastic.loggenerator.database.model.Customer;

public class CustomerService implements ICustomerService {

	private EntityManagerFactory entityManagerFactory;

	public CustomerService() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("ElasticFantasticDS");
	}
	
	@Override
	public Customer findById(String ssn) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager.find(Customer.class, ssn);
	}

	@Override
	public Collection<Customer> findAll() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		TypedQuery<Customer> tq = entityManager.createNamedQuery("Customer.findAll", Customer.class);
		return tq.getResultList();
	}
	
	@Override
	public Collection<String> findAllSsns() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		TypedQuery<String> tq = entityManager.createNamedQuery("Customer.findAllSsns", String.class);
		return tq.getResultList();
	}

	@Override
	public Customer update(Customer customer) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.merge(customer);
		return customer;
	}

}
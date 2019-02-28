package com.github.elasticfantastic.loggenerator.database.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.github.elasticfantastic.loggenerator.database.model.Order;

public class OrderService implements IOrderService {

	private EntityManagerFactory entityManagerFactory;

	public OrderService() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("ElasticFantasticDS");
	}
	
	@Override
	public void add(Order order) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.persist(order);
	}
	
	@Override
	public Collection<Order> findAll() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		TypedQuery<Order> tq = entityManager.createNamedQuery("Order.findAll", Order.class);
		return tq.getResultList();
	}

//	@Override
//	public Collection<Order> findBySsn(String ssn) {
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		TypedQuery<Order> tq = entityManager.createNamedQuery("Order.findBySsn", Order.class);
//		tq.setParameter("ssn", ssn);
//		return tq.getResultList();
//	}
	
}

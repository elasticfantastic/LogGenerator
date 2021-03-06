package com.github.elasticfantastic.loggenerator.core.database.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.github.elasticfantastic.loggenerator.core.database.model.Customer;
import com.github.elasticfantastic.loggenerator.core.database.model.Order;

/**
 * Database manipulation methods for customers.
 * 
 * @author Daniel Nilsson
 */
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
    public Customer update(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        for (Order order : customer.getOrders()) {
            if (order.getNbr() < 0) {
                entityManager.persist(order);
            }
        }

        entityManager.merge(customer);
        transaction.commit();

        return customer;
    }

}

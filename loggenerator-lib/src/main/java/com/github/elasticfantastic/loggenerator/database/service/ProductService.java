package com.github.elasticfantastic.loggenerator.database.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.github.elasticfantastic.loggenerator.database.model.Product;

public class ProductService implements IProductService {

	private EntityManagerFactory entityManagerFactory;

	public ProductService() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("ElasticFantasticDS");
	}

	@Override
	public Product findById(String nbr) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager.find(Product.class, nbr);
	}

	@Override
	public Collection<Product> findAll() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		TypedQuery<Product> tq = entityManager.createNamedQuery("Product.findAll", Product.class);
		return tq.getResultList();
	}

}

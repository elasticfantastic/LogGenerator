package com.github.elasticfantastic.loggenerator.core.database.service;

import java.util.Collection;

import com.github.elasticfantastic.loggenerator.core.database.model.Product;

public interface IProductService {

    /**
     * Find the product with the specified number.
     * 
     * @param nbr
     *            the number
     * @return the product
     */
    Product findById(String nbr);

    /**
     * Fetch all the products.
     * 
     * @return all the products
     */
    Collection<Product> findAll();

}

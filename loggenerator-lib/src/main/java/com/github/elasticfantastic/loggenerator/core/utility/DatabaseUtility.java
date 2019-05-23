package com.github.elasticfantastic.loggenerator.core.utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.elasticfantastic.loggenerator.core.comparator.Ordering;
import com.github.elasticfantastic.loggenerator.core.comparator.ProductPriceComparator;
import com.github.elasticfantastic.loggenerator.core.database.model.Customer;
import com.github.elasticfantastic.loggenerator.core.database.model.Order;
import com.github.elasticfantastic.loggenerator.core.database.model.Product;
import com.github.elasticfantastic.loggenerator.core.database.service.CustomerService;
import com.github.elasticfantastic.loggenerator.core.database.service.ProductService;

/**
 * Utility methods for database operations.
 * 
 * @author Daniel Nilsson
 */
public class DatabaseUtility {

    private CustomerService customerService;
    private ProductService productService;

    public DatabaseUtility() {
        this.customerService = new CustomerService();
        this.productService = new ProductService();
    }

    /**
     * Places a random order and inserts this to the database.
     * 
     * @param time
     *            the time the order is placed
     * @return the inserted order
     */
    public Order placeRandomOrder(LocalDateTime time) {
        Random random = new Random();

        // Get a random customer
        List<Customer> customers = new ArrayList<>(customerService.findAll());
        Customer randomizedCustomer = CollectionUtility.getRandom(customers);

        List<Product> products = new ArrayList<>(productService.findAll());
        Collections.sort(products, new ProductPriceComparator(Ordering.DESCENDING));

        // Generate load factors for each product to mix up results
        Map<Product, Integer> loadFactors = new HashMap<>();
        int factor = 1;
        for (Product p : products) {
            loadFactors.put(p, factor);
            factor += 1;
        }

        // Get some random products
        List<Product> randomizedProducts = CollectionUtility.getRandom(products, random.nextInt(3) + 1, loadFactors);

        // Do the database stuff!
        Order order = new Order(time);
        randomizedCustomer.addOrder(order);
        customerService.update(randomizedCustomer);

        for (Product product : randomizedProducts) {
            order.addProduct(product, random.nextInt(4) + 1);
        }
        customerService.update(randomizedCustomer);

        return order;
    }

}

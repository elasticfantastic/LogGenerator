package com.github.elasticfantastic.loggenerator.core.database.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.DoubleStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * A class which encapsulates behavior for an order.
 * 
 * @author Daniel Nilsson
 */
@Entity
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
@Table(name = "Orderr")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderNbr")
    private int nbr;

    @Column(name = "time")
    private LocalDateTime time;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerSSN", referencedColumnName = "ssn", nullable = false)
    private Customer customer;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
    private Collection<OrderLine> products;

    public Order() {
        this.nbr = Integer.MIN_VALUE;
        this.products = new ArrayList<>();
    }

    public Order(LocalDateTime time) {
        this();
        this.time = time;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        if (!customer.getOrders().contains(this)) {
            customer.addOrder(this);
        }
    }

    public Collection<OrderLine> getProducts() {
        return products;
    }

    public void addProduct(Product product, int quantity) {
        OrderLine orderLine = new OrderLine();
        orderLine.setOrder(this);
        orderLine.setProduct(product);
        orderLine.setOrderNbr(this.getNbr());
        orderLine.setProductNbr(product.getNbr());
        orderLine.setQuantity(quantity);

        if (!products.contains(orderLine)) {
            products.add(orderLine);
            product.getOrders().add(orderLine);
        }
    }

    /**
     * Returns the sum of every order line belonging to this order.
     * 
     * @return the sum of every order line
     */
    public double getTotalPrice() {
        return products.stream().flatMapToDouble(x -> DoubleStream.of(x.getProduct().getPrice())).sum();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Order [nbr=" + nbr + ", time=" + time + "]";
    }

}

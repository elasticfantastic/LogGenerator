package com.github.elasticfantastic.loggenerator.core.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * A class which encapsulates behavior for an order line. Pure JPA fluff to enable
 * many-to-many association.
 * 
 * @author Daniel Nilsson
 */
@Entity
@Table(name = "OrderLine")
@IdClass(OrderLineId.class)
public class OrderLine {

    @Id
    @Column(name = "orderNbr")
    private int orderNbr;

    @Id
    @Column(name = "productNbr")
    private int productNbr;

    @Column(name = "quantity")
    private int quantity;

    @JsonBackReference
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "orderNbr", referencedColumnName = "orderNbr")
    private Order order;

    @JsonBackReference
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "productNbr", referencedColumnName = "productNbr")
    private Product product;

    public OrderLine() {

    }

    public long getOrderNbr() {
        return orderNbr;
    }

    public void setOrderNbr(int orderNbr) {
        this.orderNbr = orderNbr;
    }

    public int getProductNbr() {
        return productNbr;
    }

    public void setProductNbr(int productNbr) {
        this.productNbr = productNbr;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderLine2 [orderNbr=" + orderNbr + ", productNbr=" + productNbr + ", quantity=" + quantity + "]";
    }

}

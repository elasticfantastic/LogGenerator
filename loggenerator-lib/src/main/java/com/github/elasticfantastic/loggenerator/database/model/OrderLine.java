package com.github.elasticfantastic.loggenerator.database.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "OrderLine.findAll", query = "SELECT ol FROM OrderLine ol")
@Table(name = "Order_Line")
public class OrderLine {

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "orderNbr", column = @Column(name = "orderNbr")),
			@AttributeOverride(name = "articleNbr", column = @Column(name = "articleNbr")) })
	private OrderLinePK orderLinePK;

	@Column(name = "quantity")
	private int quantity;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "orderNbr", referencedColumnName = "orderNbr", insertable = false, updatable = false, nullable = false)
	private Order order;
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "articleNbr", referencedColumnName = "articleNbr", insertable = false, updatable = false, nullable = false)
	private Product product;

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
		return "OrderLine [quantity=" + quantity + "]";
	}

}

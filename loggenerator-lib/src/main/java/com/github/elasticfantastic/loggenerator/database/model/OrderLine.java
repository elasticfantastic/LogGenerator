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
	
	public OrderLine() {
		
	}

	public OrderLine(Order order, Product product, int quantity) {
		this.orderLinePK = new OrderLinePK(order.getNbr(), product.getNbr());
		System.out.println(this.orderLinePK);
		
		setOrder(order);
//		if (!order.getOrderLines().contains(this)) {
//			order.addOrderLine(this);
//		}

		setProduct(product);
//		if (!product.getOrderLines().contains(this)) {
//			product.addOrderLine(this);	
//		}

		this.quantity = quantity;
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
		this.orderLinePK.setOrderNbr(order.getNbr());
		if (!order.getOrderLines().contains(this)) {
			order.addOrderLine(this);
		}
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		this.orderLinePK.setArticleNbr(product.getNbr());
		if (!product.getOrderLines().contains(this)) {
			product.addOrderLine(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + quantity;
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
		OrderLine other = (OrderLine) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderLine [quantity=" + quantity + "]";
	}

}

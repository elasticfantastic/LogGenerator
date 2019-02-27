package com.github.elasticfantastic.loggenerator.database.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
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

@Entity
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
@NamedQuery(name = "Order.findBySsn", query = "SELECT o FROM Order o WHERE o.customer.ssn = :ssn")
@Table(name = "Orderr")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderNbr")
	private int nbr;

	@Column(name = "time")
	private LocalDateTime time;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "customerSSN", referencedColumnName = "ssn", nullable = false)
	private Customer customer;

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
	private Collection<OrderLine> orderLines;

	public Order() {
		this.orderLines = new ArrayList<>();
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

	public Collection<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(Collection<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public void addOrderLine(OrderLine orderLine) {
		if (!orderLines.contains(orderLine)) {
			System.out.println("no contains");
			orderLines.add(orderLine);
		}
		if (orderLine.getOrder() == null) {
			orderLine.setOrder(this);
		}
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

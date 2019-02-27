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

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "customerSSN", referencedColumnName = "ssn", nullable = false)
	private Customer customer;
	
	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.EAGER, mappedBy = "order", orphanRemoval = true)
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
	}

	public Collection<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(Collection<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	
	public void addOrderLine(OrderLine orderLine) {
		orderLines.add(orderLine);
	}

	@Override
	public String toString() {
		return "Order [nbr=" + nbr + ", time=" + time + "]";
	}
	
}

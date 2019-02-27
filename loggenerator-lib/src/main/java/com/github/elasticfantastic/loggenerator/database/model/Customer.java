package com.github.elasticfantastic.loggenerator.database.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
@NamedQuery(name = "Customer.findAllSsns", query = "SELECT c.ssn FROM Customer c")
@Table(name = "Customer")
public class Customer {

	@Id
	@Column(name = "ssn")
	private String ssn;

	@Column(name = "name")
	private String name;

	@Column(name = "street")
	private String street;

	@Column(name = "zip")
	private String zip;

	@Column(name = "city")
	private String city;

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.EAGER, mappedBy = "customer", orphanRemoval = true)
	private Collection<Order> orders;

	public Customer() {
		this.orders = new ArrayList<>();
	}

	public Customer(String ssn, String name, String street, String zip, String city) {
		this();
		this.ssn = ssn;
		this.name = name;
		this.street = street;
		this.zip = zip;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public Collection<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
	
	public Order getOrder(String orderNbr) {
		return orders.stream().filter(x -> orderNbr.equals(x.getNbr())).findFirst().orElse(null);
	}

	@Override
	public String toString() {
		return "Customer [ssn=" + ssn + ", name=" + name + ", street=" + street + ", zip=" + zip + ", city=" + city
				+ "]";
	}

}

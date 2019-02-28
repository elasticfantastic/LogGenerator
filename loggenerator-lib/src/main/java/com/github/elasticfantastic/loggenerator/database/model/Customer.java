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

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "customer", orphanRemoval = true)
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

	public void addOrder(Order order) {
		if (!orders.contains(order)) {
			orders.add(order);
		}
		if (order.getCustomer() == null) {
			order.setCustomer(this);
		}
	}

//	public Order getOrder(int orderNbr) {
//		return orders.stream().filter(x -> orderNbr == x.getNbr()).findFirst().orElse(null);
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		Customer other = (Customer) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ssn == null) {
			if (other.ssn != null)
				return false;
		} else if (!ssn.equals(other.ssn))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [ssn=" + ssn + ", name=" + name + ", street=" + street + ", zip=" + zip + ", city=" + city
				+ "]";
	}

}

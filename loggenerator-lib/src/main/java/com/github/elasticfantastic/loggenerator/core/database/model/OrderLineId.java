package com.github.elasticfantastic.loggenerator.core.database.model;

import java.io.Serializable;

/**
 * JPA primary key for an order line
 * 
 * @author Daniel Nilsson
 */
public class OrderLineId implements Serializable {

	private static final long serialVersionUID = 6034240577926756914L;

	private int orderNbr;

	private int productNbr;

	public int hashCode() {
		return (int) (orderNbr + productNbr);
	}

	public boolean equals(Object object) {
		if (object instanceof OrderLineId) {
			OrderLineId otherId = (OrderLineId) object;
			return (otherId.orderNbr == this.orderNbr) && (otherId.productNbr == this.productNbr);
		}
		return false;
	}

}

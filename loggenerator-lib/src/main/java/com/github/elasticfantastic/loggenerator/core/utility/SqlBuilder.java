package com.github.elasticfantastic.loggenerator.core.utility;

import java.util.ArrayList;
import java.util.Collection;

import com.github.elasticfantastic.loggenerator.core.database.model.Order;
import com.github.elasticfantastic.loggenerator.core.database.model.OrderLine;

public class SqlBuilder {

	public static Collection<String> buildOrder(Order order) {
		Collection<String> sqlStatements = new ArrayList<>();

		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO Orderr (orderNbr, time, customerSSN) ");
		builder.append("VALUES ");
		builder.append("(" + order.getNbr() + ", '" + order.getTime() + "', '" + order.getCustomer().getSsn() + "');");
		sqlStatements.add(builder.toString());

		for (OrderLine line : order.getProducts()) {
			builder = new StringBuilder();
			builder.append("INSERT INTO OrderLine (orderNbr, productNbr, quantity) ");
			builder.append("VALUES ");
			builder.append("(" + line.getOrderNbr() + ", " + line.getProductNbr() + ", " + line.getQuantity() + ");");
			sqlStatements.add(builder.toString());
		}
		return sqlStatements;
	}

}

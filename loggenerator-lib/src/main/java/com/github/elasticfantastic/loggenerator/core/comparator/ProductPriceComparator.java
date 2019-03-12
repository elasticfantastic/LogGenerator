package com.github.elasticfantastic.loggenerator.core.comparator;

import java.util.Comparator;

import com.github.elasticfantastic.loggenerator.core.database.model.Product;

public class ProductPriceComparator implements Comparator<Product> {

	private Ordering ordering;

	public ProductPriceComparator(Ordering ordering) {
		this.ordering = ordering;
	}

	@Override
	public int compare(Product o1, Product o2) {
		int orderingModifier = (this.ordering == Ordering.ASCENDING ? 1 : -1);
		double priceDifference = o1.getPrice() - o2.getPrice();
		return (priceDifference > 0 ? 1 : 0) * orderingModifier;
	}

}

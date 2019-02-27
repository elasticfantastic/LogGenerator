package com.github.elasticfantastic.loggenerator.database.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderLinePK {

	@Column(name = "orderNbr", nullable = false)
	private int orderNbr;
	
	@Column(name = "articleNbr", nullable = false)
	private int articleNbr;
	
	public OrderLinePK() {
		
	}
	
	public OrderLinePK(int orderNbr, int articleNbr) {
		this.orderNbr = orderNbr;
		this.articleNbr = articleNbr;
	}

	public int getOrderNbr() {
		return orderNbr;
	}

	public void setOrderNbr(int orderNbr) {
		this.orderNbr = orderNbr;
	}

	public int getArticleNbr() {
		return articleNbr;
	}

	public void setArticleNbr(int articleNbr) {
		this.articleNbr = articleNbr;
	}

	@Override
	public String toString() {
		return "OrderLinePK [orderNbr=" + orderNbr + ", articleNbr=" + articleNbr + "]";
	}
	
}

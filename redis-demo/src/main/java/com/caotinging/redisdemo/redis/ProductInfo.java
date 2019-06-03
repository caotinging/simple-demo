package com.caotinging.redisdemo.redis;

import java.io.Serializable;

public class ProductInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String description;
	
	private String catelog;
	
	private Double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCatelog() {
		return catelog;
	}

	public void setCatelog(String catelog) {
		this.catelog = catelog;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductInfo [name=" + name + ", description=" + description + ", catelog=" + catelog + ", price="
				+ price + "]";
	}
}

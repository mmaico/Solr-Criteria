package br.com.kohen.search.entity;

import org.apache.solr.client.solrj.beans.Field;

public class ProductSearch {

	@Field
	private String id;

	@Field
	private String name;
    
	@Field
	private String description;
    
	@Field
	private String categoryName;
    
	@Field
	private Float price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	
}

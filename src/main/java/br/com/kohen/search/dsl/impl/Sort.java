package br.com.kohen.search.dsl.impl;

import org.apache.solr.client.solrj.SolrQuery.ORDER;

public class Sort {

	private String fieldName;
	
	private ORDER order;
	
	public Sort(String fieldName, ORDER order) {
		this.fieldName = fieldName;
		this.order = order;
	}
	
	public static Sort asc(String field) {
		return new Sort(field, ORDER.asc);
	}
	
	public static Sort desc(String field) {
		return new Sort(field, ORDER.desc);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public ORDER getOrder() {
		return order;
	}

	public void setOrder(ORDER order) {
		this.order = order;
	}
	
	
	
}

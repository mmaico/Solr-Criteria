package br.com.kohen.search.entity;

public class Relevance {

	private String fieldName;
	
	private String relevance;
	
	public Relevance(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public static Relevance forField(String fieldName) {
		
		return new Relevance(fieldName);
	}
	
	public Relevance that(String relevance) {
		this.relevance = "^"+relevance;
		
		return this;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getRelevance() {
		return relevance;
	}
	
	
}

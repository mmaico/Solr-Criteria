package br.com.kohen.search.dsl.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.kohen.search.dsl.FieldContext;
import br.com.kohen.search.dsl.MatchingContext;
import br.com.kohen.search.dsl.RelevanceContext;
import br.com.kohen.search.entity.Relevance;

public class FieldContextImpl implements FieldContext{

	private Set<String> fields = new HashSet<String>();
	
	private List<Relevance> fieldsRelevance = new ArrayList<Relevance>();
	
	private Map<String, Object> context;
	
	public FieldContextImpl(Map<String, Object> context) {
		this.context = context;
	}

	public MatchingContext onFields(String... fieldsName) {
		for (String field : fieldsName) {
			fields.add(field);
		}
		
		context.put(SSolrQuery.ON_FIELDS, fields);
		
		return new MatchingContextImpl(context);
	}

	public MatchingContext onFields(Set<String> fieldsName) {
		fields.addAll(fieldsName);
		
		context.put(SSolrQuery.ON_FIELDS, fields);
		
		return new MatchingContextImpl(context);
	}

	public MatchingContext allFields() {
		context.put(SSolrQuery.ON_FIELDS, fields);
		
		return new MatchingContextImpl(context);
	}

	public Map<String, Object> getContext() {
		return context;
	}
	
	public RelevanceContext add(Relevance relevance) {
		fieldsRelevance.add(relevance);
		
		context.put(SSolrQuery.FIELDS_WITH_RELEVANCE, fieldsRelevance);
		
		return new RelevanceContextImpl(context);
	}
	

}
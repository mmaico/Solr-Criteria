package br.com.kohen.search.dsl.impl;

import java.util.Map;

import br.com.kohen.search.dsl.MatchingContext;
import br.com.kohen.search.dsl.OperationsContext;

public class MatchingContextImpl implements MatchingContext {

	private Map<String, Object> context;
	
	public MatchingContextImpl(Map<String, Object> context) {
		this.context = context;
	}
	
	public OperationsContext mathing(String keyword) {
		this.context.put(SSolrQuery.MATCH, keyword);
		
		return new OperationsContextImpl(context);
	}

	public Map<String, Object> getContext() {
		return context;
	}
	
	

}
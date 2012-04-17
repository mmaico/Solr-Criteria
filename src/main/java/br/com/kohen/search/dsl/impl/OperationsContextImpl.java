package br.com.kohen.search.dsl.impl;

import java.util.Map;

import br.com.kohen.search.dsl.OperationsContext;

public class OperationsContextImpl extends FinalContextImpl implements OperationsContext {

	private Map<String, Object> context;
	
	public OperationsContextImpl(Map<String, Object> context) {
		super(context);
		this.context = context;
	}
		
	public OperationsContext groupBy(String fieldName) {
		context.put(SSolrQuery.GROUP_BY, fieldName);
		
		return this;
	}

	public OperationsContext maxResult(Integer max) {
		context.put(SSolrQuery.MAX_RESULT, max);
		
		return this;
	}

	public OperationsContext operator(String operator) {
		context.put(SSolrQuery.OPERATOR, operator);
		
		return this;
	}
	
	
	public OperationsContext firstResult(Integer first) {
		context.put(SSolrQuery.FIRST_RESULT, first);
		
		return this;
	}

	public OperationsContext orderBy(Sort order) {
		context.put(SSolrQuery.ORDER_BY, order);
		return this;
	}
	
	public OperationsContext usePrefix(){
		context.put(SSolrQuery.USE_PREFIX, true);
		
		return this;
	}
	
	

}
package br.com.kohen.search.dsl;

import br.com.kohen.search.dsl.impl.Sort;


public interface OperationsContext extends FinalContext {

	public OperationsContext orderBy(Sort sort);
		
	public OperationsContext groupBy(String fieldName);
	
	public OperationsContext maxResult(Integer max);
	
	public OperationsContext firstResult(Integer first);
	
	public OperationsContext operator(String operator);
	
	public OperationsContext usePrefix();
}
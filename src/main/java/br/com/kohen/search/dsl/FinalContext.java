package br.com.kohen.search.dsl;

import java.util.Map;

import br.com.kohen.search.dsl.impl.SolrCriteria;

public interface FinalContext {

	public SolrCriteria list();
	
	public Map<String, Object> getContext();
		
}
package br.com.kohen.search.dsl.impl;

import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;

public class SolrCriteria<T> {

	Map<String, Object> context;
	
	private SolrQuery solrQuery;
	
	
	public SolrCriteria(Map<String, Object> context, SolrQuery solrQuery) {
		this.context = context;
		this.solrQuery = solrQuery;
 	}
	
	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	public SolrQuery getSolrQuery() {
		return solrQuery;
	}

	public void setSolrQuery(SolrQuery solrQuery) {
		this.solrQuery = solrQuery;
	}

	@SuppressWarnings("rawtypes")
	public Class getTargetEntity() {
		
		if (context == null)
			return null;
		
		return (Class)context.get(SSolrQuery.TARGET_ENTITY);
	}
	
	public boolean useGroupBy() {
		
		if (context == null)
			return false;
		
		String groupFieldName = (String) context.get(SSolrQuery.GROUP_BY);
		
		if (groupFieldName == null)
			return false;
	
		return true;
		
	}
	
}

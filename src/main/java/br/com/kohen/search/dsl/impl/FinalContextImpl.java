package br.com.kohen.search.dsl.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.GroupParams;

import br.com.kohen.search.dsl.FinalContext;
import br.com.kohen.search.entity.Relevance;
import br.com.kohen.search.utils.QueryUtils;

@SuppressWarnings("unchecked")
public class FinalContextImpl implements FinalContext {

	private Map<String, Object> context;
	private SolrQuery solrQuery = new SolrQuery(); 
	
	public FinalContextImpl(Map<String, Object> context) {
		this.context = context;
	}

	@SuppressWarnings("rawtypes")
	public SolrCriteria list() {
		addFields();
		addFieldsWithRelevance();
		addGroupBy();
		addMaxResult();
		addFirstResult();
		addSort();
		
		return new SolrCriteria(context, solrQuery);
	}
	
	private void addFieldsWithRelevance() {
		
		List<Relevance> relevances = (List<Relevance>)context.get(SSolrQuery.FIELDS_WITH_RELEVANCE);
		
		if (relevances == null || relevances.isEmpty())
			return;
		
		String[] keywords = context.get(SSolrQuery.MATCH).toString().split("[\\s ]");
		
		addQuery(QueryUtils.mountRelevanceQuery(keywords, relevances, getOperator(), usePrefix()));
		
	}

	public void addFields() {
		Set<String> fields = (Set<String>) context.get(SSolrQuery.ON_FIELDS);
		String[] keywords = context.get(SSolrQuery.MATCH).toString().split("[\\s ]");
		Object relevance = context.get(SSolrQuery.FIELDS_WITH_RELEVANCE);
		
		if ((fields == null || fields.isEmpty()) && relevance == null) {
			
			addQuery(QueryUtils.mountQueryNoFields(keywords, getOperator(), usePrefix()));
			return;
		}
		 
		if (fields == null)
			return;
		
		addQuery(QueryUtils.mountQuery(keywords, fields, getOperator(), usePrefix()));
	}
	
	public void addSort() {
		Sort sort = (Sort)context.get(SSolrQuery.ORDER_BY);
		
		if (sort == null)
			return;
		
		
		solrQuery.addSortField(sort.getFieldName(), sort.getOrder());
		
		addQuery("sort=" + sort.getFieldName() + "+" + sort.getOrder().toString());
	}
	
	public void addMaxResult() {
		Integer maxResult = (Integer)context.get(SSolrQuery.MAX_RESULT);
		
		if (maxResult == null)
			return;

		solrQuery.set(CommonParams.ROWS, maxResult);
		addQuery("rows=" + maxResult );
	}
	
	public void addFirstResult() {
		Integer firstResult = (Integer)context.get(SSolrQuery.FIRST_RESULT);
		
		if (firstResult == null)
			return;
		
		solrQuery.set(CommonParams.START, firstResult);
		addQuery("start=" + firstResult );
	}
	
	public void addGroupBy() {
		String fieldGroupBy = (String)context.get(SSolrQuery.GROUP_BY);
		
		if (fieldGroupBy == null)
			return;
		
		solrQuery.set(GroupParams.GROUP, true);
		solrQuery.set(GroupParams.GROUP_FIELD, fieldGroupBy);
		addQuery("group.field=" + fieldGroupBy + "&group=true");
	}
	
	public Map<String, Object> getContext() {
		return context;
	}
	
	private void addQuery(String query) {
		
		if (solrQuery.getQuery() == null) {
			solrQuery.set(CommonParams.Q, query);
		} else {
			solrQuery.set(CommonParams.Q, solrQuery.getQuery() +"&" + query);
		}
	}
	
	
	private String getOperator() {
		String operator = (String)context.get(SSolrQuery.OPERATOR);
		
		if (operator == null)
			return "";
		
		return operator;
		
	}
	
	private boolean usePrefix() {
		Boolean usePrefix = (Boolean)context.get(SSolrQuery.USE_PREFIX);
		
		if (usePrefix == null)
			return false;
		
		if (!usePrefix) {
			return false;
		}	
		
		return true;
	}
	
	
}
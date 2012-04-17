package br.com.kohen.search.dsl.impl;

import java.util.List;
import java.util.Map;

import br.com.kohen.search.dsl.RelevanceContext;
import br.com.kohen.search.entity.Relevance;

public class RelevanceContextImpl extends MatchingContextImpl implements RelevanceContext {

	
	public RelevanceContextImpl(Map<String, Object> context) {
		super(context);
	}
	
	@SuppressWarnings("unchecked")
	public RelevanceContext add(Relevance relevance) {
		List<Relevance> relevances = (List<Relevance>)getContext().get(SSolrQuery.FIELDS_WITH_RELEVANCE);
		
		relevances.add(relevance);
		
		return new RelevanceContextImpl(getContext());
	}

}

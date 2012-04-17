package br.com.kohen.search.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.FacetParams;

import br.com.kohen.search.dsl.impl.SSolrQuery;
import br.com.kohen.search.dsl.impl.SolrCriteria;
import br.com.kohen.search.entity.ProductSearch;
import br.com.kohen.search.entity.SolrGroup;
import br.com.kohen.search.exceptions.SearchException;
import static br.com.kohen.search.dsl.impl.Options.AND;
import static br.com.kohen.search.dsl.impl.Options.OR;

public class ProductSearchDAOImpl {

	private SolrServer server;
	
	public ProductSearchDAOImpl(SolrServer server) {
		this.server = server;
	}
	
	public List<ProductSearch> searchBaseInOneFieldName(String term) {
		
		SolrCriteria solrCriteria = new SSolrQuery().search(ProductSearch.class)
					.onFields("name", "description")
					.mathing(term)
					.list();
		
		return search(solrCriteria);
	}
	
	public List<ProductSearch> searchInAllFieldName(String term) {
		
		SolrCriteria solrCriteria = new SSolrQuery().search(ProductSearch.class).allFields()
							.mathing(term)
							.list();
		
		return search(solrCriteria);
		
		
	}
	
	
	public List<ProductSearch> searchWithOperatorOr(String term) {
		
		SolrCriteria solrCriteria = new SSolrQuery().search(ProductSearch.class).onFields("name", "description")
						.mathing(term)
						.list();
		
		return search(solrCriteria);
		
	}
	
	public List<ProductSearch> searchWithOperatorAnd(String term) {
		
		SolrCriteria solrCriteria = new SSolrQuery().search(ProductSearch.class).onFields("name", "description")
						.mathing(term)
						.operator(AND())
						.list();
		
		return search(solrCriteria);
		
	}
	
	public List<SolrGroup> searchWithGroupBy(String term) {
		
		SolrCriteria solrCriteria = new SSolrQuery().search(ProductSearch.class).onFields("description")
						.mathing(term)
						.groupBy("name")
						.operator(AND())
						.list();
		
		return searchGroup(solrCriteria);
		
	}
	
	public List<ProductSearch> searchWithAccentWord(String term) {
		
		SolrCriteria solrCriteria = new SSolrQuery().search(ProductSearch.class).onFields("name")
						.mathing(term)
						.operator(OR())
						.usePrefix()
						.list();
		
		return search(solrCriteria);
		
	}
	
	public List<ProductSearch> searchByPrefix(String term) {
		
		SolrCriteria solrCriteria = new SSolrQuery().search(ProductSearch.class).onFields("name")
						.mathing(term)
						.operator(OR())
						.list();
		
		return search(solrCriteria);
		
	}
	
	public Map<String, Integer> facetRage() {
		SolrQuery query = new SolrQuery("*:*");
		
//		query.addNumericRangeFacet("price", 0d, 500d, 100d);
//		query.addFacetField("price");
		
//		query.add("facet", "true");
//		query.add("facet.field", "price");
//		query.add("facet.range", "price");
//		query.add("facet.range.start", "0");
//		query.add("facet.range.end", "500");
//		query.add("facet.range.gap", "50");
		
//		 add(String.format("f.%s.%s", field, FacetParams.FACET_RANGE_START), start.toString());
//		    add(String.format("f.%s.%s", field, FacetParams.FACET_RANGE_END), end.toString());
//		    add(String.format("f.%s.%s", field, FacetParams.FACET_RANGE_GAP), gap.toString());
//		    this.set(FacetParams.FACET, true);
		
//		query.set(FacetParams.FACET_RANGE, "price");
//		
//		query.set(FacetParams.FACET_RANGE_START, "0");
//		query.set(FacetParams.FACET_RANGE_END, "500");
//		query.set(FacetParams.FACET_RANGE_GAP, "50");
//		query.set(FacetParams.FACET, true);
//		
		query.addFacetQuery("price:[* TO 50]");
	    query.addFacetQuery("price:[51 TO 100]");
	    query.addFacetQuery("price:[101 TO 200]");
	    query.addFacetQuery("price:[201 TO *]");
	    query.addFacetField("price");
	    
//	   query.addNumericRangeFacet("price", 0l, 500l, 50);
//	    query.setFacetMinCount(10);
//	    query.setFacet(true);
		
        
        return searchFacet(query);
        
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Integer> searchFacet(SolrQuery solrQuery) {
		String query = solrQuery.getQuery();
		
		QueryResponse queryResponse;
		
		try {
			queryResponse = this.server.query(solrQuery);
		} catch (SolrServerException e) {
			throw new SearchException("Erro ao executar a busca.", e);
		}
		
		List<RangeFacet> facetRanges = queryResponse.getFacetRanges();
		List<FacetField> facetFields = queryResponse.getFacetFields();
		
		return queryResponse.getFacetQuery();
		
				
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> List<T> search(SolrCriteria solrCriteria) {
		
		SolrQuery solrQuery = solrCriteria.getSolrQuery();
		String query = solrCriteria.getSolrQuery().getQuery();
		
		QueryResponse queryResponse;
		
		try {
			queryResponse = this.server.query(solrQuery);
		} catch (SolrServerException e) {
			throw new SearchException("Erro ao executar a busca.", e);
		}
		
		
		return queryResponse.getBeans(solrCriteria.getTargetEntity());
		
				
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> searchGroup(SolrCriteria solrCriteria) {
		
		String query = solrCriteria.getSolrQuery().getQuery();
		SolrQuery solrQuery = solrCriteria.getSolrQuery();
		
		QueryResponse queryResponse;
		
		try {
			queryResponse = this.server.query(solrQuery);
		} catch (SolrServerException e) {
			throw new SearchException("Erro ao executar a busca.", e);
		}
		
		
		return buildSolrGroup(solrCriteria, queryResponse);
		
				
	}
	
	
	
	@SuppressWarnings("rawtypes")
	private List buildSolrGroup(SolrCriteria solrCriteria, QueryResponse queryResponse) {
		List<GroupCommand> groupCommands = queryResponse.getGroupResponse().getValues();
		
		List<Group> values = groupCommands.get(0).getValues();
		List results = new ArrayList();
		
		for (Group group : values) {
						
			String commonGroupValue = group.getGroupValue();
			long numFound = group.getResult().getNumFound();
			SolrDocumentList result = group.getResult();
			
			results.add(new SolrGroup(commonGroupValue, numFound));
		}
			
		return results;
	}

	
	
}

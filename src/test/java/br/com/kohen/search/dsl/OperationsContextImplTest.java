package br.com.kohen.search.dsl;

import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.junit.Assert;
import org.junit.Test;

import br.com.kohen.search.dsl.impl.SSolrQuery;
import br.com.kohen.search.dsl.impl.Sort;
import br.com.kohen.search.entity.ProductSearch;

public class OperationsContextImplTest {

	
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldHaveAllRequiredParametersInThisIteration() {
		
		SSolrQuery solrQuery = new SSolrQuery();
		OperationsContext operationsContext = solrQuery.search(ProductSearch.class).allFields().mathing("Jack-6");
		
		Set<String> fields = (Set<String>)operationsContext.getContext().get(SSolrQuery.ON_FIELDS);
		Class<ProductSearch> clazz = (Class<ProductSearch>)operationsContext.getContext().get(SSolrQuery.TARGET_ENTITY);
		String match = (String) operationsContext.getContext().get(SSolrQuery.MATCH);
		
		Assert.assertTrue("Should have a set empty", fields.size() == 0);
		Assert.assertTrue("Should have a class target ProductSearch", clazz.equals(ProductSearch.class));
		Assert.assertTrue("Should have a match Jack-6", match.equals("Jack-6"));
		
	}
	
	@Test
	public void shouldHaveAOrderInContext() {
	
		SSolrQuery solrQuery = new SSolrQuery();
		OperationsContext operationsContext = solrQuery.search(ProductSearch.class)
					.allFields().mathing("Jack-6")
					.orderBy(Sort.desc("name"));
		
		Sort order = (Sort) operationsContext.getContext().get(SSolrQuery.ORDER_BY);
		
		Assert.assertTrue("Should have a order with orientation desc and field NAME", order.getOrder().equals(ORDER.desc));
		
	}
	
	@Test
	public void shouldHaveAOrderInContextWithFieldNAME() {
	
		SSolrQuery solrQuery = new SSolrQuery();
		OperationsContext operationsContext = solrQuery.search(ProductSearch.class)
					.allFields().mathing("Jack-6")
					.orderBy(Sort.desc("name"));
		
		Sort order = (Sort) operationsContext.getContext().get(SSolrQuery.ORDER_BY);
		
		Assert.assertTrue("Should have a order with orientation desc and field NAME", order.getOrder().equals(ORDER.desc));
		
	}
	
	@Test
	public void shouldHaveParametersOfPagination() {
	
		SSolrQuery solrQuery = new SSolrQuery();
		OperationsContext operationsContext = solrQuery.search(ProductSearch.class)
					.allFields().mathing("Jack-6")
					.maxResult(10)
					.firstResult(5);
		
		Integer maxResult = (Integer) operationsContext.getContext().get(SSolrQuery.MAX_RESULT);
		
		Integer firstResult = (Integer) operationsContext.getContext().get(SSolrQuery.FIRST_RESULT);
		
		
		
		Assert.assertTrue("Should have a max result in context", maxResult == 10);
		Assert.assertTrue("Should have a first result in context", firstResult == 5);
				
	}
	
	@Test
	public void shouldHaveAGroupByInContext() {
	
		SSolrQuery solrQuery = new SSolrQuery();
		OperationsContext operationsContext = solrQuery.search(ProductSearch.class)
					.allFields().mathing("Jack-6")
					.maxResult(10)
					.firstResult(5)
					.groupBy("category");
		
		String groupBy = (String) operationsContext.getContext().get(SSolrQuery.GROUP_BY);
				
		Assert.assertTrue("Should have a field  group by in context ", groupBy.endsWith("category"));
				
	}
	
	
	
	
	
	
}

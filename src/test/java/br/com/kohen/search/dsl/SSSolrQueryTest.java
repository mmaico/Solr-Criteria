package br.com.kohen.search.dsl;

import org.junit.Assert;
import org.junit.Test;

import br.com.kohen.search.dsl.impl.SSolrQuery;
import br.com.kohen.search.entity.ProductSearch;

public class SSSolrQueryTest {

	
	@Test
	public void shouldReturnigTheTargetClass() {
		SSolrQuery solrQuery = new SSolrQuery();
		FieldContext fieldContext = solrQuery.search(ProductSearch.class);
		
		Class targetClass = (Class) solrQuery.getContext().get(SSolrQuery.TARGET_ENTITY);
		
		Assert.assertTrue("Returns the entity that will be used in the search", targetClass.equals(ProductSearch.class));
	}
	
	@Test
	public void shouldSendTheTargetClassToNextContext() {
		
		SSolrQuery solrQuery = new SSolrQuery();
		FieldContext fieldContext = solrQuery.search(ProductSearch.class);
		
		Class targetClass = (Class) fieldContext.getContext().get(SSolrQuery.TARGET_ENTITY);
		
		Assert.assertTrue("Returns the entity that will be used in the search", targetClass.equals(ProductSearch.class));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowAException() {
		
		SSolrQuery solrQuery = new SSolrQuery();
		FieldContext fieldContext = solrQuery.search(null);
	}

}
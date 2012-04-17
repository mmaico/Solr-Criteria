package br.com.kohen.search.dsl;

import static br.com.kohen.search.dsl.impl.Options.AND;

import org.junit.Assert;
import org.junit.Test;

import br.com.kohen.search.dsl.impl.SSolrQuery;
import br.com.kohen.search.dsl.impl.SolrCriteria;
import br.com.kohen.search.dsl.impl.Sort;
import br.com.kohen.search.entity.ProductSearch;
import br.com.kohen.search.entity.Relevance;

public class FinalContextImplTest {

	@Test
	public void shoudReturABasicQuery() {
		SSolrQuery solrQuery = new SSolrQuery();
		SolrCriteria solrCriteria = solrQuery.search(ProductSearch.class)
						.onFields("name")
						.mathing("Jack")
						.operator(AND())
						.list();
	
		String query = solrCriteria.getSolrQuery().getQuery();
		Assert.assertNotNull("The query shoud not be null", query);
		Assert.assertNotNull("The query shoud not be empty", query.equals(""));
		Assert.assertEquals("Should returns the query with parameters.", "+(name:Jack )", query);
	}
	
	@Test
	public void shoudReturnABasicQueryWithPagination() {
		SSolrQuery solrQuery = new SSolrQuery();
		SolrCriteria solrCriteria = solrQuery.search(ProductSearch.class)
						.onFields("name")
						.mathing("Jack")
				  		.firstResult(10)
						.maxResult(20)
						.operator(AND())
						.list();
	
		String query = solrCriteria.getSolrQuery().getQuery();
		Assert.assertNotNull("The query shoud not be null", query);
		Assert.assertNotNull("The query shoud not be empty", query.equals(""));
		Assert.assertEquals("Should returns the query with parameters of context and pagination", "+(name:Jack )&rows=20&start=10", query.toString());
	}
	
	@Test
	public void shoudReturnABasicQueryWith() {
		
		SSolrQuery solrQuery = new SSolrQuery();
		SolrCriteria solrCriteria = solrQuery.search(ProductSearch.class)
						.onFields("name")
						.mathing("Jack")
						.firstResult(10)
						.maxResult(20)
						.orderBy(Sort.asc("name"))
						.operator(AND())
						.list();
	
		String query = solrCriteria.getSolrQuery().getQuery();
		String expectedQuery = "+(name:Jack )&rows=20&start=10&sort=name+asc";
		
		Assert.assertNotNull("The query shoud not be null", query);
		Assert.assertNotNull("The query shoud not be empty", query.equals(""));
		Assert.assertTrue("Should returns the query with parameters of context and order parameter: " + query, query.equals(expectedQuery));
	}
	
	@Test
	public void shoudReturnABasicQueryWithContextParametersAndGroupBy() {
		
		SSolrQuery solrQuery = new SSolrQuery();
		SolrCriteria solrCriteria = solrQuery.search(ProductSearch.class)
						.onFields("name")
						.mathing("Jack")
						.firstResult(10)
						.maxResult(20)
						.orderBy(Sort.asc("name"))
						.groupBy("category")
						.operator(AND())
						.list();
	
		String query = solrCriteria.getSolrQuery().getQuery();
		String expectedQuery = "+(name:Jack )&group.field=category&group=true&rows=20&start=10&sort=name+asc";
		
		Assert.assertNotNull("The query shoud not be null", query);
		Assert.assertNotNull("The query shoud not be empty", query.equals(""));
		Assert.assertTrue("Should returns the query with parameters of context and group by parameter: " + query, query.equals(expectedQuery));
	}
	
	@Test
	public void shouldMatchingKeywordUsingRelavanceWithOnlyOneField() {
		SSolrQuery solrQuery = new SSolrQuery();
		SolrCriteria solrCriteria = solrQuery.search(ProductSearch.class)
						.add(Relevance.forField("name").that("0.3"))
						.mathing("Jack")
						.firstResult(10)
						.maxResult(20)
						.orderBy(Sort.asc("name"))
						.operator(AND())
						.groupBy("category")
						.list();
		
		String query = solrCriteria.getSolrQuery().getQuery();
		String expectedQuery = "+(name:Jack^0.3 )&group.field=category&group=true&rows=20&start=10&sort=name+asc";
		
		Assert.assertEquals("Should returns the query with fields relevancy", expectedQuery, query);
		
	}
	
	@Test
	public void shouldMatchingKeywordUsingRelavanceWithSeveralFields() {
		SSolrQuery solrQuery = new SSolrQuery();
		SolrCriteria solrCriteria = solrQuery.search(ProductSearch.class)
						.add(Relevance.forField("name").that("0.5"))
						.add(Relevance.forField("description").that("0.4"))
						.mathing("Jack Max")
						.firstResult(10)
						.maxResult(20)
						.orderBy(Sort.asc("name"))
						.groupBy("category")
						.operator(AND())
						.list();
		
		String query = solrCriteria.getSolrQuery().getQuery();
		String expectedQuery = "+(name:Jack^0.5 description:Jack^0.4 )+(name:Max^0.5 description:Max^0.4 )&group.field=category&group=true&rows=20&start=10&sort=name+asc";
		
		
		Assert.assertEquals("Should returns the query with fields relevancy", expectedQuery, query);
		
	}
	
}
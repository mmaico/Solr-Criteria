package br.com.kohen.search.dsl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.com.kohen.search.dsl.impl.SSolrQuery;
import br.com.kohen.search.entity.ProductSearch;
import br.com.kohen.search.entity.Relevance;

public class FieldContextImplTest {
	
	@Test
	public void shouldHaveFiveFieldsInContext() {
		
		SSolrQuery solrQuery = new SSolrQuery();
		FieldContext fieldContext = solrQuery.search(ProductSearch.class);
		
		fieldContext.onFields("categoria", "name", "categoryId", "id", "uid");
		
		Class<ProductSearch> clazz = (Class<ProductSearch>)fieldContext.getContext().get(SSolrQuery.TARGET_ENTITY);
		Set<String> fields = (Set<String>) fieldContext.getContext().get(SSolrQuery.ON_FIELDS);
		
		Assert.assertTrue("The class should have type ProductSearch.class", clazz.equals(ProductSearch.class));
		Assert.assertTrue("Should have five fields in context", fields.size() == 5);
	}
	
	@Test
	public void shoudHaveFiveFieldsUsingTheSetFieldMethod() {
		SSolrQuery solrQuery = new SSolrQuery();
		FieldContext fieldContext = solrQuery.search(ProductSearch.class);
		
		Set<String> fields = new HashSet<String>();
		fields.add("categoria");
		fields.add("name");
		fields.add("categoryId");
		fields.add("id");
		fields.add("uid");
		
		fieldContext.onFields(fields);
			
		Set<String> fieldsInContext = (Set<String>)fieldContext.getContext().get(SSolrQuery.ON_FIELDS);
		
		Assert.assertTrue("Should have five items in fields but have: " + fieldsInContext.size(), fieldsInContext.size() == 5);
		
	}
	
	@Test
	public void shoudHaveAListEmpty() {
		SSolrQuery solrQuery = new SSolrQuery();
		FieldContext fieldContext = solrQuery.search(ProductSearch.class);
			
		fieldContext.allFields();
			
		Set<String> fieldsInContext = (Set<String>)fieldContext.getContext().get(SSolrQuery.ON_FIELDS);
		
		Assert.assertTrue("Should have a list empty but have: " + fieldsInContext.size(), fieldsInContext.size() == 0);
		
	}
	
	@Test
	public void shouldHave3Relevance() {
		SSolrQuery solrQuery = new SSolrQuery();
		FieldContext fieldContext = solrQuery.search(ProductSearch.class);
		
		fieldContext.add(Relevance.forField("name").that("ö0.3"));
		fieldContext.add(Relevance.forField("description").that("ö0.4"));
		fieldContext.add(Relevance.forField("id").that("ö0.5"));
		
		List<Relevance> relevances = (List<Relevance>) fieldContext.getContext().get(SSolrQuery.FIELDS_WITH_RELEVANCE);
		
		Assert.assertTrue("Deveria ter 2 relavancias", relevances.size() == 3 );
		
	}
	
	@Test
	public void shouldHaveThe3RelevanceExpected() {
		SSolrQuery solrQuery = new SSolrQuery();
		FieldContext fieldContext = solrQuery.search(ProductSearch.class);
		
		fieldContext.add(Relevance.forField("name").that("0.3"));
		fieldContext.add(Relevance.forField("description").that("0.4"));
		fieldContext.add(Relevance.forField("id").that("0.5"));
		
		List<Relevance> relevances = (List<Relevance>) fieldContext.getContext().get(SSolrQuery.FIELDS_WITH_RELEVANCE);
		
		Relevance relevanceOne = relevances.get(0);
		Relevance relevanceTwo = relevances.get(1);
		Relevance relevanceThree = relevances.get(2);
		
		
		Assert.assertEquals("Deveria ter o campo nome", "name", relevanceOne.getFieldName());
		Assert.assertEquals("Deveria ter a relevancia 0.3", "^0.3", relevanceOne.getRelevance());
		
		Assert.assertEquals("Deveria ter o campo nome", "description", relevanceTwo.getFieldName());
		Assert.assertEquals("Deveria ter a relevancia 0.4", "^0.4", relevanceTwo.getRelevance());
		
		Assert.assertEquals("Deveria ter o campo nome", "id", relevanceThree.getFieldName());
		Assert.assertEquals("Deveria ter a relevancia 0.5", "^0.5", relevanceThree.getRelevance());
		
		
	}
	
	
	

}
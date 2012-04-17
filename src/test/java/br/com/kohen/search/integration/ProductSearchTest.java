package br.com.kohen.search.integration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.kohen.search.config.ProductSearchDAOImpl;
import br.com.kohen.search.entity.ProductSearch;
import br.com.kohen.search.entity.SolrGroup;
import br.com.kohen.search.infra.util.SolrUtil;

/**
 * Tests to {@link ProdutoSearch}
 */
 
public class ProductSearchTest {
	
	public static final String SOLR_CORE_PRODUCTS = "produtos";
	protected static SolrServer server;
	private ProductSearchDAOImpl search;
	
	@BeforeClass
	public static void init() throws SolrServerException, IOException {
		SolrUtil.initSolrServer(SOLR_CORE_PRODUCTS);
		server = SolrUtil.solrServer;
		SolrUtil.initSolrUnit();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		SolrUtil.clearIndexes();
		SolrUtil.serverRelease();
	}
	
	@Before
	public void before() {
		search = new ProductSearchDAOImpl(server);
	}
	
	@Test
	public void searchWithSuggedWithGroupByCategoryRoot() {
		String term = "historia";
		List<ProductSearch> results = search.searchBaseInOneFieldName(term);
		
		Assert.assertEquals("Should returns three elements", 2, results.size());
	}

	
	@Test
	public void searchInAllField() {
		String term = "amor libelula";
		List<ProductSearch> list = search.searchInAllFieldName(term);
		
		Assert.assertTrue("Deveria retornar 1 mas:" + list.size(), list.size() == 1);
		
	}
	
	@Test
	public void searchUsingOperatorOr() {
		String term = "sonho lindo";
		
		List<ProductSearch> list = search.searchWithOperatorOr(term);
		
		Assert.assertTrue("Deveria retornar 1 mas:" + list.size(), list.size() == 1);
		
	}
	
	@Test
	public void searchUsingOperatorAnd() {
		String term = "sonho lindo";
		
		List<ProductSearch> list = search.searchWithOperatorAnd(term);
		
		Assert.assertTrue("Nao deveria retornar nada:" + list.size(), list.size() == 0);
		
	}
	
	@Test
	public void searchUsingGroupBy() {
		String term = "Grupo 1";
		
		List<SolrGroup> groupBy = search.searchWithGroupBy(term);
		
		
		Assert.assertTrue("Nao deveria 1 grupo:" + groupBy.size(), groupBy.size() == 1);
		
	}
	
	@Test
	public void searchWithAccentWordAndTermWithAccent() {
		
		String term = "Avião";
		
		List<ProductSearch> list = search.searchWithAccentWord(term);
		
		Assert.assertTrue("Deveria trazer pelo menos 1", list.size() > 0);
		
	}
	
	@Test
	public void searchWithAccentWordAndTermWithoutAccent() {
		String term = "Aviao";
				
	    List<ProductSearch> list = search.searchWithAccentWord(term);
		
		Assert.assertTrue("Deveria trazer pelo menos 1", list.size() > 0);		
	}

	@Test
	public void searchWithAccentWordAndTermWithoutAccentAndUpperCaseMode() {
		String term = "AVIAO";
				
	    List<ProductSearch> list = search.searchWithAccentWord(term);
		
		Assert.assertTrue("Deveria trazer pelo menos 1", list.size() > 0);		
	}
	
	@Test
	public void searchWithAccentWordAndTermWithAccentInAllLetterAndUpperCaseMode() {
		String term = "ÂVÍÃÓ";
		
	    List<ProductSearch> list = search.searchWithAccentWord(term);
		
		Assert.assertTrue("Deveria trazer pelo menos 1", list.size() > 0);
	}
	
	
	@Test
	public void searchByPrefix() {
		String term = "arapi";
		
	    List<ProductSearch> list = search.searchWithAccentWord(term);
		
		Assert.assertTrue("Deveria trazer pelo menos 3", list.size() == 3);
	}
	
	@Test
	public void searchByPrefixMareOneLetter() {
		String term = "arapir";
		
	    List<ProductSearch> list = search.searchWithAccentWord(term);
		
		Assert.assertTrue("Deveria trazer pelo menos 1", list.size() == 1);
	}
	
	@Test
	public void searchByPrefixMareOneLetterButTheCompletWord() {
		String term = "arapiraca";
		
	    List<ProductSearch> list = search.searchWithAccentWord(term);
		
		Assert.assertTrue("Deveria trazer pelo menos 1", list.size() == 1);
	}
	
	
	@Test
	public void searchByPrefixWithAccentInLetterAndUpperCaseLetter() {
		String term = "arâpíracA";
		
	    List<ProductSearch> list = search.searchWithAccentWord(term);
		
		Assert.assertTrue("Deveria trazer pelo menos 1", list.size() == 1);
	}
	
	@Test
	public void searchFacetRange() {
		
		Map<String, Integer> facetRage = search.facetRage();
		
		Set<Entry<String,Integer>> entrySet = facetRage.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			System.out.println("Key: " + entry.getKey());
			System.out.println("Value: " + entry.getValue());
		}
		
		
		
		Assert.assertTrue("Nao estar vazio", !facetRage.isEmpty());
		
	}
	
	
}
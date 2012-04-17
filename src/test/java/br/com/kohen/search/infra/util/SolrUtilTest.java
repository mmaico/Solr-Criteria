package br.com.kohen.search.infra.util;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.jdom.Attribute;
import org.jdom.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.kohen.search.entity.ProductSearch;

public class SolrUtilTest {
	
	public static final String SOLR_CORE_PRODUCTS = "produtos";
	
	private SolrServer solrServer;
	
	@Before
	public void init() {
		SolrUtil.initSolrServer(SOLR_CORE_PRODUCTS);
		solrServer = SolrUtil.solrServer;
	}
	
	@Test
	public void readXmlData() {
		
		Element readSolrUnit = SolrUtil.readSolrUnit();
		
		Assert.assertNotNull(readSolrUnit);
	}
	
	@Test
	public void elementsInSolrUnitNotWillBeLessThanOne() {
		Element readSolrUnit = SolrUtil.readSolrUnit();
		
		Assert.assertTrue(readSolrUnit.getChildren().size() > 0);
	}
	
	
	@Test
	public void nodeShouldHaveAttributes() {
		Element readSolrUnit = SolrUtil.readSolrUnit();
		Element element = (Element) readSolrUnit.getChildren().get(0);
		
		@SuppressWarnings("unchecked")
		List<Attribute> attributes = (List<Attribute>) element.getAttributes();
		
		Assert.assertTrue("there are entities without attributes", !attributes.isEmpty());
		
	}
	
	@Test
	public void convertAnttributesToEntityTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Element readSolrUnit = SolrUtil.readSolrUnit();
		Element element = (Element) readSolrUnit.getChildren().get(0);
		
		List<Attribute> attributes = (List<Attribute>) element.getAttributes();
		
		ProductSearch entityConverted = (ProductSearch) SolrUtilActions.convertAnttributesToEntity(element.getName(), attributes);
		
		Assert.assertNotNull(entityConverted);
		
	}
	
}
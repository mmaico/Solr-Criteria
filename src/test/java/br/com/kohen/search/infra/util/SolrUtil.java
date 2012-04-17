package br.com.kohen.search.infra.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.SAXException;

import br.com.kohen.search.utils.ReflectionsUtils;

/**
 * Initializes a Solr test.
 */
public class SolrUtil {

	private static final String SOLR_CONFIG_FILE = "solr.xml";
	public static final String SOLR_CORE_PRODUCTS = "produtos";
	private static final String SOLR_CONFIG_DIR = "src/test/resources/solr/multicore";
	
	private static final Logger log = Logger.getLogger(SolrUtil.class);

	public static boolean initialized;
	
	public static SolrServer solrServer;

	public static SolrServer destaqueServer;

	private static CoreContainer container;
	
	public static void initSolrServer(String core) {
		if (!initialized) {
		    File home = new File(SOLR_CONFIG_DIR);
		    File f = new File(home, SOLR_CONFIG_FILE);
		    container = new CoreContainer();

		    try {
				container.load(SOLR_CONFIG_DIR, f);
			} catch (ParserConfigurationException e) {
				log.error("Erro durante parse do arquivo de configuracao do Solr.", e);
			} catch (IOException e) {
				log.error("Arquivo de configuracao do Solr nao encontrado.", e);
			} catch (SAXException e) {
				log.error("Erro durante parse do arquivo de configuracao do Solr.", e);
			}

			container.getContainerProperties().setProperty("queryConverter", "solr.SpellingQueryConverter");
			solrServer = new EmbeddedSolrServer(container, core);
		    initialized = true;
		}
	}
	
	public static final SolrServer initCoreDestaque() {

		if (destaqueServer == null) {
		    File home = new File(SOLR_CONFIG_DIR);
		    File f = new File(home, SOLR_CONFIG_FILE);
		    container = new CoreContainer();

		    try {
				container.load(SOLR_CONFIG_DIR, f);
			} catch (ParserConfigurationException e) {
				log.error("Erro durante parse do arquivo de configuracao do Solr.", e);
			} catch (IOException e) {
				log.error("Arquivo de configuracao do Solr nao encontrado.", e);
			} catch (SAXException e) {
				log.error("Erro durante parse do arquivo de configuracao do Solr.", e);
			}

			container.getContainerProperties().setProperty("queryConverter", "solr.SpellingQueryConverter");
			destaqueServer = new EmbeddedSolrServer(container, SOLR_CORE_PRODUCTS);
		}
		return destaqueServer;
	}
	
	public static void clearIndexes() throws SolrServerException, IOException {
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
		solrServer.optimize();
	}
	
	public static void serverRelease() {
		container.shutdown();
		solrServer = null;
		initialized = false;
    }
	
	public static void initSolrUnit() throws SolrServerException, IOException {
		solrServer.addBeans(extractData());
		solrServer.optimize();
		solrServer.commit();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> extractData() {
		List<Element> children = (List<Element>) readSolrUnit().getChildren();
		
		List<Object> listData = new ArrayList<Object>();
		
		for (Element element : children) {
			List<Attribute> attributes = (List<Attribute>)element.getAttributes();
			
			Object object = convertAnttributesToEntity(element.getName(), attributes);
			
			listData.add(object);
		}
		
		return listData;
		
	}
	
	public static Object convertAnttributesToEntity(String entityName, List<Attribute> attributes)  {

		Object newInstance = ReflectionsUtils.classForName(entityName); 
					
		for (Attribute attribute : attributes) {
			ReflectionsUtils.invokeSetterMethod(newInstance, attribute.getName(), attribute.getValue());
		}
			
		return newInstance;
	}
	
	public static Element readSolrUnit() {
		SAXBuilder sab = new SAXBuilder();
		
		try {
			InputStream inputStream = SolrUtil.class.getResourceAsStream("/solrunit-dataset.xml");
			Document doc = (Document) sab.build(inputStream);
			return (Element) doc.getRootElement();
					
		} catch (JDOMException e) {
			log.error("Erro durante parse do arquivo solrunit-dataset.xml.", e);
		} catch (IOException e) {
			log.error("Erro durante parse do arquivo solrunit-dataset.xml.", e);
		}
		
		return null;	
		
	}
	
	
	
}
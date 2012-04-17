package br.com.kohen.search.infra.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.core.CoreContainer;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import br.com.kohen.search.utils.ReflectionsUtils;

public class SolrUtilActions {

	private static final Logger log = Logger.getLogger(SolrUtilActions.class);
	
	public static SolrServer solrServer;

	private static CoreContainer container;
	
	public static boolean initialized;
	
	public SolrUtilActions(SolrServer solrServer, CoreContainer container, boolean initialized) {
		SolrUtilActions.solrServer = solrServer;
		SolrUtilActions.container = container;
		SolrUtilActions.initialized = initialized;
	}
	
	public void clearIndexes() throws SolrServerException, IOException {
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
		solrServer.optimize();
	}
	
	public void serverRelease() {
		container.shutdown();
		solrServer = null;
		initialized = false;
    }
	
	public void initSolrUnit() throws SolrServerException, IOException {
		solrServer.addBeans(extractData());
		solrServer.optimize();
		solrServer.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> extractData() {
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
	
	public Element readSolrUnit() {
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

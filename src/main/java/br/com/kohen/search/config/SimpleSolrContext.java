package br.com.kohen.search.config;

import java.util.Properties;

import br.com.kohen.search.dsl.SQuery;


public class SimpleSolrContext implements SolrContext {

	private Properties solrProperties;
	
	public Properties getInfoContext() {
		return solrProperties;
	}


	public Properties getSolrProperties() {
		return solrProperties;
	}


	public void setSolrProperties(Properties solrProperties) {
		this.solrProperties = solrProperties;
	}
	
	
	public SQuery createQuery() {
		
		return null;
	}



	
	

}

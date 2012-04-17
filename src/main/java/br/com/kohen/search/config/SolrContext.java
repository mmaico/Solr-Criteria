package br.com.kohen.search.config;

import java.util.Properties;

import br.com.kohen.search.dsl.SQuery;

public interface SolrContext {

	public Properties getInfoContext();

	public Properties getSolrProperties();

	public void setSolrProperties(Properties solrProperties);
	
	public SQuery createQuery();
	
	
}

package br.com.kohen.search.utils;


public class SolrUtils {

	
	public static String getSolrUrl(String server, String port) {
		
		StringBuilder url = new StringBuilder();
				
		String webapp = "solr";
		
		return url.append("http://")
			.append(server).append(":").append(port)
			.append("/").append(webapp).append("/").toString();		
		
	}
}

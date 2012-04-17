package br.com.kohen.search.utils;

import java.util.List;
import java.util.Set;

import br.com.kohen.search.entity.Relevance;

public class QueryUtils {

	
	
	public static String mountQuery(String[] keywords, Set<String> fields, String operator, boolean usePrefix) {
		
		StringBuilder query = new StringBuilder("");
		for (String keyword : keywords) {
			query.append(operator +  "(");
			for(String field : fields) {
				query.append(field);
				query.append(":");
				query.append(keyword);
				query.append(" ");
				
				if (usePrefix) {
					query.append(field);
					query.append(":");
					query.append(keyword+ "*");
					query.append(" ");
				}	
				
			}
			query.append(")");
		}
		
		return query.toString();
	}
	
	
	public static String mountQueryNoFields(String[] keywords, String operator, boolean usePrefix) {
		
		StringBuilder query = new StringBuilder("");
		for (String key : keywords) {
			query.append(operator + "(");
			query.append(key);
			
			if (usePrefix) {
				query.append(" ");
				query.append(key + "* " );
			}	
			query.append(")");
		}
		
		return query.toString();
	}
	
	public static String mountRelevanceQuery(String[] keywords, List<Relevance> relevances,  String operator, Boolean usePrefix) {
		
		StringBuilder query = new StringBuilder("");
		for (String keyword : keywords) {
			query.append(operator +  "(");
			for(Relevance fieldRelevance : relevances) {
				query.append(fieldRelevance.getFieldName());
				query.append(":");
				query.append(keyword);
				query.append(fieldRelevance.getRelevance());
				query.append(" ");
				
				if (usePrefix) {
					query.append(fieldRelevance.getFieldName());
					query.append(":");
					query.append(keyword + "*");
					query.append(fieldRelevance.getRelevance());
					query.append(" ");
					
				}
				
				
			}
			query.append(")");
		}
		
		return query.toString();
	}
	
	
}

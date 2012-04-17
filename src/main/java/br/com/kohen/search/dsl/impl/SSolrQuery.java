package br.com.kohen.search.dsl.impl;

import java.util.HashMap;
import java.util.Map;

import br.com.kohen.search.dsl.FieldContext;
import br.com.kohen.search.dsl.SQuery;

public class SSolrQuery implements SQuery {

	public static String TARGET_ENTITY = 	"targetEntity";
	
	public static String ON_FIELDS = "fields";
	
	public static String MATCH = "match";
	
	public static String FIELDS_WITH_RELEVANCE = "match_with_relevance";
	
	public static String ORDER_BY = "order";
	
	public static String GROUP_BY = "group";
	
	public static String USE_PREFIX = "prefix";
	
	public static String MAX_RESULT = "max_result";
	
	public static String OPERATOR = "operator";
	
	public static String FIRST_RESULT = "first_result";
	
	Map<String, Object> context  = new HashMap<String, Object>();
	
	public <T> FieldContext search(Class<T> t) {
			context.put(TARGET_ENTITY, t);
			
			if (t == null)
				throw new IllegalArgumentException("The class should not be null");
			
			return new FieldContextImpl(context);
	}

	public Map<String, Object> getContext() {
		return context;
	}
	
}
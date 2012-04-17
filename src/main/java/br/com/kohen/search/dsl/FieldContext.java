package br.com.kohen.search.dsl;

import java.util.Map;
import java.util.Set;

import br.com.kohen.search.entity.Relevance;

public interface FieldContext {

public MatchingContext onFields(String... fieldsName);
	
	public MatchingContext onFields(Set<String> fieldsName);
	
	public MatchingContext allFields();
	
	public RelevanceContext add(Relevance relevance);
	
	public Map<String, Object> getContext();
	
}
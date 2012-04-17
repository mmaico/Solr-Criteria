package br.com.kohen.search.dsl;

import br.com.kohen.search.entity.Relevance;

public interface RelevanceContext extends MatchingContext {

	public RelevanceContext add(Relevance relevance);
}

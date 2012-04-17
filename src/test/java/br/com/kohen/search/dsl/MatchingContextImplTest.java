package br.com.kohen.search.dsl;

import static junit.framework.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.com.kohen.search.dsl.impl.MatchingContextImpl;

/**
 * Unit Test to {@link MatchingContextImpl}.
 * 
 * @author gonow
 *
 */
public class MatchingContextImplTest {

	private MatchingContext matchingContext;

	private Map<String, Object> context;

	@Before
	public void setUp() {
		context = new HashMap<String, Object>();
		matchingContext = new MatchingContextImpl(new HashMap<String, Object>());
	}
	
	
	@Test
	public void shouldVerifyIfOperationsContextIsMatchingOnlyUsingOneKeyword() {
		String queryExpected = "{match=livro}";
		
		OperationsContext mathing = matchingContext.mathing("livro");
		
		assertEquals(queryExpected, mathing.getContext().toString());
	}
	
	@Test
	public void shouldMatchingKeywordWhenUsingSeveralWords() {
		String expectedQuery = "{match=livro de java}";
		
		OperationsContext mathing = matchingContext.mathing("livro de java");
		
		assertEquals(expectedQuery, mathing.getContext().toString());
	}

}
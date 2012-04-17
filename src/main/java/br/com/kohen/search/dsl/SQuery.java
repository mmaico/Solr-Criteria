package br.com.kohen.search.dsl;

public interface SQuery {

	public <T> FieldContext search(Class<T> t);
}

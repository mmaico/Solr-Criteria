package br.com.kohen.search.exceptions;

/**
 * Exception para tratamento de erros na busca de dados do Solr.
 */
public class SearchException extends RuntimeException {

	private static final long serialVersionUID = -5531869715301409552L;

	public SearchException() {
	}

	public SearchException(String message) {
		super(message);
	}

	public SearchException(Throwable cause) {
		super(cause);
	}

	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
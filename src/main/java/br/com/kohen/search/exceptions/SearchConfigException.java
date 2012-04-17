package br.com.kohen.search.exceptions;

/**
 * Exception para tratamento de erros na busca de dados do Solr.
 */
public class SearchConfigException extends RuntimeException {

	private static final long serialVersionUID = -5531869715301409552L;

	public SearchConfigException() {
	}

	public SearchConfigException(String message) {
		super(message);
	}

	public SearchConfigException(Throwable cause) {
		super(cause);
	}

	public SearchConfigException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
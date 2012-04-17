package br.com.kohen.search.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import br.com.kohen.search.infra.util.SolrUtil;

public class ReflectionsUtils {

	private static final Logger log = Logger.getLogger(SolrUtil.class);
		
	public static Object classForName(String className) {
		
		try {
			Object newInstance = Class.forName(className).newInstance();
					
			return newInstance;
		} catch (InstantiationException e) {
			log.error("Erro no parse de entidade", e);
		} catch (IllegalAccessException e) {
			log.error("Erro no parse de entidade", e);
		} catch (ClassNotFoundException e) {
			log.error("Erro no parse de entidade", e);
		}
	
		return null;
	}
	
	/**
	 * Invoca o metodo setter do objeto se converte o parametro antes de fazer a
	 * chamada ao setter.
	 * 
	 */
	public static void invokeSetterMethod(Object target, String attributeName, Object value) {
		try {
			BeanUtils.setProperty(target, attributeName, value);
		} catch (IllegalAccessException e) {
			log.error("Error retrieving setter method", e);
		} catch (InvocationTargetException e) {
			log.error("Error retrieving setter method", e);
		}
			
	}	

	
	
	
	
}

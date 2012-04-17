package br.com.kohen.search.utils;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class RangeUtilsTest {

	
	
	@Test
	public void splitInterval() {
		String min = "0";
		String max = "100";
		String gap = "20";
		
		Integer numInteval = RangeUtils.calcNumInteval(min, max, gap);
		
		Assert.assertTrue("Deve retornar 5 conjuntos", numInteval.equals(5));
		
	}
	
	
	@Test
	public void splitIntervalStartInFifitin() {
		String min = "50";
		String max = "100";
		String gap = "20";
		
		Integer numInteval = RangeUtils.calcNumInteval(min, max, gap);
		
		Assert.assertTrue("Deve retornar 3 conjuntos", numInteval.equals(3));
		
	}
	
	
	@Test
	public void listWithIterval() {
		String min = "50";
		String max = "100";
		String gap = "20";
		
		List<String> numInterval = RangeUtils.numInterval("price", min, max, gap);
		
		Assert.assertTrue("Deveria ter uma lista com 3 itens", numInterval.size() == 3);
		
	}
	
	@Test
	public void listWithItervalVaidateItems() {
		String min = "50";
		String max = "100";
		String gap = "20";
		
		List<String> numInterval = RangeUtils.numInterval("price", min, max, gap);
		
		Assert.assertTrue(numInterval.get(0).equals("price:[50 TO 70]"));
		Assert.assertTrue(numInterval.get(1).equals("price:[71 TO 90]"));
		Assert.assertTrue(numInterval.get(2).equals("price:[91 TO 100]"));
		
	}
	
	
	@Test
	public void listWithItervalVaidateItemsStartZero() {
		String min = "0";
		String max = "100";
		String gap = "20";
		
		List<String> numInterval = RangeUtils.numInterval("price", min, max, gap);
		
		Assert.assertTrue(numInterval.get(0).equals("price:[0 TO 20]"));
		Assert.assertTrue(numInterval.get(1).equals("price:[21 TO 40]"));
		Assert.assertTrue(numInterval.get(2).equals("price:[41 TO 60]"));
		Assert.assertTrue(numInterval.get(3).equals("price:[61 TO 80]"));
		Assert.assertTrue(numInterval.get(4).equals("price:[81 TO 100]"));
	}
	
	
	
	
}

package br.com.kohen.search.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RangeUtils {

	public static List<String> numInterval(String field, String min, String max, String gap) {
		Integer calcNumInteval = calcNumInteval(min, max, gap);
		
		List<String> iterations =  new ArrayList<String>();
		BigDecimal gapIteration = new BigDecimal(gap);
		BigDecimal minIteration = new BigDecimal(min);
		BigDecimal maxIteration = new BigDecimal(max);
		
		for (int i = 0; i < (calcNumInteval); i++) {
			
			String iteration = calcStart(minIteration, gapIteration, i) + " TO " + calcEnd(minIteration, gapIteration, i, maxIteration);
			
			iterations.add(addFieldName(field, addBracket(iteration)));
		}
		
		
		return iterations; 
	}

	
	private static String addFieldName(String field, String addBracket) {
		
		return field + ":" + addBracket;
	}


	private static String addBracket(String iteration) {
		
		return "[" + iteration + "]";
	}


	private static BigDecimal calcEnd(BigDecimal minIteration,BigDecimal gapIteration, int i, BigDecimal max) {
		
		BigDecimal endResult = minIteration.add(gapIteration.multiply(new BigDecimal(i + 1)));
		
		if (endResult.compareTo(max) > 0)
			endResult = max;
		
		return endResult;
	}


	private static BigDecimal calcStart(BigDecimal minIteration, BigDecimal gapIteration, int i) {
		
		BigDecimal start = minIteration.add(gapIteration.multiply(new BigDecimal(i))); 
		
		if (i > 0)
			start = start.add(new BigDecimal(1));
		
		return start;
	}


	public static Integer calcNumInteval(String min, String max, String gap) {
		
		BigDecimal minNum = new BigDecimal(min);
		BigDecimal maxNum = new BigDecimal(max);
		BigDecimal gapNum = new BigDecimal(gap);
		
		BigDecimal realValue = maxNum.subtract(minNum);
		
		BigDecimal qtdIteration = realValue.divide(gapNum, 1, BigDecimal.ROUND_HALF_UP)
		.setScale(BigDecimal.ROUND_UP, 0);
		
		
		return qtdIteration.intValue();
		
	}
	
	
	
	
	
	
	
}

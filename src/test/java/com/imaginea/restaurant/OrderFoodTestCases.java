package com.imaginea.restaurant;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.imaginea.restaurant.process.RequestProcessor;

public class OrderFoodTestCases {
	
	
	@Test
	public void minAmountAndAdviceOrderedItems() throws Exception {
		RequestProcessor reqProcessor=new RequestProcessor();
		String myOrder="data.csv fancy_european_water extreme_fajita jalapeno_poppers";
		reqProcessor.processOrder(myOrder);
		int expected=6;
		Map<Integer, Float> priceMap=reqProcessor.getPriceMap();
		int actual=0;
		for(Entry<Integer, Float> mapEntry:priceMap.entrySet()){
			actual=mapEntry.getKey();
			break;
		}
		assertEquals(expected, actual);
		
		
	}
	
	@Test
	public void noMatchForOrderedItems() throws Exception {
		RequestProcessor reqProcessor=new RequestProcessor();
		String myOrder="data.csv fancy_european_water wine_spritzer";
		reqProcessor.processOrder(myOrder);
		List<Integer> restaurantList=reqProcessor.getlistOfQualifiedRestaurant();
		assertEquals(null, restaurantList);
	}
	
	@Test
	public void multipleMatchWithAdviceOrderedItems() throws Exception {
		int expected=3;
		RequestProcessor reqProcessor=new RequestProcessor();
		String myOrder="data.csv fancy_european_water burger";
		reqProcessor.processOrder(myOrder);
		List<Integer> restaurantList=reqProcessor.getlistOfQualifiedRestaurant();
		assertEquals(expected, restaurantList.size());
		
	}
	
		
}

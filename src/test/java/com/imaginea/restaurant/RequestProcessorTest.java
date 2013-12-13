package com.imaginea.restaurant;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.imaginea.restaurant.process.RequestProcessor;

public class RequestProcessorTest {


	@Test
	public void testProcessOrder() throws Exception {
		int expected=1; 
		RequestProcessor reqProcessoor= new RequestProcessor();
		String input="data.csv fancy_european_water extreme_fajita jalapeno_poppers";
		reqProcessoor.processOrder(input);
		assertEquals(expected, reqProcessoor.getlistOfQualifiedRestaurant().size());
	}


}

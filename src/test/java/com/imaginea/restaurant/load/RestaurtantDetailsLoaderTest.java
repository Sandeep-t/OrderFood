package com.imaginea.restaurant.load;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.imaginea.restaurant.model.Restaurant;

public class RestaurtantDetailsLoaderTest {
	

	@Test
	public void testLoadRestaurantItemDetails() {
		int expectedEntries=7;
		RestaurtantDetailsLoader detailsLoader= new RestaurtantDetailsLoader();
		Map<Integer, Restaurant> restDetails=detailsLoader.loadRestaurantItemDetails("data.csv");
		assertEquals(expectedEntries, restDetails.size());
		
	}

}

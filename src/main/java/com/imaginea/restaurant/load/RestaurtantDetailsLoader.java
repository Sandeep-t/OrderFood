package com.imaginea.restaurant.load;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imaginea.restaurant.model.Restaurant;

public class RestaurtantDetailsLoader {
	
	public Map<Integer, Restaurant> loadRestaurantItemDetails(String fileName){
		Map<Integer, Restaurant> restaurantMap = null;
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";
		List<String> meal;
		Restaurant restaurant;
		try {
			br = new BufferedReader(new FileReader(fileName));
			restaurantMap = new HashMap<Integer, Restaurant>();
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(splitBy);
				//don't process the input if the tokens are less than 3
				if(null == tokens || tokens.length < 3){
					continue;
				}
				int id = Integer.parseInt(tokens[0].trim());
				Float price = Float.parseFloat(tokens[1]);
				if(restaurantMap.get(id) == null){
					restaurant = new Restaurant();
					restaurant.setId(id);
				} else {
					restaurant = restaurantMap.get(id);
				}
				if(tokens.length > 3){
					meal = new ArrayList<String>();
					for(int i=2; i< tokens.length; i++){
						meal.add(tokens[i].trim());
					}
					Map<List<String>, Float> mealPriceMap = restaurant.getMealPriceMap();
					mealPriceMap.put(meal, price);
				} else {
					Map<String, Float> itemPriceMap = restaurant.getItemPriceMap();
					itemPriceMap.put(tokens[2].trim(), price);
				}
				restaurantMap.put(id, restaurant);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return restaurantMap;
	}

}

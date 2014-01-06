package com.imaginea.restaurant.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.annotations.VisibleForTesting;
import com.imaginea.restaurant.load.RestaurtantDetailsLoader;
import com.imaginea.restaurant.model.Restaurant;
import com.imaginea.restaurant.validate.DataValidator;

public class RequestProcessor {

	private Map<Integer, Restaurant> restaurantMap;
	private Map<Integer, Float> priceMap;
	private Map<Integer, List<String>> bestRestaurantMealMap;
	private List<Integer> restaurantsList = null;

	public void populateRestaurantPriceMap(int restaurantId, String[] inputTokens) {
		Restaurant res = restaurantMap.get(restaurantId);
		boolean checkFlag = true;
		int tokensLength = inputTokens.length;
		for (int i = 1; i < tokensLength; i++) {
			String item = inputTokens[i].trim();
			Float price;
			if (res.checkItem(item)) {
				price = res.getItemPrice(item);
			} else if (res.checkItemInMeal(item)) { // check the item present in
													// the meal
				price = res.getMealPrice(item);
			} else {
				// If one of the requested item is missing then remove the
				// restaurant from the priceMap
				if (priceMap.get(restaurantId) != null) {
					priceMap.remove(restaurantId);
				}
				checkFlag = false;
				break;
			}
			// check already items of this restaurant added to the priceMap
			if (priceMap.get(restaurantId) == null) {
				priceMap.put(restaurantId, price);
			} else {
				priceMap.put(restaurantId, price + priceMap.get(restaurantId));
			}
		}
		if (checkFlag) {
			Float price = getMinimumPriceForRestaurant(res, Arrays.asList(inputTokens).subList(1, tokensLength));
			if (price != 0 && priceMap.get(restaurantId) > price) {
				priceMap.put(restaurantId, price);
			}
		}
	}

	public void processOrder(String input) throws Exception {

		String tokens[] = getTokenizedData(input);

		DataValidator validator = new DataValidator();
		if (validator.validateInput(tokens)) {
			String fileName = tokens[0].trim();
			RestaurtantDetailsLoader proc = new RestaurtantDetailsLoader();
			restaurantMap = proc.loadRestaurantItemDetails(fileName);
			priceMap = new HashMap<Integer, Float>();
			bestRestaurantMealMap = new HashMap<Integer, List<String>>();
			for (int id : restaurantMap.keySet()) {
				populateRestaurantPriceMap(id, tokens);
			}
			Float minPrice = 0f;

			for (int restaurantId : priceMap.keySet()) {
				Float price = priceMap.get(restaurantId);
				if (minPrice == 0) {
					minPrice = price;
					restaurantsList = new ArrayList<Integer>();
					restaurantsList.add(restaurantId);
				} else if (minPrice > price) {
					minPrice = price;
					restaurantsList = new ArrayList<Integer>();
					restaurantsList.add(restaurantId);
				} else if (minPrice.equals(price)) {
					restaurantsList.add(restaurantId);
				}
			}
			if (restaurantsList == null) {
				System.out.println("No restaurants found as per the ordered placed....");
			} else {
				for (int id : restaurantsList) {
					System.out.println("Restaurant Id: " + id + " price:: " + priceMap.get(id));
				}
			}
			adviceMeal(tokens);
		}

	}

	private String[] getTokenizedData(String inputData) {
		String separator = " ";

		return inputData.split(separator);

	}

	public Float getMinimumPriceForRestaurant(Restaurant res, List<String> items) {
		Float price = 0f;
		Float mealPrice = 0f;

		Map<List<String>, Float> mealPriceMap = res.getMealPriceMap();
		List<String> missingItems = null;
		List<String> finalMeal = null;

		for (List<String> meal : mealPriceMap.keySet()) {
			List<String> temp = new ArrayList<String>();
			for (String item : items) {
				if (!meal.contains(item.trim())) {
					temp.add(item.trim());
				}
			}
			if (missingItems == null) {
				missingItems = temp;
				finalMeal = meal;
			} else {
				
				if (temp.size() < missingItems.size()) {
					finalMeal = meal;
					missingItems = temp;
				} else if((temp.size() == missingItems.size()) && (mealPriceMap.get(meal) > mealPriceMap.get(finalMeal))){
					finalMeal = meal;
					missingItems = temp;
				}
				}
		}
		if (missingItems != null) {
			for (String item : missingItems) {
				Float itemPrice = res.getItemPrice(item);
				price += itemPrice;
			}
		}
		if (mealPriceMap != null && finalMeal != null) {
			mealPrice = mealPriceMap.get(finalMeal);
			bestRestaurantMealMap.put(res.getId(), finalMeal);
		}
		price += mealPrice;
		return price;
	}

	public void adviceMeal(String[] inputTokens) {
		int minNoOfMissingItems = -1;
		Float minPrice = 0f;
		List<String> advicedMeal = null;
		int restaurantId = 0;
		int length = inputTokens.length;
		Map<List<String>, Float> mealPriceMap;
		for (int id : restaurantMap.keySet()) {
			Restaurant restaurant = restaurantMap.get(id);
			mealPriceMap = restaurant.getMealPriceMap();
			for (List<String> meal : mealPriceMap.keySet()) {
				Float price = mealPriceMap.get(meal);
				int noOfMissingItems = 0;
				for (String item : inputTokens) {
					if (!meal.contains(item)) {
						noOfMissingItems++;
					}
				}
				// first time: update the minprice, advicemeal, restaurantid
				// with the first data
				if (minNoOfMissingItems == -1 && noOfMissingItems < length) {
					minNoOfMissingItems = noOfMissingItems;
					minPrice = price;
					advicedMeal = meal;
					restaurantId = id;
				}
				// check the missing items equal to previous missing items then
				// check the price
				if (minNoOfMissingItems == noOfMissingItems && minPrice >= price && noOfMissingItems < length) {
					minNoOfMissingItems = noOfMissingItems;
					minPrice = price;
					advicedMeal = meal;
					restaurantId = id;
				} else if (minNoOfMissingItems > noOfMissingItems && noOfMissingItems < length) {
					minNoOfMissingItems = noOfMissingItems;
					minPrice = price;
					advicedMeal = meal;
					restaurantId = id;
				}
			}
		}
		if (advicedMeal != null) {
			System.out.println("You can also go for :: Restaurant Id " + restaurantId + " with meal Items "
					+ advicedMeal.toString() + " @ " + minPrice);
		}
	}

	@VisibleForTesting
	public List<Integer> getlistOfQualifiedRestaurant() {
		return restaurantsList;
	}

	@VisibleForTesting
	public Map<Integer, List<String>> getbestRestaurantMealMap() {
		return bestRestaurantMealMap;
	}

	@VisibleForTesting
	public Map<Integer, Float> getPriceMap() {
		return priceMap;
	}

}

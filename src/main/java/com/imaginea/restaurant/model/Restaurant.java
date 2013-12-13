package com.imaginea.restaurant.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
	
	private int id;
	private Map<String, Float> itemPriceMap = new HashMap<String, Float>();
	private Map<List<String>, Float> mealPriceMap = new HashMap<List<String>, Float>();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Map<String, Float> getItemPriceMap() {
		return itemPriceMap;
	}
	
	public void setItemPriceMap(Map<String, Float> itemPriceMap) {
		this.itemPriceMap = itemPriceMap;
	}
	public Map<List<String>, Float> getMealPriceMap() {
		return mealPriceMap;
	}
	
	public void setMealPriceMap(Map<List<String>, Float> mealPriceMap) {
		this.mealPriceMap = mealPriceMap;
	}
	
	public boolean checkItem(String item){
		if(itemPriceMap.get(item)!=null){
			return true;
		}
		return false;
	}
	
	public Float getItemPrice(String item){
		if(itemPriceMap.get(item)!=null){
			return itemPriceMap.get(item);
		}
		return null;
	}
	
	public boolean checkItemInMeal(String item){
		for(List<String> items : mealPriceMap.keySet()){
			if(items.contains(item)){
				return true;
			}
		}
		return false;
	}
	
	public Float getMealPrice(List<String> meal){
		if(mealPriceMap.get(meal)!=null){
			return mealPriceMap.get(meal);
		} 
		return null;
	}
	
	public Float getMealPrice(String item){
		for(List<String> items : mealPriceMap.keySet()){
			if(items.contains(item)){
				return mealPriceMap.get(items);
			}
		}
		return null;
	}

}

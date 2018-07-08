package com.research.jsonmappers;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class frontEndSupportMapper {
	
	public JSONArray multipleCountryWarehouseDataMapper(JSONObject countriesStatObj)
	{
		JSONArray mappedJA = new JSONArray();
		JSONArray countriesAL = new JSONArray();
		JSONArray countriesWarehouseDataAL = new JSONArray();
		Iterator<String> countryNames = countriesStatObj.keySet().iterator();
		
		while(countryNames.hasNext())
		{
			String countryName = countryNames.next();
			countriesAL.add(countryName);
			
			JSONObject countryStats = (JSONObject) countriesStatObj.get(countryName);
			
			Iterator<String> countryStatLabelIterator = countryStats.keySet().iterator();
			JSONArray countryStatLabels = new JSONArray();
			JSONArray countryStatsValues = new JSONArray();
			while(countryStatLabelIterator.hasNext()) 
			{
				
				if(countryStatsValues.size() == 0)
				{
					countryStatsValues.add(countryName);
				}
				
				String countryStatLabel = countryStatLabelIterator.next();
				countryStatLabels.add(countryStatLabel);
				countryStatsValues.add(countryStats.get(countryStatLabel));
			}
			
			countriesWarehouseDataAL.add(countryStatLabels);
			countriesWarehouseDataAL.add(countryStatsValues);
		}
		
		mappedJA.add(countriesAL);
		mappedJA.add(countriesWarehouseDataAL);
		
		return mappedJA;
	}
	
	
	// developed for the user profile stats for chart
	// not in use
	public JSONArray userStatsAsMultipleJA(JSONObject userStatsJO)
	{
		JSONArray mappedJA = new JSONArray();
		
		Iterator<String> daysIterator = userStatsJO.keySet().iterator();
		JSONArray days = new JSONArray();
		
		while(daysIterator.hasNext())
		{
			String day = daysIterator.next();
			days.add(day);
			
			JSONObject categoriesJO = (JSONObject) userStatsJO.get(day);
			Iterator<String> categoriesIterator = categoriesJO.keySet().iterator();
			JSONArray categoriesAL = new JSONArray();
			
			while(categoriesIterator.hasNext())
			{
				String categoryName = categoriesIterator.next();
				categoriesAL.add(categoryName);
				
				JSONObject subCategoryJO = (JSONObject) categoriesJO.get(categoryName);
				Iterator<String> subCategoryIterator = subCategoryJO.keySet().iterator();
				JSONArray subCategoryAL = new JSONArray();
				
				while(subCategoryIterator.hasNext())
				{
					String subCategoryName = subCategoryIterator.next();
					subCategoryAL.add(subCategoryName);
					
					JSONArray precentageOfSubCategory = new JSONArray();
					precentageOfSubCategory.add(subCategoryJO.get(subCategoryName));
					
					subCategoryAL.add(precentageOfSubCategory);
				}
				
				categoriesAL.add(subCategoryAL);
			}
			
			days.add(categoriesAL);
		}
		
		mappedJA.add(days);
		
		return mappedJA;
	}

}

package com.research.databaseAccess;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.research.tweets.SingleTweetRelationshipAnalyzer;

public class AdvertisementFetcher {
	
	public JSONObject getAdvertisementAmount(JSONObject jsonObject,int AdvertisementAmount)
	{
	
		JSONObject adAmountJsonObject = new JSONObject();
		
		Iterator<String> keyIterator = jsonObject.keySet().iterator();
		
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<Integer> values = new ArrayList<Integer>();
		
		while(keyIterator.hasNext())
		{
			String key = keyIterator.next();
			keys.add(key);
		}
		
		for (String key : keys) {
			Double currentPrecentage = (Double) jsonObject.get(key);
			int adAmount =  currentAdAmount(currentPrecentage, AdvertisementAmount);
			values.add(adAmount);
		}
		
		for (int i = 0; i < keys.size(); i++) {
			adAmountJsonObject.put(keys.get(i), values.get(i));
		}
		
		System.out.println(adAmountJsonObject.toString());
				
		return adAmountJsonObject;
	}
	
	private int currentAdAmount(Double currentPrecentage, int advertisementAmount)
	{
		Double rangeValue = 100.0/advertisementAmount;
		Double currentPrecentageFloorValue = ((int)(currentPrecentage/rangeValue)) * rangeValue;
		Double currentPrecentageCeilValue = currentPrecentageFloorValue + rangeValue;
		
		Double floorValueDifference = currentPrecentage - currentPrecentageFloorValue;
		Double ceilValueDifference = currentPrecentageCeilValue - currentPrecentage;
		
		if (floorValueDifference > ceilValueDifference)
		{
			return (int)(currentPrecentageCeilValue/rangeValue);
		}
		
		return (int)(currentPrecentageFloorValue/rangeValue);
	}
	
	//New Advertisement Fetcher Method
	public JSONObject getAdAmount(JSONObject userCharacterProfile, int adAmount)
	{
		AdvertisementFetcher af = new AdvertisementFetcher();
		
		JSONObject finalFetchingPrecentages = af.EachProductFinalFetchingAdPrecentageMapper(userCharacterProfile, adAmount);
		JSONObject userProfilePrecentageObj = af.UserProfileToAdPrecentageMapper(finalFetchingPrecentages, userCharacterProfile);
		JSONObject howManyAds = af.getAdvertisementAmount(userProfilePrecentageObj, adAmount);
		return howManyAds;
	}
	
	public JSONObject getProductCountFinalPrecentages(JSONObject userCharacterProfile, int adAmount)
	{
		AdvertisementFetcher af = new AdvertisementFetcher();
		
		JSONObject finalFetchingPrecentages = af.EachProductFinalFetchingAdPrecentageMapper(userCharacterProfile, adAmount);
		JSONObject userProfilePrecentageObj = af.UserProfileToAdPrecentageMapper(finalFetchingPrecentages, userCharacterProfile);
		return userProfilePrecentageObj;
	}
	
	//Front-end for EachProductFinalFetchingAdPrecentageMapper()
	public JSONObject getFinalItemPrecentageJO(JSONObject userCharacterProfile, int adAmount)
	{
		AdvertisementFetcher af = new AdvertisementFetcher();
		JSONObject finalFetchingPrecentages = af.EachProductFinalFetchingAdPrecentageMapper(userCharacterProfile, adAmount);
		return finalFetchingPrecentages;
	}
	
	
	private JSONObject EachProductFinalFetchingAdPrecentageMapper(JSONObject userCharacterProfile, int adAmount)
	{
		AdvertisementFetcher af = new AdvertisementFetcher();
		
		Double productPrecentage = 0.0;
		Double BrandPrecentage = 0.0;
		Double productTypePrecentage = 0.0;
		Double topCategoryPrecentage = 0.0;
		
		Double productToAdAmountRatio = 10.0;
		Double brandToAdAmountRatio = 10.0;
		Double productTypeToAdAmountRatio = 10.0;
		Double topCategoryToAdAmountRatio = 10.0;
		
		//products
		if(userCharacterProfile.containsKey("product"))
		{
			
			JSONObject productJSONObject = (JSONObject)userCharacterProfile.get("product");
			JSONObject productServicingObj = af.keysAndTotalMentions(productJSONObject);
			int productTotalMentions = (int) productServicingObj.get("totalMentions");
			productToAdAmountRatio = (adAmount/(productTotalMentions/1.0));
			productPrecentage = 100.0;

		}
		
		//brands
		if(userCharacterProfile.containsKey("brand") && (productToAdAmountRatio >= 10))
		{
			
			JSONObject brandJSONObject = (JSONObject)userCharacterProfile.get("brand");
			JSONObject brandServicingObj = af.keysAndTotalMentions(brandJSONObject);
			int brandTotalMentions = (int) brandServicingObj.get("totalMentions");
			brandToAdAmountRatio = (adAmount/(brandTotalMentions/1.0));
			productPrecentage = 50.0;
			BrandPrecentage = 50.0;
		
		}
		
		//productTypes
		if(userCharacterProfile.containsKey("producttype") && (productToAdAmountRatio >= 10) && (brandToAdAmountRatio >= 10))
		{
					
			JSONObject productTypeJSONObject = (JSONObject)userCharacterProfile.get("producttype");
			JSONObject productTypeServicingObj = af.keysAndTotalMentions(productTypeJSONObject);
			int productTypeTotalMentions = (int) productTypeServicingObj.get("totalMentions");
			productTypeToAdAmountRatio = (adAmount/(productTypeTotalMentions/1.0));
			productPrecentage = 33.3;
			BrandPrecentage = 33.3;
			productTypePrecentage = 33.3;
				
		}
		
		//topCategory
		if(userCharacterProfile.containsKey("topcategory") && (productToAdAmountRatio >= 10) && (brandToAdAmountRatio >= 10) && (topCategoryToAdAmountRatio >= 10))
		{
					
			JSONObject topCategoryJSONObject = (JSONObject)userCharacterProfile.get("topcategory");
			JSONObject topCategoryServicingObj = af.keysAndTotalMentions(topCategoryJSONObject);
			int topCategoryTotalMentions = (int) topCategoryServicingObj.get("totalMentions");
			topCategoryToAdAmountRatio = (adAmount/(topCategoryTotalMentions/1.0));
			productPrecentage = 25.0;
			BrandPrecentage = 25.0;
			productTypePrecentage = 25.0;
			topCategoryPrecentage = 25.0;
				
		}
		
		JSONObject precentageJO = new JSONObject();
		precentageJO.put("product", productPrecentage);
		precentageJO.put("brand", BrandPrecentage);
		precentageJO.put("producttype", productTypePrecentage);
		precentageJO.put("topcategory", topCategoryPrecentage);
		
		return precentageJO;
	}
	
	
	public JSONObject UserProfileToAdPrecentageMapper(JSONObject precentageJO, JSONObject userCharacterProfile)
	{
		AdvertisementFetcher af = new AdvertisementFetcher();
		JSONObject FinalAdFetcherReadyPrecentageAdvertisementObj = new JSONObject();
		Iterator<String> precentageKeySetIterator = precentageJO.keySet().iterator();
		
		while(precentageKeySetIterator.hasNext())
		{
			String key = precentageKeySetIterator.next();
			
			if(((Double)precentageJO.get(key)) > 0 && userCharacterProfile.containsKey(key))
			{
				JSONObject category = (JSONObject) userCharacterProfile.get(key);
				JSONObject precentageMappedObject = af.adAmountToPrecentageMapper(category);
				
				Double internalTotalPrecentage = (Double) precentageJO.get(key);
				
				Iterator<String> internalObjKeys = precentageMappedObject.keySet().iterator();
				
				while(internalObjKeys.hasNext())
				{
					String exisitingItemKey = internalObjKeys.next();
					Double itemFinalPrecentage = ((Double)precentageMappedObject.get(exisitingItemKey)/100.0) * internalTotalPrecentage;
					FinalAdFetcherReadyPrecentageAdvertisementObj.put(exisitingItemKey, itemFinalPrecentage);
				}
				
			}
		}
		
		
		return FinalAdFetcherReadyPrecentageAdvertisementObj;
	}
	
	
	//Product Mentions mapper to precentage
	private JSONObject adAmountToPrecentageMapper(JSONObject adObject)
	{
		JSONObject internalJSON = new AdvertisementFetcher().keysAndTotalMentions(adObject);
		JSONArray keys = (JSONArray) internalJSON.get("keylist");
		int totalMentions = (int) internalJSON.get("totalMentions");
		
		for (int j = 0; j < keys.size(); j++)
		{
			String key = (String) keys.get(j);
			int amount = (int)adObject.get(key);
			Double totalPrecentage = (amount/(totalMentions/1.0)*100.0);
			adObject.put(key, totalPrecentage);
		}
		
		System.err.println("+++++++"+adObject.toString());
		
		return adObject;
	}
	
	//Returns keys and Total amount of mentions from and object
	private JSONObject keysAndTotalMentions(JSONObject adObject)
	{
		Iterator<String> keyIterator = adObject.keySet().iterator();
		
		JSONArray keys = new JSONArray();
		int totalMentions = 0;
		
		while(keyIterator.hasNext())
		{
			String key = keyIterator.next();
			keys.add(key);
		}
		
		for(int i = 0; i < keys.size(); i++)
		{
			String key = keys.get(i).toString();
			int amount = (int)adObject.get(key);
			totalMentions = totalMentions + amount;
		}
		
		JSONObject returningJO = new JSONObject();
		returningJO.put("totalMentions", totalMentions);
		returningJO.put("keylist", keys);
		
		return returningJO;
	}
	
	// mapping mapped precentages to 100%
	public static JSONObject advertisementPrecentageProfile(JSONObject productPrecentageJO)
	{
		JSONObject newProductPrecentageJO = new JSONObject();
		Double totalPrecentageCount = 0.0;
		ArrayList<String> keys = new ArrayList<String>();
		Iterator<String> precentageIterator = productPrecentageJO.keySet().iterator();
		
		while(precentageIterator.hasNext())
		{
			String key = precentageIterator.next().toString();
			totalPrecentageCount = totalPrecentageCount + (Double)productPrecentageJO.get(key);
			keys.add(key);
		}
		
		if (totalPrecentageCount < 100)
		{
			for (String internalKey : keys) {
				Double currentPrecentage = (Double)productPrecentageJO.get(internalKey);
				Double MappedPrecentage = (currentPrecentage / totalPrecentageCount) * 100.0;
				newProductPrecentageJO.put(internalKey, MappedPrecentage);
			}
			
			return newProductPrecentageJO;
		}
		
		return productPrecentageJO;
	}

}

package com.research.wrappers;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.research.databaseAccess.AdvertisementDAO;
import com.research.databaseAccess.UserDAO;
import com.research.databaseObjects.Advertisement;
import com.research.databaseObjects.Relationship;
import com.research.databaseObjects.User;
import com.research.tweets.SingleTweetRelationshipAnalyzer;
import com.research.validation.TweetMapper;

public class UserAdditionalDataSetWrapper {
	
	public ArrayList<Advertisement> getAdvertisementsAccordingToTheUserKeyWords(String userName, int adCount)
	{
		User currentUser = new UserDAO().GetUser(userName.trim());
		JSONObject clearedWordObj = null;
		JSONArray currentWordObj = null;
		try {
			clearedWordObj = (JSONObject) new JSONParser().parse(currentUser.getClearedUserSearchedKeyWords());
			currentWordObj = (JSONArray) new JSONParser().parse(currentUser.getUserSearchedKeyWords());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ArrayList<Advertisement> searchQueryRelatedAds = new ArrayList<Advertisement>();
		
		//if (!clearedWordObj.isEmpty() || !currentWordObj.isEmpty()) {
		
		JSONObject adFetcherDetailsObj = calculateAdCountForEachProfile(clearedWordObj, currentWordObj, adCount);
		
		
		
		if (adFetcherDetailsObj.containsKey("currentAdProfile"))
		{
			JSONObject internalObj = (JSONObject) adFetcherDetailsObj.get("currentAdProfile");
			Iterator<String> queryIterator = internalObj.keySet().iterator();
			ArrayList<String> query = new ArrayList<String>();
			
			while (queryIterator.hasNext()) {
				
				String qry = queryIterator.next();
				int adAmount = (int)internalObj.get(qry);
				ArrayList<Relationship> relatedRelationshipAds = new AdvertisementDAO().GetAdvertisementArrayFromDatabase(qry, adAmount);
				
				for (Relationship relationship : relatedRelationshipAds) {
					searchQueryRelatedAds.add(relationship.getBelongingAdvertisement());
					System.err.println(relationship.getBelongingAdvertisement().getProductDescription());
				}
			}
		}

		if (adFetcherDetailsObj.containsKey("oldAdProfile"))
		{
			JSONObject internalObj = (JSONObject) adFetcherDetailsObj.get("oldAdProfile");
			Iterator<String> queryIterator = internalObj.keySet().iterator();
			ArrayList<String> query = new ArrayList<String>();
			
			while (queryIterator.hasNext()) {
				
				String qry = queryIterator.next();
				int adAmount = (int)internalObj.get(qry);
				ArrayList<Relationship> relatedRelationshipAds = new AdvertisementDAO().GetAdvertisementArrayFromDatabase(qry, adAmount);
				
				for (Relationship relationship : relatedRelationshipAds) {
					searchQueryRelatedAds.add(relationship.getBelongingAdvertisement());
					System.err.println(relationship.getBelongingAdvertisement().getProductDescription());
				}
			}
		}
//		}

		return searchQueryRelatedAds;
	}
	
	
	// return all cleared search queries to string
	public String getClearedSearchQueryListAsString(JSONObject clearedUserSearchedWordsJO)
	{
		Iterator<String> timeStampIterator = clearedUserSearchedWordsJO.keySet().iterator();
		ArrayList<String> timeStampsAL = new ArrayList<String>();
		
		while(timeStampIterator.hasNext())
		{
			timeStampsAL.add((String)timeStampIterator.next());
		}
		
		String searchQueryStr = "";
		
		for (String key : timeStampsAL) {
			JSONArray keyArr = (JSONArray) clearedUserSearchedWordsJO.get(key);
			searchQueryStr = searchQueryStr + " " + new TweetMapper().mapJsonArrayToString(keyArr);	
		}
		
		return searchQueryStr.trim();
	}
	
	// return product mention json object from old search terms
	public JSONObject getProductMentionProfileForOldSearchQuery(JSONObject clearedUserSearchedWordsJO)
	{
		String searchQueryStr = new UserAdditionalDataSetWrapper().getClearedSearchQueryListAsString(clearedUserSearchedWordsJO); 
		JSONObject mentionProfileJO = new SingleTweetRelationshipAnalyzer().TweetCategorizedUserProfileJSONObject(searchQueryStr);
		JSONObject productJA = (JSONObject) mentionProfileJO.get("product");
		return productJA;
	}
	
	// return product mention json object for current search terms
	public JSONObject getProductMentionProfileForCurrentSearchQuery(JSONArray currentProfileSearchQuery)
	{
		String str = new TweetMapper().mapJsonArrayToString(currentProfileSearchQuery);
		JSONObject mentionProfileJO = new SingleTweetRelationshipAnalyzer().TweetCategorizedUserProfileJSONObject(str);
		JSONObject productJA = (JSONObject) mentionProfileJO.get("product");
		return productJA;
	}
	
	// return product count of product profile
	public int totalProductCount(JSONObject productMentionProfile)
	{
		if (productMentionProfile != null) {
		Iterator<String> productIterator = productMentionProfile.keySet().iterator();
		ArrayList<String> products = new ArrayList<String>();
		int productCount = 0;
		
		while(productIterator.hasNext())
		{
			products.add((String)productIterator.next());
		}
		
		for (String product : products) {
			int amount = (int) productMentionProfile.get(product);
			productCount = productCount + amount;
		}
		
			return productCount;
		}
		
		return 0;
	}
	
	
	// calculate how much precentage should give each category to fetch products
	public JSONObject getProductPrecentageToFetch(JSONObject currentProductMentionProfile, JSONObject oldProductMentionProfile, int totalAdCount)
	{
		int currentProductCount = totalProductCount(currentProductMentionProfile);
		int oldProductCount = totalProductCount(oldProductMentionProfile);
		
		Double currentProductPrecentage = 0.0;
		Double oldProductPrecentage = 0.0;
		
		JSONObject productFetchingPrecentage = new JSONObject();
		
		if(currentProductCount == 0)
		{
			oldProductPrecentage = 100.0;
		}else {
			
			Double currentProductRatio = (currentProductCount / (totalAdCount/1.0)) * 100.0;
			
			if (currentProductRatio >= 20.0)
			{
				currentProductPrecentage = 100.0;
			} else {
				
				currentProductPrecentage = 50.0;
				oldProductPrecentage = 50.0;
				
			}
		}
		
		productFetchingPrecentage.put("currentPrecentage", currentProductPrecentage);
		productFetchingPrecentage.put("oldPrecentage", oldProductPrecentage);
		
		return productFetchingPrecentage;
	}
	
	public JSONObject calculateAdCountForEachProfile(JSONObject clearedUserSearchedKeyWords, JSONArray currentUserSearchedKeywords, int totalAdCount)
	{
		JSONObject oldProductMentionProfile = getProductMentionProfileForOldSearchQuery(clearedUserSearchedKeyWords);
		JSONObject currentProductMentionProfile = getProductMentionProfileForCurrentSearchQuery(currentUserSearchedKeywords);
		
		JSONObject fetchingPrecentages = getProductPrecentageToFetch(currentProductMentionProfile, oldProductMentionProfile, totalAdCount);
		
		int totalOldProducts = totalProductCount(oldProductMentionProfile);
		int totalCurrentProducts = totalProductCount(currentProductMentionProfile);
		
		Double allocatedProductCountforOLDX = Math.ceil((totalAdCount * (Double)fetchingPrecentages.get("oldPrecentage"))/100);
		Double allocatedProductCountforCURRENTX = Math.ceil((totalAdCount * (Double)fetchingPrecentages.get("currentPrecentage"))/100);
		
		int allocatedProductCountforOLD = allocatedProductCountforCURRENTX.intValue();
		int allocatedProductCountforCURRENT = allocatedProductCountforCURRENTX.intValue();
		
		JSONObject productCountEachProfile = new JSONObject();
		
		if (allocatedProductCountforCURRENT > 0)
		{
			productCountEachProfile.put("currentAdProfile", productMentionWithHowManyProducts(currentProductMentionProfile, allocatedProductCountforCURRENT));
		}
		
		if (allocatedProductCountforOLD > 0)
		{
			productCountEachProfile.put("oldAdProfile", productMentionWithHowManyProducts(oldProductMentionProfile, allocatedProductCountforOLD));
		}
		
		
		return productCountEachProfile;
	}
	
	
	public JSONObject productMentionWithHowManyProducts(JSONObject productMentionProfile, int allocatedAdsForTheProfile)
	{
		JSONObject finalProductAmountJO = new JSONObject();
		
		if (productMentionProfile != null) {
		Iterator<String> productNamesIterator = productMentionProfile.keySet().iterator();
		ArrayList<String> productNames = new ArrayList<String>();
		
		while(productNamesIterator.hasNext())
		{
			productNames.add((String)productNamesIterator.next());
		}
		
		int totalProductCount = totalProductCount(productMentionProfile);
		
		
		
		for (String product : productNames) {
			int adCount = ((Double)(((int)productMentionProfile.get(product) / (totalProductCount/1.0)) * allocatedAdsForTheProfile)).intValue();
			finalProductAmountJO.put(product, adCount);
		}

		}
		
		return finalProductAmountJO;
	}
	
	
	public JSONObject advertisementJSONmapper(ArrayList<Advertisement> advertisements)
	{
    	JSONObject AllProductsJSON = new JSONObject();
  
    	for (int i = 0; i < advertisements.size(); i++) {
    		
    		JSONObject productJSON = new JSONObject();
    		Advertisement currentAd = advertisements.get(i);
			
    		productJSON.put("ProductId", currentAd.getProductId());
    		productJSON.put("ProductTitle",currentAd.getProductTitle());
    		productJSON.put("ProductDescription", currentAd.getProductDescription());
    		productJSON.put("ProductImageURL", currentAd.getProductImageURL());
    		productJSON.put("ProductPrice", currentAd.getProductPrice());
    		
    		AllProductsJSON.put(i, productJSON);
		}

		return AllProductsJSON;
	}
	


}

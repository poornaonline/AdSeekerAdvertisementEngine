package com.research.wrappers;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.research.databaseAccess.AdvertisementDAO;
import com.research.databaseAccess.AdvertisementFetcher;
import com.research.databaseAccess.OntologyReadyJSONConverter;
import com.research.databaseAccess.UserDAO;
import com.research.databaseObjects.User;
import com.research.suggestions.EquivalentItemSuggester;
import com.research.tweets.SingleTweetRelationshipAnalyzer;
import com.research.validation.TweetMapper;

public class DirectTweetAnalyzerWrapper {
	
	//This function uses the Old advertisement Fetching Algorithm
	// Not for Production
	public static JSONObject getAdvertisementsFromTheDatabase(JSONArray analyzingTweetArray, int advertisementCount)
	{
		TweetMapper mapper = new TweetMapper();
    	String analysingTweet = mapper.mapJsonArrayToString(analyzingTweetArray);
    	
    	SingleTweetRelationshipAnalyzer stra = new SingleTweetRelationshipAnalyzer();
    	HashMap<String, Double> TweetPrecentageHashMap = stra.getTweetPrecentageHashMap(analysingTweet);
    	
    	JSONObject responseObject = new JSONObject();
    	responseObject.putAll(TweetPrecentageHashMap);
    	
    	int HTTPResopnseID = 500;
    	
    	AdvertisementDAO dao = new AdvertisementDAO();
    	JSONObject recommendedAdvertisementsJO = dao.GetAdvertisementsJSONObject(responseObject, advertisementCount);
    	   	
    	if(recommendedAdvertisementsJO != null)
    	{
    		HTTPResopnseID = 200;
    	}
		
		JSONObject returningJO = new JSONObject();
		returningJO.put("httpResponse", HTTPResopnseID);
		returningJO.put("adObject", recommendedAdvertisementsJO);
		
		return returningJO;
	}
	
	//This function uses new Algorithm to fetch algorithms from the database
	// Not for Production
	public static JSONObject getAdvertisementsFromTheDatabaseNewAlgorithm(JSONArray analyzingTweetArray, int advertisementCount)
	{
		
		TweetMapper mapper = new TweetMapper();
		String analysingTweet = mapper.mapJsonArrayToString(analyzingTweetArray);

    	SingleTweetRelationshipAnalyzer stra = new SingleTweetRelationshipAnalyzer();
    	JSONObject userProfile = stra.TweetCategorizedUserProfileJSONObject(analysingTweet);
    	
    	AdvertisementFetcher af = new AdvertisementFetcher();
    	JSONObject productPrecentageJO = af.getProductCountFinalPrecentages(userProfile, advertisementCount);
    	
    	int HTTPResopnseID = 500;
    	
    	AdvertisementDAO dao = new AdvertisementDAO();
    	JSONObject recommendedAdvertisementsJO = dao.GetAdvertisementsJSONObject(productPrecentageJO, advertisementCount);
    	   	
    	if(recommendedAdvertisementsJO != null)
    	{
    		HTTPResopnseID = 200;
    	}

    	JSONObject returningJO = new JSONObject();
		returningJO.put("httpResponse", HTTPResopnseID);
		returningJO.put("adObject", recommendedAdvertisementsJO);
		
		return returningJO;
	}
	
	// Not for Production
	public static JSONObject getEquivalentProductsFromTweets(JSONArray analyzingTweetArray, int advertisementCount, boolean fromNewAlgorithm)
	{
		TweetMapper mapper = new TweetMapper();
    	String analysingTweets = mapper.mapJsonArrayToString(analyzingTweetArray);
    	SingleTweetRelationshipAnalyzer stra = new SingleTweetRelationshipAnalyzer();
    	
    	AdvertisementDAO dao = new AdvertisementDAO();
    	EquivalentItemSuggester suggestter = new EquivalentItemSuggester();
    	JSONObject suggestedAdvertisementProducts = new JSONObject();
    	
    	if (fromNewAlgorithm)
    	{
    		JSONObject userProfile = stra.TweetCategorizedUserProfileJSONObject(analysingTweets);
    		AdvertisementFetcher af = new AdvertisementFetcher();
    		JSONObject returningFinalPrecentages = af.getProductCountFinalPrecentages(userProfile, advertisementCount);
    		suggestedAdvertisementProducts = suggestter.getEquivalentAdvertisementsJSONObj(returningFinalPrecentages);
    		
    	} else {
    		
    		HashMap<String, Double> TweetPrecentageHashMap = stra.getTweetPrecentageHashMap(analysingTweets);
    		JSONObject responseObject = new JSONObject();
        	responseObject.putAll(TweetPrecentageHashMap);
        	suggestedAdvertisementProducts = suggestter.getEquivalentAdvertisementsJSONObj(responseObject);
    		
    	}
    	

    	int HTTPResopnseID = 500;
    	
    	JSONObject suggestedAdvertisementObj = dao.GetAdvertisementsJSONObject(suggestedAdvertisementProducts, advertisementCount);
    	System.out.println(suggestedAdvertisementObj.toString());
    	
    	if(suggestedAdvertisementObj != null)
    	{
    		HTTPResopnseID = 200;
    	}
		
    	JSONObject returningJO = new JSONObject();
		returningJO.put("httpResponse", HTTPResopnseID);
		returningJO.put("adObject", suggestedAdvertisementObj);
		
		return returningJO;
		
	}
	
	/**
	 * 
	 * This function gets username, allTheTweets, AdvertisementCount
	 * If user is new or user's profile is older than 7days it creates a new user character profile using the received tweets && updates the searched keyword profile
	 */
	public static JSONObject optimisedGetAdvertisementsFromTheDb(String userName, String allTweetsString, int advertisementCount)
	{
		// Change the updating duration here
		long userProfileRefreshRate = TimeUnit.SECONDS.toMillis(10);
		
		UserDAO userDAO = new UserDAO();
    	User user = userDAO.GetUser(userName);

    	if(user == null)
    	{
    		SingleTweetRelationshipAnalyzer stra = new SingleTweetRelationshipAnalyzer();
        	JSONObject newUserProfile = stra.TweetCategorizedUserProfileJSONObject(allTweetsString);
        	userDAO.PushNewUserToTheDatabase(userName, newUserProfile);
        	user = userDAO.GetUser(userName);
    	}
    	
    	long userCharatedProfileUpdatedTime = user.getUserCharacterProfileUpdatedTime();
    	
    	if(System.currentTimeMillis() >= (userCharatedProfileUpdatedTime + userProfileRefreshRate))
    	{
    		System.out.println("UPDATING ------> User Character Profile Updating...");
    		JSONObject newUserProfile = new SingleTweetRelationshipAnalyzer().TweetCategorizedUserProfileJSONObject(allTweetsString);
    		new UserDAO().updateUserCharacterProfileAndKeyWordProfile(userName, newUserProfile);
    	}
    	
    	JSONParser parser = new JSONParser();
    	JSONObject existingUserProfile = null;
    	
    	try {
			existingUserProfile = OntologyReadyJSONConverter.mapDBFetchingJSONtoAlgorithmReadyJSONObj((JSONObject)parser.parse(user.getUserCharacterProfile()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

    	AdvertisementFetcher af = new AdvertisementFetcher();
    	JSONObject productPrecentageJOUnmapped = af.getProductCountFinalPrecentages(existingUserProfile, advertisementCount);
    	JSONObject productPrecentageJO = af.advertisementPrecentageProfile(productPrecentageJOUnmapped);
    	int HTTPResopnseID = 500;
    	
    	AdvertisementDAO dao = new AdvertisementDAO();
    	JSONObject recommendedAdvertisementsJO = dao.GetAdvertisementsJSONObject(productPrecentageJO, advertisementCount);
    	   	
    	if(recommendedAdvertisementsJO != null)
    	{
    		HTTPResopnseID = 200;
    	}
    	
    	JSONObject finalReturningObj = new JSONObject();
    	finalReturningObj.put("recommendedAdvertisements", recommendedAdvertisementsJO);
    	finalReturningObj.put("httpResponseId", HTTPResopnseID);
		
		return finalReturningObj;
	}
	
	/*
	 * Get Equivalent products from the users character profile
	 * user should be exist
	 * New Algorithm used, Production ready
	 */
	public static JSONObject optimisedGetEquivalentProductsFromTweets(String userName, int advertisementCount)
	{
		
		User user = new UserDAO().GetUser(userName);

    	JSONObject userProfile = new JSONObject();
    	
		try {
			userProfile = OntologyReadyJSONConverter.mapDBFetchingJSONtoAlgorithmReadyJSONObj((JSONObject) new JSONParser().parse(user.getUserCharacterProfile()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

    	JSONObject returningFinalPrecentages = new AdvertisementFetcher().getProductCountFinalPrecentages(userProfile, advertisementCount);
    	JSONObject suggestedAdvertisementProducts = new EquivalentItemSuggester().getEquivalentAdvertisementsJSONObj(returningFinalPrecentages);

    	int HTTPResopnseID = 500;
    	
    	JSONObject suggestedAdvertisementObj = new AdvertisementDAO().GetAdvertisementsJSONObject(suggestedAdvertisementProducts, advertisementCount);
    	System.out.println(suggestedAdvertisementObj.toString());
    	
    	if(suggestedAdvertisementObj != null)
    	{
    		HTTPResopnseID = 200;
    	}
		
    	JSONObject returningJO = new JSONObject();
		returningJO.put("httpResponse", HTTPResopnseID);
		returningJO.put("adObject", suggestedAdvertisementObj);
		
		return returningJO;
		
	}
	
	// return equivalent products for single user explicitly mentioned product
	public static JSONObject optimisedGetEqProductForSingleProductTitle(String productTitle, int adAmount)
	{
		JSONObject productTitleAnalysedObj = new SingleTweetRelationshipAnalyzer().TweetCategorizedUserProfileJSONObject(productTitle);
		JSONObject returningFinalPrecentages = new AdvertisementFetcher().getProductCountFinalPrecentages(productTitleAnalysedObj, adAmount);
    	JSONObject suggestedAdvertisementProducts = new EquivalentItemSuggester().getEquivalentAdvertisementsJSONObj(returningFinalPrecentages);

    	int HTTPResopnseID = 500;
    	
    	JSONObject suggestedAdvertisementObj = new AdvertisementDAO().GetAdvertisementsJSONObject(suggestedAdvertisementProducts, adAmount);
    	
    	if(suggestedAdvertisementObj != null)
    	{
    		HTTPResopnseID = 200;
    	}
		
    	JSONObject returningJO = new JSONObject();
		returningJO.put("httpResponse", HTTPResopnseID);
		returningJO.put("adObject", suggestedAdvertisementObj);
		
		return returningJO;
	}

}

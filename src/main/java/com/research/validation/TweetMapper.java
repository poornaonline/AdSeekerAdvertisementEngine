package com.research.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TweetMapper {
	
	// Map Tweets arrayList to String
	public String mapTweetArrayListToString(ArrayList<String> tweetObj)
	{
		String allTweets = "";
		
		for (String str : tweetObj) {
			allTweets = allTweets + " " + str;
		}
		
		return allTweets;
	}
	
	// Map tweet JSONObject to String
	public String mapTweetJSONtoString(JSONObject tweetObj)
	{
		String allTweets = "";
		
		Set<String> keys = tweetObj.keySet();
		
		for (String key : keys) {
			String currentTweet = tweetObj.get(key).toString();
			allTweets = allTweets + " " + currentTweet;
		}
		
		return allTweets;
	}
	
	// Map JSONArray to JSONObj
	public JSONObject mapJsonArrayToJsonObject(JSONArray arr)
	{
		JSONObject convertedObj = new JSONObject();
		
		for (int i = 0; i < arr.size(); i++) {
			convertedObj.put(Integer.toString(i), arr.get(i));
		}
		
		return convertedObj;
	}
	
	// Map JSONArray to String
	public String mapJsonArrayToString(JSONArray arr)
	{
		String allTweets = "";

		for(int i = 0; i < arr.size(); i++)
		{
			String currentTweet = (String)arr.get(i);
			allTweets = allTweets + " " + currentTweet;
		}
		
		return allTweets;
	}
	
	// Map ArrayList<String> to JSONArray 
	public JSONArray mapArrayListToJsonArray(ArrayList<String> tweetsArrayList)
	{
		JSONArray tweetJSONArray = new JSONArray();
		
		for (String tweet : tweetsArrayList) {
			tweetJSONArray.add(tweet);
		}
		
		return tweetJSONArray;
	}
}

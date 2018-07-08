package com.research.validation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;

public class Validation {
	
	
	public static String spaceReducer(String dirtyTweet)
	{
		String filteredTweet = dirtyTweet.trim();
		filteredTweet = filteredTweet.replaceAll("  ", " ");
		
		return filteredTweet;
	}
	
	public static String getOnlyNumbersFromString(String s) 
	{
	    Pattern pattern = Pattern.compile("[^0-9]");
	    Matcher matcher = pattern.matcher(s);
	    String number = matcher.replaceAll("");
	    
	    return number;
	 }
	
	 public static String getOnlyLettersFromString(String s) 
	 {
	    Pattern pattern = Pattern.compile("[^a-z A-Z]");
	    Matcher matcher = pattern.matcher(s);
	    String str = matcher.replaceAll("");
	    
	    return str;
	 }
	 
	 public static String getSpecialCharRemovedString(String s) 
	 {
		 
		 String newStr = spaceReducer(s);
		 newStr = newStr.replaceAll("[^a-zA-Z0-9\\s]", "");
		 return newStr;
	 }
	 
	 public static String getSpecialCharRemovedOntoReadyString(String s)
	 {
		 String newStr = getSpecialCharRemovedString(s);
		 newStr = newStr.toLowerCase().replaceAll(" ", "_");
		 return newStr;
	 }
	 
	 public static ArrayList<String> ontologyReadyRelationshipHierarchy(ArrayList<String> al)
	 {
		 ArrayList<String> ontoReadyAl = new ArrayList<String>();
		 
		 for (int i = 0; i < al.size(); i++)
		 {
			 String str = getSpecialCharRemovedOntoReadyString(al.get(i));
			 ontoReadyAl.add(str);
		 }
		 
		 return ontoReadyAl;
	 }
	 
	 public static String trimAndtoLowerCase(String s)
	 {
		 String returningString = s.trim().toLowerCase();
		 return returningString;
	 }
	 
	 public static JSONArray sentimentAnalyserReadyTweetArray(JSONArray tweetArray)
	 {
		 JSONArray readyTweetArray = new JSONArray();
		 
		 for(int i = 0; i < tweetArray.size(); i++)
		 {
			 String tweet = (String) tweetArray.get(i);
			 String formattedTweet = trimAndtoLowerCase(tweet);
			 readyTweetArray.add(formattedTweet);
		 }
		 
		 return readyTweetArray;
	 }
	 
	 public static String getUnderscoreReplacedWithSpaceString(String str)
	 {
		 String newStr = str.replaceAll("_", " ");
		 return newStr.trim();
	 }
	 
	 public static String getSpaceReplacedWithUndescore(String str)
	 {
		 String newStr = str.replaceAll(" ", "_");
		 return newStr.trim();
	 }
	 
	 public static String pushInToOntologyReady(String str)
	 {
		 String newStr = str.replaceAll(" ", "_").toLowerCase().trim();
		 return newStr;
	 }

}

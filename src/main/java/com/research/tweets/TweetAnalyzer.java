package com.research.tweets;
import java.util.ArrayList;

import com.research.IO.FilterWordList;
import com.research.validation.Validation;



public class TweetAnalyzer {
	
	private static ArrayList<String> wordSet = null;
	
	public TweetAnalyzer() {
		
		if (wordSet == null)
		{
			wordSet = FilterWordList.getWordListArray();
		}
	}
	

	private String finalFilterForTheTweet(String tweet)
	{	
		tweet = Validation.spaceReducer(tweet);
		
		String modifiedTweet = new String();
		
		for (String word : tweet.split(" "))
		{
			if( !word.contains("@") && 
				!word.contains(".") && 
				!word.contains(",") && 
				!word.contains("/") && 
				!word.contains("#") &&
				!wordSet.contains(word) )
			{
				modifiedTweet += " " + word;
			}
		}

		return modifiedTweet.trim();
	}
	 
	
	public ArrayList<String> getTweetWordFinalArrayList(String tweet)
	{
		ArrayList<String> tweetWords = new ArrayList<String>();
		
		String filteredTweet = finalFilterForTheTweet(tweet);
		
		for (String word : filteredTweet.split(" "))
		{
			tweetWords.add(word);
		}
		
		return tweetWords;
	}
	
	
	public ArrayList<String> getTweetVariations(ArrayList<String> filteredWordList)
	{
		ArrayList<String> wordVariations = (ArrayList<String>)filteredWordList.clone();
		
		String currentWord = filteredWordList.get(0);
		
		for(int i=1; i < filteredWordList.size(); i++)
		{
			currentWord = currentWord + " " + filteredWordList.get(i);
			wordVariations.add(currentWord);
		}
		
		String doubleWordPair;
		
		for(int j=1; j < filteredWordList.size()-1; j++)
		{
			doubleWordPair = filteredWordList.get(j) + " " + filteredWordList.get(j+1);
			wordVariations.add(doubleWordPair);
			
		}
		
		String tribleWordPair;
		
		for(int j=1; j < filteredWordList.size()-2; j++)
		{
			tribleWordPair = filteredWordList.get(j) + " " + filteredWordList.get(j+1) + " " + filteredWordList.get(j+1);
			wordVariations.add(tribleWordPair);
		}
		
		return wordVariations;
	}
	
	
	public ArrayList<String> getHashArrayListFromTweet(String tweet)
	{
		ArrayList<String> hashArrayList = new ArrayList<String>();
		
		for (String word : tweet.split(" "))
		{
			if (word.startsWith("#"))
			{
				word = word.replace("#", "");
				hashArrayList.add(word);
			}
		}
		
		return hashArrayList;
	}
	
	
}

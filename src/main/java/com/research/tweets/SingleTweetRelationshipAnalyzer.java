package com.research.tweets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import com.research.IO.AdSeekerOntology;
import com.research.IO.AdSeekerUpdatingOntology;
import com.research.ontology.OntologyRelationships;


public class SingleTweetRelationshipAnalyzer {
	
	
	//Return ArrayList of ArrayList with All tweets relationships
	// Updated with Secondary Ontology
	public ArrayList<ArrayList<String>> getTweetRelationshipArrayList(String tweet)
	{
		
		System.out.println("Receiving Tweet : " + tweet);
		
		OntologyRelationships ontologyRelationshipObj = new OntologyRelationships();
		TweetAnalyzer twitterAnalyzerObj = new TweetAnalyzer();
		
		ArrayList<ArrayList<String>> arrayListOfRelationshipArrayList = new ArrayList<ArrayList<String>>();
		ArrayList<String> asdf = twitterAnalyzerObj.getTweetWordFinalArrayList(tweet);
		ArrayList<String> tweetWordFinalArrayList = twitterAnalyzerObj.getTweetVariations(asdf);
		
		for (String tweetWordArrayList : tweetWordFinalArrayList) {
			
			ArrayList<String> relationshipArray = ontologyRelationshipObj.listOntologyRelationshipInArrayList(tweetWordArrayList, AdSeekerOntology.getModel(), AdSeekerOntology.getOntologyURI());
			
			if (relationshipArray.size() > 0)
			{
				arrayListOfRelationshipArrayList.add(relationshipArray);
			}else {
				
				ArrayList<String> secondaryRelationshipArray = ontologyRelationshipObj.listOntologyRelationshipInArrayList(tweetWordArrayList, AdSeekerUpdatingOntology.getModel(), AdSeekerUpdatingOntology.getOntologyURI());
				
				if (secondaryRelationshipArray.size() > 0)
				{
					arrayListOfRelationshipArrayList.add(secondaryRelationshipArray);
				}
			}
			
		}
		
		System.out.println("LOG : Returning Relationship ArrayList : " + arrayListOfRelationshipArrayList.toString());
		
		return arrayListOfRelationshipArrayList;
	}
	
	// Tweet Analysis Algorithm 1
	public HashMap<String, Double> getTweetPrecentageHashMap(String tweet)
	{
		
		Double firstElementWeight = 0.1;
		Double secondElementWeight = 1.0;
		Double thirdElementWeight = 10.0;
		Double fourthElementWeight = 50.0;
		Double fifthElementWeight = 50.0;
		
		SingleTweetRelationshipAnalyzer stAnalyzer = new SingleTweetRelationshipAnalyzer();
		
		ArrayList<ArrayList<String>> trArrayList = stAnalyzer.getTweetRelationshipArrayList(tweet);
		
		HashMap<String, Double> tweetWeightDictionary = new HashMap<String, Double>();
		
		for (ArrayList<String> arrayList : trArrayList) {
			
			Collections.reverse(arrayList);
			
			int elementNumber = 1;

			for (String category : arrayList) {
				
				switch (elementNumber) {
				case 1:
					if(!tweetWeightDictionary.containsKey(category))
					{
						tweetWeightDictionary.put(category, firstElementWeight);
						
					}else {
						Double existingWeight = tweetWeightDictionary.get(category);
						existingWeight = existingWeight * 2;
						tweetWeightDictionary.put(category, existingWeight);
					}
					elementNumber++;
					break;
					
				case 2:
					if(!tweetWeightDictionary.containsKey(category))
					{
						tweetWeightDictionary.put(category, secondElementWeight);
						
					}else {
						Double existingWeight = tweetWeightDictionary.get(category);
						existingWeight = existingWeight * 2;
						tweetWeightDictionary.put(category, existingWeight);
					}
					elementNumber++;
					break;
					
				case 3:
					if(!tweetWeightDictionary.containsKey(category))
					{
						tweetWeightDictionary.put(category, thirdElementWeight);
						
					}else {
						Double existingWeight = tweetWeightDictionary.get(category);
						existingWeight = existingWeight * 2;
						tweetWeightDictionary.put(category, existingWeight);
					}
					elementNumber++;
					break;
					
				case 4:
					if(!tweetWeightDictionary.containsKey(category))
					{
						tweetWeightDictionary.put(category, fourthElementWeight);
						
					}else {
						Double existingWeight = tweetWeightDictionary.get(category);
						existingWeight = existingWeight * 2;
						tweetWeightDictionary.put(category, existingWeight);
					}
					elementNumber++;
					break;
					
				case 5:
					if(!tweetWeightDictionary.containsKey(category))
					{
						tweetWeightDictionary.put(category, fifthElementWeight);
						
					}else {
						Double existingWeight = tweetWeightDictionary.get(category);
						existingWeight = existingWeight * 2;
						tweetWeightDictionary.put(category, existingWeight);
					}
					elementNumber++;
					break;

				default:
					break;
				}
				
			}
			
		}
		
		System.out.println("********************************");
		
		Double totalTweetCategoryWeight = 0.0;
		
		for (String categoryX : tweetWeightDictionary.keySet()) {
			
			Double currentWeight = tweetWeightDictionary.get(categoryX);
			totalTweetCategoryWeight = totalTweetCategoryWeight + currentWeight;
			
		}
		
		HashMap<String, Double> tweetCategoryPrecentageHashMap = (HashMap<String, Double>) tweetWeightDictionary.clone();
		
		for (String categoryX : tweetWeightDictionary.keySet()) {
			
			Double currentWeightPrecentage = (tweetCategoryPrecentageHashMap.get(categoryX) / totalTweetCategoryWeight) * 100;
			DecimalFormat decimalFormat = new DecimalFormat("#.###");
			currentWeightPrecentage = Double.valueOf(decimalFormat.format(currentWeightPrecentage));
			tweetCategoryPrecentageHashMap.put(categoryX, currentWeightPrecentage);
		}
		
		System.out.println("LOG : Tweet Precentage HashMap");
		System.out.println(tweetCategoryPrecentageHashMap.toString());
		
		return tweetCategoryPrecentageHashMap;
		
	}
	
	// Get the element type from the ontology
	private String getElementType(String elementName)
	{
		Individual ontologyElement = AdSeekerOntology.getReasonedModel().getIndividual(AdSeekerOntology.getOntologyURI() + elementName);
		Property isType = AdSeekerOntology.getReasonedModel().getProperty(AdSeekerOntology.getOntologyURI() + "isTypeOf");
		
		String elementType = "undefined";
		
		try {
		
			if(ontologyElement != null)
			{
				Statement statement = ontologyElement.getProperty(isType);
				elementType = statement.getString();
				
			}else {
				
				OntClass classElement = AdSeekerOntology.getReasonedModel().getOntClass(AdSeekerOntology.getOntologyURI() + elementName);
				Statement statement = classElement.getProperty(isType);
				elementType = statement.getString();
				
			}
		}catch(NullPointerException np)
		{
			np.printStackTrace();
		}
		
		return elementType;
	}
	
	// User profile creation algorithm
	// Return how many mentions are there in all the tweets
	public JSONObject TweetCategorizedUserProfileJSONObject(String tweet)
	{
		SingleTweetRelationshipAnalyzer stAnalyzer = new SingleTweetRelationshipAnalyzer();
		ArrayList<ArrayList<String>> trArrayList = stAnalyzer.getTweetRelationshipArrayList(tweet);
		
		JSONObject UserCharacterProfileJSONObject = new JSONObject();
		
		for (ArrayList<String> relationshipAL : trArrayList) {
			
			for (String elementName : relationshipAL) {
				
				String categoryName = stAnalyzer.getElementType(elementName);
				
				if(!UserCharacterProfileJSONObject.containsKey(categoryName))
				{
					JSONObject categoryJO = new JSONObject();
					categoryJO.put(elementName, 1);
					UserCharacterProfileJSONObject.put(categoryName, categoryJO);
					
				}else {
					
					JSONObject storedObj = (JSONObject) UserCharacterProfileJSONObject.get(categoryName);
					
					if(storedObj.containsKey(elementName))
					{
						int elementCount = (int) storedObj.get(elementName);
						elementCount = elementCount + 1;
						storedObj.put(elementName, elementCount);
					}else {
						storedObj.put(elementName, 1);
					}
					
					UserCharacterProfileJSONObject.put(categoryName, storedObj);
					
				}
			}
		}
		
		System.err.println(UserCharacterProfileJSONObject.toString());
		return UserCharacterProfileJSONObject;
	}

}

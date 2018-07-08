package com.research.suggestions;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.research.IO.AdSeekerOntology;

public class EquivalentItemSuggester {
	
	
	private JSONObject getEquivalentProducts(JSONObject analyzedAdObj)
	{
		Iterator<String> adIterator = analyzedAdObj.keySet().iterator();
		JSONObject eqProductObj = new JSONObject();
		
		Property sameRange = AdSeekerOntology.getReasonedModel().getProperty(AdSeekerOntology.getOntologyURI() + "sameRange");

		while(adIterator.hasNext())
		{
			String itemName = adIterator.next().toString();
			Individual individual = AdSeekerOntology.getModel().getIndividual(AdSeekerOntology.getOntologyURI() + itemName);
			
	
			if(individual != null && individual.hasProperty(sameRange))
			{
				StmtIterator propertyIterator = individual.listProperties(sameRange);
				JSONArray suggestingProductArray = new JSONArray();
				
				while(propertyIterator.hasNext())
				{
					String sameRangeProductURI = propertyIterator.next().getObject().toString();
					Individual sameRangeURIIndividual = AdSeekerOntology.getModel().getIndividual(sameRangeProductURI);
					suggestingProductArray.add(sameRangeURIIndividual.getLocalName());
				}
				
				JSONObject internalObj = new JSONObject();
				Double precentage = (Double) analyzedAdObj.get(individual.getLocalName());
				internalObj.put("precentage", precentage);
				internalObj.put("suggestions", suggestingProductArray);
				
				eqProductObj.put(individual.getLocalName().toString(), internalObj);
			}
		}
		System.out.println("LOG : " + eqProductObj.toString());
		
		return eqProductObj;
	}
	
	
	private JSONObject equivalentProductPrecentageMapper(JSONObject equivalentProductObj)
	{
		Double totalProductPrecentage = 0.0;
		Iterator<String> keyIterator = equivalentProductObj.keySet().iterator();
		
		while(keyIterator.hasNext())
		{
			JSONObject internalJson = (JSONObject)equivalentProductObj.get(keyIterator.next());
			totalProductPrecentage = totalProductPrecentage + (Double)(internalJson.get("precentage"));
		}
		
		Iterator<String> keyIterator2 = equivalentProductObj.keySet().iterator();
		
		while(keyIterator2.hasNext())
		{
			String key = keyIterator2.next();
			JSONObject internalJson = (JSONObject)equivalentProductObj.get(key);
			Double newPrecentage = ((Double)(internalJson.get("precentage"))/ totalProductPrecentage) * 100.0;
			internalJson.put("precentage", newPrecentage);
			
			equivalentProductObj.put(key, internalJson);
		}
		System.out.println("LOG : " + equivalentProductObj.toString());
		
		return equivalentProductObj;
	}
	
	
	private JSONObject adFetcherReadyEqAdsObj(JSONObject mappedAdsObj)
	{
		Iterator<String> keyIterator = mappedAdsObj.keySet().iterator();
		JSONObject fetcherReadyObj = new JSONObject();
		
		while(keyIterator.hasNext())
		{
			JSONObject internalJson = (JSONObject)mappedAdsObj.get(keyIterator.next());
			Double wholePrecenage = (Double)(internalJson.get("precentage"));
			JSONArray eqProductJArray = (JSONArray) internalJson.get("suggestions");
			Double PrecentageForOneItem = wholePrecenage / eqProductJArray.size();
			
			for(int i = 0; i < eqProductJArray.size(); i++)
			{
				String productName = (String) eqProductJArray.get(i);
				if(fetcherReadyObj.containsKey(productName))
				{
					Double newPrecenage = (Double)fetcherReadyObj.get(productName) + PrecentageForOneItem;
					fetcherReadyObj.put(productName, newPrecenage);
				}else {
					fetcherReadyObj.put(productName, PrecentageForOneItem);
				}
			}
			
		}
		System.out.println("LOG : " + fetcherReadyObj.toString());
		
		return fetcherReadyObj;
	}
	
	
	public JSONObject getEquivalentAdvertisementsJSONObj(JSONObject analyzedAdObj)
	{
		EquivalentItemSuggester suggester = new EquivalentItemSuggester();
		JSONObject eqProductObj = suggester.getEquivalentProducts(analyzedAdObj);
		JSONObject eqProductPrecentageMappedObj = suggester.equivalentProductPrecentageMapper(eqProductObj);
		JSONObject fetcherReadyObj = suggester.adFetcherReadyEqAdsObj(eqProductPrecentageMappedObj);
		
		System.out.println("LOG : " + fetcherReadyObj.toString());
		
		return fetcherReadyObj;
	}
}

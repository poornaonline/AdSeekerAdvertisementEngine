package com.research.ontology;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.research.validation.Validation;


public class OntologyRelationships {
	
	private void recursionInOntology(Object existingOntologyItem, ArrayList<String> relationshipArrayList)
	{
		
		if (existingOntologyItem instanceof Individual && existingOntologyItem != null)
		{
			String individualLocalName = ((Individual)existingOntologyItem).getLocalName();
			System.out.println(individualLocalName);
			
			relationshipArrayList.add(individualLocalName);
			
			OntClass existingOntologyIndividualOntClassObject = ((Individual) existingOntologyItem).getOntClass();
			recursionInOntology(existingOntologyIndividualOntClassObject,relationshipArrayList);
	
		}
		
		if (existingOntologyItem instanceof OntClass && existingOntologyItem != null)
		{
			String OntClassLocalName = ((OntClass)existingOntologyItem).getLocalName();
			System.out.println(OntClassLocalName);
			
			relationshipArrayList.add(OntClassLocalName);
			
			OntClass existingOntologyOntClassObject = ((OntClass) existingOntologyItem).getSuperClass();
			recursionInOntology(existingOntologyOntClassObject,relationshipArrayList);
		}
		
	}


	public JSONObject listOntologyRelationshipsInJson(String item, OntModel model, String OntologyURI)
	{
		JSONObject relationshipJsonObject = new JSONObject();
		
		ArrayList<String> returnedArrayList = new ArrayList<String>();
		
		returnedArrayList = listOntologyRelationshipInArrayList(item, model, OntologyURI);
		
		if (returnedArrayList.size() > 0)
		{
			int relationshipCount = 1;
			
			for (String value : returnedArrayList) {
				relationshipJsonObject.put(relationshipCount, value);
				relationshipCount++;
			}
			
		}
		
		return relationshipJsonObject;
	}
	
	
	public ArrayList<String> listOntologyRelationshipInArrayList(String item, OntModel model, String OntologyURI)
	{
		ArrayList<String> relationshipArrayList = new ArrayList<String>();
		
		String internalItem = Validation.getSpecialCharRemovedOntoReadyString(item);
		
		Individual itemIndividual =  model.getIndividual(OntologyURI + internalItem);
		
		if (itemIndividual == null)
		{
			OntClass itemOntClass = model.getOntClass(OntologyURI + internalItem); 
			
			if (itemOntClass == null)
			{
				System.out.println("Item is not in the ontology");
			} else {
				
				recursionInOntology(itemOntClass,relationshipArrayList);
			}
			
		}else {
			
			recursionInOntology(itemIndividual,relationshipArrayList);
		}
		
		return relationshipArrayList;
	}
}

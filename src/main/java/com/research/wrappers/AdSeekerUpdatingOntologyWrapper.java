package com.research.wrappers;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.research.IO.AdSeekerUpdatingOntology;
import com.research.ontology.OntologyRelationships;


// This is use to fetch relationship hierarchy from the updating Ontology system
public class AdSeekerUpdatingOntologyWrapper {
	
	
	public ArrayList<String> getRelationshipArrayList(String elementName)
	{
		ArrayList<String> relationshipArrayList = new ArrayList<String>();
		relationshipArrayList = new OntologyRelationships().listOntologyRelationshipInArrayList(elementName, AdSeekerUpdatingOntology.getModel(), AdSeekerUpdatingOntology.getOntologyURI());
		return relationshipArrayList;
	}
	
	public JSONObject getRelationshipJsonObject(String elementName)
	{
		JSONObject relationshipJSONObject = new JSONObject();
		relationshipJSONObject = new OntologyRelationships().listOntologyRelationshipsInJson(elementName, AdSeekerUpdatingOntology.getModel(), AdSeekerUpdatingOntology.getOntologyURI());
		return relationshipJSONObject;
	}
	

}

package com.research.ontology;

import org.json.simple.JSONArray;

import com.hp.hpl.jena.ontology.Individual;
import com.research.IO.AdSeekerOntology;
import com.research.IO.AdSeekerUpdatingOntology;
import com.research.validation.Validation;

public class OntologyHelper {
	
	public String getTheIndividualFromTheRelationshipArray(JSONArray relationshipArray)
	{
		for(int i = 0; i < relationshipArray.size(); i++)
		{
			String element = Validation.pushInToOntologyReady(relationshipArray.get(i).toString());
			Individual individual = AdSeekerOntology.getModel().getIndividual(AdSeekerOntology.getOntologyURI() + element);
			Individual individual2 = AdSeekerUpdatingOntology.getModel().getIndividual(AdSeekerUpdatingOntology.getOntologyURI() + element);
			
			if(individual != null || individual2 != null)
			{
				return element;
			}
		}
		
		return null;
	}

}

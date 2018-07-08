package com.research.IO;

import java.util.ArrayList;

import com.research.ontology.OntologyRelationships;
import com.research.wrappers.AdSeekerUpdatingOntologyWrapper;

// Use two ontologies and fetch relationship from either of them
public class AdSeekerOntologySystemRelationshipMapper {
	
	public ArrayList<String> getRelationshipFromOntologies(String elementName)
	{
		ArrayList<String> relationshipArrayList = new ArrayList<String>();
		
		relationshipArrayList = new OntologyRelationships().listOntologyRelationshipInArrayList(elementName, AdSeekerOntology.getModel(), AdSeekerOntology.getOntologyURI());
		
		if (relationshipArrayList.size() == 0)
		{
			relationshipArrayList = new AdSeekerUpdatingOntologyWrapper().getRelationshipArrayList(elementName);
		}
		
		return relationshipArrayList;
	}

}

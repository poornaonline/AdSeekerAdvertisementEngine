package com.research.ontology;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.research.IO.AdSeekerOntology;

public class OntologyElement {
	
	public static ArrayList<String> getEquivalentClassesFromOntology(String elementName)
	{
		ArrayList<String> eqNames = new ArrayList<String>();
		OntClass ontClass = AdSeekerOntology.getReasonedModel().getOntClass(AdSeekerOntology.getOntologyURI() + elementName);
		
		if (ontClass != null)
		{
			ExtendedIterator<OntClass> eqClasses = ontClass.listEquivalentClasses();
			while(eqClasses.hasNext())
			{
				String className = ((OntClass) eqClasses.next()).getLocalName();
				eqNames.add(className);
			}
		}
		return eqNames;
	}

}

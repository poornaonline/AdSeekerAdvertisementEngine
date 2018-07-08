
package com.research.IO;

import java.io.File;
import java.io.InputStream;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;

public class AdSeekerUpdatingOntology {
	
	
	private static String OntologyURI = "http://www.semanticweb.org/poorna/ontologies/2016/4/untitled-ontology-2#";
	private static String OntologyFileNamePath = "/Users/Poorna/Documents/Eclipse Workspace/adseeker/Ontology/adseeker2.owl";
	
	private static OntModel reasonedOntModel = null;
	private static OntModel nonReasonedOntModel = null;
	private static long currentModelLastModifiedTimeForNonReasonedModel = 0;
	private static long currentModelLastModifiedTimeForReasonedModel = 0;
	
	public static OntModel getModel()
	{
		
		
		if (nonReasonedOntModel == null || (currentModelLastModifiedTimeForNonReasonedModel != (new File(OntologyFileNamePath).lastModified())))
		{
			nonReasonedOntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
			InputStream in = FileManager.get().open(OntologyFileNamePath);
			nonReasonedOntModel.read(in,null);
			
			// Setting the file's modified date
			currentModelLastModifiedTimeForNonReasonedModel = new File(OntologyFileNamePath).lastModified();
		}

		System.out.println("Non-Reasoned Model Returned");
		
		return nonReasonedOntModel;
	}
	
	public static OntModel getReasonedModel()
	{
		
		if (reasonedOntModel == null || (currentModelLastModifiedTimeForReasonedModel != (new File(OntologyFileNamePath).lastModified())))
		{
			OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
			InputStream in = FileManager.get().open(OntologyFileNamePath);
			model.read(in,null);

			Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
			reasoner= reasoner.bindSchema(model);
			 
			OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM_RDFS_INF;
			ontModelSpec.setReasoner(reasoner);
			reasonedOntModel = ModelFactory.createOntologyModel(ontModelSpec, model);
			
			// Setting the file's modified date
			currentModelLastModifiedTimeForReasonedModel = new File(OntologyFileNamePath).lastModified();
		}

		System.out.println("Reasoned Model Returned");
		
		return reasonedOntModel;
	}
	
	public static String getOntologyURI()
	{
		return OntologyURI;
	}
	
	public static String getOntologyFilePath()
	{
		return OntologyFileNamePath;
	}

}

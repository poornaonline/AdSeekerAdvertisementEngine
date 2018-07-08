package com.research.adseeker;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.research.IO.AdSeekerOntology;
import com.research.IO.AdSeekerUpdatingOntology;
import com.research.ontology.OntologyRelationships;
import com.research.tweets.SingleTweetRelationshipAnalyzer;
import com.research.validation.JSONArrayExtended;
import com.research.validation.TweetMapper;
import com.research.validation.Validation;

@Path("ontology")
public class OntologyResourse {
	
	@POST
	@Path("/getrelationship")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRelationshipInJSON(String itemName) {

		JSONObject returningJsonObject = new JSONObject();
    	
    	OntologyRelationships or = new OntologyRelationships();
    	returningJsonObject = or.listOntologyRelationshipsInJson(itemName.trim(), AdSeekerOntology.getModel(), AdSeekerOntology.getOntologyURI());
    	
		return Response.status(200).entity(returningJsonObject).build();
	}
	
	@POST
	@Path("/getontologydata")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOntologyDataInJSON(String ontClassName) {

		JSONObject initialObject = new JSONObject();
		
		String ontReadyStr = Validation.getSpaceReplacedWithUndescore(ontClassName);
		
		OntClass ontClass = AdSeekerOntology.getReasonedModel().getOntClass(AdSeekerOntology.getOntologyURI() + ontReadyStr);
		OntClass ontClass2 = AdSeekerUpdatingOntology.getReasonedModel().getOntClass(AdSeekerUpdatingOntology.getOntologyURI() + ontReadyStr);
		
		JSONArray subClassJR = new JSONArray();
		JSONArray individualJR = new JSONArray();

		int httpResponseCode = 500;
		
		if(ontClass == null)
		{
			initialObject.put("Error", "Mentioned object doesn't exist in Ontology");
		}else {
			
			Iterator<OntClass> ontClassIterator = ontClass.listSubClasses(true);
			
			while(ontClassIterator.hasNext())
			{
				OntClass subclass = ontClassIterator.next();
				subClassJR.add(subclass.getLocalName());
			}
			
			initialObject.put("subclasses", subClassJR.toJSONString());
			
			ExtendedIterator<? extends OntResource> instanceIterator = ontClass.listInstances(true);
			
			while(instanceIterator.hasNext())
			{
				Individual individual = (Individual) instanceIterator.next();
				individualJR.add(individual.getLocalName());
			}
			
			initialObject.put("individuals", individualJR.toJSONString());
			httpResponseCode = 200;
		}
		
		if(ontClass2 != null)
		{
			Iterator<OntClass> ontClass2Iterator = ontClass2.listSubClasses(true);
			
			while(ontClass2Iterator.hasNext())
			{
				OntClass subclass = ontClass2Iterator.next();
				if (!JSONArrayExtended.checkJSONArrayContained(subclass.getLocalName(), subClassJR))
				{
					subClassJR.add(subclass.getLocalName());
				}
			}
			
			initialObject.put("subclasses", subClassJR.toJSONString());
			
			ExtendedIterator<? extends OntResource> instanceIterator = ontClass2.listInstances(true);
			
			while(instanceIterator.hasNext())
			{
				Individual individual = (Individual) instanceIterator.next();
				
				System.err.println(individual.getLocalName());
				
				if (!JSONArrayExtended.checkJSONArrayContained(individual.getLocalName(), individualJR))
				{
					individualJR.add(individual.getLocalName());
					System.err.println(individual.getLocalName());
				}
			}
			
			initialObject.put("individuals", individualJR.toJSONString());
		}
		
		System.err.println("Returning JSON Object -> " + initialObject.toJSONString());
		
    	
		return Response.status(httpResponseCode).entity(initialObject).build();
	}
	
	@POST
	@Path("/getsuggestionrelationship")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSuggestionRelationships(String productTitle) {

		JSONObject returningSuggestionObject = new JSONObject();
    	
    	ArrayList<ArrayList<String>> titleRelationshipArrayList = new SingleTweetRelationshipAnalyzer().getTweetRelationshipArrayList(productTitle);
    	int relationshipNumber = 0;
    	for (ArrayList<String> relationshipArrayList : titleRelationshipArrayList) {
    		relationshipNumber++;
			JSONArray relationshipJsonArray = new TweetMapper().mapArrayListToJsonArray(relationshipArrayList);
			returningSuggestionObject.put(relationshipNumber, relationshipJsonArray.toJSONString());
		}
    	
		return Response.status(200).entity(returningSuggestionObject).build();
	}
	
	
	@POST
	@Path("/insertsubclass")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response insertSubClassToTheOntology(JSONObject classDetailsJO) {
		
		String superClassName = Validation.pushInToOntologyReady((String) classDetailsJO.get("superClass"));
		String newClassName = Validation.pushInToOntologyReady((String) classDetailsJO.get("newClass"));

		int httpStatusCode = 0;
		String httpStatusMessage = null;
		
		OntModel model = AdSeekerUpdatingOntology.getModel();
		
		OntClass superClass = model.getOntClass(AdSeekerUpdatingOntology.getOntologyURI() + superClassName);
		OntClass subClass = model.createClass(AdSeekerUpdatingOntology.getOntologyURI() + newClassName);
		
		superClass.addSubClass(subClass);
		subClass.addSuperClass(superClass);

		OutputStream out;
		try {
			PrintStream p= new PrintStream(AdSeekerUpdatingOntology.getOntologyFilePath());
			model.write(p, "RDF/XML");//-ABBREV 
			p.close();
			httpStatusMessage = "Successfully added an subclass to the ontology";
			httpStatusCode = 200;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			httpStatusMessage = "Error Occured";
			httpStatusCode = 500;
		}
    	
		return Response.status(httpStatusCode).entity(httpStatusMessage).build();
	}
	
	
	@POST
	@Path("/insertindividual")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response insertIndividualToTheOntology(JSONObject newIndividualDataJO) {
		
		String className = Validation.getSpecialCharRemovedOntoReadyString((String) newIndividualDataJO.get("class"));
		String individualName = Validation.getSpecialCharRemovedOntoReadyString((String) newIndividualDataJO.get("individual"));

		int httpStatusCode = 0;
		String httpStatusMessage = null;
		
		OntModel model = AdSeekerUpdatingOntology.getModel();
		
		OntClass superClass = model.getOntClass(AdSeekerUpdatingOntology.getOntologyURI() + className);
		Individual newIndividual = model.createIndividual(AdSeekerUpdatingOntology.getOntologyURI() + individualName, superClass);
	
		OutputStream out;
		try {
			PrintStream p= new PrintStream(AdSeekerUpdatingOntology.getOntologyFilePath());
			model.write(p, "RDF/XML");//-ABBREV 
			p.close();
			httpStatusMessage = "Successfully added an individual to the ontology";
			httpStatusCode = 200;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			httpStatusMessage = "Error Occured while adding to the ontology";
			httpStatusCode = 500;
		}
    	
		return Response.status(httpStatusCode).entity(httpStatusMessage).build();
	}

}

package com.research.ontology;

import java.io.File;
import java.io.FileReader;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class OntologyTest {
	
	
String filename = "/Users/Poorna/Documents/Ontology/adseeker.owl";
    
OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
    
    
    public void Readfile()
    {
        System.out.println("Read File Method Executing");
        
        try{
               File file =new File(filename);
               FileReader reader = new FileReader(file);
               model.read(reader,null);
                                  
        }catch(Exception e){
             
        	e.printStackTrace();
        }
    }
    
    public void ExecuteQuery()
    {
        try{
             String query = 
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
        "PREFIX owl: <http://www.w3.org/2002/07/owl#>"+
        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + 
        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
        "SELECT ?subject ?object\n" +
        "WHERE { ?subject rdfs:subClassOf ?object }";
       
       
         Query q1 = QueryFactory.create(query);
         QueryExecution qe = QueryExecutionFactory.create(q1,model);
         ResultSet r1 = qe.execSelect();
         ResultSetFormatter.out(System.out, r1, q1);
         
         
         DatatypeProperty loan = model.getDatatypeProperty("http://www.semanticweb.org/poorna/ontologies/2016/4/untitled-ontology-2"); 
         
        ExtendedIterator individuals = model.listIndividuals();
        
        while(individuals.hasNext())
        {
            Individual ind = (Individual) individuals.next();
            if(ind.hasProperty(loan))
            {
                String indName = ind.getLocalName().toString();
                System.out.println(indName);
            }     
            
              StmtIterator iter = ind.listProperties(loan);
                      while ( iter.hasNext() ) {
                    	  System.out.println( iter.nextStatement() );
                      }
        }       
         
        }catch(Exception e){
           e.printStackTrace();
       }
        
    }
    
    
    public void getDataFromOntology()
    {
    	Readfile();
    	ExecuteQuery();
    }
    
    
}

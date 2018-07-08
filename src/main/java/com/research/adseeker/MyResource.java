package com.research.adseeker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.research.databaseSeed.Seed;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("resource")
public class MyResource {
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	
    	String tweet = "This is awesome";
    	new Seed().seedProducts();

    	return tweet;
    	
    }
    
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt2() {
    	
    	JSONObject testObj = new JSONObject();
    	JSONArray testArr = new JSONArray();
    	testArr.add("Poorna");
    	testArr.add("Jayasinghe");
    	testObj.put("name", testArr);

    	return Response.status(200).entity(testObj).build();
    	
    }

}

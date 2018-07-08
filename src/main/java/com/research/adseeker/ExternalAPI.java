package com.research.adseeker;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.research.api.HTTPRestAPI;

@Path("externalapi")
public class ExternalAPI {
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testExternalApi()
	{
		return "Adseeker ExternalAPI test get method";
	}
	
	
	@POST
	@Path("/getsentiment")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getTweetSentiment(String analysingTweet) {

    	HTTPRestAPI adseekerExternalAPI = new HTTPRestAPI();
    	JSONObject responseObject = null;
    	
		try {
			responseObject = adseekerExternalAPI.sentimentPostRequest(analysingTweet);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(responseObject).build();
	}
	
	
	@POST
   	@Path("/uclassifyanalysis")
   	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
   	public Response getTweetTopicAnalysisFromUClassify(String analysingTweet) {

       	HTTPRestAPI adseekerExternalAPI = new HTTPRestAPI();
       	JSONObject responseObject = null;
       	
       	try {
       		responseObject = adseekerExternalAPI.uClassifyTopicRequest(analysingTweet);
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
       	
   		return Response.status(200).entity(responseObject).build();
   	}

}

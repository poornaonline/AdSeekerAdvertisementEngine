package com.research.adseeker;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.research.databaseAccess.AdminDAO;
import com.research.databaseAccess.UnpublishedAdvertisementDAO;
import com.research.databaseObjects.UnpublishedAdvertisement;
import com.research.ontology.OntologyHelper;
import com.research.validation.TweetMapper;

@Path("unpublishedad")
public class UnpublishedAdResource {
	
	@POST
	@Path("/pushunpublishad")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
	public Response getHomeFeedAdsNewUserOrExistingUser(JSONObject unresolvedAdData) {
		
		int httpHeader = 200;
		String headerMessage = "Successfully ad pushed to the database for review";
		
		try {
			new UnpublishedAdvertisementDAO().PushNewUnpublishedAdvertisementToTheDatabase(unresolvedAdData);
			
		}catch(Exception e)
		{
			httpHeader = 500;
			headerMessage = "Problem occured while saving the advertisement";
		}
		
		
    	return Response.status(200).entity(headerMessage).build();
	}
	
	@POST
	@Path("/getallunpublishedads")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getAllUnpublishedAds(@Context HttpHeaders header) {
		
		String username = header.getRequestHeader("username").get(0).toString();
		String password = header.getRequestHeader("password").get(0).toString();
		boolean authorized = new AdminDAO().adminCredentialCorrect(username, password);
		JSONObject allUnresolvedAds = new JSONObject();

		if (authorized) {
			ArrayList<UnpublishedAdvertisement> unpublishedAdsAL = new UnpublishedAdvertisementDAO().getAllUnresolvedAdvertisements();
			
			int adNumber = 0;
			for (UnpublishedAdvertisement unpublishedAdvertisement : unpublishedAdsAL) {
				adNumber++;
				JSONObject unresolvedAd = new JSONObject();
			
				unresolvedAd.put("ProductId", unpublishedAdvertisement.getProductId());
				unresolvedAd.put("ProductTitle", unpublishedAdvertisement.getProductTitle());
				unresolvedAd.put("ProductDescription", unpublishedAdvertisement.getProductDescription());
				unresolvedAd.put("ProductImageURL", unpublishedAdvertisement.getProductImageURL());
				unresolvedAd.put("ProductPrice", unpublishedAdvertisement.getProductPrice());
				unresolvedAd.put("BelongingUsername", unpublishedAdvertisement.getBelongingUsername());
				unresolvedAd.put("UserComment", unpublishedAdvertisement.getUserComment());
			
				allUnresolvedAds.put(adNumber, unresolvedAd);
			}
		}else {
			
			JSONObject errorObject = new JSONObject();
			errorObject.put("Error", "Authorization Failed");
			return Response.status(401).entity(errorObject).build();
		}
		
    	return Response.status(200).entity(allUnresolvedAds).build();
	}
	
	
	@POST
	@Path("/moveunpublishadtouserad")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response moveUnpublisedAdToDb(@Context HttpHeaders header, JSONObject unpublishedAdDetails) {
		
		String username = header.getRequestHeader("username").get(0).toString();
		String password = header.getRequestHeader("password").get(0).toString();
		boolean authorized = new AdminDAO().adminCredentialCorrect(username, password);
		JSONObject allUnresolvedAds = new JSONObject();

		if (authorized) {
			
			String unpublishedAdProductId = (String) unpublishedAdDetails.get("productId");
			ArrayList<String> chosenRelationshipAL = (ArrayList<String>) unpublishedAdDetails.get("relationship");
			JSONArray chosenRelationshipJA = new TweetMapper().mapArrayListToJsonArray(chosenRelationshipAL);
			String individualName = new OntologyHelper().getTheIndividualFromTheRelationshipArray(chosenRelationshipJA);
			
			new UnpublishedAdvertisementDAO().moveUnpublishedAdToTheUserAdvertisementDb(unpublishedAdProductId, individualName);
			
		}else {
			
			JSONObject errorObject = new JSONObject();
			errorObject.put("Error", "Authorization Failed");
			return Response.status(401).entity(errorObject).build();
		}
		
    	return Response.status(200).entity(allUnresolvedAds).build();
	}
}

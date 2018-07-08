package com.research.adseeker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.research.IO.AdSeekerOntology;
import com.research.api.SentimentAnalyserServerAPI;
import com.research.databaseAccess.AdConverter;
import com.research.databaseAccess.AdvertisementDAO;
import com.research.databaseAccess.UserDAO;
import com.research.databaseObjects.Advertisement;
import com.research.databaseObjects.User;
import com.research.ontology.OntologyRelationships;
import com.research.validation.TweetMapper;
import com.research.validation.Validation;
import com.research.wrappers.DirectTweetAnalyzerWrapper;
import com.research.wrappers.UserAdditionalDataSetWrapper;

@Path("user")
public class UserRelatedAds {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testUserRelatedAdsAPI()
	{
		return "Adseeker User Related Ads test get method";
	}
	
	
	@POST
	@Path("/homefeed")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getHomeFeedAdsNewUserOrExistingUser(@Context HttpHeaders header,JSONObject tweetsAndData) {

		String username = header.getRequestHeader("username").get(0).toString();
    	int numberOfAds = (int) tweetsAndData.get("adAmount");
    	
    	JSONArray allTweetsOfUser = new TweetMapper().mapArrayListToJsonArray((ArrayList<String>)tweetsAndData.get("tweets"));
    	System.out.println("All Tweets : " + allTweetsOfUser.toJSONString());
    	JSONArray allPostiveTweets = new SentimentAnalyserServerAPI().getPositiveTweets(allTweetsOfUser);
    	System.err.println("Tweets after sentiment analysis : " + allPostiveTweets.toJSONString());
    	
    	String AllTweetString = new TweetMapper().mapJsonArrayToString(allPostiveTweets);
    	JSONObject advertisementObj = DirectTweetAnalyzerWrapper.optimisedGetAdvertisementsFromTheDb(username, AllTweetString, numberOfAds);
    	JSONObject recommendedAds = (JSONObject) advertisementObj.get("recommendedAdvertisements");
    	int httpResponseId = (int) advertisementObj.get("httpResponseId");

    	return Response.status(httpResponseId).entity(recommendedAds).build();
	}
	
	
	@POST
	@Path("/eqads")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getEquivalentProductsFromDbForAUser(@Context HttpHeaders header) {

		String username = header.getRequestHeader("username").get(0).toString();
		int adCount = Integer.parseInt(header.getRequestHeader("adcount").get(0).toString());
		JSONObject returingAdObj = DirectTweetAnalyzerWrapper.optimisedGetEquivalentProductsFromTweets(username, adCount);    	
    	int httpResponseId = (int) returingAdObj.get("httpResponse");
    	JSONObject adObj = (JSONObject) returingAdObj.get("adObject");

    	return Response.status(httpResponseId).entity(adObj).build();
	}
	
	
    @POST
   	@Path("/savead")
   	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
   	public Response saveNewAdvertisementToTheDatabase(@Context HttpHeaders header, JSONObject advertisementObject) {
    	
    	AdvertisementDAO dao = new AdvertisementDAO();
    	String username = header.getRequestHeader("username").get(0).toString();
    	
    	ArrayList<String> relationshipArrayList = Validation.ontologyReadyRelationshipHierarchy((ArrayList<String>) advertisementObject.get("relationship"));
    	
    	JSONArray relationshipJSONArray = new TweetMapper().mapArrayListToJsonArray(relationshipArrayList);
    	advertisementObject.put("relationship", relationshipJSONArray);
    	
    	User existingUser = new UserDAO().GetUser(username);
    	
    	String HTTPResponseMessage = null;
    	int HTTPResopnseID = 500;
    	
    	try {
			dao.PushNewAdvertisementToTheDatabase(advertisementObject,existingUser);
			HTTPResopnseID = 200;
			HTTPResponseMessage = "Advertisement Successfully Saved";
		} catch (Exception e) {
			HTTPResponseMessage = "Error occured while saving the advertisement";
			e.printStackTrace();
		}
    	
   		return Response.status(HTTPResopnseID).entity(HTTPResponseMessage).build();
   	}
    
    @POST
   	@Path("/search")
   	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
   	public Response getAdsFromTheDatabase(@Context HttpHeaders header, String searchQuery) {
    	
    	AdvertisementDAO dao = new AdvertisementDAO();
    	Object usernameHeader = header.getRequestHeader("username");
    	String username = null;
    	User existingUser = null;
    	
    	if(usernameHeader != null)
    	{
    		username = header.getRequestHeader("username").get(0);
    		if(!username.equals(""))
    		{
    			existingUser = new UserDAO().GetUser(username);
    		}
    	}
    	
    	int adCount = Integer.parseInt(header.getRequestHeader("adcount").get(0).toString());
   	
    	int HTTPResopnseID = 500;
    	JSONObject receivedAds = null;
    	
    	try {
			
    		ArrayList<Advertisement> adsAL = new AdvertisementDAO().SearchAdsByTitle(username, searchQuery, adCount);
    		if(adsAL.size() == 0)
    		{
    			adsAL = new AdvertisementDAO().SearchAdsByDescription(username, searchQuery, adCount);
    		}
    		
    		receivedAds = AdConverter.adArrayListToJSONObject(adsAL);
    		
    		if(existingUser != null) {
    			new UserDAO().addUserSearchedKeyWord(username, searchQuery);
    		}
    		
			HTTPResopnseID = 200;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
   		return Response.status(HTTPResopnseID).entity(receivedAds).build();
   	}
    
    @POST
   	@Path("/deletead")
   	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
   	public Response deleteAdFromTheDatabase(@Context HttpHeaders header, String deletingAdProductId) {
    	
    	String username = header.getRequestHeader("username").get(0).toString();
    	
    	int HTTPResopnseID = 500;
    	String responseMessage = null;
    	
    	try {
			
    		new AdvertisementDAO().deleteAdvertisementById(username, deletingAdProductId);
    		HTTPResopnseID = 200;
    		responseMessage = "Advertisement Deleted";
			
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage = "Error occured";
		}
    	
   		return Response.status(HTTPResopnseID).entity(responseMessage).build();
   	}
    
    @POST
   	@Path("/getsimilarproducts")
   	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
   	public Response getSimilarProductsFromTheDatabase(@Context HttpHeaders header, String productTitle) {
    	
    	int adCount = Integer.parseInt(header.getRequestHeader("adCount").get(0));
    	JSONObject similarProductObject = DirectTweetAnalyzerWrapper.optimisedGetEqProductForSingleProductTitle(productTitle, adCount);
    	
    	int httpResponseCode = (int) similarProductObject.get("httpResponse");
    	JSONObject returningJO = new JSONObject();
    	returningJO.put("title", productTitle);
    	returningJO.put("ads", (JSONObject) similarProductObject.get("adObject"));
    	
   		return Response.status(httpResponseCode).entity(returningJO).build();
   	}
    
    
    @POST
   	@Path("/getuserinsertedads")
    @Produces(MediaType.APPLICATION_JSON)
   	public Response getUserInsertedAds(@Context HttpHeaders header) {
    	
    	String username = header.getRequestHeader("username").get(0).toString();
    	Collection<Advertisement> ads = new AdvertisementDAO().getUserEnteredAdvertisementList(username);
    	ArrayList<Advertisement> adsAL = AdConverter.collectionOfAdsToArrayList(ads);
    	JSONObject userAds = AdConverter.adArrayListToJSONObject(adsAL);
    	
   		return Response.status(200).entity(userAds).build();
   	}
    
    @POST
   	@Path("/getusersearchedkeywordsads")
    @Produces(MediaType.APPLICATION_JSON)
   	public Response getUserSearchedKeywordsRelatedAds(@Context HttpHeaders header) {
    	
    	String username = header.getRequestHeader("username").get(0).toString();
    	int adCount = Integer.parseInt(header.getRequestHeader("adCount").get(0));
    	ArrayList<Advertisement> suggestedAds = new UserAdditionalDataSetWrapper().getAdvertisementsAccordingToTheUserKeyWords(username, adCount);
    	JSONObject suggestedAdsInJSON = new UserAdditionalDataSetWrapper().advertisementJSONmapper(suggestedAds);
   		return Response.status(200).entity(suggestedAdsInJSON).build();
   	}

}

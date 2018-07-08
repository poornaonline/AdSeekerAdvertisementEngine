package com.research.adseeker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.research.databaseAccess.AdminDAO;
import com.research.databaseAccess.UnpublishedAdvertisementDAO;
import com.research.databaseAccess.UserDAO;
import com.research.databaseObjects.User;
import com.research.ontology.OntologyHelper;
import com.research.validation.TweetMapper;
import com.research.wrappers.UserStats;

@Path("adminapi")
public class AdminResource {
	
	@POST
	@Path("/admincredentials")
    @Produces(MediaType.TEXT_PLAIN)
	public Response moveUnpublisedAdToDb(@Context HttpHeaders header) {
		
		String username = header.getRequestHeader("username").get(0).toString();
		String password = header.getRequestHeader("password").get(0).toString();
		boolean authorized = new AdminDAO().adminCredentialCorrect(username, password);
		String responseMessage = null;

		if (authorized) {
			responseMessage = "Authorized";
			return Response.status(200).entity(responseMessage).build();
			
		}else {
			
			responseMessage = "Unauthorized";
			return Response.status(401).entity(responseMessage).build();
		}
		
	}
	
	
	
	@GET
	@Path("/allusernames")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsernames() {
		
		ArrayList<User> allUsers = new UserDAO().getAllUsers();
		JSONArray usernames = new JSONArray();
		
		for (User user : allUsers) {
			usernames.add(user.getUserName());
		}
		
		return Response.status(200).entity(usernames).build();
		
	}
	
	@POST
	@Path("/getuserfavbrands")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getFavouriteBrands(String username) {
		
		User user = new UserDAO().GetUser(username.trim());
		JSONObject pastUserProfiles = null;
		try {
			pastUserProfiles = (JSONObject) new JSONParser().parse(user.getClearedCharacterProfiles());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		Iterator<String> timeIterator = pastUserProfiles.keySet().iterator();
		JSONArray userFavBrandList = new JSONArray();
		
		while(timeIterator.hasNext())
		{
			String time = timeIterator.next();
			JSONObject timeObj = (JSONObject) pastUserProfiles.get(time);
			if (timeObj.containsKey("brand"))
			{
				JSONObject brandList = (JSONObject) timeObj.get("brand");
				
				Iterator<String> brandIterator = brandList.keySet().iterator();
				
				while(brandIterator.hasNext())
				{
					String brandName = brandIterator.next();
					
					if (!userFavBrandList.contains(brandName))
					{
						userFavBrandList.add(brandName);
					}
		
				}
			}
		}
		
		return Response.status(200).entity(userFavBrandList).build();
	}
	
	
	@POST
	@Path("/getproductdatesandmentions")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getFavouriteBrands(JSONObject userData) {
		
		String username = (String) userData.get("username");
		String productBrand = (String) userData.get("brand");
		
		JSONArray timeStamps = new JSONArray();
		JSONArray howManyMentions = new JSONArray();
		
		User user = new UserDAO().GetUser(username.trim());
		JSONObject pastUserProfiles = null;
		try {
			pastUserProfiles = (JSONObject) new JSONParser().parse(user.getClearedCharacterProfiles());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		Iterator<String> timeIterator = pastUserProfiles.keySet().iterator();
		JSONArray userFavBrandList = new JSONArray();
		
		while(timeIterator.hasNext())
		{
			String time = timeIterator.next();
			JSONObject timeObj = (JSONObject) pastUserProfiles.get(time);
			if (timeObj.containsKey("brand"))
			{
				JSONObject brandList = (JSONObject) timeObj.get("brand");
				
				Iterator<String> brandIterator = brandList.keySet().iterator();
				
				while(brandIterator.hasNext())
				{
					String brandName = brandIterator.next();
					
					if (brandName.equals(productBrand))
					{
						Long precentage = (Long) brandList.get(productBrand);
						String convertedTime = new UserStats().getDateByTimeStamp(Long.parseLong(time));
						timeStamps.add(convertedTime);
						howManyMentions.add(precentage);
						
					}
				}
			}
		}
		
		JSONArray returningArray = new JSONArray();
		returningArray.add(timeStamps);
		returningArray.add(howManyMentions);
		
		return Response.status(200).entity(returningArray).build();
	}

}

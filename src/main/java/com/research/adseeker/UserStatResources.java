package com.research.adseeker;

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

import com.research.api.SentimentAnalyserServerAPI;
import com.research.datawarehouse.dataWareHouse;
import com.research.jsonmappers.frontEndSupportMapper;
import com.research.wrappers.UserStats;

@Path("userstats")
public class UserStatResources {
	
	@POST
	@Path("/countrystats")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getCountryStats(String countryName) {
		
		String country = countryName.trim();
		
		if(country.equals("") || country == null)
		{
			country = "Sri Lanka";
		}

    	JSONObject cuntryPreferencesDWJO = new SentimentAnalyserServerAPI().getCountryPreferencesViaDataWarehouse(country);
    	JSONObject totalPrecentageConvertedObj = new dataWareHouse().getTotalPrecentageConvertedJO(cuntryPreferencesDWJO);

		return Response.status(200).entity(totalPrecentageConvertedObj).build();
	}
	
	
	@POST
	@Path("/userstats")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getUserStats(String username) {
		
		JSONObject userStats = new UserStats().getUserStats(username);
		
		JSONArray dates = new JSONArray();
		Iterator<String> datesIterator = userStats.keySet().iterator();
		while(datesIterator.hasNext())
		{
			dates.add(datesIterator.next());
		}
		
		JSONObject returningJO = new JSONObject();
		returningJO.put("dates", dates);
		returningJO.put("stats", userStats);

		return Response.status(200).entity(returningJO).build();
	}
	
	
	@POST
	@Path("/severalcountrystats")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getSeveralCountryStats(JSONObject countryList) {
		
		ArrayList<String> countryAL = (ArrayList<String>) countryList.get("countries");
		
		JSONObject finalCountriesStat = new JSONObject();
		
		for (String country : countryAL) {
			JSONObject cuntryPreferencesDWJO = new SentimentAnalyserServerAPI().getCountryPreferencesViaDataWarehouse(country);
	    	JSONObject totalPrecentageConvertedObj = new dataWareHouse().getTotalPrecentageConvertedJO(cuntryPreferencesDWJO);
	    	finalCountriesStat.put(country, totalPrecentageConvertedObj);
		}
		
		JSONArray formattedStats = new frontEndSupportMapper().multipleCountryWarehouseDataMapper(finalCountriesStat);

		return Response.status(200).entity(formattedStats).build();
	}

}

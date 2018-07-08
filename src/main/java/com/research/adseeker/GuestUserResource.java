package com.research.adseeker;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.research.api.SentimentAnalyserServerAPI;
import com.research.databaseAccess.DatawarehouseDAO;
import com.research.databaseObjects.Advertisement;
import com.research.datawarehouse.dataWareHouse;

@Path("guestuser")
public class GuestUserResource {
	
	@POST
	@Path("/countrybasedads")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
	public Response testapi(String countryName) {
		
		String country = countryName.trim();
		
		if(country.equals("") || country == null)
		{
			country = "Sri Lanka";
		}

    	JSONObject cuntryPreferencesDWJO = new SentimentAnalyserServerAPI().getCountryPreferencesViaDataWarehouse(country);
    	JSONObject totalPrecentageConvertedObj = new dataWareHouse().getTotalPrecentageConvertedJO(cuntryPreferencesDWJO);
    	JSONObject adAmount = new dataWareHouse().getTotalAdsShouldFetch(totalPrecentageConvertedObj, 20);
    	ArrayList<Advertisement> ads = new DatawarehouseDAO().getAdvertisementsForTheGuestUser(adAmount);
    	JSONObject adsJO = new dataWareHouse().advertisementJSONmapper(ads);

		return Response.status(200).entity(adsJO).build();
	}

}

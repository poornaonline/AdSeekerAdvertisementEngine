package com.research.databaseAccess;

import java.util.ArrayList;
import java.util.Collection;

import org.json.simple.JSONObject;

import com.research.databaseObjects.Advertisement;

public class AdConverter {
	
	public static JSONObject adArrayListToJSONObject(ArrayList<Advertisement> advertisementAL)
	{
		JSONObject convertedAdsObj = new JSONObject();
		
		for (int i = 0; i < advertisementAL.size(); i++)
		{
			Advertisement advertisement = advertisementAL.get(i);
			JSONObject currentAd = new JSONObject();
			currentAd.put("ProductTitle", advertisement.getProductTitle());
			currentAd.put("ProductDescription", advertisement.getProductDescription());
			currentAd.put("ProductPrice", advertisement.getProductPrice());
			currentAd.put("ProductId", advertisement.getProductId());
			currentAd.put("ProductImageURL", advertisement.getProductImageURL());
			convertedAdsObj.put(i, currentAd);
		}

		return convertedAdsObj;
	}
	
	public static ArrayList<Advertisement> collectionOfAdsToArrayList(Collection<Advertisement> adCollection)
	{
		ArrayList<Advertisement> ads = new ArrayList<Advertisement>();
		
		for (Advertisement internalAd : adCollection) {
			ads.add(internalAd);
		}
		
		return ads;
	}

}

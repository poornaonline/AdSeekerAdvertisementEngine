package com.research.datawarehouse;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.research.databaseObjects.Advertisement;

public class dataWareHouse {
	
	public JSONObject getTotalPrecentageConvertedJO(JSONObject warehouseData)
	{
		JSONObject precentageConvertedJO = new JSONObject();
		Double bikeAmount = Double.parseDouble((String)warehouseData.get("bike"));
		Double carAmount = Double.parseDouble((String)warehouseData.get("car"));
		Double mobileAmount = Double.parseDouble((String)warehouseData.get("mobile"));
		Double computerAmount = Double.parseDouble((String)warehouseData.get("computer"));
		Double total = bikeAmount + carAmount + mobileAmount + computerAmount;
		Double bikePrecentage = (bikeAmount / total) * 100.00;
		Double carPrecentage = (carAmount / total) * 100.00;
		Double mobilePrecentage = (mobileAmount / total) * 100.00;
		Double computerPrecentage = (computerAmount / total) * 100.00;
		precentageConvertedJO.put("bike", bikePrecentage);
		precentageConvertedJO.put("car", carPrecentage);
		precentageConvertedJO.put("mobile", mobilePrecentage);
		precentageConvertedJO.put("computer", computerPrecentage);
		System.err.println(precentageConvertedJO.toJSONString());
		return precentageConvertedJO;
	}
	
	public JSONObject getTotalAdsShouldFetch(JSONObject precentageConvertedJO, int AdAmount)
	{
		JSONObject adCountJO = new JSONObject();
		
		Integer bikeAdAmount = ((Double)(((Double)precentageConvertedJO.get("bike") / 100 ) * AdAmount)).intValue();
		Integer carAdAmount = ((Double)(((Double)precentageConvertedJO.get("car") / 100 ) * AdAmount)).intValue();
		Integer mobileAdAmount = ((Double)(((Double)precentageConvertedJO.get("mobile") / 100 ) * AdAmount)).intValue();
		Integer computerAdAmount = ((Double)(((Double)precentageConvertedJO.get("computer") / 100 ) * AdAmount)).intValue();
		
		adCountJO.put("bike", bikeAdAmount);
		adCountJO.put("car", bikeAdAmount);
		adCountJO.put("mobile", bikeAdAmount);
		adCountJO.put("computer", bikeAdAmount);
		
		return adCountJO;
	}
	
	
	public JSONObject advertisementJSONmapper(ArrayList<Advertisement> advertisements)
	{
    	JSONObject AllProductsJSON = new JSONObject();
  
    	for (int i = 0; i < advertisements.size(); i++) {
    		
    		JSONObject productJSON = new JSONObject();
    		Advertisement currentAd = advertisements.get(i);
			
    		productJSON.put("ProductId", currentAd.getProductId());
    		productJSON.put("ProductTitle",currentAd.getProductTitle());
    		productJSON.put("ProductDescription", currentAd.getProductDescription());
    		productJSON.put("ProductImageURL", currentAd.getProductImageURL());
    		productJSON.put("ProductPrice", currentAd.getProductPrice());
    		
    		AllProductsJSON.put(i, productJSON);
		}

		return AllProductsJSON;
	}
	 

}

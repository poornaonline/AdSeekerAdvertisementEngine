package com.research.wrappers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.research.databaseAccess.UserDAO;
import com.research.databaseObjects.User;

public class UserStats {
	
	public JSONObject getUserStats(String userName)
	{
		User user = new UserDAO().GetUser(userName.trim());
		JSONObject userPastCharacterProfile = new JSONObject();
		
		try {
			userPastCharacterProfile = (JSONObject) new JSONParser().parse(user.getClearedCharacterProfiles());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JSONObject userStats = getTopCategoryAndBrandStats(userPastCharacterProfile);
		
		return userStats;
		
	}
	
	public String getDateByTimeStamp(long timeStamp)
	{
		Date date = new Date(timeStamp);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);
        System.out.println(formatted);
        
        return formatted;
	}
	
	public JSONObject getTopCategoryAndBrandStats(JSONObject userProfile)
	{
		Iterator<String> timeStampIterator = userProfile.keySet().iterator();
		ArrayList<String> timeStampAL = new ArrayList<String>();
		
		JSONObject finalReturningJO = new JSONObject();
		
		while(timeStampIterator.hasNext())
		{
			timeStampAL.add(timeStampIterator.next());
		}
		
		for (String timeStamp : timeStampAL) {
			
			JSONObject internalMentions = (JSONObject) userProfile.get(timeStamp);
			JSONObject brandJO = (JSONObject) internalMentions.get("brand");
			JSONObject topCategoryJO = (JSONObject) internalMentions.get("topcategory");
			String dateStr = getDateByTimeStamp(Long.parseLong(timeStamp));
			
			JSONObject singleProduct = new JSONObject();
			if (brandJO != null) singleProduct.put("brand", precentageMapper(brandJO));
			if (topCategoryJO != null) singleProduct.put("topcategory", precentageMapper(topCategoryJO));
			
			finalReturningJO.put(dateStr, singleProduct);
			
		}
		
		
		return finalReturningJO;
	}
	
	public JSONObject precentageMapper(JSONObject category)
	{
		Iterator<String> categoryIterator = category.keySet().iterator();
		ArrayList<String> categoryAL = new ArrayList<String>();
		int totalCount = 0;
		
		while(categoryIterator.hasNext())
		{
			String key = categoryIterator.next();
			int count = ((Long) category.get(key)).intValue();
			totalCount = totalCount + count;
			categoryAL.add(key);
		}
		
		JSONObject returningObj = new JSONObject();
		
		for (String str : categoryAL) {
			double precentage = Math.round((((Long)category.get(str)).intValue() / (totalCount/1.0)) * 100);
			returningObj.put(str, precentage);
		}
		
		return returningObj;
	}

}

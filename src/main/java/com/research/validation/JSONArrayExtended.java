package com.research.validation;

import org.json.simple.JSONArray;

public class JSONArrayExtended {
	
	public static boolean checkJSONArrayContained(String value, JSONArray checkingArray)
	{
		boolean isContained = false;
		
		for (int i = 0; i < checkingArray.size(); i++)
		{
			String arrayValue = (String) checkingArray.get(i);
			if (arrayValue.equals(value))
			{
				isContained = true;
			}
		}
		
		return isContained;
	}

}

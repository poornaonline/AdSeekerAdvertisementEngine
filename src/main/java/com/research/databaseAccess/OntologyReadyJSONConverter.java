package com.research.databaseAccess;

import java.util.Iterator;

import org.json.simple.JSONObject;

public class OntologyReadyJSONConverter {
	
		// Converting db receving user profile object's longs to ints
		public static JSONObject mapDBFetchingJSONtoAlgorithmReadyJSONObj(JSONObject databaseRetrivedJO)
		{
			Iterator<String> objectFinalKeys = databaseRetrivedJO.keySet().iterator();
			
			while(objectFinalKeys.hasNext())
			{
				String finalKey = objectFinalKeys.next();
				JSONObject internalObj = (JSONObject)databaseRetrivedJO.get(finalKey);
				
				Iterator<String> internalObjKeys = internalObj.keySet().iterator();
				
				while(internalObjKeys.hasNext())
				{
					String internalItem = internalObjKeys.next();
					int value = ((Long)internalObj.get(internalItem)).intValue();
					internalObj.put(internalItem, value);
				}
				
				databaseRetrivedJO.put(finalKey, internalObj);
			}
			
			return databaseRetrivedJO;
		}
}

package com.research.api;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.parser.JSONParser;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;



public class HTTPRestAPI {
	
public JSONObject sentimentPostRequest(String dataString) throws Exception {
		
		String USER_AGENT = "Mozilla/5.0";
		String url = "http://text-processing.com/api/sentiment/";

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

		post.setHeader("User-Agent", USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("text", dataString));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		System.out.println("Response Status : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		JSONObject httpResponseJsonObject = (JSONObject)JSONValue.parse(result.toString());
		
		return httpResponseJsonObject;
		

	}


public JSONObject uClassifyTopicRequest(String dataString) throws Exception {
	
	String urlSendingDataString = dataString.replaceAll(" ", "+");
	
	String USER_AGENT = "Mozilla/5.0";
	String url = "https://uclassify.com/browse/uclassify/topics/ClassifyText?readkey=oupWK2iiWhtF&output=json&version=1.01&text=" + urlSendingDataString;

	HttpClient client = new DefaultHttpClient();
	HttpPost post = new HttpPost(url);

	post.setHeader("User-Agent", USER_AGENT);
	HttpResponse response = client.execute(post);

	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	StringBuffer result = new StringBuffer();
	String line = "";
	
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}
	
	JSONObject httpResponseJsonObject = (JSONObject)JSONValue.parse(result.toString());
	
	return (JSONObject) httpResponseJsonObject.get("cls1");
	
}


}

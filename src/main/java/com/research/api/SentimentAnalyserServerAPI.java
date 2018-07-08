package com.research.api;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.research.validation.Validation;

public class SentimentAnalyserServerAPI {
	
	public JSONArray getPositiveTweets(JSONArray AnalysingTweetsArray)
	{
		JSONArray tweetsArray = Validation.sentimentAnalyserReadyTweetArray(AnalysingTweetsArray);
		JSONObject tweets = new JSONObject();
		tweets.put("tweets", tweetsArray);
		JSONArray positveTweets = new JSONArray();
		
		try {
		
			URL url = new URL("http://127.0.0.1:5000/getpostivetweets");
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestMethod("POST");
			httpCon.connect();
			OutputStream os = httpCon.getOutputStream();
			os.write(tweets.toString().getBytes("UTF-8"));
			System.out.println(httpCon.getResponseCode());
			System.out.println(httpCon.getResponseMessage());
			os.flush();
			os.close();
		
			InputStream in = new BufferedInputStream(httpCon.getInputStream());
			String result = IOUtils.toString(in, "UTF-8");
			positveTweets = (JSONArray) new JSONParser().parse(result);
			in.close();
			httpCon.disconnect();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return positveTweets;

	}
	
	
	public String getSentimentOfATweet(String analysingTweet)
	{
		String tweet = Validation.trimAndtoLowerCase(analysingTweet);
		String sentimentOfTheTweet = null;
		
		JSONObject sendingJO = new JSONObject();
		sendingJO.put("tweet", tweet);
		
		try {
		
			URL url = new URL("http://127.0.0.1:5000/sentimentoftweet");
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Accept", "text/plain");
			httpCon.setRequestMethod("POST");
			httpCon.connect();
			OutputStream os = httpCon.getOutputStream();
			os.write(sendingJO.toString().getBytes("UTF-8"));
			System.out.println(httpCon.getResponseCode());
			System.out.println(httpCon.getResponseMessage());
			os.flush();
			os.close();
		
			InputStream in = new BufferedInputStream(httpCon.getInputStream());
			sentimentOfTheTweet = IOUtils.toString(in, "UTF-8");
			in.close();
			httpCon.disconnect();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return sentimentOfTheTweet;

	}
	
	public JSONObject getCountryPreferencesViaDataWarehouse(String countryName)
	{
		String country = countryName.trim();
		JSONObject countryPreferencesJO = new JSONObject();
		
		JSONObject sendingJO = new JSONObject();
		sendingJO.put("country", country);
		
		try {
		
			URL url = new URL("http://127.0.0.1:5000/datawarehouseforcountry");
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Accept", "text/plain");
			httpCon.setRequestMethod("POST");
			httpCon.connect();
			OutputStream os = httpCon.getOutputStream();
			os.write(sendingJO.toString().getBytes("UTF-8"));
			System.out.println(httpCon.getResponseCode());
			System.out.println(httpCon.getResponseMessage());
			os.flush();
			os.close();
		
			InputStream in = new BufferedInputStream(httpCon.getInputStream());
			String result = IOUtils.toString(in, "UTF-8");
			countryPreferencesJO = (JSONObject) new JSONParser().parse(result);
			in.close();
			httpCon.disconnect();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return countryPreferencesJO;

	}

}

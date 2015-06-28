/**
	ItemInfoFinder class is designed to retrieve product information from an international database,
	given a barcode to search for.
	
	TODO Manage error handling - Outpan provides error codes and their messages in the JSON if one is produced.
*/

package com.example.thatshoppingapp.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

import android.os.StrictMode;

public class ItemInfoFinder
{
	private String jsonValue = "";
	
	//Establishes the link to the outpan API 
	private void getData(String code) 
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		//Check for connection/connection that times out here.
		
		
		URL url;
		try {
			url = new URL("http://www.outpan.com/api/get_product.php?barcode=" + code);
			HttpURLConnection con;
			con = (HttpURLConnection)url.openConnection();
			con.setConnectTimeout(5000);
			readStream(con.getInputStream());
		} catch (MalformedURLException e) {
			// This one will not happen, but it's required by HttpURLConnection.
			e.printStackTrace();
		} catch (IOException e) {
			//Includes SocketTimeoutException
			//TODO - Improve how this is handled - Currently fakes a JSON message with a generic error message.
			jsonValue = "{ \"error\": { \"code\": -1, \"message\": \"Unable to establish connection\" } }";
		}
	}
	
	//Obtains the JSON string for a given product obtained from the API
	private void readStream(InputStream in)
	{
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = reader.readLine()) != null)
			{
				jsonValue += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e)	{
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getName(String code)
	{
		getData(code);
		JSONObject obj;
		try {
			obj = new JSONObject(jsonValue);
			return obj.getString("name");
		} catch (JSONException e) {
			try {
				obj = new JSONObject(jsonValue);
			
			
				//If there's a way to remove the Name: portion on the display, it should be done to improve layout.
				/*
				 * Two main error messages will be encountered (according to outpan):
				 * No product found (Error code 100)
				 * Barcode does not exist (Error code 215)
				 * Unable to establish connection (Error code -1)
				 * It might be possible to provide more focused error messages at a later point
				 */
				return obj.getJSONObject("error").getString("message");
			
			} catch (JSONException e1) {
				//This error should never be encountered due to outpan's error handling. If it is encountered, maybe it's time to switch APIs...
				return "Error error: Generic error";
			}
		}
	}
}


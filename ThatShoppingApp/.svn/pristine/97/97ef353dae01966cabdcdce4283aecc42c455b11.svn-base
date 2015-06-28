package com.example.thatshoppingapp.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.thatshoppingapp.ItemObject;

/**
 * 
 * @author Matt McKillip
 *
 * This is the interface between the android application and the MySQL database
 * 
 */
public class RemoteDatabaseHelper2{	
	// URLs for database
	private static final String SEARCH_KEYWORD_URL = "http://proj-309-t15.cs.iastate.edu/testing/search_item.php";
	private static final String SEARCH_BARCODE_URL = "http://proj-309-t15.cs.iastate.edu/testing/search_barcode.php";
	private static final String ADD_ITEM_URL = "http://proj-309-t15.cs.iastate.edu/testing/add_item.php";
	private static final String ADD_INVENTORY_URL = "http://proj-309-t15.cs.iastate.edu/testing/add_inventory.php";
	private static final String REMOVE_ITEM_URL = "http://proj-309-t15.cs.iastate.edu/testing/remove_item.php";
	private static final String REMOVE_INVENTORY_URL = "http://proj-309-t15.cs.iastate.edu/testing/remove_inventory.php";
	
	// Values to pass to AsyncTask
	public static final int KEYWORD = 1;
	public static final int BARCODE = 2; 
	public static final int ITEM = 3; 
	public static final int INVENTORY = 4;
	public static final int RITEM = 5; 
	public static final int RINVENTORY = 6;
	
	// JSON variables
	private JSONArray itemJSONArray = null;
	private JSONArray inventoryJSONArray = null;
	private JSONParser jsonParser = new JSONParser();
	private JSONObject jobj = new JSONObject();
	
	private ArrayList<ItemObject> itemlist = new ArrayList<ItemObject>();
	private Context context;
	
	private String preferredStore = "0";
	
	/**
	 * Public Constructor
	 * 
	 * @param context	Pass context from fragment
	 */
	public RemoteDatabaseHelper2(Context context){
		//save the context for the display dialog
		this.context=context;
	}

	/**
	 * 
	 * @param search	Keyword serach term
	 * @return	Arraylist<ItemObject> of all of the database entries that returned hits
	 */
	public ArrayList<ItemObject> queryDB_Keyword(String search){	
		//start asynchronous task
		AttemptQuery searchAttempt = new AttemptQuery(search,null, null, null, KEYWORD);
		
		try 
		{
			searchAttempt.execute().get(1000, TimeUnit.MILLISECONDS);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ExecutionException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (TimeoutException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("queryDB_Keyword", jobj.toString());
		//return getJSONlist();	
		
		//log for debugging
		Log.d("queryDB_Keyword", jobj.toString());
				
			//Check if JSON is correct
			if (jobj.has("success"))
			{
				try 
				{
					//No Results
					if( jobj.get("success").equals(0))
					{
						Log.d("queryDB_Keyword", "Not a success: Returning Null");
						return null;
					}
						
					//Double check there are results
					else if (getJSONlist().size() > 0){
						Log.d("queryDB_Keyword", "Returning results");
						return getJSONlist();	
					}
						
					//No results
					else{
						Log.d("queryDB_Keyword", "No results: Returning Null");
						return null;
					}
				} 
					
				//unhandled Exception
				catch (JSONException e) 
				{
					Log.d("queryDB_Keyword", "Unhandled Exception " + e.toString());
					return null;
				}		
			}
			//bad JSON
			else
			{
				Log.d("queryDB_Keyword",  "Bad JSON");
				return null;
			}
	}
	

	/**
	 * 
	 * @param barcode	This is a 13 digit barcode string to search remote database for.
	 * @return	Returns and ItemObject if there are results, else it returns null
	 * 
	 */
	public ItemObject queryDB_Barcode(String barcode){	
		//start asynchronous task
		AttemptQuery searchAttempt = new AttemptQuery(barcode,null, null, null,BARCODE);
		try {
			searchAttempt.execute().get(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		//log for debugging
		Log.d("queryDB barcode", jobj.toString());
		
		//Check if JSON is correct
		if (jobj.has("success"))
		{
			try 
			{
				//No Results
				if( jobj.get("success").equals(0))
				{
					Log.d("queryDB_Barcode", "No results: Returning Null");
					return null;
				}
				
				//Double check there are results
				else if (getJSONlist().size() > 0){
					Log.d("queryDB_Barcode", "Returning results");
					return getJSONlist().get(0);	
				}
				
				//No results
				else{
					Log.d("queryDB_Barcode", "No results: Returning Null");
					return null;
				}
			} 
			
			//unhandled Exception
			catch (JSONException e) {
				Log.d("queryDB_Barcode", "Unhandled Exception " + e.toString());
				return null;
			}		
		}
		//bad JSON
		else
		{
			Log.d("queryDB_Barcode",  "Bad JSON");
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param name	item name
	 * @param brand	item brand
	 * @param barcode	item barcode
	 * @return	returns string noting the status of the insert to the remote database
	 */
	public boolean add_item(String name, String brand, String barcode){	
		AttemptQuery searchAttempt = new AttemptQuery(name,brand, barcode, "",ITEM);
		try 
		{
			searchAttempt.execute().get(1000, TimeUnit.MILLISECONDS);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		catch (ExecutionException e) 
		{
			e.printStackTrace();
		} 
		catch (TimeoutException e) 
		{
			e.printStackTrace();
		}
		
		//Check if JSON is good
		if(jobj.has("success")){
			try 
			{
				Boolean test = false;
				Log.d("add_item",jobj.getString("message"));
				//return message
				if (jobj.getString("success").equals("1")){
					test=true;
				}
				
				return test;
			} 
			catch (JSONException e) 
			{
				Log.d("add_item","Exception: " + e.toString());
				return false;
			}
		}
		//JSON is incorrect
		else
		{
			Log.d("add_item", "Badd JSON");
			return false;
		}
	}
	
	public boolean add_inventory(String storeID, String barcode, String price, String location){
		
		//TODO
		/**
		int intBarcode = Integer.parseInt(barcode);
		String.format("%013d", intBarcode);
		barcode = String.valueOf(intBarcode);
		Log.d("add_inventory", "0 padded barcode: " + barcode);*/
		
		AttemptQuery searchAttempt = new AttemptQuery(barcode, price, location, storeID, INVENTORY);
		try {
			searchAttempt.execute().get(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//log for debugging
		Log.d("add_inventory", jobj.toString());
		
		//Check if JSON is good
		if(jobj.has("success")){
			try 
			{
				Log.d("add_inventory",jobj.getString("message"));
				//return message
				return jobj.getString("success").equals("1");	
			} 
			catch (JSONException e) 
			{
				Log.d("add_inventory","Exception: " + e.toString());
				return false;
			}
		}
		//JSON is incorrect
		else
		{
			Log.d("add_inventory", "Badd JSON");
			return false;
		}
	}

	public boolean remove_item(String barcode){	
		AttemptQuery searchAttempt = new AttemptQuery(barcode,"","", "",RITEM);
		try 
		{
			searchAttempt.execute().get(1000, TimeUnit.MILLISECONDS);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		catch (ExecutionException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (TimeoutException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Check if JSON is good
		if(jobj.has("success")){
			try 
			{
				Log.d("remove_item",jobj.getString("message"));
				
				//return message
				if (jobj.getString("success").equals("1")){
					return true;
				}
				
				return false;
			} 
			catch (JSONException e) 
			{
				Log.d("remove_item","Exception: " + e.toString());
				return false;
			}
		}
		//JSON is incorrect
		else
		{
			Log.d("remove_item", "Badd JSON");
			return false;
		}
	}
	
	public boolean remove_inventory(String barcode, String storeID){
		AttemptQuery searchAttempt = new AttemptQuery(barcode,storeID,"", "",RINVENTORY);
		try 
		{
			searchAttempt.execute().get(1000, TimeUnit.MILLISECONDS);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		catch (ExecutionException e) 
		{
			e.printStackTrace();
		} 
		catch (TimeoutException e) 
		{
			e.printStackTrace();
		}
		
		//Check if JSON is good
		if(jobj.has("success")){
			try 
			{
				Log.d("remove_item",jobj.getString("message"));
				
				//return message
				if (jobj.getString("success").equals("1")){
					return true;
				}
				
				return false;
			} 
			catch (JSONException e) 
			{
				Log.d("remove_item","Exception: " + e.toString());
				return false;
			}
		}
		//JSON is incorrect
		else
		{
			Log.d("remove_item", "Badd JSON");
			return false;
		}
	}
	
	
	//helper method helps pass values
	private void updateJSON(JSONObject obj){
		jobj = obj;
	}
	
	// Helper method which takes the JSON and parses that to make an array list
	// Of ItemObjects
	private ArrayList<ItemObject> getJSONlist(){
		itemlist.clear();
		try {
			if( jobj.get("success").equals(0)){
				Log.d("getJSONlist", "no results");
				return null;
			}
			
			itemJSONArray = jobj.getJSONArray("items");
			Log.d("getJSONlist", "items array: " + itemJSONArray.toString());
			
			// looping through all posts according to the json object returned
			for (int i = 0; i < itemJSONArray.length(); i++) {
				JSONObject c = itemJSONArray.getJSONObject(i);
				Log.d("getJSONlist","loop: " + c.toString());
	
				// gets the content of each tag
				String itemname = c.getString("item_name");
				String itemdescription = c.getString("item_brand");
				String itembarcode = c.getString("item_barcode");
				
				//build the item object
				ItemObject item = new ItemObject(itemname ,itembarcode, itemdescription);
				item.addInventory("NA", "NA", "NA");
				
				//inventory exists
				if(c.has("inventory")){
					inventoryJSONArray = c.getJSONArray("inventory");
					// looping through all posts according to the json object returned
					for (int j = 0; j < inventoryJSONArray.length(); j++) {
						
						JSONObject d = inventoryJSONArray.getJSONObject(j);
						Log.d("getJSONlist","inventory: " + d.toString());
						
						// gets the content of each tag
						String storeid = d.getString("store_id");
						String itemprice = d.getString("item_price");
						String itemlocation = d.getString("item_location");
						
						item.addInventory(storeid, itemprice, itemlocation);
						Log.d("check price", item.getPrice("2"));
					}
				}
				//add object to the arrayList
				itemlist.add(item);	
			}
		} 
		catch (JSONException e) 
		{
			Log.d("getJSONlist", "JSON exception: " + e.toString());
			return null;
		}
		
		Log.d("getJSONlist", "result: " + itemlist.toString());
		return itemlist;
	}
	
	
	/**
	 * This allows the search to be executed asynchronously
	 * 
	 * @author Matt
	 *
	 */
	private class AttemptQuery extends AsyncTask<String, String, String> {
		private String name;
		private String brand;
		private String barcode;
		private String location;
		private String price;
		private String storeID;
		
		private Integer search_type;
		
		//constuctor
		public AttemptQuery(String s1, String s2, String s3,String s4, Integer search_type){
			this.search_type = search_type;
			if (search_type == ITEM){
				this.name = s1;
				this.brand = s2;
				this.barcode = s3;
			}
			else if (search_type == INVENTORY){
				this.barcode = s1;
				this.price = s2;
				this.location = s3;
				this.storeID = s4;
			}
			else if (search_type == KEYWORD){
				this.name = s1;
			}
			else if (search_type == BARCODE){
				this.barcode = s1;
			}

			else if (search_type == RITEM){
				this.barcode = s1;
			}
			
			else if (search_type == RINVENTORY){
				this.barcode = s1;
				this.storeID = s2;
			}
			dialog = new ProgressDialog(context);
		}
		
		/** progress dialog to show user that the backup is processing. */
	    private ProgressDialog dialog;
		
		/**
		 * 
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		    this.dialog.setMessage("Query is taking awhile \n make sure you are connected \n to ISU wireless");
		    this.dialog.show();
		}
		
		/**
		 * 
		 */
		@Override
		protected String doInBackground(String... args) {
			
			if (search_type == KEYWORD){
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("itemname", name));
					
				// getting product details by making HTTP request
				jobj = jsonParser.makeHttpRequest(SEARCH_KEYWORD_URL, "POST", params);
				Log.d("why isnt keywords working?", name);
				updateJSON(jobj);
				
				try {
					return jobj.getString("message");
				} catch (JSONException e) {
					return "No results available";
				}
			}
			
			else if (search_type == BARCODE){
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("barcode", barcode));
					
				// getting product details by making HTTP request
				jobj = jsonParser.makeHttpRequest(SEARCH_BARCODE_URL, "POST", params);
				
				updateJSON(jobj);
				
				try {
					return jobj.getString("message");
				} catch (JSONException e) {
					return "No results available";
				}
			}
			
			else if (search_type == ITEM){
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("barcode", barcode));
				params.add(new BasicNameValuePair("name", name));
				params.add(new BasicNameValuePair("brand", brand));
					
				// getting product details by making HTTP request
				jobj = jsonParser.makeHttpRequest(ADD_ITEM_URL, "POST", params);
				Log.d("add item", jobj.toString());
				updateJSON(jobj);
				
				try {
					return jobj.getString("message");
				} catch (JSONException e) {
					return "No results available";
				}
			}
				
			else if (search_type == INVENTORY){
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("storeid", storeID));
				params.add(new BasicNameValuePair("barcode", barcode));
				params.add(new BasicNameValuePair("price", price));
				params.add(new BasicNameValuePair("location", location));
					
				// getting product details by making HTTP request
				jobj = jsonParser.makeHttpRequest(ADD_INVENTORY_URL, "POST", params);
				Log.d("add ivnentory", jobj.toString());
				updateJSON(jobj);
				
				try {
					return jobj.getString("message");
				} catch (JSONException e) {
					return "Error Adding Item to Inventory";
				}
			}
			
			else if (search_type == RITEM){
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("barcode", barcode));
					
				// getting product details by making HTTP request
				jobj = jsonParser.makeHttpRequest(REMOVE_ITEM_URL, "POST", params);
				
				updateJSON(jobj);
				
				try {
					return jobj.getString("message");
				} catch (JSONException e) {
					return "No results available";
				}
			}
			
			else if (search_type == RINVENTORY){
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				Log.d("in RINVENTORY", storeID);
				params.add(new BasicNameValuePair("storeid", storeID));
				params.add(new BasicNameValuePair("barcode", barcode));
					
				// getting product details by making HTTP request
				jobj = jsonParser.makeHttpRequest(REMOVE_INVENTORY_URL, "POST", params);
				
				updateJSON(jobj);
				
				try {
					return jobj.getString("message");
				} catch (JSONException e) {
					return "No results available";
				}
			}
			
			else{
				return "Error query type not found";
			}

		}
		
		//display toast of message
		protected void onPostExecute(String file_url) {
			if (dialog.isShowing()) {
	            dialog.dismiss();
	        }
			Toast.makeText(context, file_url, Toast.LENGTH_SHORT).show();
			
		}
	}
}

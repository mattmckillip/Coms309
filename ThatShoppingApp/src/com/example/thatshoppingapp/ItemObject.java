package com.example.thatshoppingapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

import com.example.thatshoppingapp.scanner.ItemInfoFinder;

/**
 * This class is the way Items information will be stored
 * Each entry to the item has a get, and a set/update
 * 
 * @author Matt and a little bit of Carson and Justin too
 *
 */
public class ItemObject 
{
	//store_inventory is a hashmap that stores values first by store id and then within each store
	//there is a a price and a location so to get price an item from store 0 you would use the following:
	//store_inventory.get("0").get(0)
	private HashMap<String, ArrayList<String>> store_inventory = new HashMap<String, ArrayList<String>>();
	private int _id;
	private String ItemName;
	private String keywords;
	private String barcode;
	private String manufacturer;
	private boolean selected; //this is for list activity to see selected items
	private ArrayList<String> list;
	
	//empty constructor
	public ItemObject()
	{
		
	}
	
	//For when the user is not currently in a store
	public ItemObject(String code)
	{
		this.barcode = code;
		
		ItemName = new ItemInfoFinder().getName(code);
		if (ItemName.equals(null))
		{
			this.ItemName = "Item Not Found"; //TODO Fix
		}
	}
	
	public ItemObject(String name,String barcode, String manufacturer) 
	{
		this.ItemName = name;
		this.barcode = barcode;
		this.manufacturer = manufacturer;
		this.selected = false;
	}
	
	
	public String getItemName()
	{
		return this.ItemName;
	}
	
	
	public String getManufacturer()
	{
		return this.manufacturer;
	}
	
	public void setManufacturer(String manufacturer)
	{
		 this.manufacturer = manufacturer;
	}
	
	/**
	 * 
	 * @param storeID
	 * @param price
	 * @param location
	 */
	public void addInventory(String storeID, String price, String location)
	{
		//instantiate the data
		this.list = new ArrayList<String>();
		this.list.add(price);
		this.list.add(location);
		
		//add to hashmap
		this.store_inventory.put(storeID, this.list);
		
		//clear object
		this.list=null;
	}
	

	public ArrayList<String> getStoresList()
	{
		this.list = new ArrayList<String>();

		for ( String key : this.store_inventory.keySet() )
		{
			this.list.add(key);
		}
		return this.list;
	}
	
	public boolean atStore(String storeID){
		for ( String key : this.store_inventory.keySet() ) 
		{
			if(key.equals(storeID)){
				return true;
			}
		}
		return false;
	}
	

	public String getStores()
	{
		for ( String key : this.store_inventory.keySet() ) 
		{
			return key;
		}
		return "";

	}
	

	public String getKeywords()
	{
		return this.keywords;
	}
	
	
	public String getLocation()
	{
		try{
			return this.store_inventory.get("HyVee").get(1);
		}
		catch(NullPointerException e){
			return "";
		}
	}
	
	
	public String getLocation(String storeID)
	{
		try{
		return this.store_inventory.get(storeID).get(1);
		}
		catch(NullPointerException e){
			return "";
		}
	}
	
	
	public String getPrice()
	{
		try{
			return this.store_inventory.get("HyVee").get(0);
		}
		catch(NullPointerException e){
			return "";
		}
	
	}
	
	public String getPrice(String storeID)
	{
		try{
			Log.d("getPrice", this.store_inventory.toString());
			return this.store_inventory.get(storeID).get(0);
		}
		catch(NullPointerException e){
			return "";
		}
	}
	
	public void setPrice(String storeID, String price)
	{
		this.list = new ArrayList<String>();
		this.list.add(price);
		this.list.add(this.getLocation());
		
		this.store_inventory.put(storeID, this.list);
	}
	
	public void setLocation(String storeID, String location)
	{
		this.list = new ArrayList<String>();
		this.list.add(this.getPrice());
		this.list.add(location);
		
		this.store_inventory.put(storeID, this.list);
	}
	
	public void setName(String newName)
	{
		this.ItemName=newName;
	}
	

	public void addKeywords(String keyword)
	{
		this.keywords = this.keywords.concat(" , ".concat(keyword.toLowerCase()));
	}

	
	public void setCode(String code)
	{ 
		this.barcode = code; 
	}
	
	
	public String getCode()
	{ 
		return this.barcode; 
	}

	
	public void setSelected(boolean checked)
	{
		this.selected = checked;	
	}

	
	public boolean isSelected()
	{
		return this.selected;
	}
	
	
	/**
	 * This method gets a nice looking string combining itemName and manufacturer
	 * @return
	 */
	public String getDisplayName()
	{
		return this.manufacturer.concat(": ").concat(this.ItemName);
	}

	public int getID()
	{
		return this._id;
	}
	
	public void setID(int id)
	{
		this._id = id;
	}
}

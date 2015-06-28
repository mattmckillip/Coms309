package com.example.thatshoppingapp.cart;

import java.util.ArrayList;

import com.example.thatshoppingapp.ItemObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CartDatabaseHelper extends SQLiteOpenHelper
{
	private static final String TAG = CartDatabaseHelper.class.getSimpleName();

	// database configuration
	// if you want the onUpgrade to run then change the database_version
	private static final int DATABASE_VERSION = 4;
	private static final String DATABASE_NAME = "cartDB.db";

	// table configuration
	private static final String TABLE_NAME = "cart_table"; // Table name
	private static final String ITEM_TABLE_COLUMN_ID = "_id"; // a column named "_id" is required for cursor
	private static final String ITEM_TABLE_COLUMN_NAME = "item_name";
	private static final String ITEM_TABLE_COLUMN_BARCODE = "item_barcode";
	private static final String ITEM_TABLE_COLUMN_MANUFACTURER = "item_manufacturer";
	private static final String ITEM_TABLE_COLUMN_PRICE = "item_price";
	private static final String ITEM_TABLE_COLUMN_LOCATION = "item_location";
	
	protected String prefStore = "HyVee";
	
	//private DatabaseOpenHelper openHelper;
	private  SQLiteDatabase db;

	public CartDatabaseHelper(Context aContext)
	{
		super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// Create your tables here
		String buildSQL = "CREATE TABLE " + TABLE_NAME + "( " + ITEM_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, " + 
																ITEM_TABLE_COLUMN_NAME + " TEXT , " + 
																ITEM_TABLE_COLUMN_BARCODE + " TEXT , " +
																ITEM_TABLE_COLUMN_MANUFACTURER + " TEXT , " +
																ITEM_TABLE_COLUMN_PRICE + " TEXT , " +
																ITEM_TABLE_COLUMN_LOCATION + " TEXT )";
		Log.d(TAG, "onCreate SQL: " + buildSQL);
		db.execSQL(buildSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// Database schema upgrade code goes here
		String buildSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
		Log.d(TAG, "onUpgrade SQL: " + buildSQL);
		db.execSQL(buildSQL); // drop previous table
		onCreate(db); // create the table from the beginning
	}
	
	public void insertItem(ItemObject item)
	{
		db = this.getWritableDatabase();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(ITEM_TABLE_COLUMN_NAME, item.getItemName());
		contentValues.put(ITEM_TABLE_COLUMN_BARCODE, item.getCode());
		contentValues.put(ITEM_TABLE_COLUMN_MANUFACTURER, item.getManufacturer());
		contentValues.put(ITEM_TABLE_COLUMN_PRICE, item.getPrice(prefStore));
		contentValues.put(ITEM_TABLE_COLUMN_LOCATION, item.getLocation(prefStore));
		
		Log.d(TAG, item.getPrice(prefStore));
		db.insert(TABLE_NAME, null, contentValues);
		db.close();
	}
	
	public void updateItem(ItemObject item)
	{
		db = this.getWritableDatabase();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(ITEM_TABLE_COLUMN_NAME, item.getItemName());
		contentValues.put(ITEM_TABLE_COLUMN_BARCODE, item.getCode());
		contentValues.put(ITEM_TABLE_COLUMN_MANUFACTURER, item.getManufacturer());
		contentValues.put(ITEM_TABLE_COLUMN_PRICE, item.getPrice(prefStore));
		contentValues.put(ITEM_TABLE_COLUMN_LOCATION, item.getLocation(prefStore));
		
		db.update(TABLE_NAME, contentValues, ITEM_TABLE_COLUMN_ID + " = ?", new String[] { String.valueOf(item.getID()) });
		db.close();
	}
	
	public void deleteAllData()
	{
		db = this.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
		db.close();
	}

	public void deleteItem(ItemObject item) 
	{
		db = this.getWritableDatabase();
		db.delete(TABLE_NAME, ITEM_TABLE_COLUMN_ID + " = ?", new String[] { String.valueOf(item.getID()) });
		db.close();
	}
	
	//not sure if this method works currently
	public ItemObject getItem(int id)
	{
		db = this.getReadableDatabase();
		Cursor c = db.query(TABLE_NAME, new String[] {ITEM_TABLE_COLUMN_ID, 
													  ITEM_TABLE_COLUMN_NAME,
													  ITEM_TABLE_COLUMN_BARCODE,
													  ITEM_TABLE_COLUMN_MANUFACTURER,
													  ITEM_TABLE_COLUMN_PRICE,
													  ITEM_TABLE_COLUMN_LOCATION},
							ITEM_TABLE_COLUMN_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
		if(c != null) c.moveToPosition(id);
		
		ItemObject item = new ItemObject(c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_NAME)),
										 c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_BARCODE)),
										 c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_MANUFACTURER)));
		item.addInventory(prefStore, c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_PRICE)), c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_LOCATION)));
		
		item.setID(Integer.parseInt(c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_ID))));
									
		c.close();
		db.close();
		return item;
	}
	
	public ArrayList<ItemObject> getAllData()
	{
		db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		ArrayList<ItemObject> itemList = new ArrayList<ItemObject>();
		Log.d(TAG, "getAllData SQL: " + selectQuery);
		
		Cursor c = db.rawQuery(selectQuery, null);
	    c.moveToFirst();
        if (c.moveToFirst())
        {
        	do
        	{
        		//create an item object for this row of the table
        		ItemObject item = new ItemObject(c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_NAME)),
						 						 c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_BARCODE)),
						 						 c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_MANUFACTURER)));
        		
        		//add the item price and location based on the store
        		item.addInventory(prefStore, c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_PRICE)), c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_LOCATION)));
        		
        		//set the primary id of the item to what it was in the table
        		item.setID(Integer.parseInt(c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_ID))));
        		
        		//determine if the item is selected
//        		if(c.getString(c.getColumnIndex(ITEM_TABLE_COLUMN_SELECTED)).equalsIgnoreCase("TRUE")) selected = true;
//        		else selected = false;
//        		item.setSelected(selected);
        		
        		//add item to the arraylist
            	itemList.add(item);
            	
        	} while(c.moveToNext()); //then move to next row
	    }
		c.close();
		db.close();
		return itemList;
	}
}


package com.example.thatshoppingapp.store_manager;

import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.MainActivity;
import com.example.thatshoppingapp.ShoppingAppPage;
import com.example.thatshoppingapp.home.HomeAddon;
import com.example.thatshoppingapp.search.RemoteDatabaseHelper2;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * 
 * This class acts as an interface between the GUI and the database.
 * The method onCreate instantiates the database, text input, and the item list
 * The method search is called by the search buttons onclick. search() calls a 
 * method in the class DatabaseHelper.java to query the database. Then calls the 
 * custom adapter to pass the database results back to the item list.
 * 
 * @author MattMcKillip
 * 
 */

public class DatabaseManagerFragment extends ShoppingAppPage implements HomeAddon {
	protected EditText searchText;
	protected SQLiteDatabase db;
	protected Cursor cursor;

	protected Context context;
	
	protected Button addItem;
	protected Button addInventory;
	protected Button removeItem;
	protected Button removeInventory;
	
	private String preferredStore;
	private Dialog myDialog;
	
	protected RemoteDatabaseHelper2 rdatabaseHelper;
	
	static final String NAME = "Store Manager";
	
	public DatabaseManagerFragment() 
	{
		super(NAME, NAME, R.layout.activity_databasemanager);
	}
	
	/**
	 * Called when the activity is first created to instantiate variables
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		View view = super.onCreateView(inflater, container, savedInstanceState);
		super.onCreate(savedInstanceState);

		addItem = (Button)view.findViewById(R.id.AddItem);
		addInventory = (Button)view.findViewById(R.id.AddInventory);
		removeItem = (Button)view.findViewById(R.id.RemoveItem);
		removeInventory = (Button)view.findViewById(R.id.RemoveInventory);
		
		context = view.getContext();
		preferredStore = getPreferredStore();
		
		//set the action bar title
		getActivity().getActionBar().setTitle("Store Manager");
		
		rdatabaseHelper = new RemoteDatabaseHelper2(view.getContext());
		
		myDialog = new Dialog(view.getContext());
		


		/**
		 * This method is called by the onClick attribute of the search button
		 * Once it is called, the method gets the text in the search box,
		 * calls the queryDB method. Then passes the information to the custom
		 * list adapter.
		 * 
		 * @param v search term text view
		 */
		
		addItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				AddItemDialog();
			}
		});
		
		addInventory.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				AddInventoryDialog();
			}
		});
		
		
		removeItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				RemoveItemDialog();
			}
		});
		
		removeInventory.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				RemoveInventoryDialog();
			}
		});
		
		
		return view;
	}
	
	
	/**
	 * This is a convenience method for developing. It allows everyone to easily add items
	 * @param code
	 */
	private void AddItemDialog() 
    {
        myDialog.setContentView(R.layout.item_add_popup);
        myDialog.setCancelable(true);
        myDialog.setTitle("Add Item To Database");
        Button addItem = (Button) myDialog.findViewById(R.id.AddItemConfirm);
        Button cancel = (Button) myDialog.findViewById(R.id.AddItemCancel);

        final EditText barcode = (EditText) myDialog.findViewById(R.id.AddItemPopUpBarcode);
        final EditText name = (EditText) myDialog.findViewById(R.id.AddItemPopUpName);
        final EditText brand = (EditText) myDialog.findViewById(R.id.AddItemPopUpBrand);
              
        myDialog.show();
        
  
        addItem.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
               String nameStr = name.getText().toString();
               String brandStr = brand.getText().toString();
               String barcodeStr = barcode.getText().toString();
               StringBuilder stringBuilder = new StringBuilder();
               
               //pad the barcode, since it needs to be 13 digits
               if(barcodeStr.length() == 12){
            	   stringBuilder.append("0");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               else if(barcodeStr.length() == 11){
            	   stringBuilder.append("00");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               else if(barcodeStr.length() == 10){
            	   stringBuilder.append("000");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               //too short
               else{
            	 //close dialog
            	  myDialog.dismiss();
               }
               
               
        	   Log.d("ItemEntryDialog", "in additem onclick");
        	   
        	   rdatabaseHelper.add_item(nameStr, brandStr, barcodeStr);
        	   
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
        
        
        cancel.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
        cancel.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
    }
	
	
	private void AddInventoryDialog() 
    {
        myDialog.setContentView(R.layout.inventory_add_popup);
        myDialog.setCancelable(true);
        myDialog.setTitle("Add Item To Inventory");
        Button addItem = (Button) myDialog.findViewById(R.id.AddInventoryConfirm);
        Button cancel = (Button) myDialog.findViewById(R.id.AddInventoryCancel);
        
        Log.d("AddInventoryDialog", "past buttons");        
       
        final Spinner storeID = (Spinner) myDialog.findViewById(R.id.AddInventorySpinner);
        final EditText location = (EditText) myDialog.findViewById(R.id.AddInventoryLocation);
        final EditText price = (EditText) myDialog.findViewById(R.id.AddInventoryPrice);
        
        //TODO use spinner adapter
        if(preferredStore.equals("Hyvee")){
        	storeID.setSelection(0);
        }
        else if(preferredStore.equals("Target")){
        	storeID.setSelection(1);
        }
        else if(preferredStore.equals("Walmart")){
        	storeID.setSelection(2);
        }
        
        final EditText invbarcode = (EditText) myDialog.findViewById(R.id.AddInventoryBarcode1);
        
        Log.d("AddInventoryDialog", "past edit texts");
        myDialog.show();
        
        
        addItem.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   String storeidStr = (String) storeID.getSelectedItem();
        	   
        	   if (storeidStr.equals("HyVee")){
        		   storeidStr = "0";
        	   }
        	   else if (storeidStr.equals("Target")){
        		   storeidStr = "1";
        	   }
        	   else if (storeidStr.equals("Walmart")){
        		   storeidStr = "2";
        	   }
        	   
               String locationStr = location.getText().toString();
               String priceStr = price.getText().toString();
               String barcodeStr = invbarcode.getText().toString();
               StringBuilder stringBuilder = new StringBuilder();
               
               //pad the bar code, since it needs to be 13 digits
               if(barcodeStr.length() == 12){
            	   stringBuilder.append("0");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               else if(barcodeStr.length() == 11){
            	   stringBuilder.append("00");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               else if(barcodeStr.length() == 10){
            	   stringBuilder.append("000");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               //too short
               else{
            	 //close dialog
            	  myDialog.dismiss();
               }
               
               
        	   Log.d("ItemEntryDialog", "in additem onclick");
        	   
        	   rdatabaseHelper.add_inventory(storeidStr, barcodeStr, priceStr,locationStr);
        	   
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
        
        
        cancel.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
    }
	
	
	
	private void RemoveItemDialog() 
    {
        myDialog.setContentView(R.layout.remove_item_popup);
        myDialog.setCancelable(true);
        myDialog.setTitle("Remove Item From DB");
        Button addItem = (Button) myDialog.findViewById(R.id.RemoveItemConfirm);
        Button cancel = (Button) myDialog.findViewById(R.id.RemoveItemCancel);

        final EditText barcode = (EditText) myDialog.findViewById(R.id.RemoveItemPopUpBarcode);

        myDialog.show();
        
  
        addItem.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
               String barcodeStr = barcode.getText().toString();
               StringBuilder stringBuilder = new StringBuilder();
               
               //pad the barcode, since it needs to be 13 digits
               if(barcodeStr.length() == 12){
            	   stringBuilder.append("0");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               else if(barcodeStr.length() == 11){
            	   stringBuilder.append("00");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               else if(barcodeStr.length() == 10){
            	   stringBuilder.append("000");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               //too short
               else{
            	 //close dialog
            	  myDialog.dismiss();
               }
        	   
        	   rdatabaseHelper.remove_item(barcodeStr);
        	   
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
        
        
        cancel.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
        cancel.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
    }
	
	
	
	private void RemoveInventoryDialog() 
    {
        myDialog.setContentView(R.layout.remove_inventory_popup);
        myDialog.setCancelable(true);
        myDialog.setTitle("Remove Inventory");
        Button addItem = (Button) myDialog.findViewById(R.id.RemoveInventoryConfirm);
        Button cancel = (Button) myDialog.findViewById(R.id.RemoveInventoryCancel);

        final EditText barcode = (EditText) myDialog.findViewById(R.id.RemoveInventoryPopUpBarcode);
        final Spinner storeID = (Spinner) myDialog.findViewById(R.id.RemoveInventorySpinner);
        
        //TODO use spinner adapter
        if(preferredStore.equals("Hyvee")){
        	storeID.setSelection(0);
        }
        else if(preferredStore.equals("Target")){
        	storeID.setSelection(1);
        }
        else if(preferredStore.equals("Walmart")){
        	storeID.setSelection(2);
        }
        
        myDialog.show();
        
  
        addItem.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   
        	   String storeIDStr = (String) storeID.getSelectedItem();
        	   Log.d("Remove Inventory", storeIDStr);
        	   if (storeIDStr.equals("HyVee")){
        		   storeIDStr = "0";
        		   Log.d("Remove Inventory2", storeIDStr);
        	   }
        	   else if (storeIDStr.equals("Target")){
        		   storeIDStr = "1";
        	   }
        	   else if (storeIDStr.equals("Walmart")){
        		   storeIDStr = "2";
        	   }
        	   
               String barcodeStr = barcode.getText().toString();
               StringBuilder stringBuilder = new StringBuilder();
               
               //pad the barcode, since it needs to be 13 digits
               if(barcodeStr.length() == 12){
            	   stringBuilder.append("0");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               else if(barcodeStr.length() == 11){
            	   stringBuilder.append("00");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               else if(barcodeStr.length() == 10){
            	   stringBuilder.append("000");
            	   stringBuilder.append(barcodeStr);
            	   barcodeStr = stringBuilder.toString();
               }
               //too short
               else{
            	 //close dialog
            	  myDialog.dismiss();
               }
 
        	   rdatabaseHelper.remove_inventory(barcodeStr, storeIDStr);
        	   
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
        
        
        cancel.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
        cancel.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   //close dialog
        	   myDialog.dismiss();
           }
       });
    }
	
	
	@Override
	public View getHomeAddon(ViewGroup container)
	{
		return null;
	}

	@Override
	public int getIcon()
	{
		return R.drawable.search_icon;
	}	
}
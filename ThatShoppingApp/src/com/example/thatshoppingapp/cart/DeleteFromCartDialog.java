package com.example.thatshoppingapp.cart;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.thatshoppingapp.ItemObject;

public class DeleteFromCartDialog extends DialogFragment
{
	public CartDatabaseHelper cartDB; 
	//private static final String TAG = DeleteDialog.class.getSimpleName();
	public ItemObject itemDelete;
	public ArrayList<ItemObject> cartItems;
	public CartListAdapter cartAdapter;
    
	@Override
	 public Dialog onCreateDialog(Bundle savedInstanceState) 
	{
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        cartDB = new CartDatabaseHelper(getActivity());
	        builder.setMessage("Delete Item fom Cart?")
	               .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
	               {
	            	   @Override
	                   public void onClick(DialogInterface dialog, int id) 
	                   {
	                       // Delete the entry here
	                	   //Log.d(TAG, "tried to delete item: " + itemID);
	            		   cartDB.deleteItem(itemDelete);
	            		   cartItems.clear();
	            		   cartItems = cartDB.getAllData();
	            		   cartAdapter.sortItems("Name", cartItems);
	            		   cartAdapter.refresh(cartItems);
	                   }
	               })
	               .setNegativeButton("No", new DialogInterface.OnClickListener() 
	               {
	                   public void onClick(DialogInterface dialog, int id) 
	                   {
	                       // don't delete and return to fragment
	                   }
	               });
	        
	        return builder.create();
	    }		
}

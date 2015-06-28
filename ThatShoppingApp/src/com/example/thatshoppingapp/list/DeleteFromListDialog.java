package com.example.thatshoppingapp.list;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.example.thatshoppingapp.ItemObject;

public class DeleteFromListDialog extends DialogFragment
{
	public ListDatabaseHelper listDB; 
	//private static final String TAG = DeleteDialog.class.getSimpleName();
	public ItemObject itemDelete;
	public ArrayList<ItemObject> listItems;
	public GroceryListAdapter listAdapter;
    
	@Override
	 public Dialog onCreateDialog(Bundle savedInstanceState)
	{
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        listDB = new ListDatabaseHelper(getActivity());
	        builder.setMessage("Delete Item from List?")
	               .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
	               {
	            	   @Override
	                   public void onClick(DialogInterface dialog, int id) 
	                   {
	                       // Delete the entry here
	                	   //Log.d(TAG, "tried to delete item: " + itemID);
	            		   listDB.deleteItem(itemDelete);
	            		   listItems.clear();
	            		   listItems = listDB.getAllData();
	            		   listAdapter.sortItems("Name", listItems);
	            		   listAdapter.refresh(listItems);
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

package com.example.thatshoppingapp.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.list.ListDatabaseHelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * This class is a custom list adapter that can populate the list in the grocery list
 * with ArrayList<ItemObject>
 * 
 * @author Justin Long
 *
 */
public class GroceryListAdapter extends BaseAdapter 
{

	protected ArrayList<ItemObject> itemList;
	protected ListDatabaseHelper localDB;
	private LayoutInflater layoutInflater;
	private Context context;
	private static final String TAG = GroceryListAdapter.class.getSimpleName();

	/**
	 * 
	 * @param context
	 * @param list
	 */
	public GroceryListAdapter(Context context, ArrayList<ItemObject> list) 
	{
		this.itemList = list;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		localDB = new ListDatabaseHelper(context);
	}

	@Override
	public int getCount() 
	{
		return itemList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	public ArrayList<ItemObject> getItems()
	{
		return this.itemList;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder = null;
		
		if (convertView == null) 
		{
			convertView = layoutInflater.inflate(R.layout.itemlayout, null);
			
			/*
			 * Get holders from list
			 */
			holder = new ViewHolder();
			holder.nameView = (TextView) convertView.findViewById(R.id.itemName);
			
			convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();
		
		/*
		 * Add listener to each item so that when pressed it will
		 * mark a check box to show that the item has been selected
		 */
//	    holder.nameView.setOnClickListener( new View.OnClickListener() 
//	    {  
//	        public void onClick(View v) 
//	        {  
//	        	CheckBox cb = (CheckBox) v ;  
//	        	ItemObject item = (ItemObject) cb.getTag();
//        		item.setSelected(!(item.isSelected()));
//        		cb.setChecked(item.isSelected());
//        		Log.d(TAG, item.getItemName() + " is Selected: " + item.isSelected());
//	        	localDB.updateItem(item);
//	        	itemList = localDB.getAllData();
//	        }  
//	    });
		holder.nameView.setText(itemList.get(position).getItemName());
		
		return convertView;
	}
	
    public void refresh(ArrayList<ItemObject> itemList)
    {
    	this.itemList = itemList;
        notifyDataSetChanged();
    } 
	
	static class ViewHolder 
	{
		TextView nameView;
		CheckBox checkBox;
	}
	
	public ArrayList<ItemObject> sortItems(String sortBy, ArrayList<ItemObject> listToSort)
	{
		switch (sortBy)
		{
			case "Location":
				//Sorting by location
				Collections.sort(listToSort, new Comparator<ItemObject>() 
				{
			        @Override
			        public int compare(ItemObject  item1, ItemObject  item2)
			        {
			        	return item1.getLocation().compareTo(item2.getLocation());
			        }
				});
				break;
			
			case "Name":
				//Sorting by name
				Collections.sort(listToSort, new Comparator<ItemObject>() 
				{
			        @Override
			        public int compare(ItemObject  item1, ItemObject  item2)
			        {
			        	return item1.getItemName().compareTo(item2.getItemName());
			        }
				});
				break;
			
			case "Price":
				//Sorting by price
				Collections.sort(listToSort, new Comparator<ItemObject>() 
				{
			        @Override
			        public int compare(ItemObject  item1, ItemObject  item2)
			        {
			        	return item1.getPrice().compareTo(item2.getPrice());
			        }
				});
				break;

			default:
				break;
		}

		//delete all data and then re insert the items in order
		//so that if get all data is called after sort it wont 
		//put the list back in the original order that it was in
		localDB.deleteAllData();
		int i = 0;
		for(ItemObject item : listToSort)
		{
			item.setID(i);
			localDB.insertItem(item);
			i++;
		}
		
		return listToSort;	
	}	
}


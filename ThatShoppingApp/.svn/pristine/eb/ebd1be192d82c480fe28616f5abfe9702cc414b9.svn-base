package com.example.thatshoppingapp.cart;

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
import android.widget.TextView;

/**
 * This class is a custom list adapter that can populate the list in the grocery cart
 * with ArrayList<ItemObject>
 * 
 * @author Justin Long
 *
 */
public class CartListAdapter extends BaseAdapter 
{

	protected ArrayList<ItemObject> itemList;
	protected ListDatabaseHelper cartDB;
	private LayoutInflater layoutInflater;
	private Context context;
	private static final String TAG = CartListAdapter.class.getSimpleName();
	protected String prefStore = "HyVee";

	/**
	 * 
	 * @param context
	 * @param list
	 */
	public CartListAdapter(Context context, ArrayList<ItemObject> list, String store) 
	{
		this.itemList = list;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		cartDB = new ListDatabaseHelper(context);
		this.prefStore = store;
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
			convertView = layoutInflater.inflate(R.layout.itemlayout_cart, null);
			
			/*
			 * Get holders from list
			 */
			holder = new ViewHolder();
			holder.nameView = (TextView) convertView.findViewById(R.id.itemName);
			holder.locationView = (TextView) convertView.findViewById(R.id.location);
			holder.priceView = (TextView) convertView.findViewById(R.id.price);
			
			convertView.setTag(holder);
		}
		else holder = (ViewHolder) convertView.getTag();
		
		holder.nameView.setText(itemList.get(position).getDisplayName());
		holder.locationView.setText("Aisle: " + (itemList.get(position)).getLocation(prefStore));
		Log.d("ITEM LOCATION", itemList.get(position).getLocation(prefStore));
		holder.priceView.setText("$" + (itemList.get(position)).getPrice(prefStore));
		Log.d("ITEM PRICE", itemList.get(position).getPrice(prefStore));
//		if (itemList.get(position).atStore(prefStore)){
//			holder.locationView.setText("Aisle: " + (itemList.get(position)).getLocation(prefStore));
//			holder.priceView.setText("$" + (itemList.get(position)).getPrice(prefStore));
//		}
//		else{
//			String storeID = (itemList.get(position)).getStoresList().get(0);
//			holder.locationView.setText("Aisle: " + (itemList.get(position)).getLocation(storeID));
//			holder.priceView.setText("$" + (itemList.get(position)).getPrice(storeID));
//		}
		
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
		TextView locationView;
		TextView priceView;
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
		cartDB.deleteAllData();
		int i = 0;
		for(ItemObject item : listToSort)
		{
			item.setID(i);
			cartDB.insertItem(item);
			i++;
		}
		
		return listToSort;	
	}	
}


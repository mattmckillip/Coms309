package com.example.thatshoppingapp.search;

import java.util.ArrayList;

import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.MainActivity;
import com.example.thatshoppingapp.store_manager.DatabaseManagerFragment;
import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.cart.CartDatabaseHelper;
import com.example.thatshoppingapp.list.ListDatabaseHelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * This class is a custom list adapter that can populate the list in list_entry.xml
 * with ArrayList<ItemObject>
 * 
 * @author MattMcKillip
 *
 */
public class SearchListAdapter extends BaseAdapter {

	private ArrayList<ItemObject> itemList;
	private LayoutInflater layoutInflater;
	private Context context;
	private String preferredStore;
	
	/**
	 * 
	 * @param context
	 * @param list
	 */
	public SearchListAdapter(Context context, ArrayList<ItemObject> list, String store) {
		this.itemList = list;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		preferredStore = store;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	/**
	 * This method gets the view from the list_entry.xml file and also
	 * contains the code for the onClick attribute of the buttons
	 */
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		String nameText = "";
		//Instantiate the local database helper to add the item
    	final ListDatabaseHelper listDB = new ListDatabaseHelper(context);
    	final CartDatabaseHelper cartDB = new CartDatabaseHelper(context);
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_entry, null);
			
			/*
			 * Get holders from list
			 */
			holder = new ViewHolder();
			holder.nameView = (TextView) convertView.findViewById(R.id.itemName);
			holder.locationView = (TextView) convertView.findViewById(R.id.stores);
			holder.cartButton = (ImageButton) convertView.findViewById(R.id.cart_button);
			holder.listButton = (ImageButton) convertView.findViewById(R.id.list_button);

			/*
			 * Add a listener to the cart button
			 * When the button is pressed the button text changes
			 */
            holder.cartButton.setOnClickListener(new OnClickListener()
            {
            @Override
            public void onClick(View v) {
            	//add to shopping cart
            	cartDB.insertItem(itemList.get(position));
            	Toast.makeText(context, itemList.get(position).getDisplayName().concat(" added to cart"), Toast.LENGTH_SHORT).show();
            }
           });
            
			/*
			 * Add a listener to the list button
			 * When the button is pressed the button text changes
			 */
            holder.listButton.setOnClickListener(new OnClickListener()
            {
            @Override
            public void onClick(View v) {
            	//add to shopping list
            	listDB.insertItem(itemList.get(position));
            	Toast.makeText(context, itemList.get(position).getDisplayName().concat(" added to list"), Toast.LENGTH_SHORT).show();
            }
           });
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//Build the string to display nicely
		nameText = itemList.get(position).getDisplayName();
		
		//split up the long string so it can easily be read on a phone
		nameText = splitLongString(nameText);
		
		//the views
		holder.nameView.setText(nameText);
		Log.d("Prefered store?", preferredStore);
		if (itemList.get(position).atStore(preferredStore)){
			holder.locationView.setText("At " + preferredStore + 
					": Aisle " + (itemList.get(position)).getLocation(preferredStore) + 
					" For: $" + (itemList.get(position)).getPrice(preferredStore)
					);
		}
		else{
			String storeID = (itemList.get(position)).getStoresList().get(0);
			holder.locationView.setText("At " + storeID + 
					": Aisle " + (itemList.get(position)).getLocation(storeID) + 
					" For: $" + (itemList.get(position)).getPrice(storeID)
					);
		}
		return convertView;
		
		
	}
	/**
	 * Helper method to split the nameText if it gets too long for the display
	 * @param s input long string
	 * @return a string with line length set to 20
	 */
	private String splitLongString(String s){
		StringBuilder sb = new StringBuilder(s);
		int i = 0;
		while ((i = sb.indexOf(" ", i + 20)) != -1) {
			   sb.replace(i, i + 1, "\n");
		}
		return sb.toString();
	}
	
	
	/**
	 * Static class to hold the different GUI items
	 * 
	 * @author MattMcKillip
	 *
	 */
	static class ViewHolder {
		TextView nameView;
		TextView locationView;
		ImageButton cartButton;
		ImageButton listButton;
	}
	
}


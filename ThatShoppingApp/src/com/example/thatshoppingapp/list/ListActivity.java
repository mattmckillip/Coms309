package com.example.thatshoppingapp.list;

import java.util.ArrayList;

import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.ShoppingAppPage;
import com.example.thatshoppingapp.Preferences;
import com.example.thatshoppingapp.home.HomeAddon;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Handler;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

//need to fix the select delete button to give it actual functionality and imporve the list view

public class ListActivity extends ShoppingAppPage implements HomeAddon
{
	protected GroceryListAdapter customAdapter;
	protected ShoppingAppPage enterItem;
	protected Fragment prefs;
	protected ListDatabaseHelper dbHelper;
	protected ArrayList<ItemObject> items;
	
	//view elements
	protected ListView listView;
	protected Context context;
	protected Button addButton;
	protected Button deleteAllButton;
	protected Button sortButton;
	protected Button selectStoreButton;
	protected Button findStoreButton;

	private static final String TAG = ListActivity.class.getSimpleName();

	public ListActivity() 
	{
		super("list", "Grocery List", R.layout.activity_list);
	}

	
	/**
	 * Called when the activity is first created.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		super.onCreate(savedInstanceState);
		
		context = view.getContext();
		enterItem = new EnterDataActivity();
		prefs = new Preferences();
		dbHelper = new ListDatabaseHelper(context);
		final DeleteFromListDialog del = new DeleteFromListDialog();
		
		//buttons and text values from XML
		listView = (ListView) view.findViewById(R.id.listView1);
		addButton = (Button) view.findViewById(R.id.inputPrompt);
		deleteAllButton = (Button) view.findViewById(R.id.deleteAll);
		sortButton = (Button) view.findViewById(R.id.sortList);
		selectStoreButton = (Button) view.findViewById(R.id.selectStore);
		findStoreButton = (Button) view.findViewById(R.id.findStore);
		
//		for testing purposes to add random test entries
		/**dbHelper.insertItem(new ItemObject("Number 3", "0012345678987", "TEST_PRODUCT"));
		dbHelper.insertItem(new ItemObject("Number 1", "0012345678987", "TEST_PRODUCT"));
		dbHelper.insertItem(new ItemObject("Number 2", "0012345678987", "TEST_PRODUCT"));*/
				
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				ItemObject item = (ItemObject) listView.getItemAtPosition(position);
				del.itemDelete = item;
				del.listItems = items;
				del.listAdapter = customAdapter;
				del.show(getFragmentManager(), TAG);
				return true;
			}
		});
		
		new Handler().post(new Runnable() 
		{
			@Override
			public void run()
			{
				items = dbHelper.getAllData();
				customAdapter = new GroceryListAdapter(context, items);
				listView.setAdapter(customAdapter);
			}
		});
		
		findStoreButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//TODO find cheapest store logic
			}	
		});
		
		addButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
				transaction.replace(R.id.content_frame, enterItem);
				transaction.addToBackStack("enterItem");
				transaction.commit();
			}	
		});
		
		sortButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				customAdapter.sortItems("Name", items);
				customAdapter.refresh(items);
			}	
		});
		
		selectStoreButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//TODO logic for selecting store
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
				transaction.replace(R.id.content_frame, prefs);
				transaction.addToBackStack("enterItem");
				transaction.commit();
			}
		});
		
		deleteAllButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				dbHelper.deleteAllData();
				items.clear();
				items = dbHelper.getAllData();
				customAdapter.refresh(items);
				Toast.makeText(context, "All Items Deleted", Toast.LENGTH_LONG).show();
			}	
		});
		
		getActivity().getActionBar().setTitle("Grocery List");
		
		return view;
	}
	
	@Override
	public View getHomeAddon(ViewGroup container)
	{
		return null;
	}

	@Override
	public int getIcon()
	{
		return R.drawable.list_icon;
	}
}

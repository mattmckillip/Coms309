package com.example.thatshoppingapp.search;

import java.util.ArrayList;

import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.MainActivity;
import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.ShoppingAppPage;
import com.example.thatshoppingapp.cart.CartActivity;
import com.example.thatshoppingapp.home.HomeAddon;
import com.example.thatshoppingapp.list.ListActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;

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

public class SearchFragment extends ShoppingAppPage implements HomeAddon {
	protected EditText searchText;
	protected SQLiteDatabase db;
	protected Cursor cursor;
	protected ListAdapter adapter;
	protected ListView itemList;
	protected TextView resultsFor;
	protected Context context;
	protected ImageButton searchButton;
	/**protected Button toCart;
	protected Button toList;*/
	protected RemoteDatabaseHelper2 sitem;
	static final String NAME = "Search";
	
	private String preferredStore;
	
	public SearchFragment() 
	{
		super(NAME, NAME, R.layout.activity_search);
	}
	
	/**
	 * Called when the activity is first created to instantiate variables
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		View view = super.onCreateView(inflater, container, savedInstanceState);
		super.onCreate(savedInstanceState);
		context = view.getContext();
		
		searchText = (EditText) view.findViewById(R.id.searchText);
		searchText.requestFocus();
		final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		
		searchButton = (ImageButton)view.findViewById(R.id.searchButton);
		itemList = (ListView) view.findViewById (R.id.search_results_list);
		resultsFor = (TextView) view.findViewById(R.id.results_for);
		
		sitem = new RemoteDatabaseHelper2(context);
		/**toCart = (Button)view.findViewById(R.id.gotoCart);
		toList = (Button)view.findViewById(R.id.gotoList);*/
		//final InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		//set the action bar title
		getActivity().getActionBar().setTitle("Search Item");
		preferredStore = getPreferredStore();
		
		
		
		searchText.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		          executeSearch(imm);
		          return true;
		        }
		        return false;
		    }
		});


		/**
		 * This method is called by the onClick attribute of the search button
		 * Once it is called, the method gets the text in the search box,
		 * calls the queryDB method. Then passes the information to the custom
		 * list adapter.
		 * 
		 * @param v search term text view
		 */
		
		searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				/**hide the keyboard after user hits search button
				imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
				
				String term = searchText.getText().toString();
				ArrayList<ItemObject> result_arr = sitem.queryDB_Keyword(term);
				if (result_arr == null){
					result_arr = new ArrayList<ItemObject>();
				}
				
				resultsFor.setText("Showing results for " + term);
				SearchListAdapter listA = new SearchListAdapter(context, result_arr);
				itemList.setAdapter(listA);*/
				executeSearch(imm);
			}
		});
		
		/**toCart.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{	
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
				transaction.replace(R.id.content_frame, (Fragment) new CartActivity());
				transaction.addToBackStack(null);
				transaction.commit();
			}	
		});
		
		toList.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
				transaction.replace(R.id.content_frame, (Fragment) new ListActivity());
				transaction.addToBackStack(null);
				transaction.commit();
			}	
		});*/
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
		return R.drawable.search_icon;
	}
	
	private void executeSearch(InputMethodManager imm){
		//hide the keyboard after user hits search button
		imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
		
		String term = searchText.getText().toString();
		ArrayList<ItemObject> result_arr = sitem.queryDB_Keyword(term);
		if (result_arr == null){
			result_arr = new ArrayList<ItemObject>();
		}
		
		resultsFor.setText("Showing results for " + term);
		SearchListAdapter listA = new SearchListAdapter(context, result_arr, preferredStore);
		itemList.setAdapter(listA);
	}
	

}
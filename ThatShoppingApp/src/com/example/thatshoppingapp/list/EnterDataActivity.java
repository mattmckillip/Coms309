package com.example.thatshoppingapp.list;

import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.ShoppingAppPage;
import com.example.thatshoppingapp.scanner.ScanActivity;
import com.example.thatshoppingapp.search.SearchFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EnterDataActivity extends ShoppingAppPage
{

	EditText editTextItemName;

	protected Context context;
	protected View view;
	protected Button addButton;
	protected Button goToScan;
	protected Button goToSearch;
	public EditText itemNameText;
	protected ListActivity localList;
	protected ListDatabaseHelper localDB;

	public EnterDataActivity ()
	{
		super("enterItem", "Enter Item Information", R.layout.enter_item);
	}
	
	/**
	 * Called when the activity is first created.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = super.onCreateView(inflater, container, savedInstanceState);
		super.onCreate(savedInstanceState);
		
		context = view.getContext();		
		//itemNameText = (EditText) view.findViewById(R.id.item_name);
		//addButton = (Button) view.findViewById(R.id.addItem);
		goToScan = (Button) view.findViewById(R.id.scanItem);
		goToSearch = (Button) view.findViewById(R.id.searchItem);
		localList = new ListActivity();
		localDB = new ListDatabaseHelper(context);
		
	//	final InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		
		
		//adding an item by putting in just item name is disablled for now
//		addButton.setOnClickListener(new OnClickListener()
//		{
//			public void onClick(View v)
//			{
//				String item = getItemName();
//				if(item.length() != 0 || item != "" || !(item.equals(null)))
//				{
//					localDB.insertData(item);
//				}
//				
//				imm.hideSoftInputFromWindow(itemNameText.getWindowToken(), 0);
//				
//				FragmentTransaction transaction = getFragmentManager().beginTransaction();
//				transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
//				transaction.replace(R.id.content_frame, localList);
//				transaction.addToBackStack("localList");
//				transaction.commit();
//				
//				//getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, localList).commit();
//			}	
//		});
		
		goToScan.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
				transaction.replace(R.id.content_frame, (Fragment) new ScanActivity());
				transaction.addToBackStack(null);
				transaction.commit();
				
				//getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new ScanActivity()).commit();
			}	
		});
		
		goToSearch.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
				transaction.replace(R.id.content_frame, (Fragment) new SearchFragment());
				transaction.addToBackStack(null);
				transaction.commit();
				
				//getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new ScanActivity()).commit();
			}	
		});
		
		return view;
	}
	
//	public String getItemName()
//	{
//		itemNameText = (EditText) view.findViewById(R.id.item_name);
//		String itemName = itemNameText.getText().toString();
//		return itemName;
//	}
}

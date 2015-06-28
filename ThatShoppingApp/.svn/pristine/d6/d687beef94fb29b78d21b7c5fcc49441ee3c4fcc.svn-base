package com.example.thatshoppingapp.cart;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.ShoppingAppPage;
import com.example.thatshoppingapp.home.HomeAddon;
import com.example.thatshoppingapp.list.DeleteFromListDialog;
import com.example.thatshoppingapp.list.GroceryListAdapter;
import com.example.thatshoppingapp.list.ListDatabaseHelper;
import com.example.thatshoppingapp.scanner.QRGenerator;
import com.example.thatshoppingapp.scanner.ScanActivity;
import com.google.zxing.WriterException;

public class CartActivity extends ShoppingAppPage implements HomeAddon
{
	//LIST: adapter, database helper, array list of items and listview
	protected GroceryListAdapter listAdapter;
	protected ListDatabaseHelper listDB;
	protected ArrayList<ItemObject> listItems;
	protected ListView listView;
	
	//CART: adapter, database helper, array list of items and listview
	protected CartListAdapter cartAdapter;
	protected CartDatabaseHelper cartDB;
	protected ArrayList<ItemObject> cartItems;
	protected ListView cartView;
	
	protected TextView totalText;
	protected ImageView imageView;
	protected Context context;
	protected Button cartScanItem;
	protected Button checkoutButton;
	protected String prefStore = "HyVee";

	private static final String TAG = CartActivity.class.getSimpleName();
	
	public CartActivity()
	{
		super("cart", "Shopping Cart", R.layout.activity_cart);
	}
	
	/**
	 * Called when the activity is first created.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		super.onCreate(savedInstanceState);
		
		context = view.getContext();
		final DeleteFromListDialog listDelete = new DeleteFromListDialog();
		final DeleteFromCartDialog cartDelete = new DeleteFromCartDialog();
		listDB = new ListDatabaseHelper(context);
		cartDB = new CartDatabaseHelper(context);
		this.prefStore = getPreferredStore();
		
		//values from the XML
		cartScanItem = (Button) view.findViewById(R.id.scanItem);
		checkoutButton = (Button) view.findViewById(R.id.checkout);
		totalText = (TextView) view.findViewById(R.id.total);
		imageView = (ImageView) view.findViewById(R.id.qrcode);
		cartView = (ListView) view.findViewById(R.id.cartView1);
		listView = (ListView) view.findViewById(R.id.listView1);
				
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				ItemObject item = (ItemObject) listView.getItemAtPosition(position);
				listDelete.itemDelete = item;
				listDelete.listItems = listItems;
				listDelete.listAdapter = listAdapter;
				listDelete.show(getFragmentManager(), TAG);
				return true;
			}
		});
		
		cartView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				ItemObject item = (ItemObject) listView.getItemAtPosition(position);
				cartDelete.itemDelete = item;
				cartDelete.cartItems = cartItems;
				cartDelete.cartAdapter = cartAdapter;
				cartDelete.show(getFragmentManager(), TAG);
				return true;
			}
		});
		
		new Handler().post(new Runnable() 
		{
			@Override
			public void run()
			{
				listItems = listDB.getAllData();
				cartItems = cartDB.getAllData();
				listAdapter = new GroceryListAdapter(context, listItems);
				cartAdapter = new CartListAdapter(context, cartItems, prefStore);
				cartAdapter.prefStore = prefStore;
				cartView.setAdapter(cartAdapter);
				listView.setAdapter(listAdapter);
				
				//TODO get total price display here
				double total = 0.0;
				for(ItemObject item : cartItems)
				{
					total += Double.parseDouble(item.getPrice(prefStore));
				}
				totalText.setText("Cart Total: $" + String.valueOf(total));
			}
		});
		
		cartScanItem.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//proper way to go to another fragment
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
				transaction.replace(R.id.content_frame, (Fragment) new ScanActivity());
				transaction.addToBackStack(null);
				transaction.commit();
			}	
		});
		
		//On checkout, a QR Code containing a condensed version of the shopping cart's contents is displayed
		checkoutButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (cartDB.getAllData().size() != 0)
				{
					//All of the generation is done here. Should a loading symbol be added if it takes a while? --Carson
					QRGenerator gen = new QRGenerator(cartDB);
					Bitmap code = gen.getCode();
					imageView.setVisibility(View.VISIBLE);
					imageView.setBackgroundColor(Color.WHITE);
					//TODO Scale the bitmap to appropriate size
					imageView.setImageBitmap(code);
					
					//clear cart when checking out
					cartDB.deleteAllData();
					cartItems.clear();
					cartItems = cartDB.getAllData();
					cartAdapter.refresh(cartItems);
					Toast.makeText(context, "All Items Deleted", Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(context, "There are no items in the cart!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		imageView.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				imageView.setVisibility(View.GONE);
			}
		});
		
		getActivity().getActionBar().setTitle("Shopping Cart");
		
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
		return R.drawable.cart_icon;
	}
}

package com.example.thatshoppingapp;

import java.util.ArrayList;

import com.example.thatshoppingapp.coupon.CouponFragment;
import com.example.thatshoppingapp.home.HomeFragment;
import com.example.thatshoppingapp.cart.CartActivity;
import com.example.thatshoppingapp.list.ListActivity;
import com.example.thatshoppingapp.list.ListDatabaseHelper;
import com.example.thatshoppingapp.scanner.ScanActivity;
import com.example.thatshoppingapp.search.SearchFragment;
import com.example.thatshoppingapp.store_manager.DatabaseManagerFragment;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thatshoppingapp.R;


public class MainActivity extends FragmentActivity implements ListView.OnItemClickListener 
{
	ArrayList<ShoppingAppPage> pages = new ArrayList<ShoppingAppPage>();
	private ActionBarDrawerToggle mDrawerToggle;
	protected Context context;
	protected ListDatabaseHelper localDBhelper;
	private DrawerLayout mDrawerLayout;
	//private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
	
	//preferences stuff
	CheckBox prefStoreManagerCheckBox;
	public boolean isStoreManager;
	public String managerStore;
	public String preferredStore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
				
		//Populate collection of pages for drawer here.
		pages.add(new HomeFragment());
		pages.add(new ListActivity());
		pages.add(new CartActivity());
		pages.add(new ScanActivity());
		pages.add(new CouponFragment(this));
		pages.add(new SearchFragment());
		pages.add(new DatabaseManagerFragment());
		pages.add(new SetPreferenceActivity());
		

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, (Fragment) pages.get(0)).commit();
		
		
		//Sets home page as initial page.
		//this.getFragmentManager().beginTransaction().replace(R.id.content_frame, pages.get(0)).commit();
		
		//Populate list for menu.
		ArrayList<String> menuList = new ArrayList<String>();
		for(ShoppingAppPage f: pages)
		{
			if(!f.getPageName().isEmpty())
			{
				menuList.add(f.getPageName());

			}
		}

		//Populate drawer menu.
		ListView DrawerList = (ListView) findViewById(R.id.left_drawer);
		
		//TODO write custom adapter
		DrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuList));
		//DrawerList.setAdapter(new DrawerListAdapter(this, menuList, pages));
		DrawerList.setOnItemClickListener(this);

		//set title
		this.getActionBar().setTitle("Shopping Buddy");
		//set the color to material design
		this.getActionBar().setBackgroundDrawable(new ColorDrawable(0xffE53935));
	    

	    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	    // ActionBarDrawerToggle ties together the the proper interactions
	    // between the sliding drawer and the action bar app icon
	    mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.drawable.drawer_icon,R.string.add, R.string.add);
	    mDrawerLayout.setDrawerListener(mDrawerToggle);

	    //enable action bar buttons to be clicked
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
	}
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

	@SuppressWarnings("rawtypes")
	@Override
	public void onItemClick(AdapterView parent, View view, int position, long id)
	{
		//proper way to go to another fragment
		final FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, R.animator.left_out);
		transaction.replace(R.id.content_frame, (Fragment) pages.get(position));
		transaction.addToBackStack(null);
		transaction.commit();
	    
		//old fragment transaction keep just in case
		//FragmentManager fragmentManager = getFragmentManager();
	    //fragmentManager.beginTransaction()
	                        //.replace(R.id.content_frame, (Fragment) pages.get(position)).commit();
		
		//this forces the drawer to wait 200 ms before closing
		new Handler().postDelayed(new Runnable() {
			@Override
		    public void run() {
		    	((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawers();
		    }
		}, 200);
		
	}
	
	public ArrayList<ShoppingAppPage> getPages()
	{
		return pages;
	}
	
	/**
	 * 
	 * @param title	String to go in the title of the action bar
	 * 
	 * Allows fragments to change the title of the action bar
	 */
	public void setActionBarTitle(String title)
	{
		getActionBar().setTitle(title);
	}

}

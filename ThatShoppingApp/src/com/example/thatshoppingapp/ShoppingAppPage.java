package com.example.thatshoppingapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//TODO: Make abstract
public class ShoppingAppPage extends Fragment
{
	private String name;
	private String pageName;
	private int layout;
	private Context context;
	
	public ShoppingAppPage(String name, String pageName, int layout)
	{
		this.name = name;
		this.pageName = pageName;
		this.layout = layout;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        // Inflate the layout for this fragment
		View view = inflater.inflate(layout, container, false);
		context = view.getContext();
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new View.OnKeyListener() 
		{
		        @Override
		        public boolean onKey(View v, int keyCode, KeyEvent event) 
		        {
		            //Log.i(tag, "keyCode: " + keyCode);
		            if( keyCode == KeyEvent.KEYCODE_BACK ) 
		            {
		                    //Log.i(tag, "onKey Back listener is working!!!");
		                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		                
		                return true;
		            } 
		            else return false;
		        }
		});
        return inflater.inflate(layout, container, false);
    }
	
	public String getName()
	{
		return name;
	}
	
	public String getPageName()
	{
		return pageName;
	}
	
	//returns preferred store
	public String getPreferredStore(){
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return mySharedPreferences.getString("store_preference", "");
	}
}

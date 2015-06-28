package com.example.thatshoppingapp.home;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.thatshoppingapp.MainActivity;
import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.ShoppingAppPage;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class HomeFragment extends ShoppingAppPage
{
	private ExpandableListView listView;
	private ArrayList<ShoppingAppPage> addons;
	private HashMap<ShoppingAppPage,View> map;
	
	public HomeFragment()
	{
		super("home", "Home", R.layout.home_fragment);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		ArrayList<ShoppingAppPage> pages = ((MainActivity)getActivity()).getPages();
		addons = new ArrayList<ShoppingAppPage>();
		map = new HashMap<ShoppingAppPage,View>();
		
		for(ShoppingAppPage p: pages)
		{
			if(p instanceof HomeAddon)
			{
				addons.add(p);
				map.put(p, ((HomeAddon)p).getHomeAddon(listView));
			}
		}
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		listView = (ExpandableListView) view.findViewById(R.id.home_fragment);
		
		
		
		listView.setAdapter(new HomeExpandableListAdapter(getActivity(), addons, map));
		listView.setEmptyView(getActivity().getLayoutInflater().inflate(R.layout.activity_scan, listView, false));
		listView.setOnGroupClickListener(new OnGroupClickListener(){

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				// TODO Auto-generated method stub
				if(map.get(addons.get(groupPosition)) == null)
				{
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
					transaction.replace(R.id.content_frame, (Fragment) addons.get(groupPosition));
					transaction.addToBackStack(null);
					transaction.commit();
					
					//getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, addons.get(groupPosition)).commit();
					return true;
				}
				
				return false;
			}
			
		});
		
		
        return view;
    }
}

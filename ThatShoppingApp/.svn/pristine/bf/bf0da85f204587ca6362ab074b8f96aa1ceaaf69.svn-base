package com.example.thatshoppingapp.coupon;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.ShoppingAppPage;
import com.example.thatshoppingapp.coupon.CouponDB.SearchType;
import com.example.thatshoppingapp.home.HomeAddon;
import com.example.thatshoppingapp.list.ListDatabaseHelper;

public class CouponFragment extends ShoppingAppPage implements HomeAddon
{
	private List<Coupon> related;
	private CouponDB db;
	private ListDatabaseHelper ldh;
	private List<ItemObject> cachedList;
	
	public CouponFragment(Context context)
	{
		super("coupon", "Coupons", R.layout.coupon_fragment);
		related = new ArrayList<Coupon>();
		db = new CouponDB(context);
		
		cachedList = new ArrayList<ItemObject>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		ListView view = (ListView)super.onCreateView(inflater, container, savedInstanceState);
		
		if(ldh == null)
		{
			ldh = new ListDatabaseHelper(this.getActivity());
		}
		if(isListChanged())
		{
			updateRelated();
		}
		
		view.setAdapter(new CouponListAdapter(this.getActivity(), related, ldh));
		
		view.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				List<ItemObject> toBeAdded = related.get(position).getAssociatedItems();
				for(ItemObject i: cachedList)
				{
					for(int count = 0; count < toBeAdded.size(); count++)
					{
						if(toBeAdded.get(count).getCode().equals(i.getCode()))
						{
							toBeAdded.remove(count);
							count--;
						}
					}
				}
				
				if(toBeAdded.isEmpty())
				{
					return;
				}
				ArrayList<String> converted = new ArrayList<String>();
				for(ItemObject i: toBeAdded)
				{
					converted.add(i.getCode());
				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Add the following to your list?");
				
				builder.setAdapter(new ArrayAdapter<String>(builder.getContext(), R.layout.drawer_list_item, converted), new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						
					}
					
				});
				
				builder.setPositiveButton("Yes", new OnClickListener(){
					
					private List<ItemObject> items;
					private View view;
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						for(ItemObject i: items)
						{
							ldh.insertItem(i);
						}
						
						if(isListChanged())
						{
							updateRelated();
						}
						
						view.findViewById(R.id.coupon_in_cart).setVisibility(View.VISIBLE);
						view.postInvalidate();
					}
					
					public OnClickListener init(List<ItemObject> items, View view)
					{
						this.items = items;
						this.view = view;
						return this;
					}
					
				}.init(toBeAdded, view));
				
				builder.setNegativeButton("No", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						
					}
					
				});
				builder.create().show();
			}
			
		});
		
		getActivity().getActionBar().setTitle("Coupons");
		
		return view;
	}
	
	private void updateRelated()
	{
		related.clear();
		for(ItemObject i: cachedList)
		{
			List<Coupon> results = db.findCoupons(SearchType.Item, i.getCode());
			boolean found;
			for(Coupon c: results)
			{
				found = false;
				for(Coupon a: related)
				{
					if(a.getId() == c.getId())
					{
						found = true;
						break;
					}
				}
				if(!found)
				{
					related.add(c);
				}
			}
		}
	}
	
	private boolean isListChanged()
	{
		List<ItemObject> curList = ldh.getAllData();
		if(curList.size() == cachedList.size())
		{
			for(int count = 0; count < curList.size(); count++)
			{
				if(!curList.get(count).getCode().equals(cachedList.get(count).getCode()))
				{
					cachedList = curList;
					return true;
				}
			}
		}
		else
		{
			cachedList = curList;
			return true;
		}
		
		return false;
	}

	@Override
	public View getHomeAddon(ViewGroup container)
	{
		return null;
		// TODO Auto-generated method stub
//		ListView view = (ListView)context.getLayoutInflater().inflate(R.layout.coupon_fragment, container, false);
//		
//		CouponListAdapter adapter = new CouponListAdapter(context, applicable);
//		view.setAdapter(adapter);
//		return view;
	}

	@Override
	public int getIcon()
	{
		return R.drawable.coupon_icon;
	}

}

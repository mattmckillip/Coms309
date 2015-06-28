package com.example.thatshoppingapp.coupon;

import java.util.List;

import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.list.ListDatabaseHelper;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CouponListAdapter implements ListAdapter
{
	
	private Activity context;
	private List<Coupon> coupons;
	private ListDatabaseHelper listDB;
	
	public CouponListAdapter(Activity context, List<Coupon> coupons, ListDatabaseHelper listDB)
	{
		this.context = context;
		this.coupons = coupons;
		this.listDB = listDB;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return coupons.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return coupons.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		LinearLayout coupon = (LinearLayout)context.getLayoutInflater().inflate(R.layout.coupon, parent, false);
		TextView textView = (TextView)coupon.findViewById(R.id.coupon_title);
		textView.setText(coupons.get(position).getText());
		
		List<ItemObject> toBeAdded = coupons.get(position).getAssociatedItems();
		for(ItemObject i: listDB.getAllData())
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
			coupon.findViewById(R.id.coupon_in_cart).setVisibility(View.VISIBLE);
		}
		else
		{
			coupon.setOnClickListener(new OnClickListener(){
				
				private ViewGroup parent;
				private int position;
				private int id;
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					((ListView)parent).getOnItemClickListener().onItemClick(null, v, position, id);
				}
				
				public OnClickListener init(ViewGroup parent, int position, int id)
				{
					this.parent = parent;
					this.position = position;
					this.id = id;
					
					return this;
				}
				
			}.init(parent, position, 0));
		}
		
		return coupon;
	}

	@Override
	public int getItemViewType(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return coupons.isEmpty();
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(int position)
	{
		// TODO Auto-generated method stub
		return true;
	}
	
}

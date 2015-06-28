package com.example.thatshoppingapp.coupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.example.thatshoppingapp.ItemObject;
import com.example.thatshoppingapp.search.JSONParser;
import com.example.thatshoppingapp.search.RemoteDatabaseHelper2;

public class CouponDB
{
	public static enum SearchType{Store, Item};
	
	private static HashMap<String, ArrayList<Coupon>> storeToCoupon;
	
	private Context context;
	
	public CouponDB(Context context)
	{
		this.context = context;
		storeToCoupon = new HashMap<String, ArrayList<Coupon>>();
	}
	
	public List<Coupon> findCoupons(SearchType criteria, String keyword)
	{
		ArrayList<Coupon> results = new ArrayList<Coupon>();
		switch(criteria)
		{
			case Store:
				for(String s: storeToCoupon.keySet())
				{
					if (s.contains(keyword))
					{
						results = storeToCoupon.get(s);
					}
				}
				break;
			case Item:
				try
				{
					(new BackgroundSearch(results, keyword)).execute().get(1000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		}
		
		return results;
	}
	
	public List<Coupon> getAllCoupons()
	{
		ArrayList<Coupon> result = new ArrayList<Coupon>();
		for(ArrayList<Coupon> a: storeToCoupon.values())
		{
			result.addAll(a);
		}
		
		return result;
	}
	
	private class BackgroundSearch extends AsyncTask<String, String, List<Coupon>>
	{
		private List<Coupon> results;
		private String keyword;
		private RemoteDatabaseHelper2 db;
		
		public BackgroundSearch(List<Coupon> results, String keyword)
		{
			this.results = results;
			this.keyword = keyword;
			db = new RemoteDatabaseHelper2(context);
		}

		@Override
		public List<Coupon> doInBackground(String... parameters)
		{
			// TODO Auto-generated method stub
			JSONParser link = new JSONParser();
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("item",keyword));
			JSONObject queryResult = link.makeHttpRequest("http://proj-309-t15.cs.iastate.edu/testing/search_coupon.php", "POST", params);
			try
			{
				JSONArray coupons = queryResult.getJSONArray("coupons");
				System.out.println("# of coupons:" + coupons.length());
				for(int count = 0; count < coupons.length(); count++)
				{
					JSONObject coupon = coupons.getJSONObject(count);
					ArrayList<ItemObject> items = new ArrayList<ItemObject>();
					System.out.println(coupon.getString("coupon_assoc"));
					
					StringTokenizer assoc = new StringTokenizer(coupon.getString("coupon_assoc"),",");
					String temp;
					while(assoc.hasMoreTokens())
					{
						items.add(new ItemObject(assoc.nextToken()));
					}
					results.add(new Coupon(coupon.getInt("coupon_id"), coupon.getString("coupon_text"), items));
					System.out.println("Results size: "+results.size());
				}
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return results;
		}
	}
}

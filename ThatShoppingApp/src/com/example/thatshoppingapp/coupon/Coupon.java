package com.example.thatshoppingapp.coupon;

import java.util.List;

import com.example.thatshoppingapp.ItemObject;

public class Coupon
{
	private int id;
	private String text;
	private List<ItemObject> items;
	
	public Coupon(int id, String text, List<ItemObject> items)
	{
		this.id = id;
		this.text = text;
		this.items = items;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getText()
	{
		return text;
	}
	
	public List<ItemObject> getAssociatedItems()
	{
		return items;
	}
}

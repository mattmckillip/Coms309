package com.example.thatshoppingapp.home;

import java.util.List;
import java.util.Map;

import com.example.thatshoppingapp.R;
import com.example.thatshoppingapp.ShoppingAppPage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class HomeExpandableListAdapter extends BaseExpandableListAdapter
{
	private Activity context;
	private List<ShoppingAppPage> groups;
	private Map<ShoppingAppPage, View> children;
	
	public HomeExpandableListAdapter(Activity context, List<ShoppingAppPage> groups, Map<ShoppingAppPage, View> children)
	{
		this.context = context;
		this.groups = groups;
		this.children = children;
	}
	
	

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return (children.get(groups.get(groupPosition))==null)?0:1;
		//return children.get(groups.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return children.get(groups.get(groupPosition));
		//return children.get(groups.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View view = inflater.inflate(R.layout.home_category, parent, false);
		
		TextView text = (TextView) view.findViewById(R.id.home_category_name);
		text.setText(groups.get(groupPosition).getPageName());
		
		//ImageView icon = (ImageView) view.findViewById(R.id.home_category_icon);
		//icon.setImageResource(((HomeAddon)groups.get(groupPosition)).getIcon());
		//((ExpandableListView)view).get
		
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		View view = children.get(groups.get(groupPosition));
		if(view == null)
		{
			view = context.getLayoutInflater().inflate(R.layout.blank_layout, parent, false);
		}
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}

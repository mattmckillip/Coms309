package com.example.thatshoppingapp;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;

import com.example.thatshoppingapp.home.HomeAddon;

public class SetPreferenceActivity extends ShoppingAppPage implements HomeAddon {
	public static final String NAME = "Settings";
	public SetPreferenceActivity() {
		super(NAME, NAME, R.layout.activity_main);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null)
        {
			Fragment preferenceFragment = new Preferences();
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
			transaction.replace(R.id.content_frame, preferenceFragment);
			transaction.addToBackStack(null);
			transaction.commit();
        }
      }
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		Fragment preferenceFragment = new Preferences();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out);
		transaction.replace(R.id.content_frame, preferenceFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	@Override
	public View getHomeAddon(ViewGroup container) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIcon() {
		// TODO Auto-generated method stub
		return 0;
	}
}
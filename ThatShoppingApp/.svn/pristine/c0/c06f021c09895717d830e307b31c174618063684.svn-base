package com.example.thatshoppingapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.Toast;

public class Preferences extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
	}

}
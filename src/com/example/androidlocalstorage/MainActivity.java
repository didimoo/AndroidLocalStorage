package com.example.androidlocalstorage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) 
		{
			MainFragment myFragment = new MainFragment();
			
			if (findViewById(R.id.fragment_container) != null) 
			{
				android.support.v4.app.FragmentTransaction fTransition;
				fTransition = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment);
				fTransition.commit();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}

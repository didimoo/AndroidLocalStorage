package com.example.androidlocalstorage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class MainFragment extends Fragment {

	private WebView mWebview;
	private Button mButton;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container,false);
		
		mWebview = (WebView) view.findViewById(R.id.webView);
		mButton = (Button) view.findViewById(R.id.button);
		
		init();
		
		return view;
	}


	private void init() {
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),MainActivity.class);	
				getActivity().startActivity(intent);
				
			}
		});
		
		//load HTML File in webview
		mWebview.loadUrl("file:///android_asset/");
		
		//add the JavaScriptInterface so that JavaScript is able to use LocalStorageJavaScriptInterface's methods when calling "LocalStorage"
		mWebview.addJavascriptInterface(new LocalStorageJavaScriptInterface(getActivity().getApplicationContext()), "LocalStorage");
		
		WebSettings webSettings = mWebview.getSettings();
		//enable JavaScript in webview
		webSettings.setJavaScriptEnabled(true);

		//Enable and setup JS localStorage
		//webSettings.setDomStorageEnabled(true); 
		webSettings.setDatabaseEnabled(true);
		webSettings.setDatabasePath(getActivity().getFilesDir().getParentFile().getPath()+"/databases/");
		
	}
	
	
	/**
	 * This class is used as a substitution of the local storage in Android webviews
	 * 
	 * @author Diane
	 */
	private class LocalStorageJavaScriptInterface {
		private Context mContext;
		private LocalStorage localStorageDBHelper;
		private SQLiteDatabase database;

		LocalStorageJavaScriptInterface(Context c) {
			mContext = c;
			localStorageDBHelper = LocalStorage.getInstance(mContext);
		}

		/**
		 * This method allows to get an item for the given key
		 * @param key : the key to look for in the local storage
		 * @return the item having the given key
		 */
		@JavascriptInterface
		public String getItem(String key)
		{
			String value = null;
			if(key != null)
			{
				database = localStorageDBHelper.getReadableDatabase();
				Cursor cursor = database.query(LocalStorage.LOCALSTORAGE_TABLE_NAME,
						null, 
						LocalStorage.LOCALSTORAGE_ID + " = ?", 
						new String [] {key},null, null, null);
				if(cursor.moveToFirst())
				{
					value = cursor.getString(1);
				}
				cursor.close();
				database.close();
			}
			return value;
		}

		/**
		 * set the value for the given key, or create the set of datas if the key does not exist already.
		 * @param key
		 * @param value
		 */
		@JavascriptInterface
		public void setItem(String key,String value)
		{
			if(key != null && value != null)
			{
				String oldValue = getItem(key);
				database = localStorageDBHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(LocalStorage.LOCALSTORAGE_ID, key);
				values.put(LocalStorage.LOCALSTORAGE_VALUE, value);
				if(oldValue != null)
				{
					database.update(LocalStorage.LOCALSTORAGE_TABLE_NAME, values, LocalStorage.LOCALSTORAGE_ID + " = " + key, null);
				}
				else
				{
					database.insert(LocalStorage.LOCALSTORAGE_TABLE_NAME, null, values);
				}
				database.close();
			}
		}

		/**
		 * removes the item corresponding to the given key
		 * @param key
		 */
		@JavascriptInterface
		public void removeItem(String key)
		{
			if(key != null)
			{
				database = localStorageDBHelper.getWritableDatabase();
				database.delete(LocalStorage.LOCALSTORAGE_TABLE_NAME, LocalStorage.LOCALSTORAGE_ID + " = " + key, null);
				database.close();
			}
		}

		/**
		 * clears all the local storage.
		 */
		@JavascriptInterface
		public void clear()
		{
			database = localStorageDBHelper.getWritableDatabase();
			database.delete(LocalStorage.LOCALSTORAGE_TABLE_NAME, null, null);
			database.close();
		}
	}
}

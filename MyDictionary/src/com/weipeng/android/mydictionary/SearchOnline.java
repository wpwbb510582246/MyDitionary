package com.weipeng.android.mydictionary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class SearchOnline extends Activity {
	
	
	private EditText etWebSite;
	
	private Button btnSearchWebSite;
	
	private WebView wvWebSiteInfo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_client);
		
		
		etWebSite=(EditText) findViewById(R.id.actWebsite);
		
		
		btnSearchWebSite.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		
		
	}
	

}

package com.weipeng.android.mydictionary;

import com.weipeng.android.mydictionary.webclient.WebClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchWay extends Activity implements OnClickListener {
	
	
	private Button btnSearchLocally;
	private Button btnSearchOnline;
	private Button btnAbout;
	
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_way);
		
		
		btnSearchLocally=(Button) findViewById(R.id.btnSearchLocally);
		btnSearchOnline=(Button) findViewById(R.id.btnSearchOnline);
		btnAbout=(Button) findViewById(R.id.btnAbout);
		
		
		btnSearchLocally.setOnClickListener(this);
		btnSearchOnline.setOnClickListener(this);
		btnAbout.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSearchLocally:
			intent=new Intent(SearchWay.this, MainActivity.class);
			startActivity(intent);
			break;

		case R.id.btnSearchOnline:
			intent=new Intent(SearchWay.this, WebClient.class);
			startActivity(intent);
			break;
		case R.id.btnAbout:
			intent=new Intent(SearchWay.this, About.class);
			startActivity(intent);
			break;
		}
	}
	
}

package com.weipeng.android.mydictionary.webclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.weipeng.android.mydictionary.Login;
import com.weipeng.android.mydictionary.R;

public class WebClient extends Activity implements OnClickListener, TextWatcher {



	private ImageView imgHome;
	private ImageView imgSearch;

	private AutoCompleteTextView actWebsite;

	private WebView wvWebInfo;

	private WebViewClientDemo webViewClientDemo;

	private String website;
	private static final String HOME_URL="http://fanyi.youdao.com/";
	private static final String HYPERTEXT_TRANSFER_PROTECOL="http://";
	private static final String WORLD_WIDE_WEB="www.";
	private static final String BAIDU_SEARCH="http://www.baidu.com.cn/s?wd=";
	private static final String URL_ENCODING="GB2312";
	private String currentWebsite;
	private String currentWebsiteTitle;
	private String select_sql_1;
	private String select_sql_2;
	private String insert_sql;

	private SQLiteDatabase database;

	private Cursor cursor;

	private WebsiteHistoryAdapter websiteHistoryAdapter;

	private WebChromeClient webChromeClient;

	private ProgressBar progressBar;

	private int checkWebViewGoBack=0;
	private int checkWebViewTouch=0;

	private InputMethodManager imm;




	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_client);


		database=new Login().openDatabase();


		imgHome=(ImageView) findViewById(R.id.imgHome);
		imgSearch=(ImageView) findViewById(R.id.imgSearch);
		actWebsite=(AutoCompleteTextView) findViewById(R.id.actWebsite);
		wvWebInfo=(WebView) findViewById(R.id.wvWebInfo);
		progressBar=(ProgressBar) findViewById(R.id.pbProgress);
		System.out.println("已执行：findViewById");


		wvWebInfo.getSettings().setJavaScriptEnabled(true);
		webViewClientDemo=new WebViewClientDemo(progressBar);
		wvWebInfo.setWebViewClient(webViewClientDemo);
		webChromeClient=new WebChromeClient(){
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				actWebsite.setText(title);
				currentWebsiteTitle=title;
				System.out.println("网址标题为："+title);
			}


			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				progressBar.setProgress(newProgress);
				System.out.println("已执行：onProgressChanged");
			}


		};
		wvWebInfo.setWebChromeClient(webChromeClient);
		wvWebInfo.loadUrl(HOME_URL);
		System.out.println("已执行：wvWebInfo.loadUrl(HOME_URL)");


		imgHome.setOnClickListener(this);
		imgSearch.setOnClickListener(this);
//		actWebsite.setOnClickListener(this);
//		System.out.println("已执行：setOnClickListener");
		
		
		actWebsite.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!actWebsite.getText().toString().equals(currentWebsite) && checkWebViewTouch==0){
					actWebsite.setText(currentWebsite);
					checkWebViewTouch=checkWebViewTouch+1;
					return true;
				}
				else {
					return false;
				}
			}
		});
		
		actWebsite.addTextChangedListener(this);
		System.out.println("已执行：addTextChangedListener");
		

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgHome:
			wvWebInfo.loadUrl(HOME_URL);
			break;

		case R.id.imgSearch:
			website=actWebsite.getText().toString().trim();
			select_sql_1="select website_history from website where website_history=?";
			cursor=database.rawQuery(select_sql_1, new String[]{website});
			System.out.println("已执行："+select_sql_1);
			if(cursor.getCount()==0){
				insert_sql="insert into website(website_history) values(?)";
				database.execSQL(insert_sql, new String[]{website});
				System.out.println("已执行："+insert_sql);
			}
			if(website.equals("")){
				Toast.makeText(WebClient.this, getResources().getText(R.string.search_error), Toast.LENGTH_SHORT).show();
			}
			else {
				if(website.startsWith(HYPERTEXT_TRANSFER_PROTECOL)){
					wvWebInfo.loadUrl(website);
				}
				else {
					if(website.startsWith(WORLD_WIDE_WEB)){
						wvWebInfo.loadUrl(HYPERTEXT_TRANSFER_PROTECOL+website);
					}
					else {
						try {
							wvWebInfo.loadUrl(BAIDU_SEARCH+URLEncoder.encode(website, URL_ENCODING));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
			}
			break;
//		case R.id.actWebsite:
//			actWebsite.setText(currentWebsite);
//			break;
		}


	}


	public class WebViewClientDemo extends WebViewClient {



		private ProgressBar progressBar;


		public WebViewClientDemo(ProgressBar progressBar) {
			super();
			this.progressBar=progressBar;
		}



		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			System.out.println("已执行：loadUrl");
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			actWebsite.setText(url);
			currentWebsite=url;
			System.out.println("已执行：setText");
			progressBar.setVisibility(View.VISIBLE);
			System.out.println("已执行：progressBar.setVisibility(View.VISIBLE)");
			checkWebViewTouch=0;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			progressBar.setVisibility(View.GONE);
			System.out.println("已执行：progressBar.setVisibility(View.GONE)");
		}
	}




	@Override
	public void afterTextChanged(Editable s) {
		System.out.println("准备执行：afterTextChanged");
		select_sql_2="select website_history as _id from website where website_history like ? and website_history!=?";
		//		select_sql_2="select website_history as _id from website where website_history like ?";
		//		cursor=database.rawQuery(select_sql_2, new String[]{actWebsite.getText().toString()+"%"});
		cursor=database.rawQuery(select_sql_2, new String[]{actWebsite.getText().toString()+"%",actWebsite.getText().toString()});
		System.out.println("cursor.getCount()："+cursor.getCount());
		websiteHistoryAdapter=new WebsiteHistoryAdapter(this, cursor, true);
		actWebsite.setAdapter(websiteHistoryAdapter);
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}




	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("已执行：goBack");
		System.out.println("wvWebInfo.canGoBack()："+wvWebInfo.canGoBack());
		if(keyCode==KeyEvent.KEYCODE_BACK){
			wvWebInfo.goBack();
			if(wvWebInfo.canGoBack()){
				checkWebViewGoBack=0;
			}
			if(!wvWebInfo.canGoBack()){
				checkWebViewGoBack=checkWebViewGoBack+1;
				if(checkWebViewGoBack==1){
					Toast.makeText(WebClient.this, getResources().getText(R.string.finish_activity_after_go_back), Toast.LENGTH_SHORT).show();
				}
				if(checkWebViewGoBack==2){
					WebClient.this.finish();
					checkWebViewGoBack=0;
				}
				return true;
			}
			return true;
		}

		else {
			return false;
		}

	}

}

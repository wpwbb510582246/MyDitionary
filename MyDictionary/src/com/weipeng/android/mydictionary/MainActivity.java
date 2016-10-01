package com.weipeng.android.mydictionary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements TextWatcher, OnClickListener {

	
	
	private AutoCompleteTextView actInputWords;
	
	private Button btnSearch;
	
	private TextView tvSearchResult;
	
	private static final String NOT_FOUND_FOR_SEARCH="本地词库中未找到单词：";
	private String select_sql_1;
	private String select_sql_2;
	private String searchResult;
	
	private Cursor cursor;
	
	private SQLiteDatabase database;
	
	private DictionaryAdapter dictionaryAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		actInputWords=(AutoCompleteTextView) findViewById(R.id.actInputWords);
		btnSearch=(Button) findViewById(R.id.btnSearch);
		tvSearchResult=(TextView) findViewById(R.id.tvSearchResult);
		
		
		database=new Login().openDatabase();
		
		
		actInputWords.addTextChangedListener(this);
		btnSearch.setOnClickListener(this);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void afterTextChanged(Editable s) {
		
		
		select_sql_1="select english as _id from t_words where english like ?";
		cursor=database.rawQuery(select_sql_1, new String[]{s.toString()+"%"});
		dictionaryAdapter=new DictionaryAdapter(this, cursor, true);
		actInputWords.setAdapter(dictionaryAdapter);
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@Override
	public void onClick(View v) {
		
		
		select_sql_2="select chinese from t_words where english like ?";
		cursor=database.rawQuery(select_sql_2, new String[]{actInputWords.getText().toString()});
		if (cursor.getCount()>0) {
			cursor.moveToFirst();
			searchResult=actInputWords.getText().toString()+"\n"+cursor.getString(cursor.getColumnIndex("chinese"));
			tvSearchResult.setText(searchResult);
		}
		else {
			tvSearchResult.setText(NOT_FOUND_FOR_SEARCH+actInputWords.getText().toString());
		}
		
	}
	
	
	
	

}

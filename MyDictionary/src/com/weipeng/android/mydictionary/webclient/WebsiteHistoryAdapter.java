package com.weipeng.android.mydictionary.webclient;

import com.weipeng.android.mydictionary.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WebsiteHistoryAdapter extends CursorAdapter {
	
	
	private LayoutInflater layoutInflater;
	
	private TextView tvWebsiteHistory;
	
	private View view;
	

	public WebsiteHistoryAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	
	@Override
	public CharSequence convertToString(Cursor cursor) {
		return cursor==null ? "" :cursor.getString(cursor.getColumnIndex("_id"));
	}
	
	
	public void setView(View view,Cursor cursor) {
		tvWebsiteHistory=(TextView) view;
		tvWebsiteHistory.setText(cursor.getString(cursor.getColumnIndex("_id")));
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		setView(arg0, arg2);
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		view=layoutInflater.inflate(R.layout.website_history_list_item, null);
		setView(view, arg1);
		return view;
	}

}

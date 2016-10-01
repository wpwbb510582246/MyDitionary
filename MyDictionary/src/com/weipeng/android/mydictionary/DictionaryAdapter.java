package com.weipeng.android.mydictionary;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DictionaryAdapter extends CursorAdapter {

	private Cursor cursor;

	private TextView tvWordItem;

	private View view;

	private LayoutInflater layoutInflater;


	public DictionaryAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}

	public CharSequence convertToString(Cursor cursor){
		return cursor==null ? "" : cursor.getString(cursor.getColumnIndex("_id"));
	}

	private void setView(View view , Cursor cursor) {


		tvWordItem=(TextView) view;
		tvWordItem.setText(cursor.getString(cursor.getColumnIndex("_id")));

	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {


		setView(arg0, arg2);

	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {


		view=layoutInflater.inflate(R.layout.word_list_item, null);
		setView(view, arg1);

		return view;
	}

}

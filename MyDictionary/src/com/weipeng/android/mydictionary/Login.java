package com.weipeng.android.mydictionary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {



	private EditText etLoginName;
	private EditText etLoginCode;

	private Button btnLogin;
	private Button btnRegister;

	private SQLiteDatabase database;

	private Cursor cursor;

	private String select_sql_1;
	private String loginNameInput;
	private String loginCodeInput;
	private String loginNameDatabase;
	private String loginCodeDatabase;
	private String BUNDLE_LOGIN_NAME="bundleLoginName";
	private String BUNDLE_LOGIN_CODE="bundleLoginCode";
	private final String DATABASE_FILENAME = "dictionary.db";
	private final String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+ "/dictionary";

	private Intent intent;

	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);


		database=openDatabase();


		etLoginName=(EditText) findViewById(R.id.etLoginName);
		etLoginCode=(EditText) findViewById(R.id.etLoginCode);
		btnLogin=(Button) findViewById(R.id.btnLogin);
		btnRegister=(Button) findViewById(R.id.btnRegister);
		System.out.println("已执行：findViewById");


		System.out.println("已执行：openDatabase");


		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);



	}

	@Override
	public void onClick(View v) {


		switch (v.getId()) {
		case R.id.btnLogin:
			System.out.println("准备执行：btnLogin.setOnClickListener");
			select_sql_1="select user_name,user_password from user where user_name=?";
			System.out.println("准备执行：select_sql_1");
			cursor=database.rawQuery(select_sql_1, new String[]{etLoginName.getText().toString()});
			System.out.println("输入的用户名为："+etLoginName.getText().toString());
			System.out.println("已执行：select_sql_1");
			loginNameInput=etLoginName.getText().toString().trim();
			loginCodeInput=etLoginCode.getText().toString().trim();
			System.out.println("cursor.getCount()："+cursor.getCount());
			if(cursor.getCount()>0){
				cursor.moveToFirst();
				System.out.println("cursor.getColumnIndex："+cursor.getColumnIndex("user_name"));
				System.out.println("cursor.getString："+cursor.getString(1));
				loginNameDatabase=cursor.getString(cursor.getColumnIndex("user_name")).trim();
				loginCodeDatabase=cursor.getString(cursor.getColumnIndex("user_password")).trim();
				System.out.println("loginNameInput："+loginNameInput+" "+"loginNameDatabase："+loginNameDatabase);
				System.out.println("loginCodeInput："+loginCodeInput+" "+"loginCodeDatabase："+loginCodeDatabase);
				if(loginNameInput.equals(loginNameDatabase) && loginCodeInput.equals(loginCodeDatabase)){
					intent=new Intent(Login.this, SearchWay.class);
					startActivity(intent);
					System.out.println("输入的用户名和密码匹配！");
				}
				else {
					while(cursor.moveToNext()==true){
						loginNameDatabase=cursor.getString(cursor.getColumnIndex("user_name")).trim();
						loginCodeDatabase=cursor.getString(cursor.getColumnIndex("user_password")).trim();
						System.out.println("loginNameInput："+loginNameInput+" "+"loginNameDatabase："+loginNameDatabase);
						System.out.println("loginCodeInput："+loginCodeInput+" "+"loginCodeDatabase："+loginCodeDatabase);
						if(loginNameInput.equals(loginNameDatabase) && loginCodeInput.equals(loginCodeDatabase)){
							intent=new Intent(Login.this, SearchWay.class);
							startActivity(intent);
							System.out.println("输入的用户名和密码匹配！");
							break;
						}
					}
					Toast.makeText(Login.this, R.string.toast_1, Toast.LENGTH_SHORT).show();
					System.out.println("输入的用户名和密码不匹配！");
				}
			}
			else {
				Toast.makeText(Login.this, R.string.toast_1, Toast.LENGTH_SHORT).show();
				System.out.println("输入的用户名和密码不匹配！");
			}
			break;
		case R.id.btnRegister:
			intent=new Intent(Login.this, Register.class);
			bundle=new Bundle();
			loginNameInput=etLoginName.getText().toString().trim();
			loginCodeInput=etLoginCode.getText().toString().trim();
			bundle.putString(BUNDLE_LOGIN_NAME, loginNameInput);
			bundle.putString(BUNDLE_LOGIN_CODE, loginCodeInput);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
			break;
		}

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		switch (resultCode) {
		case RESULT_OK:
			bundle=data.getExtras();
			loginNameInput=bundle.getString(BUNDLE_LOGIN_NAME);
			loginCodeInput=bundle.getString(BUNDLE_LOGIN_CODE);
			etLoginName.setText(loginNameInput);
			etLoginCode.setText(loginCodeInput);
			break;
		}

	}


	public SQLiteDatabase openDatabase()
	{
		try
		{
			// 获得dictionary.db文件的绝对路径
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			// 如果/sdcard/dictionary目录中不存在，创建这个目录
			if (!dir.exists())
				dir.mkdir();
			// 如果在/sdcard/dictionary目录中不存在
			// dictionary.db文件，则从res\raw目录中复制这个文件到
			// SD卡的目录（/sdcard/dictionary）
			if (!(new File(databaseFilename)).exists())
			{
				// 获得封装dictionary.db文件的InputStream对象
				InputStream is = getResources().openRawResource(
						R.raw.dictionary);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				// 开始复制dictionary.db文件
				while ((count = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, count);
				}
				//关闭文件流
				fos.close();
				is.close();
			}
			// 打开/sdcard/dictionary目录中的dictionary.db文件
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
					databaseFilename, null);
			return database;
		}
		catch (Exception e)
		{
		}
		//如果打开出错，则返回null
		return null;
	}


}

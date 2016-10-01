package com.weipeng.android.mydictionary;

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

public class Register extends Activity implements OnClickListener {



	private EditText etRegisterName;
	private EditText etRegisterCode;

	private Button btnRegister;
	private Button btnBack;

	private Intent intent;

	private Bundle bundle_activity_start;
	private Bundle bundle_activity_finish;

	private String BUNDEL_LOGIN_NAME="bundleLoginName";
	private String BUNDE_LOGIN_CODE="bundleLoginCode";
	private String loginNameInput;
	private String loginCodeInput;
	private String registerName;
	private String registerCode;
	private String insert_sql;
	private String select_sql;

	private SQLiteDatabase database;

	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);


		database=new Login().openDatabase();


		etRegisterName=(EditText) findViewById(R.id.etRegiserName);
		etRegisterCode=(EditText) findViewById(R.id.etRegisterCode);
		btnRegister=(Button) findViewById(R.id.btnRegister);
		btnBack=(Button) findViewById(R.id.btnBack);
		System.out.println("已执行：findViewById");

		bundle_activity_start=this.getIntent().getExtras();
		System.out.println("已执行：getExtras");
		etRegisterName.setText(bundle_activity_start.getString(BUNDEL_LOGIN_NAME));
		etRegisterCode.setText(bundle_activity_start.getString(BUNDE_LOGIN_CODE));
		System.out.println("已执行：setText");



		btnRegister.setOnClickListener(this);
		btnBack.setOnClickListener(this);


	}

	@Override
	public void onClick(View arg0) {


		switch (arg0.getId()) {
		case R.id.btnRegister:
			registerName=etRegisterName.getText().toString().trim();
			registerCode=etRegisterCode.getText().toString().trim();
			select_sql="select user_name from user where user_name=?";
			cursor=database.rawQuery(select_sql, new String[]{registerName});
			if(cursor.getCount()!=0){
				Toast.makeText(Register.this, getResources().getText(R.string.user_already_exits), Toast.LENGTH_SHORT).show();
			}
			else {
				insert_sql="insert into user(user_name,user_password) values(?,?)";
				database.execSQL(insert_sql, new String[]{registerName,registerCode});
				Toast.makeText(Register.this, R.string.register_succeed, Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.btnBack:
			bundle_activity_finish=new Bundle();
			Register.this.setResult(RESULT_OK, Register.this.getIntent());
			loginNameInput=etRegisterName.getText().toString();
			loginCodeInput=etRegisterCode.getText().toString();
			bundle_activity_finish.putString(BUNDEL_LOGIN_NAME, loginNameInput);
			bundle_activity_finish.putString(BUNDE_LOGIN_CODE, loginCodeInput);
			Register.this.getIntent().putExtras(bundle_activity_finish);
			Register.this.finish();
			break;
		}

	}

}

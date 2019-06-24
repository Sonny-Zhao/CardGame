package com.tianyu.cardgame.activity;

/**
 * All rights Reserved, Designed By Vongvia
 * @Title:  程序启动的欢迎页
 * @author:	Vongvia  欢迎各位童鞋来交流 ：441256563
 * @date:	2015.11.17
 * @version	V1.0
 */

import com.tianyu.cardgame.R;
import com.tianyu.cardgame.activity.Guide;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class welcome extends Activity
{

	private boolean isFirst = false;
	private static final int TIME = 2000;
	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;

	private Handler mHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case GO_HOME:
					goHome();
					break;

				case GO_GUIDE:
					goGuide();
					break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		init();
	}

	private void init()
	{
		SharedPreferences preferences = getSharedPreferences("first", MODE_PRIVATE);
		isFirst = preferences.getBoolean("isFirst", true);
		if (!isFirst)
		{
			mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
		} else
		{
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
			Editor editor = preferences.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
		}
	}

	private void goHome()
	{
		Intent intent = new Intent(welcome.this, MainActivity.class);
		startActivity(intent);
		finish();

	}

	private void goGuide()
	{
		Intent intent = new Intent(welcome.this, Guide.class);
		startActivity(intent);
		finish();

	}
}

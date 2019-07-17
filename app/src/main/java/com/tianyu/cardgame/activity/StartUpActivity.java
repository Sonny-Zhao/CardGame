package com.tianyu.cardgame.activity;

import com.tianyu.cardgame.R;
import com.tianyu.cardgame.constants.Constants;
import com.tianyu.cardgame.utils.MMKVUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

/**
 * @Synopsis: StartUpActivity
 * @Author: 启动欢迎页
 * @Date: 2019-07-17 11:22
 */
public class StartUpActivity extends AppCompatActivity {
    private static final int TIME = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        init();
    }

    private void init() {
        if (!MMKVUtils.getBoolean(Constants.MMKVKey.START_UP_IS_FIRST, true)) {
            mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
            MMKVUtils.putBoolean(Constants.MMKVKey.START_UP_IS_FIRST, false);
        }
    }

    private void goHome() {
        Intent intent = new Intent(StartUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void goGuide() {
        Intent intent = new Intent(StartUpActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }
}

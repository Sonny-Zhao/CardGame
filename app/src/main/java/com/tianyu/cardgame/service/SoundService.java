package com.tianyu.cardgame.service;
/**
 * All rights Reserved, Designed By Vongvia
 * @Title:  背景音乐的service
 * @author:	Vongvia  欢迎各位童鞋来交流 ：441256563
 * @date:	2015.11.17
 * @version	V1.0
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.tianyu.cardgame.R;

public class SoundService extends Service {
    private MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.playing);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();

        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean playing = intent.getBooleanExtra("playing", false);
        if (playing) {
            mp.start();
        } else {
            onDestroy();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
 
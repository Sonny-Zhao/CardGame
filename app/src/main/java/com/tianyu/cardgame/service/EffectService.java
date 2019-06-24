/**
 * All rights Reserved, Designed By Vongvia
 * @Title:  音效Service
 * @author:	Vongvia  欢迎各位童鞋来交流 ：441256563
 * @date:	2015.11.17
 * @version	V1.0
 */
package com.tianyu.cardgame.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.tianyu.cardgame.R;

public class EffectService extends Service {

    private MediaPlayer win;
    private MediaPlayer lose;
    private MediaPlayer ef;
    private MediaPlayer se;
 
    @Override
    public void onCreate() {
        super.onCreate();
 
        win = MediaPlayer.create(this, R.raw.win);
        ef= MediaPlayer.create(this, R.raw.effect);
        lose = MediaPlayer.create(this, R.raw.lose);
        se = MediaPlayer.create(this, R.raw.selected);
       
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
        ef.release();
        win.release();
        lose.release();
        se.release();
        stopSelf();
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
   
      String what=intent.getStringExtra("what");
      switch(what)
      {
      case "effect":
    	  ef.start();
    	  break;
      case "win":
    	  win.start();
    	  break;
      case "lose":
    	  lose.start();
    	  break;
      case "selected":
    	  se.start();
    	  break;
      default:
    	  onDestroy();
      }
       
        return super.onStartCommand(intent, flags, startId);
    }
 
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
 
}


package com.tianyu.cardgame.application;

import android.app.Application;

/**
 * @Synopsis: CardGameApplication
 * @Author: shisheng.zhao
 * @Date: 2019-07-17 11:41
 */
public final class CardGameApplication extends Application {

    protected volatile static CardGameApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getThis();
    }

    public CardGameApplication getThis() {
        return this;
    }

    public static CardGameApplication getApp() {
        return instance;
    }
}

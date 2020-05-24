package com.example.wardrobeassistant;

import android.app.Application;

import com.example.wardrobeassistant.db.DbManager;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QMUISwipeBackActivityManager.init(this);
        DbManager.init(this);
    }
}

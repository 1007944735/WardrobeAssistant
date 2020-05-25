package com.example.wardrobeassistant;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.example.wardrobeassistant.db.DbManager;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QMUISwipeBackActivityManager.init(this);
        DbManager.init(this);
//        RequestOptions options = new RequestOptions().placeholder(R.drawable.qmui_icon_notify_error).error(R.mipmap.ico_clothing_shoes);
//        Glide.init(this, new GlideBuilder().setDefaultRequestOptions(options));
    }
}

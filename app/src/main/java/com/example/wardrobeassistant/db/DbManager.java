package com.example.wardrobeassistant.db;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

public class DbManager {
    private static final String DB_NAME = "wardrobeassistant.db";
    private static DbManager instance;
    private static DaoSession mDaoSession;

    private DbManager() {
    }

    //初始化，在application中使用
    public static void init(Application context) {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static DbManager getInstance() {
        if (instance == null) {
            synchronized (DbManager.class) {
                if (instance == null) {
                    instance = new DbManager();
                }
            }
        }
        return instance;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }


}

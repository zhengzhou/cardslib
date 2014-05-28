package com.zhou.appmanager.service;

import android.app.Application;

/**
 * Created by xzz on 2014/5/29 0029.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public IService getService(String service){
        return null;
    }
}

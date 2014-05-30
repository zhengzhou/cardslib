package com.zhou.appmanager.service;

import android.app.Application;

/**
 * Created by xzz on 2014/5/29 0029.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

    ServiceManger serviceManger;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        serviceManger = new ServiceManger(this);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public IService getService(String service) {
        return serviceManger.getService(service);
    }
}

package com.zhou.appmanager.controller;

import android.content.Context;
import android.content.pm.PackageManager;

import com.zhou.appmanager.service.IService;

/**
 * Created by xzz on 2014/5/29 0029.
 */
public class AppChecker implements IService {
    private Context mContext;

    public AppChecker(Context mContext) {
        this.mContext = mContext;
    }

    public void readPhoneApp() {
        if (mContext != null) {
            PackageManager pm = mContext.getPackageManager();

        }
    }
}

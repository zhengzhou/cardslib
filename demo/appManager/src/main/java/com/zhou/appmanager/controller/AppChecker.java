package com.zhou.appmanager.controller;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.IBinder;

import com.zhou.appmanager.helper.AsyncImageLoader;
import com.zhou.appmanager.model.AppInfo;
import com.zhou.appmanager.service.IService;
import com.zhou.appmanager.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xzz on 2014/5/29 0029.
 */
public class AppChecker implements IService {

    private Context mContext;
    private List<AppInfo> appInfos;
    private static String BACKUP_PATH = null;

    public AppChecker(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        BACKUP_PATH = Environment.getExternalStorageDirectory().getPath() + "/backups";
    }

    public void readPhoneApp() {
        PackageManager pm = mContext.getPackageManager();
        List<PackageInfo> pkgInfos = pm.getInstalledPackages(0);
        appInfos = new ArrayList<AppInfo>(pkgInfos.size());
        for (PackageInfo pkgInfo : pkgInfos) {
            if ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                AppInfo appInfo = new AppInfo();
                appInfo.setName(pkgInfo.applicationInfo.loadLabel(pm).toString());
                appInfo.setPackageName(pkgInfo.packageName);
                appInfo.setVersionName(pkgInfo.versionName);
                appInfo.setSourcePath(pkgInfo.applicationInfo.sourceDir);
                Drawable drawable = pkgInfo.applicationInfo.loadLogo(pm);
                AsyncImageLoader.putDrawable(pkgInfo.packageName, drawable);
//                appInfo.appInfo(drawable);
                appInfos.add(appInfo);
            }
        }
    }

    @Override
    public void Prepare() {
        if (appInfos == null && mContext != null) {
            readPhoneApp();
        }
    }

    public void backUpApp(AppInfo... apps) {
        for (AppInfo app : apps) {
            File sourceFile = new File(app.getSourcePath());
            File destFile = getDestFile(app, BACKUP_PATH);

            if (sourceFile.exists()) {
                try {
                    FileUtil.copyFile(sourceFile, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public File getDestFile(AppInfo app, String backupPath) {
        String fileName = app.getPackageName() + "-" + app.getVersionName() + ".apk";
        return new File(backupPath + fileName);
    }


    public List<AppInfo> getAppInfos() {
        return appInfos;
    }

}

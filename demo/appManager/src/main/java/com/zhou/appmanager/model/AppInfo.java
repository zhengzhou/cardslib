package com.zhou.appmanager.model;

import android.graphics.drawable.Drawable;

import java.lang.ref.WeakReference;

/**
 * Created by xzz on 2014/5/29 0029.
 */
public class AppInfo {

    private Statu status;

    private String size;

    private String name;

    private String packageName;

    private String versionName;

    private int versionCode;

    private WeakReference<Drawable> icon;

    public Statu getStatus() {
        return status;
    }

    public void setStatus(Statu status) {
        this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getIcon() {
        return icon.get();
    }

    public void setIcon(Drawable icon) {
        this.icon = new WeakReference<Drawable>(icon);
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public enum Statu{
        Backuped,
        UnBackuped,
        UnInstall,
        Installed;
    }
}

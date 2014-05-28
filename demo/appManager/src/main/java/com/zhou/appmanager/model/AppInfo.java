package com.zhou.appmanager.model;

/**
 * Created by xzz on 2014/5/29 0029.
 */
public class AppInfo {

    private Statu status;

    private String size;

    private String name;

    private String versionName;

    private int versionCode;

    public enum Statu{
        Backuped,
        UnBackuped,
        UnInstall,
        Installed;
    }
}

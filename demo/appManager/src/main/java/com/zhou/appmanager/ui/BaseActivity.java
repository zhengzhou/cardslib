package com.zhou.appmanager.ui;

import android.app.Activity;
import android.os.Bundle;

import com.zhou.appmanager.helper.ViewFinder;
import com.zhou.appmanager.service.IService;
import com.zhou.appmanager.service.MyApplication;
import com.zhou.appmanager.service.ServiceManger;

import java.io.Serializable;

/**
 * Created by zhou on 14-5-30.
 */
public abstract class BaseActivity extends Activity {

    /**
     * Finder bound to this activity's view
     */
    protected ViewFinder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finder = new ViewFinder(this);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return serializable
     */
    @SuppressWarnings("unchecked")
    protected <V extends Serializable> V getSerializableExtra(final String name) {
        return (V) getIntent().getSerializableExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return int
     */
    protected int getIntExtra(final String name) {
        return getIntent().getIntExtra(name, -1);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string
     */
    protected String getStringExtra(final String name) {
        return getIntent().getStringExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string array
     */
    protected String[] getStringArrayExtra(final String name) {
        return getIntent().getStringArrayExtra(name);
    }

    /**
     * 获取制定名称的服务
     *
     * @param serviceName @see ServiceManger
     * @return 抽象服务
     */
    IService getService(String serviceName) {
        return MyApplication.getInstance().getService(serviceName);
    }
}

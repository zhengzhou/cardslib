package com.zhou.appmanager.ui.fragment;

import android.app.Fragment;

import com.zhou.appmanager.service.IService;
import com.zhou.appmanager.service.MyApplication;
import com.zhou.appmanager.ui.BaseActivity;

/**
 * Created by zhou on 14-5-30.
 */
public class BaseFragment extends Fragment {

    public BaseActivity getMyActivity() {
        return (BaseActivity) super.getActivity();
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

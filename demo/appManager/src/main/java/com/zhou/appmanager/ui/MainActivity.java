package com.zhou.appmanager.ui;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.zhou.appmanager.R;
import com.zhou.appmanager.controller.AppChecker;
import com.zhou.appmanager.service.ServiceManger;
import com.zhou.appmanager.ui.fragment.UserAppFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppChecker checker = (AppChecker) getService(ServiceManger.AppCheckerService);
        checker.Prepare();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, Fragment.instantiate(this, UserAppFragment.class.getName()))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, Fragment.instantiate(this, UserAppFragment.class.getName()))
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.wenjackp.android.example;

import android.app.Application;

import com.wenjackp.android.lib.util.LogUtil;
import com.wenjackp.android.lib.util.ScreenUtil;

/**
 * Created by Administrator on 2015/12/5.
 */
public class FastAndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.initConfig(this, true);
        ScreenUtil.initConfig(this);

    }
}

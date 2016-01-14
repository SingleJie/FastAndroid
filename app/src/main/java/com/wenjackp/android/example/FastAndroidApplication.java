package com.wenjackp.android.example;

import android.app.Application;

import com.wenjackp.android.lib.utils.LogUtils;
import com.wenjackp.android.lib.utils.ScreenUtils;

/**
 * Created by Administrator on 2015/12/5.
 */
public class FastAndroidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.initConfig(this, true);
        ScreenUtils.initConfig(this);

    }
}

package com.wenjackp.android.example;

import android.app.Activity;
import android.os.Bundle;

import com.wenjackp.android.lib.utils.XCacheUtils;

/**
 * Created by Administrator on 2015/12/5.
 */
public class XCacheActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XCacheUtils mCache = new XCacheUtils(this);
    }
}

package com.wenjackp.android.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * XML
 * Created by Administrator on 2015/12/5.
 */
public class XCacheUtils {

    private Context context;
    private Gson mGson;

    private static Map<String, SharedPreferences> mCaches = new HashMap<>();
    private static final int EMPTY_MODE = -1;

    public XCacheUtils(Context context) {
        this.context = context;
        mGson = new Gson();
        String clazzName = getClassName(context);

        if (!EmptyUtils.emptyOfString(clazzName)) {
            SharedPreferences mPreferences = getPreferences(clazzName, Context.MODE_PRIVATE);
            mCaches.put(clazzName, mPreferences);
        }
    }

    public XCacheUtils putObject(String key, Object content) {
        if (content != null) {
            getDefault()
                    .edit()
                    .putString(key, mGson.toJson(content));
        }
        return this;
    }

    public <T> T getObject(String key, Class<? extends Object> clazz) {
        String querContent = getDefault()
                .getString(key, null);

        if (!EmptyUtils.emptyOfString(querContent)) {
            return (T) mGson.fromJson(querContent, clazz);
        }
        return null;
    }

    public <T> T getObject(String key, Type typedOf) {
        String querContent = getDefault()
                .getString(key, null);

        if (!EmptyUtils.emptyOfString(querContent)) {
            return (T) mGson.fromJson(querContent, typedOf);
        }
        return null;
    }

    public XCacheUtils putString(String key, String content) {

        if (!EmptyUtils.emptyOfString(content)) {
            getDefault()
                    .edit()
                    .putString(key, content);
        }
        return this;
    }

    public String getString(String key) {
        return getDefault()
                .getString(key, null);
    }

    public void commit() {
        getDefault()
                .edit()
                .commit();
    }

    public long getCacheSize() {
        int totalLength = 0;
        return totalLength;
    }

    private SharedPreferences getDefault() {
        return getPreferences(getClassName(context), EMPTY_MODE);
    }

    private SharedPreferences getPreferences(String name, int mode) {

        if (!EmptyUtils.emptyOfString(name)) {
            return mCaches.get(name);
        }

        return context.getSharedPreferences(name, mode);
    }

    private String getClassName(Context context) {
        if (context instanceof Activity) {
            String className = ((Activity) context).getClass().getName();
            return className;
        }
        return null;
    }

    public XCacheUtils clear() {
        getDefault()
                .edit()
                .clear();
        return this;
    }

    public XCacheUtils remove(String key){
        getDefault()
                .edit()
                .remove(key);
        return this;
    }

    public XCacheUtils removes(String[] keys){
        if(!EmptyUtils.emptyOfArray(keys)){
            SharedPreferences.Editor mEditor = getDefault().edit();
            int length = keys.length;
            for(int i=0;i<length;i++){
                mEditor.remove(keys[i]);
            }
        }
        return this;
    }
}

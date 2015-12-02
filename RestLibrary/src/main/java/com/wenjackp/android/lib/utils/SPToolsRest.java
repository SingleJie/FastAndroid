package com.wenjackp.android.lib.utils;

import android.content.Context;

import java.util.Map;

/**
 *
 * Created by Single on 15-4-22.
 */
public class SPToolsRest {

    private static SPTools mApplicationTools;
    private static final String DEFAULT_NAME = SPToolsRest.class.getName();
    private static String encryKey;
    private static int encryType;
    private static Context mContext;
    private static Map<String, SPTools> mTools;

    /**
     * 设置加密配置
     *
     * @param encryType
     * @param encryKey
     */
    public static void setEncryConfig(int encryType, String encryKey) {
        SPToolsRest.encryKey = encryKey;
        SPToolsRest.encryType = encryType;
    }

    /**
     * 初始化配置
     *
     * @param mContext
     */
    public static void initConfig(Context mContext) {
        SPToolsRest.mContext = mContext;
        mApplicationTools = new SPTools(mContext, DEFAULT_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @return
     */
    public static SPTools getDefaultPreferences() {
        return mApplicationTools;
    }

    /**
     * 创建一个Preferences
     *
     * @param name
     * @param mode
     * @return
     */
    public static SPTools buildPreferences(String name, int mode) {

        SPTools mTool;

        if (mTools.containsKey(name)) {
            mTool = mTools.get(name);
            mTool.clearEditor();
        } else {
            mTool = new SPTools(mContext, name, mode);
            mTools.put(name, mTool);
        }

        return mTool;
    }

    /**
     * 创建一个Preferences
     *
     * @param name
     * @param mode
     * @return
     */
    public static SPTools buildPreferences(Context mContext, String name, int mode) {

        SPTools mTool;

        if (mTools.containsKey(name)) {
            mTool = mTools.get(name);
            mTool.clearEditor();
        } else {
            mTool = new SPTools(mContext, name, mode);
            mTools.put(name, mTool);
        }
        return mTool;
    }

    /**
     * 回收垃圾
     */
    public static void recycler() {
        mContext = null;
        mTools.clear();
        mTools = null;
        mApplicationTools = null;
        encryType = 0;
        encryKey = null;
    }
}

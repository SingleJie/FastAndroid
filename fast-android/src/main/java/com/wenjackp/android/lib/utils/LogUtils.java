package com.wenjackp.android.lib.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 日志输出工具类
 *
 * @author Single
 * @version 1.5
 */
public class LogUtils {

    private static Context mContext;
    private static boolean mShowDebug;
    private static String TAG = LogUtils.class.getName();

    public static void initConfig(Context mContext,boolean mShowDebug) {
        LogUtils.mContext = mContext;
        LogUtils.mShowDebug = mShowDebug;
    }

    private LogUtils() {
    }

    public static void toastShortMsgForRes(int resourceId) {
        String msg = mContext.getResources().getString(resourceId);
        toastMsg(msg, Toast.LENGTH_SHORT);
    }

    public static void toastShortMsg(String format, Object... args) {
        toastMsg(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void toastShortMsg(Object args) {
        toastMsg(String.valueOf(args), Toast.LENGTH_SHORT);
    }

    private static void toastMsg(String msg, int duration) {
        if (mContext != null && !EmptyUtils.emptyOfString(msg)) {
            Toast.makeText(mContext, msg, duration).show();
        }
    }

    private static void toastLongMsg(Object args) {
        toastMsg(String.valueOf(args), Toast.LENGTH_LONG);
    }

    public static void toastLongMsgForRes(int resourceId) {
        String msg = mContext.getResources().getString(resourceId);
        toastLongMsg(msg);
    }

    public static void toastLongMsg(String format, Object... args) {
        toastMsg(String.format(format, args), Toast.LENGTH_LONG);
    }

    public static void printlnMsg(Object args) {
        if(mShowDebug) {
            System.out.println(String.valueOf(args));
        }
    }

    public static void printlnMsg(String format, Object... args) {
        printlnMsg(String.format(format, args));
    }

    public static void printMsg(Object args) {
        if(mShowDebug) {
            System.out.print(String.valueOf(args));
        }
    }

    public static void printMsg(String format, Object... args) {
        printMsg(String.format(format, args));
    }

    public static void logMsg(DeBugMode mode, String tag, String msg) {

        if(!mShowDebug){
            return;
        }

        switch (mode) {
            case D:

                Log.d(tag, msg);
                break;

            case E:

                Log.e(tag, msg);
                break;

            case I:

                Log.i(tag, msg);
                break;

            case V:

                Log.v(tag, msg);
                break;

            case W:

                Log.w(tag, msg);
                break;
        }

    }

    public static void logDMsg(Object args) {
        logMsg(DeBugMode.D, TAG, String.valueOf(args));
    }

    public static void logDMsg(String format, Object... args) {
        logMsg(DeBugMode.D, TAG, String.format(format, args));
    }

    public static void logDMsg(String tag, Object args) {
        logMsg(DeBugMode.D, tag, String.valueOf(args));
    }

    public static void logDMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.D, tag, String.format(format, args));
    }

    public static void logIMsg(Object args) {
        logMsg(DeBugMode.I, TAG, String.valueOf(args));
    }

    public static void logIMsg(String format, Object... args) {
        logMsg(DeBugMode.I, TAG, String.format(format, args));
    }

    public static void logIMsg(String tag, Object args) {
        logMsg(DeBugMode.I, tag, String.valueOf(args));
    }

    public static void logIMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.I, tag, String.format(format, args));
    }

    public static void logEMsg(Object args) {
        logMsg(DeBugMode.E, TAG, String.valueOf(args));
    }

    public static void logEMsg(String format, Object... args) {
        logMsg(DeBugMode.E, TAG, String.format(format, args));
    }

    public static void logEMsg(String tag, Object args) {
        logMsg(DeBugMode.E, tag, String.valueOf(args));
    }

    public static void logEMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.E, tag, String.format(format, args));
    }

    public static void logVMsg(Object args) {
        logMsg(DeBugMode.V, TAG, String.valueOf(args));
    }

    public static void logVMsg(String format, Object... args) {
        logMsg(DeBugMode.V, TAG, String.format(format, args));
    }

    public static void logVMsg(String tag, Object args) {
        logMsg(DeBugMode.V, tag, String.valueOf(args));
    }

    public static void logVMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.V, tag, String.format(format, args));
    }

    public static void logWMsg(Object args) {
        logMsg(DeBugMode.W, TAG, String.valueOf(args));
    }

    public static void logWMsg(String format, Object... args) {
        logMsg(DeBugMode.W, TAG, String.format(format, args));
    }

    public static void logWMsg(String tag, Object args) {
        logMsg(DeBugMode.W, tag, String.valueOf(args));
    }

    public static void logWMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.W, tag, String.format(format, args));
    }

    public static void setLogTag(String TAG) {
        LogUtils.TAG = TAG;
    }

    public enum DeBugMode {
        E,
        I,
        D,
        V,
        W
    }
}

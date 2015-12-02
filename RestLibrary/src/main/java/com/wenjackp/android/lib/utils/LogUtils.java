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

    /**
     * 设置上下文对象
     *
     * @param mContext
     */
    public static void initConfig(Context mContext,boolean mShowDebug) {
        LogUtils.mContext = mContext;
        LogUtils.mShowDebug = mShowDebug;
    }

    private LogUtils() {
    }

    /**
     * Toast短消息打印
     *
     * @param resourceId
     */
    public static void toastShortMsgForRes(int resourceId) {
        String msg = mContext.getResources().getString(resourceId);
        toastMsg(msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast短消息打印
     *
     * @param format %s代替参数
     * @param args   参数
     */
    public static void toastShortMsg(String format, Object... args) {
        toastMsg(String.format(format, args), Toast.LENGTH_SHORT);
    }

    /**
     * Toast短消息打印
     *
     * @param args
     */
    public static void toastShortMsg(Object args) {
        toastMsg(String.valueOf(args), Toast.LENGTH_SHORT);
    }

    /**
     * Toast长消息打印
     *
     * @param msg 需要打印的内容
     */
    private static void toastMsg(String msg, int duration) {
        if (mContext != null && !EmptyUtils.emptyOfString(msg)) {
            Toast.makeText(mContext, msg, duration).show();
        }
    }

    /**
     * Toast长消息打印
     *
     * @param args
     */
    private static void toastLongMsg(Object args) {
        toastMsg(String.valueOf(args), Toast.LENGTH_LONG);
    }

    /**
     * Toast长消息打印
     *
     * @param resourceId 资源ID
     */
    public static void toastLongMsgForRes(int resourceId) {
        String msg = mContext.getResources().getString(resourceId);
        toastLongMsg(msg);
    }

    /**
     * Toast长消息打印
     *
     * @param format 格式化字符串
     * @param args 参数
     */
    public static void toastLongMsg(String format, Object... args) {
        toastMsg(String.format(format, args), Toast.LENGTH_LONG);
    }

    /**
     * 控制台消息换行打印
     *
     * @param args 打印内容
     */
    public static void printlnMsg(Object args) {
        if(mShowDebug) {
            System.out.println(String.valueOf(args));
        }
    }

    /**
     * 控制台消息换行打印
     *
     * @param format 格式化文本
     * @param args   格式化参数
     */
    public static void printlnMsg(String format, Object... args) {
        printlnMsg(String.format(format, args));
    }

    /**
     * 控制台消息不换行打印
     *
     * @param args 打印内容
     */
    public static void printMsg(Object args) {
        if(mShowDebug) {
            System.out.print(String.valueOf(args));
        }
    }

    /**
     * 控制台消息打印
     *
     * @param format 格式化文本
     * @param args   格式化参数
     */
    public static void printMsg(String format, Object... args) {
        printMsg(String.format(format, args));
    }

    /**
     * 日志调试
     *
     * @param mode 调试模式
     * @param tag  标识
     * @param msg  消息
     */
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

    /**
     * Debug调试
     *
     * @param args 打印内容
     */
    public static void logDMsg(Object args) {
        logMsg(DeBugMode.D, TAG, String.valueOf(args));
    }

    /**
     * Debug调试
     *
     * @param format 格式化文本
     * @param args   格式化内容
     */
    public static void logDMsg(String format, Object... args) {
        logMsg(DeBugMode.D, TAG, String.format(format, args));
    }

    /**
     * Debug调试
     *
     * @param tag  日志标识
     * @param args 格式化参数
     */
    public static void logDMsg(String tag, Object args) {
        logMsg(DeBugMode.D, tag, String.valueOf(args));
    }

    /**
     * Debug调试
     *
     * @param tag    日志标志
     * @param format 格式化文本
     * @param args   格式化内容
     */
    public static void logDMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.D, tag, String.format(format, args));
    }

    /**
     * INFO打印
     *
     * @param args 打印内容
     */
    public static void logIMsg(Object args) {
        logMsg(DeBugMode.I, TAG, String.valueOf(args));
    }

    /**
     * INFO打印
     *
     * @param format 格式化文本
     * @param args   格式化参数
     */
    public static void logIMsg(String format, Object... args) {
        logMsg(DeBugMode.I, TAG, String.format(format, args));
    }

    /**
     * INFO打印
     *
     * @param tag  日志标识
     * @param args 打印内容
     */
    public static void logIMsg(String tag, Object args) {
        logMsg(DeBugMode.I, tag, String.valueOf(args));
    }

    /**
     * Info消息打印
     *
     * @param tag    日志标志
     * @param format 格式化文本
     * @param args   格式化参数
     */
    public static void logIMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.I, tag, String.format(format, args));
    }

    /**
     * Debug调试
     *
     * @param args 打印内容
     */
    public static void logEMsg(Object args) {
        logMsg(DeBugMode.E, TAG, String.valueOf(args));
    }

    /**
     * Debug调试
     *
     * @param format 格式化文本
     * @param args   格式化内容
     */
    public static void logEMsg(String format, Object... args) {
        logMsg(DeBugMode.E, TAG, String.format(format, args));
    }

    /**
     * Debug调试
     *
     * @param tag  日志标识
     * @param args 格式化参数
     */
    public static void logEMsg(String tag, Object args) {
        logMsg(DeBugMode.E, tag, String.valueOf(args));
    }

    /**
     * Debug调试
     *
     * @param tag    日志标志
     * @param format 格式化文本
     * @param args   格式化内容
     */
    public static void logEMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.E, tag, String.format(format, args));
    }

    /**
     * Debug调试
     *
     * @param args 打印内容
     */
    public static void logVMsg(Object args) {
        logMsg(DeBugMode.V, TAG, String.valueOf(args));
    }

    /**
     * Debug调试
     *
     * @param format 格式化文本
     * @param args   格式化内容
     */
    public static void logVMsg(String format, Object... args) {
        logMsg(DeBugMode.V, TAG, String.format(format, args));
    }

    /**
     * Debug调试
     *
     * @param tag  日志标识
     * @param args 格式化参数
     */
    public static void logVMsg(String tag, Object args) {
        logMsg(DeBugMode.V, tag, String.valueOf(args));
    }

    /**
     * Debug调试
     *
     * @param tag    日志标志
     * @param format 格式化文本
     * @param args   格式化内容
     */
    public static void logVMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.V, tag, String.format(format, args));
    }

    /**
     * Debug调试
     *
     * @param args 打印内容
     */
    public static void logWMsg(Object args) {
        logMsg(DeBugMode.W, TAG, String.valueOf(args));
    }

    /**
     * Debug调试
     *
     * @param format 格式化文本
     * @param args   格式化内容
     */
    public static void logWMsg(String format, Object... args) {
        logMsg(DeBugMode.W, TAG, String.format(format, args));
    }

    /**
     * Debug调试
     *
     * @param tag  日志标识
     * @param args 格式化参数
     */
    public static void logWMsg(String tag, Object args) {
        logMsg(DeBugMode.W, tag, String.valueOf(args));
    }

    /**
     * Debug调试
     *
     * @param tag    日志标志
     * @param format 格式化文本
     * @param args   格式化内容
     */
    public static void logWMsg(String tag, String format, Object... args) {
        logMsg(DeBugMode.W, tag, String.format(format, args));
    }

    /**
     * 设置日志标识
     *
     * @param TAG 标识
     */
    public static void setLogTag(String TAG) {
        LogUtils.TAG = TAG;
    }

    /**
     * 调试模式
     *
     * @author single
     */
    public enum DeBugMode {
        E,
        I,
        D,
        V,
        W
    }
}

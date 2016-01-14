package com.wenjackp.android.lib.utils;

import android.content.Context;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字符串缓存工具类
 *
 * @version 1.0
 *          <p/>
 *          使用
 *          //先初始化
 *          StrCacheManager.getInstance().initConfig(this);
 *          <p/>
 *          <p/>
 *          //然后再调用存储
 *          StrCacheManager.getInstance().saveCache()
 *          StrCacheManager.getInstance().getCache()
 */
public class EasyCacheUtils {

    private final static int INVALIDE_VALUE = -1;
    private static final String LOG_TAG = EasyCacheUtils.class.getName();

    private static volatile EasyCacheUtils manager = null;
    private String cachePath;
    private File file;
    private String encode = "UTF-8";
    private boolean mDebug = true;

    public static final int TIME_DAY = 1;
    public static final int TIME_HOUR = 2;
    public static final int TIME_MINUTE = 3;
    public static final int TIME_SECONDS = 4;

    private Context context;

    public static EasyCacheUtils getInstance() {
        if (manager == null) {
            synchronized (EasyCacheUtils.class) {
                if (manager == null) {
                    manager = new EasyCacheUtils();
                }
            }
        }
        return manager;
    }

    private EasyCacheUtils() {
    }

    /**
     * 只需初始化一次,主要用于获取context的缓存目录，在没SD卡情况下使用
     *
     * @param context
     */
    public void initConfig(Context context) {
        this.context = context;
        //获取当前应用所在的Data目录
        cachePath = context.getCacheDir() + "/" + LOG_TAG + "/";
        logMsg(cachePath);
    }

    public Context getContext() {
        return context;
    }

    /**
     * 保存数据 默认为永久保存
     *
     * @param key   保存的Key
     * @param value 保存的值
     */
    public void saveCache(String key, String value) {
        valideKey(key);
        checkParentDirectoryExists();
        write(key, value, null);
    }

    /**
     * 读取数据
     *
     * @param key 保存的Key
     * @return
     */
    public String getCache(String key) {
        valideKey(key);
        checkParentDirectoryExists();
        return read(key);
    }

    /**
     * 保存数据
     *
     * @param key      保存的Key
     * @param value    保存的值
     * @param timeType 缓存时间类型 天、时、分、秒
     * @param dueTime  缓存时间
     */
    public void saveCache(String key, String value, int timeType, int dueTime) {
        valideKey(key);
        checkParentDirectoryExists();
        int time;

        switch (timeType) {
            case TIME_HOUR:

                time = dueTime * 60 * 60;
                break;

            case TIME_MINUTE:

                time = dueTime * 60;
                break;

            case TIME_SECONDS:

                time = dueTime;
                break;

            case TIME_DAY:
            default:
                time = dueTime * 24 * 60 * 60;
                break;
        }

        write(key, value, String.valueOf(time));
    }

    /**
     * 写入内容
     *
     * @param key   保存的Key
     * @param value 保存的值
     * @param time  单位秒
     */
    private void write(String key, String value, String time) {

        if (!file.exists()) {
            return;
        }

        File mFile = getFile(key);

        if (mFile == null) {
            return;
        }

        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            synchronized (this) {
                fos = new FileOutputStream(mFile);
                int length = 1024;
                int len = INVALIDE_VALUE;
                byte[] buffer = new byte[length];
                String content = encryption(value) + (emptyOfString(time) ? "" : ("," + encryption(time)));
                logMsg("Save Content : " + content + "   " + time);
                bis = new BufferedInputStream(new ByteArrayInputStream(content.getBytes()));
                while ((len = bis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取内容
     *
     * @param key
     * @return
     */
    private String read(String key) {
        String value = null;
        FileInputStream fis = null;

        if (!file.exists()) {
            return null;
        }

        File mTempFile = getFile(key);
        if (mTempFile == null) {
            return null;
        }

        try {

            synchronized (this) {
                fis = new FileInputStream(mTempFile);
                int bufferLen = 1024;
                byte[] buffer = new byte[bufferLen];
                int len = INVALIDE_VALUE;
                StringBuffer mBuffer = new StringBuffer();

                while ((len = fis.read(buffer)) != -1) {
                    mBuffer.append(new String(buffer, 0,len,encode));
                }
                value = mBuffer.toString();

                logMsg("Value : " + value);

                if (!emptyOfString(value)) {

                    if (value.indexOf(",") != -1) {
                        String[] mTemp = value.split(",");
                        if (checkTimeIsDUE(mTempFile.lastModified(), Long.parseLong(decryption(mTemp[1])))) {
                            value = null;
                            removeCache(key);
                        } else {
                            value = decryption(mTemp[0]);
                        }
                    } else {
                        value = decryption(value);
                    }
                } else {
                    value = null;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     */
    private String encryption(String data) {
        String value = null;
        try {
            value = AesEncryUtils.encodeToString(String.valueOf(LOG_TAG.hashCode()), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    private String decryption(String data) {
        String value = null;
        try {
            value = AesEncryUtils.encodeToString(String.valueOf(LOG_TAG.hashCode()), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 根据Key获取一个File
     *
     * @param key
     * @return
     */
    private File getFile(String key) {
        key = encryption(key);
        File mTempFile = new File(cachePath + key);
        logMsg("File Path : " + mTempFile.getPath());

        try {
            if (!mTempFile.exists()) {
                mTempFile.createNewFile();
            }
            return mTempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日志打印
     *
     * @param msg
     */
    private void logMsg(String msg) {
        if (mDebug) {
            Log.e(LOG_TAG, msg);
        }
    }

    /**
     * 判断缓存父路径是否存在
     */
    private void checkParentDirectoryExists() {
        file = new File(cachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 校验Key是否为空
     *
     * @param key 保存的Key
     */
    private void valideKey(String key) {
        if (emptyOfString(key)) {
            throw new RuntimeException("Key must be not null !");
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    public long getCacheSize() {
        if (file != null && file.exists()) {
            return file.length();
        }
        return 0;
    }

    /**
     * 删除所有缓存
     */
    public void removeAllCache() {
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            int length = files.length;
            for (int i = 0; i < length; i++) {
                File mTemp = files[i];
                if (mTemp.exists()) {
                    mTemp.delete();
                }
            }
            file.delete();
            logMsg("删除所有文件完毕");
        }
    }

    /**
     * 根据Key删除指定缓存
     *
     * @param key 保存的Key
     */
    public void removeCache(String key) {
        valideKey(key);
        File mTempFile = getFile(key);

        if (mTempFile.exists()) {
            mTempFile.delete();
            logMsg("删除指定缓存 : " + key);
        } else {
            logMsg("文件不存在，无法删除");
        }
    }

    /**
     * 判断文件是否过期
     *
     * @param lastTime 最后修改时间
     * @param millise  保存秒数
     * @return true表示逾期
     */
    private boolean checkTimeIsDUE(long lastTime, long millise) {
        //获取当前时间
        long offsetTime = System.currentTimeMillis() - lastTime;
        logMsg("offsetTime : " + offsetTime + "    millise : " + millise + "  LastTime : " + lastTime);
        return (offsetTime / 1000) > millise;
    }

    /**
     * 判断SD卡存在
     *
     * @return true 表示SD卡存在
     */
    private boolean isSDCardExists() {
        boolean sdCardExists = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExists) {
            return true;
        }
        logMsg("SDCard Exists : " + sdCardExists);
        return false;
    }

    /**
     * 判断字符串是否为空
     *
     * @param params 需要判断的字符串
     * @return true 字符串为空
     */
    private boolean emptyOfString(String params) {
        if (params != null && params.length() > 0) {
            return false;
        }
        return true;
    }

}

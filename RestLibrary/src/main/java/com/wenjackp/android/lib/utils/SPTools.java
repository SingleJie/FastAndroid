package com.wenjackp.android.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Map;

/**
 *  SharedPreferences二次封装类
 *
 *  简介: 为了使用起来更轻松、方便
 *
 *  功能: 支持Object对象存储、加密
 *
 *  @author SingleJie
 *
 *  @createTime 2015-4-22
 *
 *  @version  1.0
 */
public class SPTools {

    private Gson mGson = new Gson();
    private SharedPreferences mNewSP;

    /**
     * 加密类型
     */
    public static final int AES_ENCRY  = 2;

    /**
     * 默认加密Key
     */
    private String encryKey = SPTools.class.getName();

    /**
     * 默认没加密
     */
    private int encryType  = -1;


    public SPTools(Context mContext,String name,int mode){
        mNewSP = mContext.getSharedPreferences(name,mode);
    }

    public SPTools putString(String key, String value) {
        buildEdit()
                .putString(key,value);
        return this;
    }

    public SPTools putInt(String key,int value){
        buildEdit()
                .putInt(key,value);
        return this;
    }

    public SPTools putFloat(String key, float value) {
        buildEdit()
                .putFloat(key, value);
        return this;
    }

    public SPTools putLong(String key,long value){
        buildEdit()
                .putLong(key,value);
        return this;
    }

    public SPTools putBoolean(String key, boolean value) {
        buildEdit()
                .putBoolean(key, value);
        return this;
    }

    public SPTools putObject(String key,Object obj){
        return putString(key,mGson.toJson(obj));
    }

    public <T> T getObject(String key,Class<T> mClass){
        String json = getPreferences().getString(key,null);
        T obj =  mGson.fromJson(json,mClass);
        return obj;
    }

    public String getString(String key, String defaultValue) {
        return getPreferences()
                .getString(key,defaultValue);
    }

    public int getInt(String key,int defaultValue){
        return getPreferences()
                .getInt(key,defaultValue);
    }

    public boolean getBoolean(String key,boolean defaultValue){
        return getPreferences()
                .getBoolean(key,defaultValue);
    }

    public float getFloat(String key,float defaultValue){
        return getPreferences()
                .getFloat(key,defaultValue);
    }

    public long getLong(String key,Long defaultValue){
        return getPreferences()
                .getLong(key,defaultValue);
    }

    public Map<String,?> getAll(String key){
        return getPreferences()
                .getAll();
    }

    public SharedPreferences.Editor buildEdit(){
        return getPreferences()
                .edit();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener mListener){
        getPreferences().registerOnSharedPreferenceChangeListener(mListener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener mListener){
        getPreferences().unregisterOnSharedPreferenceChangeListener(mListener);
    }

    public SharedPreferences getPreferences(){
        return mNewSP;
    }

    /**
     * 提交
     */
    public void commit(){
        buildEdit()
                .commit();
    }

    /**
     * 清空editor
     */
    public void clearEditor(){
        buildEdit()
                .clear();
    }

    /**
     * AES加密
     * @param content
     * @return
     */
    protected String aesEncry(String content){
        try {
            return AesEncryUtils.encrypt(encryKey,content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密码
     * @param content
     * @return
     */
    protected String aesDecrypt(String content){
        try {
            return AesEncryUtils.decrypt(encryKey,content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取加密后的文本内容
     * @param content
     * @return
     */
    protected String getEncryContent(String content) {
        switch (encryType) {
            case AES_ENCRY:

                return aesEncry(content);

            default:
                return content;
        }
    }

    /**
     * 解密文本
     * @param content
     * @return
     */
    protected String decryptContent(String content){
        switch (encryType){
            case AES_ENCRY:

                return aesDecrypt(content);

            default:
                return content;
        }
    }

    /**
     * 设置加密配置
     * @param encryType
     * @param encryKey
     */
    public void setEncryConfig(int encryType,String encryKey){

        this.encryType = encryType;

        if(!EmptyUtils.emptyOfString(encryKey)){
            this.encryKey = encryKey;
        }
    }
}

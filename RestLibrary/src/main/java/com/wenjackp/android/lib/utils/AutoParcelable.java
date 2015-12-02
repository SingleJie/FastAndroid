package com.wenjackp.android.lib.utils;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 自动序列化工具类
 * @author Single
 * @version 1.0
 */
public class AutoParcelable implements Parcelable {
	
	public static String className;
	
	
	public AutoParcelable()
	{
		className = getClass().getName();
	}
	
	@Override
	public int describeContents() {
		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		try
		{
	
		 Object obj = AutoParcelable.this;
		 Field mFileds[] = obj.getClass().getDeclaredFields();

		 for(Field mField : mFileds)
		 {
			 //允许访问私有属性
			 mField.setAccessible(true);
			 Class<?> mClassType = mField.getType();
			 
			 if(mClassType == String.class)
			 {
				 dest.writeString(mField.get(obj).toString());
			 }
			 else if(mClassType== int.class)
			 {
				 dest.writeInt(mField.getInt(obj));
			 }
			 else if(mClassType==float.class)
			 {
				 dest.writeFloat(mField.getFloat(obj));
			 }
			 else if(mClassType == double.class)
			 {
				 dest.writeDouble(mField.getDouble(obj));
			 }
			 else if(mClassType == byte.class)
			 {
				 dest.writeByte(mField.getByte(obj));
			 }
			 else if(mClassType==long.class)
			 {
				dest.writeLong(mField.getLong(obj));
			 }
			 //数组类型
			 else if(mClassType == String[].class)
			 {
				 dest.writeStringArray((String[])mField.get(obj));
			 }
			 else if(mClassType==int[].class)
			 {
				 dest.writeIntArray((int[])mField.get(obj));
			 }
			 else if(mClassType==Object[].class)
			 {
				 dest.writeArray((Object[])mField.get(obj));
			 }
			 else if(mClassType==char[].class)
			 {
				 dest.writeCharArray((char[])mField.get(obj));
			 }
			 else if(mClassType==double[].class)
			 {
				 dest.writeDoubleArray((double[])mField.get(obj));
			 }
			 else if(mClassType==byte[].class)
			 {
				 dest.writeByteArray((byte[])mField.get(obj));
			 }
			 else if(mClassType==float[].class)
			 {
				 dest.writeFloatArray((float[])mField.get(obj));
			 }
			 else if(mClassType == boolean[].class)
			 {
				 dest.writeBooleanArray((boolean[])mField.get(obj));
			 }
			 else if(mClassType==long[].class)
			 {
				 dest.writeLongArray((long[])mField.get(obj));
			 }
			 else if(mClassType==IBinder[].class)
			 {
				 dest.writeBinderArray((IBinder[])mField.get(obj));
			 }
			 else if(mClassType == List.class)
			 {
				//集合类型
				dest.writeList((List<?>)mField.get(obj));
			 }
			 else if(mClassType==HashMap.class)
			 {
				 dest.writeMap((Map<?,?>)mField.get(obj));
			 }
			 else
			 {
				dest.writeValue(mField.get(obj));
			 }
		 }
			 
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}	
	}
	
	public final static Creator<Object> CREATOR = new Creator<Object>() {

		@Override
		public Object createFromParcel(Parcel source) {
	
			Class<?> cls = null;
			 Object obj = null;
			 
			try
			{
				Log.e("sb",className);
				cls = Class.forName(className);
				
				obj = cls.newInstance();
				Field mFileds[] =obj.getClass().getDeclaredFields();
			
			 for(Field mField : mFileds)
			 {
				 //允许访问私有属性
				 mField.setAccessible(true);
				 Class<?> mClassType = mField.getType();
				 
				 Log.e("sb",mField.getName()+"------");

				 if(mClassType == String.class)
				 {
					 Log.e("sb",mField.getName()+"------1");
					mField.set(obj,source.readString());
				 }
				 else if(mClassType== int.class)
				 {
					 Log.e("sb",mField.getName()+"------2");
					mField.set(obj,source.readInt());
				 }
				 else if(mClassType==float.class)
				 {
					 Log.e("sb",mField.getName()+"-----3-");
					mField.set(obj,source.readFloat());
				 }
				 else if(mClassType == double.class)
				 {
					 Log.e("sb",mField.getName()+"------4");
					mField.set(obj,source.readDouble());
				 }
				 else if(mClassType == byte.class)
				 {
					 Log.e("sb",mField.getName()+"------5");
					mField.set(obj,source.readByte());
				 }
				 else if(mClassType==long.class)
				 {
					 Log.e("sb",mField.getName()+"------6");
					mField.set(obj,source.readLong());
				 }
				 //数组类型
				 else if(mClassType == String[].class)
				 {
					 Log.e("sb",mField.getName()+"------7");
					mField.set(obj,source.createStringArray());
				 }
				 else if(mClassType==int[].class)
				 {
					 Log.e("sb",mField.getName()+"------8");
					 mField.set(obj,source.createIntArray());
				 }
				 else if(mClassType==Object[].class)
				 {Log.e("sb",mField.getName()+"------9");
					mField.set(obj,source.readArray(mClassType.getClassLoader()));
				 }
				 else if(mClassType==char[].class)
				 {
					 Log.e("sb",mField.getName()+"------10");
					 mField.set(obj,source.createCharArray());
				 }
				 else if(mClassType==double[].class)
				 {
					 Log.e("sb",mField.getName()+"------11");
					 mField.set(obj,source.createDoubleArray());
				 }
				 else if(mClassType==byte[].class)
				 {
					 Log.e("sb",mField.getName()+"------12");
					 mField.set(obj,source.createByteArray());
				 }
				 else if(mClassType==float[].class)
				 {
					 Log.e("sb",mField.getName()+"------13");
					 mField.set(obj,source.createFloatArray());
				 }
				 else if(mClassType == boolean[].class)
				 {
					 Log.e("sb",mField.getName()+"------14");
					 mField.set(obj,source.createBooleanArray());
				 }
				 else if(mClassType==long[].class)
				 {
					 Log.e("sb",mField.getName()+"------15");
					 mField.set(obj,source.createLongArray());
				 }
				 else if(mClassType==IBinder[].class)
				 {
					 Log.e("sb",mField.getName()+"------16");
					 mField.set(obj,source.createBinderArray());
				 }
				 //集合类型
				 else if(mClassType==List.class)
				 {
					 Log.e("sb",mField.getName()+"------17");
					 Type mType = mField.getGenericType();
				     ParameterizedType mParameterizedType = (ParameterizedType)mType;
				     Class<?> clazz = (Class<?>)mParameterizedType.getActualTypeArguments()[0];
					 mField.set(obj,new ArrayList());
					 source.readList((List<?>)mField.get(obj),clazz.getClassLoader());
				 }
				 else if(mClassType == HashMap.class)
				 {
					 Log.e("sb",mField.getName()+"------18");
					 Type mType = mField.getGenericType();
				     ParameterizedType mParameterizedType = (ParameterizedType)mType;
				     Class<?> clazz = (Class<?>)mParameterizedType.getActualTypeArguments()[1];
				     mField.set(obj,source.readHashMap(clazz.getClassLoader()));
				 }
				 else if(mClassType == Map.class)
				 {
					 Log.e("sb",mField.getName()+"------19");
					 Type mType = mField.getGenericType();
				     ParameterizedType mParameterizedType = (ParameterizedType)mType;
				     Class<?> clazz = (Class<?>)mParameterizedType.getActualTypeArguments()[1];
				     Map<?,?> mTmepMap = new HashMap();
				     source.readMap(mTmepMap,clazz.getClassLoader());
				     mField.set(obj,mTmepMap);
				 }
				 else
				 {
					 Log.e("sb",mField.getName()+"------20");
					 System.out.println("Out:default"+mClassType);
					 mField.set(obj,source.readValue(mClassType.getClassLoader()));
				 }
			 }
			 
			 Log.e("sb","------22");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();

				 Log.e("sb","------23"+ex.getMessage());
			}
			
			Log.e("sb","------24"+obj.getClass().getName());
			
			return obj;
		}

		@Override
		public Object[] newArray(int size) {
			
			return new Object[size];
		}
	};
	
	public static Object abc(Parcel source,String className)
	{
		Class<?> cls = null;
		 Object obj = null;
		 
		try
		{
			Log.e("sb",className);
			cls = Class.forName(className);
			
			obj = cls.newInstance();
			Field mFileds[] =obj.getClass().getDeclaredFields();
		
		 for(Field mField : mFileds)
		 {
			 //允许访问私有属性
			 mField.setAccessible(true);
			 Class<?> mClassType = mField.getType();
			 
			 Log.e("sb",mField.getName()+"------");

			 if(mClassType == String.class)
			 {
				 Log.e("sb",mField.getName()+"------1");
				mField.set(obj,source.readString());
			 }
			 else if(mClassType== int.class)
			 {
				 Log.e("sb",mField.getName()+"------2");
				mField.set(obj,source.readInt());
			 }
			 else if(mClassType==float.class)
			 {
				 Log.e("sb",mField.getName()+"-----3-");
				mField.set(obj,source.readFloat());
			 }
			 else if(mClassType == double.class)
			 {
				 Log.e("sb",mField.getName()+"------4");
				mField.set(obj,source.readDouble());
			 }
			 else if(mClassType == byte.class)
			 {
				 Log.e("sb",mField.getName()+"------5");
				mField.set(obj,source.readByte());
			 }
			 else if(mClassType==long.class)
			 {
				 Log.e("sb",mField.getName()+"------6");
				mField.set(obj,source.readLong());
			 }
			 //数组类型
			 else if(mClassType == String[].class)
			 {
				 Log.e("sb",mField.getName()+"------7");
				mField.set(obj,source.createStringArray());
			 }
			 else if(mClassType==int[].class)
			 {
				 Log.e("sb",mField.getName()+"------8");
				 mField.set(obj,source.createIntArray());
			 }
			 else if(mClassType==Object[].class)
			 {Log.e("sb",mField.getName()+"------9");
				mField.set(obj,source.readArray(mClassType.getClassLoader()));
			 }
			 else if(mClassType==char[].class)
			 {
				 Log.e("sb",mField.getName()+"------10");
				 mField.set(obj,source.createCharArray());
			 }
			 else if(mClassType==double[].class)
			 {
				 Log.e("sb",mField.getName()+"------11");
				 mField.set(obj,source.createDoubleArray());
			 }
			 else if(mClassType==byte[].class)
			 {
				 Log.e("sb",mField.getName()+"------12");
				 mField.set(obj,source.createByteArray());
			 }
			 else if(mClassType==float[].class)
			 {
				 Log.e("sb",mField.getName()+"------13");
				 mField.set(obj,source.createFloatArray());
			 }
			 else if(mClassType == boolean[].class)
			 {
				 Log.e("sb",mField.getName()+"------14");
				 mField.set(obj,source.createBooleanArray());
			 }
			 else if(mClassType==long[].class)
			 {
				 Log.e("sb",mField.getName()+"------15");
				 mField.set(obj,source.createLongArray());
			 }
			 else if(mClassType==IBinder[].class)
			 {
				 Log.e("sb",mField.getName()+"------16");
				 mField.set(obj,source.createBinderArray());
			 }
			 //集合类型
			 else if(mClassType==List.class)
			 {
				 Log.e("sb",mField.getName()+"------17");
				 Type mType = mField.getGenericType();
			     ParameterizedType mParameterizedType = (ParameterizedType)mType;
			     Class<?> clazz = (Class<?>)mParameterizedType.getActualTypeArguments()[0];
				 mField.set(obj,new ArrayList());
				 source.readList((List<?>)mField.get(obj),clazz.getClassLoader());
			 }
			 else if(mClassType == HashMap.class)
			 {
				 Log.e("sb",mField.getName()+"------18");
				 Type mType = mField.getGenericType();
			     ParameterizedType mParameterizedType = (ParameterizedType)mType;
			     Class<?> clazz = (Class<?>)mParameterizedType.getActualTypeArguments()[1];
			     mField.set(obj,source.readHashMap(clazz.getClassLoader()));
			 }
			 else if(mClassType == Map.class)
			 {
				 Log.e("sb",mField.getName()+"------19");
				 Type mType = mField.getGenericType();
			     ParameterizedType mParameterizedType = (ParameterizedType)mType;
			     Class<?> clazz = (Class<?>)mParameterizedType.getActualTypeArguments()[1];
			     Map<?,?> mTmepMap = new HashMap();
			     source.readMap(mTmepMap,clazz.getClassLoader());
			     mField.set(obj,mTmepMap);
			 }
			 else
			 {
				 Log.e("sb",mField.getName()+"------20");
				 System.out.println("Out:default"+mClassType);
				 mField.set(obj,source.readValue(mClassType.getClassLoader()));
			 }
		 }
		 
		 Log.e("sb","------22");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();

			 Log.e("sb","------23"+ex.getMessage());
		}
		
		Log.e("sb","------24"+obj.getClass().getName());
		
		return obj;
	}

}

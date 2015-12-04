package com.wenjackp.android.lib.utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;

import java.lang.reflect.Field;

/**
 * 联系人工具类
 * @author Single
 *
 */
public abstract class ContactUtils<T> extends AsyncQueryHandler {

	public ContactUtils(ContentResolver cr) 
	{
		super(cr);
	}
	
	@Override
	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
	
		try {
			String a= FormatCursorUtils.format(String.class, cursor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onQueryComplete(token, cookie, cursor);
	}
	
	public static class FormatCursorUtils 
	{
		@SuppressWarnings("unchecked")
		public static <T>T format(Class<T> cls,Cursor cursor) throws Exception
		{
			Object obj = null;
			
			try
			{
			
			obj = cls.newInstance();
			Field[] mFields = obj.getClass().getDeclaredFields();
			
			if(!EmptyUtils.emptyOfArray(mFields))
			{
				for(Field mField : mFields)
				{
					mField.setAccessible(true);
					Class<?> mClassType = mField.getType();
					
					if(mClassType==String.class)
					{
					   mField.set(obj,cursor.getString(cursor.getColumnIndex(mField.getName())));	
					}
					else if(mClassType==Integer.class)
					{
						mField.set(obj,cursor.getInt(cursor.getColumnIndex(mField.getName())));	
					}
					else if(mClassType==Double.class)
					{
						mField.set(obj,cursor.getDouble(cursor.getColumnIndex(mField.getName())));	
					}
					else if(mClassType==Float.class)
					{
						mField.set(obj,cursor.getFloat(cursor.getColumnIndex(mField.getName())));	
					}
					else if(mClassType==Long.class)
					{
						mField.set(obj,cursor.getLong(cursor.getColumnIndex(mField.getName())));	
					}
					else if(mClassType==byte[].class)
					{
						mField.set(obj,cursor.getBlob(cursor.getColumnIndex(mField.getName())));	
					}
					else if(mClassType==Short.class)
					{
						mField.set(obj,cursor.getShort(cursor.getColumnIndex(mField.getName())));	
					}
				}
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return (T)obj;
		}
	}
 }

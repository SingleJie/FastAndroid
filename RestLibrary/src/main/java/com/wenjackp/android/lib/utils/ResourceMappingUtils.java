package com.wenjackp.android.lib.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * 资源反射类
 * @author Single
 * @category 资源反射类
 * @version 1.2
 * @alertTime 2014/4/2 22:17
 */
public final class ResourceMappingUtils {
	
	public static final String DRAWABLE = "drawable";
	public static final String LAYOUT = "layout";
	public static final String XML = "xml";
	public static final String VALUES = "values";
	public static final String RAW = "raw";
	public static final String COLOR = "color";
	public static final String ANIM = "anim";
    public static final String ID = "id";	
    public static final String STRING = "string";
    public static final String ARRAY = "array";
    public static final String STYLEABLE = "styleable";
    public static final String DIMEN = "dimen";
    public static final String ATTR = "attr";
    public static final String BOOL = "bool";
    public static final String INTEGER = "integer";
    
    private Context mContext;
    
    public ResourceMappingUtils(Context mContext)
    {
    	this.mContext = mContext;
    }
    
	
	/**
	 * 反射Res资源
	 * @param resourceName 资源名称
	 * @param resourceType 资源类型 
	 * @return
	 */
	public int getAllResourceId(String resourceName,String resourceType)
	{
		int resourceId = 0;
		
		try
		{
			resourceId = mContext.getResources().getIdentifier(resourceName,resourceType,mContext.getPackageName());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return resourceId;
	}
	
	/**
	 * 反射Res资源
	 * @param mContext 上下文对象
	 * @param resourceName 资源名称
	 * @param resourceType 资源类型 
	 * @return
	 */
	public static int getAllResourceId(Context mContext,String resourceName,String resourceType)
	{
		int resourceId = 0;
		
		try
		{
			resourceId = mContext.getResources().getIdentifier(resourceName,resourceType,mContext.getPackageName());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return resourceId;
	}
	
	/**
	 * 获取图片资源Id
	 * @param name
	 * @return
	 */
	public int getDrawableId(String name)
	{
		return getAllResourceId(name,DRAWABLE);
	}
	
	/**
	 * 获取LayoutId
	 * @param name
	 * @return
	 */
	public int getLayoutId(String name)
	{
		return getAllResourceId(name,LAYOUT);
	}
	
	/**
	 * 获取动画ID
	 * @param name
	 * @return
	 */
	public int getAnimationId(String name)
	{
		return getAllResourceId(name,ANIM);
	}
	
	/**
	 * 获取StyleableId
	 * @param name
	 * @return
	 */
	public int getStyleableId(String name)
	{
		return getAllResourceId(name,STYLEABLE);
	}
	
	/**
	 * 获取StyleableId
	 * @param name
	 * @return
	 */
	public static int getStyleableId(Context mContext,String name)
	{
		return getAllResourceId(mContext,name,STYLEABLE);
	}
	
	/**
	 * 获取颜色资源ID
	 * @param name
	 * @return
	 */
	public int getColorId(String name)
	{
		return getAllResourceId(name,COLOR);
	}
	
	/**
	 * 获取颜色资源ID
	 * @param mContext
	 * @param name
	 * @return
	 */
	public static int getColorId(Context mContext,String name)
	{
		return getAllResourceId(mContext,name,COLOR);
	}
	
	/**
	 * 获取控件ID
	 * @param name
	 * @return
	 */
	public int getControlsId(String name)
	{
		return getAllResourceId(name,ID);
	}
	
	/**
	 * 获取字符串ID
	 * @param name
	 * @return
	 */
	public int getStringId(String name)
	{
		return getAllResourceId(name,STRING);
	}
	
	/**
	 * 获取DimenId
	 * @param name
	 * @return
	 */
	public int getDimenId(String name)
	{
		return getAllResourceId(name,DIMEN);
	}
	
	/**
	 * 获取DimenId
	 * @param mContext
	 * @param name
	 * @return
	 */
	public static int getDimenId(Context mContext,String name)
	{
		return getAllResourceId(mContext,name,DIMEN);
	}
	
	/**
	 * 获取AttrsId
	 * @param name
	 * @return
	 */
	public int getAttrId(String name)
	{
		return getAllResourceId(name,ATTR);
	}
	
	/**
	 * 获取AttrsId
	 * @param mContext 上下文对象
	 * @param name 属性名称
	 * @return
	 */
	public static int getAttrId(Context mContext,String name)
	{
		return getAllResourceId(mContext,name,ATTR);
	}
	
	/**
	 * 获取Bool型Id
	 * @param name
	 * @return
	 */
	public int getBoolId(String name)
	{
		return getAllResourceId(name,BOOL);
	}
	
	/**
	 * 获取Bool型Id
	 * @param mContext
	 * @param name
	 * @return
	 */
	public static int getBoolId(Context mContext,String name)
	{
		return getAllResourceId(mContext,name, BOOL);
	}
	
	/**
	 * 获取Integer类型ID
	 * @param name
	 * @return
	 */
	public int getIntegerId(String name)
	{
		return getAllResourceId(name,INTEGER);
	}
	
	/**
	 * 获取Integer类型ID
	 * @param mContext
	 * @param name
	 * @return
	 */
	public static int getIntegerId(Context mContext,String name)
	{
		return getAllResourceId(mContext,name,INTEGER);
	}
	
	/**
	 * 获取资源数组ID
	 * @param name
	 * @return
	 */
	public int[] getIdsByName(String name)
	{
	    try
	    {
	        //use reflection to access the resource class
	        Field[] fields2 = Class.forName(mContext.getPackageName() + ".R$styleable" ).getFields();

	        //browse all fields
	        for ( Field f : fields2 )
	        {
	            //pick matching field
	            if ( f.getName().equals( name ) )
	            {
	                //return as int array
	                int[] ret = (int[])f.get( null );
	                return ret;
	            }
	        }
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }

	    return null;
	}
	
	/**
	 * 获取资源数组ID
	 * @param name
	 * @return
	 */
	public static int[] getIdsByName(Context mContext,String name)
	{
	    try
	    {
	        //use reflection to access the resource class
	        Field[] fields2 = Class.forName(mContext.getPackageName() + ".R$styleable" ).getFields();

	        //browse all fields
	        for ( Field f : fields2 )
	        {
	            //pick matching field
	            if ( f.getName().equals( name ) )
	            {
	                //return as int array
	                int[] ret = (int[])f.get( null );
	                return ret;
	            }
	        }
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }

	    return null;
	}

	
}

package com.wenjackp.android.lib.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片格式转换工具类
 * @author Single
 * @category 图片格式转换工具类
 * @createTime 2014/2/19
 * @minSDK 2.3  api > 10
 * @version 1.2
 */
public final class ImageFormatUtils 
{
	
	private final static int QUALITY_FULL = 100;
	
	/**
	 * Bitmap 转 Byte[] <br><br>
	 * @param mBitmap 位图
	 * @param format 转换图片格式
	 * @param quality 质量 1-100
	 * @return
	 */
	public static byte[] bitmapToByteArray(Bitmap mBitmap,CompressFormat format,int quality)
	{
		if(mBitmap!=null)
		{	
			ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();	
			mBitmap.compress(format,QUALITY_FULL,mByteArrayOutputStream);
			return mByteArrayOutputStream.toByteArray();
		}
		return null;
	}
	
	/**
	 * Bitmap 转 Byte[] <br><br>
	 * <b> 默认保持原图 质量为100 </b>
	 * @param mBitmap 为徒
	 * @param format
	 * @return
	 */
	public static byte[] bitmapToByteArray(Bitmap mBitmap,CompressFormat format)
	{
		if(mBitmap!=null)
		{	
			ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();	
			mBitmap.compress(format,QUALITY_FULL,mByteArrayOutputStream);
			return mByteArrayOutputStream.toByteArray();
		}
		return null;
	}
	
	/**
	 * Bitmap 转 Byte[] <br><br>
	 * <b> 默认 JPEG 格式 质量为100 </b>
	 * @param mBitmap 位图
	 * @return
	 */
	public static byte[] bitmapToByteArray(Bitmap mBitmap)
	{
		if(mBitmap!=null)
		{	
			ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();	
			mBitmap.compress(CompressFormat.JPEG,QUALITY_FULL,mByteArrayOutputStream);
			return mByteArrayOutputStream.toByteArray();
		}
		return null;
	}
	
	/**
	 * byte[] 转  Bitmap
	 * @param mByte
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] mByte)
	{
		if(mByte!=null && mByte.length>0)
		{
		    return BitmapFactory.decodeByteArray(mByte,0,mByte.length);
		}
		return null;
	}
	
	/**
	 * Bitmap 转  Drawable
	 * @param mBitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap mBitmap)
	{
		BitmapDrawable mDrawable = new BitmapDrawable(mBitmap);
	    return mDrawable;	
	}
	
	/**
	 * Drawable 转 Bitmap
	 * @param mDrawable
	 * @return Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable mDrawable)
	{
		Bitmap mBitmap = null;
		
		if(mDrawable instanceof BitmapDrawable)
		{
			mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
		}
		else
		{
		    mBitmap = Bitmap.createBitmap(mDrawable.getIntrinsicWidth(),mDrawable.getIntrinsicHeight(),mDrawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
			Canvas mCanvas = new Canvas(mBitmap);
			mDrawable.setBounds(0,0,mDrawable.getIntrinsicWidth(),mDrawable.getIntrinsicHeight());
			mDrawable.draw(mCanvas);
		}
		
		return mBitmap;
	}

	/**
	 * Bitmap 转 Inputstream <br><br>
	 * <b> 默认 JPEG 格式 质量为100 </b>
	 * @param mBitmap
	 * @return
	 */
    public static InputStream bitmapToStream(Bitmap mBitmap)
    {
    	byte[] mBuffer = bitmapToByteArray(mBitmap);
    	return new ByteArrayInputStream(mBuffer);
    }
    
    /**
     * Drawable 转 InputStream
     * @param mDrawable
     * @return
     */
    public static InputStream drawableToStream(Drawable mDrawable)
    {
    	Bitmap mBitmap = drawableToBitmap(mDrawable);
    	return bitmapToStream(mBitmap);
    }

    /**
     * InputStream 转 Bitmap
     * @param mInputStream
     * @return
     */
    public static Bitmap inputStreamToBitmap(InputStream mInputStream)
    {
    	return BitmapFactory.decodeStream(mInputStream);
    }

    /**
     * InputStream 转  byte[]
     * @param mInputStream
     * @return
     * @throws Exception
     */
    public static byte[] inputStreamToByteArray(InputStream mInputStream) throws IOException
    {
    	ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
    	
    	byte[] tempBuffer = new byte[1024];
    	int tempLength = 0;
    	
    	while((tempLength = mInputStream.read(tempBuffer))!=-1)
    	{
    		mByteArrayOutputStream.write(tempBuffer,0,tempLength);
    	}
    	
    	return mByteArrayOutputStream.toByteArray();
    }
    
    /**
     * Byte[] 转  InputStream
     * @param mByte
     * @return
     */
    public static InputStream byteArrayToInputStream(byte[] mByte)
    {
    	if(mByte!=null && mByte.length>0)
    	{
    		return new ByteArrayInputStream(mByte);
    	}
    	
    	return null;
    }
}
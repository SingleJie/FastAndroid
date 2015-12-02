package com.wenjackp.android.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 系统图片裁剪
 * @author Single
 * @category 图片裁剪工具类
 * @version 1.2
 */
@Deprecated
public class ImageCropUtils {
	
	private String strFilePath = "/mnt/sdcard/BayouGame/CropImage/";
	private File file = null;
	private Context context = null;
	private static final String PHOTO_NAME_STYLE = "yyyyMMddHHmm_ss";
	public static Uri imageUri;
	private Activity activity;
	private final String TAG = ImageCropUtils.class.getSimpleName();
		
	public ImageCropUtils(Context context)
	{
		this.context = context;
		activity = (Activity) context;
		init();
	}
	
	/**初始化*/
	private void init()
	{
		if(checkSDCardIsExits())
		{
			//如果SD卡存在
			if(!EmptyUtils.emptyOfString(strFilePath))
			{
				//如果路径不为空
				file = new File(strFilePath);
				
				if(!file.exists())
				{
					file.mkdirs();
				}
			}
		}
		else
		{
			loadMsg("SD is Not Exists");
		}
	}
	
	/**
	 * 设置文件保存路径
	 * @param strfile
	 */
	public void setfile(String strFilePath)
	{
		this.strFilePath = strFilePath;
		
		if(strFilePath.indexOf("/mnt/sdcard/") == -1 )
		{
			if(strFilePath.indexOf("/") != -1 )
			{
				strFilePath = Environment.getExternalStorageDirectory() + strFilePath;
			}
			else
			{
				strFilePath = Environment.getExternalStorageDirectory() + "/" + strFilePath;
			}
		}
	}
	
	/**
	 * 检测SD卡是否存在
	 * @return true表示存在
	 */
	public boolean checkSDCardIsExits()
	{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			return true;
		}
		
		return false;
	}

	/**
	 * 使用系统当前日期加以调整作为照片的名称
	 */
	public static String createNewPhotoName()
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(PHOTO_NAME_STYLE);
		return dateFormat.format(date) + ".jpg";
	}

	/**
	 * 打开相机
	 * @param resultCode 回调码
	 */
	public void openCamera(int resultCode)
	{
		/*调用系统的拍照功能*/
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		/*指定调用相机拍照后照片的储存路径*/
		autoResetImageUri();
		/*设置输出路径*/
		intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
		/*跳转到相机*/
		activity.startActivityForResult(intent,resultCode);
	}
	
	/**重置图片URI*/
	public void autoResetImageUri()
	{
		imageUri =  Uri.parse(Uri.fromFile(file)+"/"+createNewPhotoName());
		loadMsg(imageUri+"");
	}

	/**
	 * 重置图片URI
	 * @param uri 指定的URI
	 */
	public void resetImageUri(Uri uri)
	{
		imageUri = uri;
		loadMsg(imageUri+"");
	}
	
	/**
	 * 打开相册
	 * @param resultCode 回调码
	 */
	public void openGallery(int resultCode)
	{
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		resetImageUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(imageUri,"image/*");
		activity.startActivityForResult(intent,resultCode);
	}
	
	/**
	 * 裁剪照相机图片,适用于裁剪后获得大图    通过getBitmapByUri获得对象
	 * @param requestCode 裁剪回调码
	 */
	public void cropBigPhotoByCamera(int requestCode)
	{
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(imageUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX",200);
		intent.putExtra("outputY",200);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
		intent.putExtra("return-data",false);
		intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection",true); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 裁剪照相机图片,适用于裁剪后获得小图    通过getBitmapByIntent获得对象
	 * @param requestCode 裁剪回调码
	 */
	public void cropSmallPhotoByCamera(int requestCode)
	{
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(imageUri,"image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX",200);
		intent.putExtra("outputY",200);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
		intent.putExtra("return-data",true);
		intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection",true); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 打开相册并裁剪,适用于裁剪后获得小图      通过getBitmapByIntent获得对象
	 * @param requestCode 裁剪回调
	 */
	public void openGalleryAndCropSmallPhoto(int requestCode)
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX",1);
		intent.putExtra("aspectY",1);
		intent.putExtra("outputX",200);
		intent.putExtra("outputY",200);
		intent.putExtra("scale",true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection",true); // no face detection
		activity.startActivityForResult(intent,requestCode);
	}
	
	/**
	 * 打开相册并裁剪,适用于裁剪后获得大图      通过getBitmapByUri获得对象
	 * @param requestCode 裁剪回调
	 */
	public void openGalleryAndCropBigPhoto(int requestCode)
	{
		autoResetImageUri();
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false); // no face detection
		activity.startActivityForResult(intent,requestCode);
	}
	
	/**
	 * 根据Uri得到Bitmap
	 * @return Bitmap
	 */
	public Bitmap getBitmapByUri()
	{
		Bitmap bitmap = null;
		try 
		{
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri));
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 根据Uri得到Bitmap
	 * @return Bitmap
	 */
	public Bitmap getBitmapByUri(Options opts)
	{
		Bitmap bitmap = null;
		try 
		{
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri),null,opts);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (OutOfMemoryError e) 
		{
			//内存溢出
		}
		return bitmap;
	}

	/**
	 * 通过数据返回获取Bitmap对象
	 * @param data
	 * @return
	 */
	public Bitmap getBitmapByIntent(Intent data) 
	{
		Bundle bundle = data.getExtras();
		
		if (bundle != null)
		{
			return bundle.getParcelable("data");
		}
		return null;
	}

	/**
	 * 根据Intent得到文件输入流
	 * @param data
	 * @return
	 */
	public InputStream getInPutStreamByIntent(Intent data) 
	{
		Bitmap bitmap = getBitmapByIntent(data);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
		
		byte[] b = stream.toByteArray(); // 将图片流以字符串形式存储下来
		return new ByteArrayInputStream(b);
	}
	
	/**
	 * 根据Bitmap得到byte[]
	 * @param bitmap
	 * @return
	 */
	public byte[] getByteArrayByBitmap(Bitmap bitmap)
	{	
		if(null == bitmap)
		{
			throw new NullPointerException("Bitmap is Empty");
		}
		else
		{
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
			return stream.toByteArray();
		}
	}
	
    /**
     * 根据Bitmap得到文件输入流
     * @param bitmap
     * @return
     */
	public InputStream getInPutStreamByBitmap(Bitmap bitmap) 
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG,90, stream);
		byte[] b = stream.toByteArray(); // 将图片流以字符串形式存储下来
		return new ByteArrayInputStream(b);
	}
	
	/**
	 * 根据Uri获取文件的绝对路径
	 * @param originalUri
	 * @return
	 */
	public String getAbsloutePath(Uri originalUri)
	{
		try
		{
          //这里开始的第二部分，获取图片的路径：
          String[] proj = {MediaStore.Images.Media.DATA};        
          Cursor cursor = ((Activity)context).managedQuery(originalUri, proj, null, null, null); 
          //按我个人理解 这个是获得用户选择的图片的索引值
          int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);           
          cursor.moveToFirst();
          //最后根据索引值获取图片路径
          String path = cursor.getString(column_index);
          System.out.println(path);
          //"文件路径：\n"+
          return path;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public class CameraBuilder
	{
		private int outputX;
		private int outputY;
		private int aspectX;
		private int aspectY;
		private boolean scale;
		private boolean noFaceDetection;
	}
	
	/**
	 * Log消息打印
	 * @param msg
	 */
	private void loadMsg(String msg)
	{
		Log.i(TAG+"--->",msg);
	}
	
}

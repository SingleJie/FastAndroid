package com.wenjackp.android.lib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 获取屏幕设备分辨率
 * @author Single
 * @category 获取屏幕分辨率参数工具类
 * @version 2.1
 */
public class ScreenUtils {
	
	/**设备宽度Px*/
	public static int widthPixels =0;
	
	/**设备高度Px*/
	public static int heightPixels = 0;

    /**状态栏的高度*/
    public static int statusBarPixels = 0;
	
	/**指每英寸中的像素数。如160dpi指手机水平或垂直方向上每英寸距离有160个像素点。假定设备分辨率为320*240，屏幕长2英寸宽1.5英寸，dpi=320/2=240/1.5=160*/
	public static float density = 0;
	
	/**屏幕密度表示为dots-per-inch.*/
	public static float densityDpi = 0;
	
	/**导航栏高度*/
	public static float navigationBarPixels = 0;
	
	/**确切的物理像素每英寸X屏幕的尺寸.*/
	public static float xdpi;
	
	/**确切的物理像素每英寸的屏幕在Y维度. */
	public static float ydpi;
	
	/**主要用于字体显示（best for textsize）。根据 google 的建议，TextView 的字号最好使用 sp 做单位，而且查看TextView的源码可知 Android 默认使用 sp 作为字号单位*/
	public static float scaledDensity;
	
	/**DisplayMetrics对象*/
	private static DisplayMetrics mDisplayMetrics;
		
	/**
	 * 初始化配置
	 * @param mContext 
	 */
	public static void initConfig(Context mContext)
	{
		mDisplayMetrics = new DisplayMetrics();
		mDisplayMetrics = mContext.getResources().getDisplayMetrics();
		widthPixels = mDisplayMetrics.widthPixels;
		heightPixels = mDisplayMetrics.heightPixels;
		density = mDisplayMetrics.density;
		densityDpi = mDisplayMetrics.densityDpi;
		scaledDensity = mDisplayMetrics.scaledDensity;
		xdpi = mDisplayMetrics.xdpi;
		ydpi = mDisplayMetrics.ydpi;

        navigationBarPixels = getNavigationBarHeight(mContext);
        statusBarPixels = getStatusBarHeight(mContext);
	}
	
	/**
	 * 得到DisplayMetrics对象
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics()
	{
		return mDisplayMetrics;
	}
	
	/**
	 * dp转px
	 * @param value
	 * @return px
	 */
    public static int dp2px(float value) 
    {
        return (int) (value * (mDisplayMetrics.densityDpi / 160) + 0.5f);
    }

    /**
     * px转dp
     * @param value the value
     * @return dp
     */
    public static int px2dp(float value) 
    {
        return (int) ((value * 160) / mDisplayMetrics.densityDpi + 0.5f);
    }

    /**
     * sp转px
     * @param value
     * @return px
     */
    public static int sp2px(float value) 
    {
        return (int) (mDisplayMetrics.scaledDensity + 0.5f);
    }

    /**
     * px转sp
     * @param value
     * @return sp
     */
    public static int px2sp(float value) 
    {
        return (int) (value / mDisplayMetrics.scaledDensity + 0.5f);
    }

    /**
     * 获取StatusBar高度
     * @param mContext
     * @return
     */
    private static int getStatusBarHeight (Context mContext){
        Resources resources = mContext.getResources();
        int result =  0 ;
        int resourceId = resources.getIdentifier("status_bar_height","dimen","android" );

        if  ( resourceId >  0 )  {
            result = resources. getDimensionPixelSize ( resourceId );
        }
        return result ;
    }

    /**
     * 获得NavigationBar高度
     * @param mContext
     * @return
     */
    private static int getNavigationBarHeight(Context mContext) {
        int result = 0;
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen","android");

        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
